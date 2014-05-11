package nc.notus.workflow;

import java.sql.Date;

import nc.notus.dao.ServiceOrderDAO;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.entity.ServiceInstance;
import nc.notus.entity.ServiceOrder;
import nc.notus.states.OrderStatus;

import java.util.Calendar;

import org.apache.log4j.Logger;

import nc.notus.dao.CableDAO;
import nc.notus.dao.CircuitDAO;
import nc.notus.dao.DeviceDAO;
import nc.notus.dao.OSSUserDAO;
import nc.notus.dao.PortDAO;
import nc.notus.dao.ServiceCatalogDAO;
import nc.notus.dao.ServiceInstanceDAO;
import nc.notus.dao.ServiceInstanceStatusDAO;
import nc.notus.dao.ServiceTypeDAO;
import nc.notus.dao.impl.CableDAOImpl;
import nc.notus.dao.impl.CircuitDAOImpl;
import nc.notus.dao.impl.DeviceDAOImpl;
import nc.notus.dao.impl.OSSUserDAOImpl;
import nc.notus.dao.impl.PortDAOImpl;
import nc.notus.dao.impl.ServiceCatalogDAOImpl;
import nc.notus.dao.impl.ServiceInstanceDAOImpl;
import nc.notus.dao.impl.ServiceInstanceStatusDAOImpl;
import nc.notus.dao.impl.ServiceTypeDAOImpl;
import nc.notus.email.BillEmail;
import nc.notus.email.Email;
import nc.notus.email.EmailSender;
import nc.notus.entity.Device;
import nc.notus.entity.Port;
import nc.notus.states.InstanceStatus;
import nc.notus.states.PortState;
import nc.notus.states.UserRole;
import nc.notus.states.WorkflowScenario;
import nc.notus.entity.Cable;
import nc.notus.entity.Circuit;
import nc.notus.entity.OSSUser;
import nc.notus.entity.ServiceCatalog;
import nc.notus.entity.ServiceType;

/**
 * This class provides functionality for "New" scenario workflow
 * @author Igor Litvinenko
 */
public class NewScenarioWorkflow extends Workflow {

    //private static Logger logger = Logger.getLogger(NewScenarioWorkflow.class.getName());
    /**
     * This method creates NewScenarioWorkflow for given Order. It doesn't
     * proceed Order to execution(See {@link Workflow#proceedOrder()})
     * @param order Order to create Workflow for
     * @param dbManager data base manager
     * @throws DBManagerException
     */
    public NewScenarioWorkflow(ServiceOrder order, DBManager dbManager)
            throws DBManagerException {
        super(order, dbManager);

        int scID = order.getScenarioID();
        if (scID != WorkflowScenario.NEW.toInt()) {
            throw new DBManagerException("Cannot proceed Order: wrong order scenario");
        }
    }

    /**
     * This method proceeds Order by creating tasks for
     * corresponding user groups which take part in Order execution.
     * Order should have status "Entering" and workflow scenario "New"
     * @throws DBManagerException
     */
    @Override
    public void proceedOrder() throws DBManagerException {
        try {
            if (!OrderStatus.ENTERING.toString().equals(getOrderStatus())) {
                throw new DBManagerException("Cannot proceed Order: wrong order state");
            }
            ServiceOrderDAO orderDAO = new ServiceOrderDAOImpl(dbManager);

            changeOrderStatus(OrderStatus.PROCESSING);
            ServiceInstance serviceInstance = createServiceInstance();

            // Link Order with SI
            order.setServiceInstanceID(serviceInstance.getId());
            orderDAO.update(order);

            /*
             * task for IE is created, whether or not we have free ports,
             * because physical link to customer is always absent for "new"
             * scenario, so we have to create it mannualy
             */
            createTask(UserRole.INSTALLATION_ENGINEER, "Proceed new order");

            // dbManager.commit();
        } catch (DBManagerException ex) {
            // logger.error("Error while proceed the order!", ex);
            dbManager.rollback();
            throw new DBManagerException("Error was occured, contact an administrator!");
        }
    }

