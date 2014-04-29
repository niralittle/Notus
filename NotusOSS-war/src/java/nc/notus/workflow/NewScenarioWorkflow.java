package nc.notus.workflow;

import java.sql.Date;
import nc.notus.dao.ServiceOrderDAO;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.ServiceInstance;
import nc.notus.entity.ServiceOrder;
import nc.notus.states.OrderStatus;
import java.util.Calendar;
import nc.notus.dao.CableDAO;
import nc.notus.dao.CircuitDAO;
import nc.notus.dao.DeviceDAO;
import nc.notus.dao.PortDAO;
import nc.notus.dao.ServiceInstanceDAO;
import nc.notus.dao.ServiceInstanceStatusDAO;
import nc.notus.dao.impl.CableDAOImpl;
import nc.notus.dao.impl.CircuitDAOImpl;
import nc.notus.dao.impl.DeviceDAOImpl;
import nc.notus.dao.impl.PortDAOImpl;
import nc.notus.dao.impl.ServiceInstanceDAOImpl;
import nc.notus.dao.impl.ServiceInstanceStatusDAOImpl;
import nc.notus.entity.Device;
import nc.notus.entity.Port;
import nc.notus.states.InstanceStatus;
import nc.notus.states.PortState;
import nc.notus.states.UserRole;
import nc.notus.states.WorkflowScenario;
import nc.notus.entity.Cable;
import nc.notus.entity.Circuit;

/**
 * This class provides functionality for "New" scenario workflow
 * @author Igor Litvinenko
 */
public class NewScenarioWorkflow extends Workflow {

    /**
     * This method creates NewScenarioWorkflow for given Order.
     * It doesn't proceed Order to execution(See {@link Workflow#proceedOrder()})
     * @param order Order to create Workflow for
     * @throws Workflow exception if Order scenario doesn't match "New" scenario
     * workflow
     */
    public NewScenarioWorkflow(ServiceOrder order) {
        super(order);
        DBManager dbManager = new DBManager();
        try {
            if (!getOrderScenario(dbManager).equals(WorkflowScenario.NEW.toString())) {
                throw new WorkflowException("Cannot proceed Order: wrong order scenario");
            }
        } finally {
            dbManager.close();
        }
    }

    /**
     * This method proceeds Order by creating tasks for
     * corresponding user groups which take part in Order execution.
     * Order should have status "Entering" and workflow scenario "New"
     */
    @Override
    public void proceedOrder() {
        DBManager dbManager = new DBManager();
        try {
            if (!getOrderStatus(dbManager).equals(OrderStatus.ENTERING.toString())) {
                throw new WorkflowException("Cannot proceed Order: wrong order state");
            }
            ServiceOrderDAO orderDAO = new ServiceOrderDAOImpl(dbManager);

            changeOrderStatus(dbManager, OrderStatus.PROCESSING);
            ServiceInstance serviceInstance = createServiceInstance(dbManager);

            // Link Order with SI
            order.setServiceInstanceID(serviceInstance.getId());
            orderDAO.update(order);

            /*
             * task for IE is created, whether or not we have free ports,
             * because physical link to customer is always absent for "new"
             * scenario, so we have to create it mannualy
             */
            createTask(dbManager, UserRole.INSTALLATION_ENGINEER, "Proceed new order");

            dbManager.commit();
        } finally {
            dbManager.close();
        }
    }

    private ServiceInstance createServiceInstance(DBManager dbManager) {
        ServiceInstanceDAO siDAO = new ServiceInstanceDAOImpl(dbManager);
        ServiceInstanceStatusDAO sisDAO = new ServiceInstanceStatusDAOImpl(dbManager);

        ServiceInstance serviceInstance = new ServiceInstance();
        serviceInstance.setCircuitID(null);
        serviceInstance.setPortID(null);
        serviceInstance.setServiceInstanceDate(null); // date is not set because SI wasn't activated yet
        int statusID = sisDAO.getServiceInstanceStatusID(InstanceStatus.PLANNED);
        serviceInstance.setServiceInstanceStatusID(statusID);

        Object id = siDAO.add(serviceInstance);
        serviceInstance.setId((Integer) id);

        return serviceInstance;
    }

    /**
     * This method creates new Router in system. It also creates Ports
     * and links them with Router.
     * @param portQuantity amount of Ports that Router accommodates
     * @param taskID ID of task for installation engineer                  
     */
    public void createRouter(int taskID, int portQuantity) {
        DBManager dbManager = new DBManager();
        try {
            if (!isTaskValid(dbManager, taskID,
                    UserRole.INSTALLATION_ENGINEER.toInt())) {
                throw new WorkflowException("Given Task is not valid");
            }

            DeviceDAO deviceDAO = new DeviceDAOImpl(dbManager);
            PortDAO portDAO = new PortDAOImpl(dbManager);

            if (portDAO.getFreePort() != null) {
                throw new WorkflowException("Router creation is not allowed: " +
                        "free ports available");
            }

            Device device = new Device();
            device.setName("Cisco 7606");
            device.setPortQuantity(portQuantity);
            Integer deviceID = (Integer) deviceDAO.add(device);

            for (int portNumber = 1; portNumber <= portQuantity; portNumber++) {
                Port port = new Port();
                port.setCableID(null); // no cable so far
                port.setDeviceID(deviceID);
                port.setPortNumber(portNumber);
                port.setPortStatus(PortState.FREE.toInt());
                portDAO.add(port);
            }

            dbManager.commit();
        } finally {
            dbManager.close();
        }
    }

