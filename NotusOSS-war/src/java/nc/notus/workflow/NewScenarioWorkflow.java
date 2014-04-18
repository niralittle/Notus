package nc.notus.workflow;

import java.sql.Date;
import nc.notus.dao.ServiceOrderDAO;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.ServiceInstance;
import nc.notus.entity.ServiceOrder;
import nc.notus.states.OrderStatus;
import java.util.Calendar;
import nc.notus.dao.DeviceDAO;
import nc.notus.dao.PortDAO;
import nc.notus.dao.ServiceInstanceDAO;
import nc.notus.dao.ServiceInstanceStatusDAO;
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
        if (!getOrderScenario(dbManager).equals(WorkflowScenario.NEW.toString())) {
            throw new WorkflowException("Cannot proceed Order: wrong order scenario");
        }
        dbManager.close();
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
     */
    public void createRouter(int portQuantity) {                                // TODO: add task param
        DBManager dbManager = new DBManager();
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
        dbManager.close();
    }

    public void createCable() {
    }

    public void plugCableToPort() {
    }
}
