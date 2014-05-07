package controllers;

import nc.notus.dao.TaskDAO;
import nc.notus.dao.impl.OSSUserDAOImpl;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dao.impl.TaskDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.entity.OSSUser;
import nc.notus.entity.ServiceOrder;
import nc.notus.entity.Task;
import nc.notus.workflow.NewScenarioWorkflow;

public class SupportEngineerController {
	
	private String actionStatus;
	
	/**
	 * Send bill to customer user.
	 * (Implementation note: Commit changes in the DB after updating user)
	 * 
	 * @param taskID
	 * @throws DBManagerException 
	 */
	public void sendBillToCustomer(int taskID) throws DBManagerException {
		DBManager dbManager = null;
		NewScenarioWorkflow wf = null;
		ServiceOrderDAOImpl orderDAO = null;
		TaskDAO taskDAO = null;
		try {
			dbManager = new DBManager();

			taskDAO = new TaskDAOImpl(dbManager);
			Task task = taskDAO.find(taskID);
			int orderID = task.getServiceOrderID();

			orderDAO = new ServiceOrderDAOImpl(dbManager);
			ServiceOrder order = orderDAO.find(orderID);

			wf = new NewScenarioWorkflow(order, dbManager);
			wf.approveBill(taskID);
			dbManager.commit();
			actionStatus = "Bill successfully sent!";
		} catch (DBManagerException ex) {
			throw new DBManagerException("Error was occured while sending bill.");
		} finally {
			dbManager.close();
		}
	}
	
	/**
	 * Send bill to customer user.
	 * (Implementation note: Not commit changes in the DB after updating user)
	 * 
	 * @param taskID
	 * @param dbManager
	 * @throws DBManagerException 
	 */
	public void sendBillToCustomer(int taskID, DBManager dbManager) throws DBManagerException {
		NewScenarioWorkflow wf = null;
		ServiceOrderDAOImpl orderDAO = null;
		TaskDAO taskDAO = null;
		try {
			taskDAO = new TaskDAOImpl(dbManager);
			Task task = taskDAO.find(taskID);
			int orderID = task.getServiceOrderID();

			orderDAO = new ServiceOrderDAOImpl(dbManager);
			ServiceOrder order = orderDAO.find(orderID);

			wf = new NewScenarioWorkflow(order, dbManager);
			wf.approveBill(taskID);
			actionStatus = "Bill successfully sent!";
		} catch (DBManagerException ex) {
			throw new DBManagerException("Error was occured while sending bill.");
		} 
	}
	
	public void changeCustomerPassword(int userID, String newPassword) throws DBManagerException {
		OSSUserDAOImpl userDAO = null;
		DBManager dbManager = null;
		try {
			dbManager = new DBManager();
			userDAO = new OSSUserDAOImpl(dbManager);

			// get user by id and set him new password
			OSSUser user = userDAO.find(userID);
			user.setPassword(newPassword);
			userDAO.update(user);

			dbManager.commit();
			actionStatus = "Password successfully changed!";
		} catch (DBManagerException exc) {
			throw new DBManagerException("Error was occured while"
							+ " changing password.");
		} finally {
			dbManager.close();
		}
	}
	
	public void changeCustomerPassword(int userID, String newPassword, DBManager dbManager) throws DBManagerException {
		OSSUserDAOImpl userDAO = null;
		try {
			dbManager = new DBManager();
			userDAO = new OSSUserDAOImpl(dbManager);

			// get user by id and set him new password
			OSSUser user = userDAO.find(userID);
			user.setPassword(newPassword);
			userDAO.update(user);

			actionStatus = "Password successfully changed!";
		} catch (DBManagerException exc) {
			throw new DBManagerException("Error was occured while"
					+ " changing password.");
		} 
	}

	public String getActionStatus() {
		return actionStatus;
	}

	public void setActionStatus(String actionStatus) {
		this.actionStatus = actionStatus;
	}
}
