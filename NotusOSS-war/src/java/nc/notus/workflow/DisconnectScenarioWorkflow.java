package nc.notus.workflow;

import java.sql.Date;
import java.util.Calendar;
import nc.notus.dao.CableDAO;
import nc.notus.dao.CircuitDAO;
import nc.notus.dao.OSSUserDAO;
import nc.notus.dao.PortDAO;
import nc.notus.dao.ServiceInstanceDAO;
import nc.notus.dao.impl.CableDAOImpl;
import nc.notus.dao.impl.CircuitDAOImpl;
import nc.notus.dao.impl.OSSUserDAOImpl;
import nc.notus.dao.impl.PortDAOImpl;
import nc.notus.dao.impl.ServiceInstanceDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.email.DisconnectSuccessfulEmail;
import nc.notus.email.Email;
import nc.notus.email.EmailSender;
import nc.notus.entity.OSSUser;
import nc.notus.entity.Port;
import nc.notus.entity.ServiceInstance;
import nc.notus.entity.ServiceOrder;
import nc.notus.states.InstanceStatus;
import nc.notus.states.OrderStatus;
import nc.notus.states.PortState;
import nc.notus.states.UserRole;
import nc.notus.states.WorkflowScenario;

/**
 * This class provides functionality for "Disconnect" scenario workflow
 * @author Igor Litvinenko & Panchenko Dmytro
 */
public class DisconnectScenarioWorkflow extends Workflow {

    //private static Logger logger = Logger.getLogger(DisconnectScenarioWorkflow.class.getName());
    /**
     * This method creates DisconnectScenarioWorkflow for given Order. It
     * doesn't proceed Order to execution(See {@link Workflow#proceedOrder()})
     * @param order Order to create Workflow for
     * @param dbManager data base manager
     * @throws DBManagerException
     */
    public DisconnectScenarioWorkflow(ServiceOrder order, DBManager dbManager) throws DBManagerException {
        super(order, dbManager);
        if (!WorkflowScenario.DISCONNECT.toString().equals(getOrderScenario())) {
            throw new DBManagerException("Cannot proceed Order: " + "wrong order scenario");
        }
    }

    /**
     * This method proceeds Order by creating tasks for corresponding user
     * groups which take part in Order execution. Order should have status
     * "Entering" and workflow scenario "Disconnect"
     * @throws DBManagerException
     */
    @Override
    public void proceedOrder() throws DBManagerException {
        try {
            if (!OrderStatus.ENTERING.toString().equals(getOrderStatus())) {
                throw new DBManagerException("Cannot proceed Order: " + "wrong order state");
            }

            ServiceInstanceDAO siDAO = new ServiceInstanceDAOImpl(dbManager);
            ServiceInstance si = siDAO.find(order.getServiceInstanceID());

            si.setServiceInstanceStatusID(InstanceStatus.PENDING_TO_DISCONNECT.toInt());
            siDAO.update(si);

            changeOrderStatus(OrderStatus.PROCESSING);
            createTask(UserRole.PROVISIONING_ENGINEER, "Remove circuit from SI");
        } catch (DBManagerException ex) {
            // logger.error("Error while proceed the order!", ex);
            dbManager.rollback();
            throw new DBManagerException("Error was occured, contact to administrator!");
        }
    }

    /**
     * This method unassigns given Port from given Service Instance.
     * Then method unplugs Cable from specified Port. Then delete cable.
     * And it sets Port status to
     * "Free" and changes status of Task to "Completed" after execution.
     * @param taskID taskID ID of task for installation engineer
     * @param cableID ID of Cable to unplug
     * @param portID ID of Port to unplug Cable from
     * @param serviceInstanceID id of Service Instance
     * @throws DBManagerException
     */
    public void unplugCableFromPort(int taskID, int cableID, int portID,
            int serviceInstanceID) throws DBManagerException {
        try {
            if (!isTaskValid(taskID, UserRole.INSTALLATION_ENGINEER.toInt())) {
                throw new DBManagerException("Given Task is not valid");
            }
            PortDAO portDAO = new PortDAOImpl(dbManager);
            Port port = portDAO.find(portID);
            port.setCableID(null);
            port.setPortStatus(PortState.FREE.toInt());
            portDAO.update(port);

            CableDAO cableDAO = new CableDAOImpl(dbManager);
            cableDAO.delete(cableID);

            ServiceInstanceDAO siDAO = new ServiceInstanceDAOImpl(dbManager);

            ServiceInstance si = siDAO.find(serviceInstanceID);
            si.setPortID(null);
            Calendar cal = java.util.Calendar.getInstance();
            Date date = new Date(cal.getTimeInMillis());
            si.setServiceInstanceDate(date);
            siDAO.update(si);

            completeTask(taskID);
            changeOrderStatus(OrderStatus.COMPLETED);
            // dbManager.commit();
        } catch (DBManagerException ex) {
            // logger.error("Error while proceed the order!", ex);
            dbManager.rollback();
            throw new DBManagerException("Error was occured, contact to administrator!");
        }
    }

    /**
     * This method removes Circuit from given Service Instance.
     * Then method changes status of Service Instance to "Disconnected".
     * Then sends disconnect e-mail.
     * @param taskID taskID ID of task for provisioning engineer
     * @throws DBManagerException
     */
    public void removeCurcuitFromSI(int taskID) throws DBManagerException {
        try {
            if (!isTaskValid(taskID, UserRole.PROVISIONING_ENGINEER.toInt())) {
                throw new DBManagerException("Given Task is not valid");
            }
            ServiceInstanceDAOImpl siDAO = new ServiceInstanceDAOImpl(dbManager);
            CircuitDAO ciDAO = new CircuitDAOImpl(dbManager);

            ServiceInstance si = siDAO.find(order.getServiceInstanceID());

            int circuitID = si.getCircuitID();
            int portID = si.getPortID();
            OSSUserDAO userDAO = new OSSUserDAOImpl(dbManager);
            OSSUser user = userDAO.find(order.getUserID());
            si.setCircuitID(null);
            siDAO.update(si);

            ciDAO.delete(circuitID);

            changeServiceInstanceStatus(InstanceStatus.DISCONNECTED);
            completeTask(taskID);
            createTask(UserRole.INSTALLATION_ENGINEER, "Remove port and cable from SI");
            // dbManager.commit();

            Email disconnectMail = new DisconnectSuccessfulEmail(
                    user.getFirstName(), portID, order.getServiceLocation());
            EmailSender emailSender = new EmailSender();
            emailSender.sendEmail(user.getId(), disconnectMail);

        } catch (DBManagerException ex) {
            // logger.error("Error while proceed the order!", ex);
            dbManager.rollback();
            throw new DBManagerException("Error was occured, contact to administrator!");
        }
    }
}
