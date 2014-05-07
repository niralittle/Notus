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
	
	public void sendBillToCustomer(int taskID) {
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
			actionStatus = "Oops, some error happened! ";
		} finally {
			dbManager.close();
		}
	}
	
	public void sendBillToCustomer(int taskID, DBManager dbManager) {
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
			actionStatus = "Oops, some error happened! ";
		} 
	}
	
	public void changeCustomerPassword(int userID, String newPassword) {
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
			actionStatus = "Oops, some error happened! ";
		} finally {
			dbManager.close();
		}
	}

	public String getActionStatus() {
		return actionStatus;
	}

	public void setActionStatus(String actionStatus) {
		this.actionStatus = actionStatus;
	}
}
