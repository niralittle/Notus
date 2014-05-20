package nc.notus.controllers;

import java.sql.Date;
import java.util.Calendar;

import org.apache.log4j.Logger;

import nc.notus.dao.OSSUserDAO;
import nc.notus.dao.ScenarioDAO;
import nc.notus.dao.ServiceOrderDAO;
import nc.notus.dao.ServiceOrderStatusDAO;
import nc.notus.dao.impl.OSSUserDAOImpl;
import nc.notus.dao.impl.ScenarioDAOImpl;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dao.impl.ServiceOrderStatusDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.email.Email;
import nc.notus.email.EmailSender;
import nc.notus.email.RegistrationSuccessfulEmail;
import nc.notus.entity.OSSUser;
import nc.notus.entity.ServiceOrder;
import nc.notus.states.OrderStatus;
import nc.notus.states.UserRole;
import nc.notus.states.UserState;
import nc.notus.states.WorkflowScenario;
import nc.notus.workflow.DisconnectScenarioWorkflow;
import nc.notus.workflow.NewScenarioWorkflow;
import nc.notus.workflow.Workflow;

/**
 *
 * @author Dima
 */
public class CustomerUserController extends AbstractController {

    private static Logger logger = Logger.getLogger(DBManager.class.getName());

    /**
     *
     * @param dbManager
     */
    public CustomerUserController(DBManager dbManager) {
        super(dbManager);
    }

    /**
     *
     */
    public CustomerUserController() {
        super();
    }

    /**
     * Proceed order to disconnect.
     * [Implementation note: Used in external transaction ]
     * @param serviceInstanceID
     * @throws DBManagerException
     */
	public void proceedToDisconnect(int userId, int serviceInstanceID)
			throws DBManagerException {
		ServiceOrder serviceOrder = null;
		ServiceOrderDAOImpl soDAO = null;
		DisconnectScenarioWorkflow disconnectWF = null;
		int userIdForDisconnect;
		try {
			if (isInternal) {
				dbManager = new DBManager();
			}
			// get order by SI
			soDAO = new ServiceOrderDAOImpl(dbManager);
			serviceOrder = soDAO.getServiceOrderBySIId(serviceInstanceID);
			serviceOrder.setScenarioID(WorkflowScenario.DISCONNECT.toInt());
			serviceOrder.setServiceOrderStatusID(OrderStatus.ENTERING.toInt());

			userIdForDisconnect = serviceOrder.getUserID();
			if (userIdForDisconnect == userId) {
				disconnectWF = new DisconnectScenarioWorkflow(serviceOrder, dbManager);
				disconnectWF.proceedOrder();

				if (isInternal) {
					dbManager.commit();
				}
			} else {
				throw new DBManagerException("You can proceed to "
						+ "disconnect only your own service");
			}

		} catch (DBManagerException wfExc) {
			if (isInternal) {
				dbManager.rollback();
			}
			throw new DBManagerException("Error while proceed to disconnect",
					wfExc);
		} finally {
			if (isInternal) {
				dbManager.close();
			}
		}
	}

    /**
     * Register in system after selecting service and creating new order.
     * @param login
     * @param password
     * @param email
     * @param firstName
     * @param lastName
     * @param catalogID 
     * @param serviceLocation
     * @return 
     * @throws DBManagerException
     */
    public int register(String login, String password, String email,
            String firstName, String lastName, int catalogID,
            String serviceLocation) throws DBManagerException {
        OSSUserDAO userDAO = null;
        int userID;
        try {
            if (isInternal) {
                dbManager = new DBManager();
            }
            userDAO = new OSSUserDAOImpl(dbManager);
            // check inputted login
            if (userDAO.isExist(login)) {
                throw new DBManagerException("User with specified login - " + login + " already exist. Choose another.");
            }
            // check inputted email
            if (userDAO.isEmailDuplicate(email)) {
                throw new DBManagerException("User with specified email - " + email + " already exist. Input another email. ");
            }
            // create new user if unique login and email
            OSSUser user = new OSSUser();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setLogin(login);
            user.setPassword(password);
            user.setBlocked(UserState.ACTIVE.toInt());
            user.setRoleID(UserRole.CUSTOMER_USER.toInt());
            userID = (Integer) userDAO.add(user);
            proceedNewOrder(userID, catalogID, serviceLocation);
            if (isInternal) {
                dbManager.commit();
            }
            sendEmail(userID, firstName, login, password);
        } catch (DBManagerException exc) {
            if (isInternal) {
                dbManager.rollback();
            }
            throw new DBManagerException(exc.getMessage());
        } finally {
            if (isInternal) {
                dbManager.close();
            }
        }
        return userID;
    }

    private void sendEmail(int userID, String firstName, String login, String password) {
        Email notificationEmail =
                new RegistrationSuccessfulEmail(firstName, login, password);
        EmailSender emailSender = new EmailSender();
        emailSender.sendEmail(userID, notificationEmail);
    }

    private void proceedNewOrder(int userID, int catalogID,
            String serviceLocation) throws DBManagerException {
        ServiceOrder newOrder = createOrder(userID, catalogID,
                serviceLocation);
        Workflow wf = new NewScenarioWorkflow(newOrder, dbManager);
        wf.proceedOrder();
    }

    private ServiceOrder createOrder(int userID,
            int catalogID, String serviceLocation) throws DBManagerException {
        ServiceOrderStatusDAO statusDAO = null;
        ScenarioDAO scenarioDAO = null;
        ServiceOrderDAO orderDAO = null;
        ServiceOrder so = null;

        statusDAO = new ServiceOrderStatusDAOImpl(dbManager);
        scenarioDAO = new ScenarioDAOImpl(dbManager);
        orderDAO = new ServiceOrderDAOImpl(dbManager);

        // create new order with status ENTERING
        so = new ServiceOrder();
        int orderStatusID = statusDAO.getServiceOrderStatusID(OrderStatus.ENTERING);
        so.setServiceOrderStatusID(orderStatusID);
        so.setScenarioID(scenarioDAO.getScenarioID(WorkflowScenario.NEW));
        so.setServiceCatalogID(catalogID);
        so.setServiceInstanceID(null);
        so.setServiceLocation(serviceLocation);
        so.setUserID(userID);

        Calendar cal = java.util.Calendar.getInstance();
        Date date = new Date(cal.getTimeInMillis());
        so.setServiceOrderDate(date);

        int orderID = (Integer) orderDAO.add(so);
        so.setId(orderID);
        return so;
    }
}

