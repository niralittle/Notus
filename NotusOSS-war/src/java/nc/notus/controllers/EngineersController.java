package nc.notus.controllers;

import nc.notus.dbmanager.DBManager;

public abstract class EngineersController {
	
	protected DBManager dbManager;
	protected boolean isInternal;
	protected String actionStatus;
	
	/**
	 * Default constructor. Used to internal transactions.
	 */
	public EngineersController() {
		isInternal = true;
	}
	
	public EngineersController(DBManager dbManager) {
		this.dbManager = dbManager;
		isInternal = false;
	}
	
	
	public String getActionStatus() {
		return actionStatus;
	}

	public void setActionStatus(String actionStatus) {
		this.actionStatus = actionStatus;
	}

}