    /**
     * This method creates Cable entity
     * @param taskID ID of task for installation engineer
     */
    public void createCable(int taskID, String cableType) {
        DBManager dbManager = new DBManager();
        try {
            if (!isTaskValid(dbManager, taskID,
                    UserRole.INSTALLATION_ENGINEER.toInt())) {
                throw new WorkflowException("Given Task is not valid");
            }

            CableDAO cableDAO = new CableDAOImpl(dbManager);

            Cable cable = new Cable();
            cable.setCable(cableType);
            cableDAO.add(cable);

            dbManager.commit();
        } finally {
            dbManager.close();
        }
    }

    /**
     * This method plugs Cable to specified free Port.
     * It changes status of Task to "Completed" after execution
     * After it's done method automatically creates task for Provisioning Engineer
     * @param taskID taskID ID of task for installation engineer
     * @param cableID ID of Cable to plug
     * @param portID ID of Port to plug Cable to
     */
    public void plugCableToPort(int taskID, int cableID, int portID) {
        DBManager dbManager = new DBManager();
        try {
            if (!isTaskValid(dbManager, taskID,
                    UserRole.INSTALLATION_ENGINEER.toInt())) {
                throw new WorkflowException("Given Task is not valid");
            }
            PortDAO portDAO = new PortDAOImpl(dbManager);

            Port port = portDAO.find(portID);
            if (port.getPortStatus() == PortState.BUSY.toInt()) {
                throw new WorkflowException("Port is busy");
            }
            port.setCableID(cableID);
            port.setPortStatus(PortState.BUSY.toInt());
            portDAO.update(port);

            ServiceInstanceDAO siDAO = new ServiceInstanceDAOImpl(dbManager);
            ServiceInstance si = siDAO.find(order.getServiceInstanceID());
            si.setPortID(portID);
            siDAO.update(si);

            this.completeTask(dbManager, taskID);
            this.createTask(dbManager, UserRole.PROVISION_ENGINEER, "Create curcuit");
            dbManager.commit();
        } finally {
            dbManager.close();
        }
    }

    /**
     * Creates new Circuit Instance
     * @param dbManager class representing connection to DB                     // TODO: update documentation
     * @return ID of created Circuit instance
     */
    public void createCircuit(int taskID, String circuitConfig) {
        DBManager dbManager = new DBManager();
        try {
            if (!isTaskValid(dbManager, taskID,
                    UserRole.PROVISION_ENGINEER.toInt())) {
                throw new WorkflowException("Given Task is not valid");
            }
            CircuitDAO circuitDAO = new CircuitDAOImpl(dbManager);
            ServiceInstanceDAO siDAO = new ServiceInstanceDAOImpl(dbManager);

            Circuit circuit = new Circuit();
            circuit.setCircuit(circuitConfig);
            int circuitID = (Integer) circuitDAO.add(circuit);

            // link Circuit with SI
            ServiceInstance si = siDAO.find(order.getServiceInstanceID());
            si.setCircuitID(circuitID);
            siDAO.update(si);

            this.completeTask(dbManager, taskID);
            this.createTask(dbManager, UserRole.SUPPORT_ENGINEER, "Approve bill");
            dbManager.commit();
        } finally {
            dbManager.close();
        }
    }

    /**
     * This method approves and sends Bill to customer and automatically activates SI
     * by changing it's status to "Active". It also changes Order status to
     * "Completed"
     * @param taskID ID of Task for Support Engineer
     */
    public void approveBill(int taskID) {
        DBManager dbManager = new DBManager();
        try {
            if (!isTaskValid(dbManager, taskID, UserRole.SUPPORT_ENGINEER.toInt())) {
                throw new WorkflowException("Given Task is not valid");
            }

            completeTask(dbManager, taskID);

            updateServiceInstanceDate(dbManager, order.getServiceInstanceID());
            changeServiceInstanceStatus(dbManager, InstanceStatus.ACTIVE);
            changeOrderStatus(dbManager, OrderStatus.COMPLETED);
            // TODO: send email here
            dbManager.commit();
        } finally {
            dbManager.close();
        }
    }

    /**
     * Sets SI creation date with current date
     * @param dbManager class that represents connection to DB
     * @param serviceInstanceID ID of SI
     */
    private void updateServiceInstanceDate(DBManager dbManager, int serviceInstanceID) {
        ServiceInstanceDAO siDAO = new ServiceInstanceDAOImpl(dbManager);
        Calendar cal = java.util.Calendar.getInstance();
        Date date = new Date(cal.getTimeInMillis());
        ServiceInstance si = siDAO.find(serviceInstanceID);
        si.setServiceInstanceDate(date);
        siDAO.update(si);
    }
}
