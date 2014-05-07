package nc.notus.controllers;

import java.io.IOException;
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

public class CustomerUserController {
	
	private static Logger logger = Logger.getLogger(DBManager.class.getName());
	
	private DBManager dbManager;
	
	public CustomerUserController(DBManager dbManager) {
		this.dbManager = dbManager;
	}
	
	public CustomerUserController() {
		
	}
	
	/**
	 * Proceed order to disconnect.
	 * [Implementation note: Used in external transaction ]
	 * 
	 * @param serviceInstanceID
	 * @throws DBManagerException
	 */
	public void proceedToDisconnect(int serviceInstanceID) throws DBManagerException {

		ServiceOrder serviceOrder = null;
		ServiceOrderDAOImpl soDAO = null;
		boolean isInternal = false;
	

		DisconnectScenarioWorkflow disconnectWF = null;
		try {
			if(dbManager == null) {
				dbManager = new DBManager();
				isInternal = true;
			}
			// get order by SI
			soDAO = new ServiceOrderDAOImpl(dbManager);
			serviceOrder = soDAO.getServiceOrderBySIId(serviceInstanceID);

			// change scenario of got order
			serviceOrder.setScenarioID(WorkflowScenario.DISCONNECT.toInt());
			serviceOrder.setServiceOrderStatusID(OrderStatus.ENTERING.toInt());

			disconnectWF = new DisconnectScenarioWorkflow(serviceOrder, dbManager);
			disconnectWF.proceedOrder();
			
			if(isInternal) {
				dbManager.commit();
			}
		} catch (DBManagerException wfExc) {
			throw new DBManagerException("");
		} finally {
			if(isInternal) {
				dbManager.close();
			}
		}
	}
	
	
	/**
	 * Register in system after selecting service and creating new order.
	 * 
	 * @param login
	 * @param password
	 * @param email
	 * @param firstName
	 * @param lastName
	 * @throws DBManagerException
	 */
	public int register(String login, String password, String email,
			String firstName, String lastName, int catalogID,
			String serviceLocation, DBManager dbManager) throws DBManagerException {
		
		OSSUserDAO userDAO = null;
		int userID;
		try {
			userDAO = new OSSUserDAOImpl(dbManager);

			// check inputted login
			if (userDAO.isExist(login)) {
				throw new DBManagerException("User with specified login - "
						+ login + " already exist. Choose another.");
			}
			// check inputted email
			if (userDAO.isEmailDuplicate(email)) {
				throw new DBManagerException("User with specified email - "
						+ email + " already exist. Input another email. ");
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
			
			proceedNewOrder(dbManager, userID, catalogID, serviceLocation);
			dbManager.commit();
			//sendEmail(userID, firstName, login, password);
		} catch (DBManagerException exc) {
			throw new DBManagerException("Error while register in system.", exc);
		}
		return userID;

	}
	
	private void sendEmail(int userID, String firstName, String login, String password) {
		try {
			RegistrationSuccessfulEmail notificationEmail = 
					new RegistrationSuccessfulEmail(firstName, login, password);
			EmailSender emailSender = new EmailSender();
			emailSender.sendEmail(userID, notificationEmail);
		} catch (IOException exc) {
			logger.error(exc.getMessage(), exc);
		}
	}

	private void proceedNewOrder(DBManager dbManager, int userID, int catalogID,
			String serviceLocation) throws DBManagerException {

		ServiceOrder newOrder = createOrder(dbManager, userID, catalogID,
				serviceLocation);

		Workflow wf = new NewScenarioWorkflow(newOrder, dbManager);
		wf.proceedOrder();
	}

	private ServiceOrder createOrder(DBManager dbManager, int userID,
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
		int orderStatusID = statusDAO
				.getServiceOrderStatusID(OrderStatus.ENTERING);
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
