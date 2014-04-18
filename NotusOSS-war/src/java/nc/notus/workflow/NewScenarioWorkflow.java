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
import nc.notus.dao.DeviceDAO;
import nc.notus.dao.PortDAO;
import nc.notus.dao.ServiceInstanceDAO;
import nc.notus.dao.ServiceInstanceStatusDAO;
import nc.notus.dao.TaskDAO;
import nc.notus.dao.impl.CableDAOImpl;
import nc.notus.dao.impl.DeviceDAOImpl;
import nc.notus.dao.impl.PortDAOImpl;
import nc.notus.dao.impl.ServiceInstanceDAOImpl;
import nc.notus.dao.impl.ServiceInstanceStatusDAOImpl;
import nc.notus.dao.impl.TaskDAOImpl;
import nc.notus.entity.Device;
import nc.notus.entity.Port;
import nc.notus.states.InstanceStatus;
import nc.notus.states.PortState;
import nc.notus.states.UserRole;
import nc.notus.states.WorkflowScenario;
import nc.notus.entity.Cable;
import nc.notus.entity.Task;

/**
 * This class provides functionality for "New" scenarion workflow
 * @author Igor Litvinenko
 */
public class NewScenarioWorkflow extends Workflow {

    /**
     * This method creates NewScenarioWorkflow for given Order.
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
            PortDAO portDAO = new PortDAOImpl(dbManager);

            changeOrderStatus(dbManager, OrderStatus.PROCESSING);
            ServiceInstance serviceInstance = createServiceInstance(dbManager);

            // Link Order with SI
            order.setServiceInstanceID(serviceInstance.getId());
            orderDAO.update(order);

            // Find free port
            Port port = portDAO.getFreePort();
            if (port == null) {
                createTask(dbManager, UserRole.INSTALLATION_ENGINEER);
            } else {
                createTask(dbManager, UserRole.PROVISION_ENGINEER);
            }

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
        Calendar cal = java.util.Calendar.getInstance();
        Date date = new Date(cal.getTimeInMillis());
        serviceInstance.setServiceInstanceDate(date);
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
     * @param taskID ID of task for installation engineer                       // TODO: check if task ID is really necessary
     */
    public void createRouter(int taskID, int portQuantity) {                    // TODO: add task check
        DBManager dbManager = new DBManager();
        try {
            DeviceDAO deviceDAO = new DeviceDAOImpl(dbManager);
            PortDAO portDAO = new PortDAOImpl(dbManager);

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
     * This method creates Cable entity and writes it to
     * @param taskID ID of task for installation engineer                       // TODO: check if task ID is really necessary
     */
    public void createCable(int taskID) {                                       // TODO: add task check
        DBManager dbManager = new DBManager();
        try {
            CableDAO cableDAO = new CableDAOImpl(dbManager);

            Cable cable = new Cable();
            cable.setCable("UTP cable");
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
    public void plugCableToPort(int taskID, int cableID, int portID) {          // TODO: add task check
        DBManager dbManager = new DBManager();
        try {
            PortDAO portDAO = new PortDAOImpl(dbManager);
            Port port = portDAO.find(portID);
            if(port.getPortStatus() == PortState.BUSY.toInt()) {
                throw new WorkflowException("Port is busy");
            }
            port.setCableID(cableID);
            portDAO.update(port);
            
            this.completeTask(dbManager, taskID);
            this.createTask(dbManager, UserRole.PROVISION_ENGINEER);
            dbManager.commit();
        } finally {
            dbManager.close();
        }
    }
}
