package nc.notus.controllers;

import nc.notus.dao.ScenarioDAO;
import nc.notus.dao.TaskDAO;
import nc.notus.dao.impl.ScenarioDAOImpl;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dao.impl.TaskDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.entity.Scenario;
import nc.notus.entity.ServiceOrder;
import nc.notus.entity.Task;
import nc.notus.states.WorkflowScenario;

public abstract class AbstractController {
	
	protected DBManager dbManager;
	protected boolean isInternal;
	protected String actionStatus;
	
	/**
	 * Default constructor. Used to internal transactions.
	 */
	public AbstractController() {
		isInternal = true;
	}
	
	public AbstractController(DBManager dbManager) {
		this.dbManager = dbManager;
		isInternal = false;
	}
	
	public Task getTask(int taskID, DBManager dbMan) throws DBManagerException {
		TaskDAO taskDAO = new TaskDAOImpl(dbMan);
		Task task = taskDAO.find(taskID);
		return task;
	}
	
	public String getTaskScenario(int orderID, DBManager dbMan) throws DBManagerException {

		String wfScenario = null;

		ServiceOrderDAOImpl soDAO = new ServiceOrderDAOImpl(dbMan);
		ScenarioDAO scenarioDAO = new ScenarioDAOImpl(dbMan);

		ServiceOrder order = soDAO.find(orderID);

		int scenarioID = order.getScenarioID();
		Scenario scenario = scenarioDAO.find(scenarioID);

		if (scenario.getScenario().equalsIgnoreCase(WorkflowScenario.NEW.toString())) {
			wfScenario = "NEW";
		} else if ((scenario.getScenario().equalsIgnoreCase(WorkflowScenario.DISCONNECT.toString()))) {
			wfScenario = "DISCONNECT";
		} 

		return wfScenario;
	}
	
	public String getTaskScenario(int orderID) throws DBManagerException {

		String wfScenario = null;

		ServiceOrderDAOImpl soDAO = new ServiceOrderDAOImpl(dbManager);
		ScenarioDAO scenarioDAO = new ScenarioDAOImpl(dbManager);

		ServiceOrder order = soDAO.find(orderID);

		int scenarioID = order.getScenarioID();
		Scenario scenario = scenarioDAO.find(scenarioID);

		if (scenario.getScenario().equalsIgnoreCase(WorkflowScenario.NEW.toString())) {
			wfScenario = "NEW";
		} else if ((scenario.getScenario().equalsIgnoreCase(WorkflowScenario.DISCONNECT.toString()))) {
			wfScenario = "DISCONNECT";
		} 

		return wfScenario;
	}
	
	public String getActionStatus() {
		return actionStatus;
	}

	public void setActionStatus(String actionStatus) {
		this.actionStatus = actionStatus;
	}

}