    private ServiceInstance createServiceInstance() throws DBManagerException {
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
     * @throws DBManagerException
     */
    public void createRouter(int taskID, int portQuantity) throws DBManagerException {
        try {
            if (!isTaskValid(taskID, UserRole.INSTALLATION_ENGINEER.toInt())) {
                throw new DBManagerException("Given Task is not valid");
            }

            DeviceDAO deviceDAO = new DeviceDAOImpl(dbManager);
            PortDAO portDAO = new PortDAOImpl(dbManager);

            if (portDAO.getFreePort() != null) {
                throw new DBManagerException("Router creation is not allowed: " +
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

            //dbManager.commit();
        } catch (DBManagerException ex) {
            // logger.error("Error while proceed the order!", ex);
            dbManager.rollback();
            throw new DBManagerException("Error was occured, contact an administrator!");
        }
    }

    /**
     * This method creates Cable entity
     * @param taskID ID of task for installation engineer
     * @param cableType type of cable
     * @throws DBManagerException
     */
    public void createCable(int taskID, String cableType) throws DBManagerException {
        try {
            if (!isTaskValid(taskID, UserRole.INSTALLATION_ENGINEER.toInt())) {
                throw new DBManagerException("Given Task is not valid");
            }
            CableDAO cableDAO = new CableDAOImpl(dbManager);
            Cable cable = new Cable();
            cable.setCable(cableType);
            cableDAO.add(cable);

            //dbManager.commit();
        } catch (DBManagerException ex) {
            // logger.error("Error while proceed the order!", ex);
            dbManager.rollback();
            throw new DBManagerException("Error was occured, contact an administrator!");
        }
    }

    /**
     * This method plugs Cable to specified free Port.
     * It changes status of Task to "Completed" after execution
     * After it's done method automatically creates task for Provisioning Engineer
     * @param taskID taskID ID of task for installation engineer
     * @param cableID ID of Cable to plug
     * @param portID ID of Port to plug Cable to
     * @throws DBManagerException
     */
    public void plugCableToPort(int taskID, int cableID, int portID) throws DBManagerException {
        try {
            if (!isTaskValid(taskID, UserRole.INSTALLATION_ENGINEER.toInt())) {
                throw new DBManagerException("Given Task is not valid");
            }
            PortDAO portDAO = new PortDAOImpl(dbManager);
            Port port = portDAO.find(portID);
            if (port.getPortStatus() == PortState.BUSY.toInt()) {
                throw new DBManagerException("Port is busy");
            }
            port.setCableID(cableID);
            port.setPortStatus(PortState.BUSY.toInt());
            portDAO.update(port);

            ServiceInstanceDAO siDAO = new ServiceInstanceDAOImpl(dbManager);
            ServiceInstance si = siDAO.find(order.getServiceInstanceID());
            si.setPortID(portID);
            siDAO.update(si);

            this.completeTask(taskID);
            this.createTask(UserRole.PROVISION_ENGINEER, "Create circuit");
            //dbManager.commit();
        } catch (DBManagerException ex) {
            // logger.error("Error while proceed the order!", ex);
            dbManager.rollback();
            throw new DBManagerException("Error was occured, contact an administrator!");
        }
    }

    /**
     * Creates new Circuit Instance
     * @param taskID id of task
     * @param circuitConfig configuration of Circuit
     * @throws DBManagerException 
     */
    public void createCircuit(int taskID, String circuitConfig) throws DBManagerException {
        try {
            if (!isTaskValid(taskID, UserRole.PROVISION_ENGINEER.toInt())) {
                throw new DBManagerException("Given Task is not valid");
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

            this.completeTask(taskID);
            this.createTask(UserRole.SUPPORT_ENGINEER, "Approve bill");
            // dbManager.commit();
        } catch (DBManagerException ex) {
            // logger.error("Error while proceed the order!", ex);
            dbManager.rollback();
            throw new DBManagerException("Error was occured, contact an administrator!");
        }
    }

    /**
     * This method approves and sends Bill to customer and automatically activates SI
     * by changing it's status to "Active". It also changes Order status to
     * "Completed"
     * @param taskID ID of Task for Support Engineer
     * @throws DBManagerException
     */
    public void approveBill(int taskID) throws DBManagerException {
        try {
            if (!isTaskValid(taskID, UserRole.SUPPORT_ENGINEER.toInt())) {
                throw new DBManagerException("Given Task is not valid");
            }

            completeTask(taskID);

            updateServiceInstanceDate(order.getServiceInstanceID());
            changeServiceInstanceStatus(InstanceStatus.ACTIVE);
            changeOrderStatus(OrderStatus.COMPLETED);

            /* Sending bill email */
            EmailSender emailSender = new EmailSender();
            OSSUserDAO userDAO = new OSSUserDAOImpl(dbManager);
            ServiceCatalogDAO catalogDAO = new ServiceCatalogDAOImpl(dbManager);
            ServiceTypeDAO serviceTypeDAO = new ServiceTypeDAOImpl(dbManager);

            OSSUser user = userDAO.find(order.getUserID());
            ServiceCatalog catalog = catalogDAO.find(order.getServiceCatalogID());
            ServiceType serviceType = serviceTypeDAO.find(catalog.getServiceTypeID());
            Email mail = new BillEmail(user.getFirstName(),
                    serviceType.getService(), String.valueOf(catalog.getPrice()));
            emailSender.sendEmail(order.getUserID(), mail);
            // dbManager.commit();
        } catch (DBManagerException ex) {
            // logger.error("Error while proceed the order!", ex);
            dbManager.rollback();
            throw new DBManagerException("Error was occured, contact an administrator!");
        }
    }

    /**
     * Sets SI creation date with current date
     * @param serviceInstanceID ID of SI
     */
    private void updateServiceInstanceDate(int serviceInstanceID) throws DBManagerException {
        ServiceInstanceDAO siDAO = new ServiceInstanceDAOImpl(dbManager);
        Calendar cal = java.util.Calendar.getInstance();
        Date date = new Date(cal.getTimeInMillis());
        ServiceInstance si = siDAO.find(serviceInstanceID);
        si.setServiceInstanceDate(date);
        siDAO.update(si);
    }
}
