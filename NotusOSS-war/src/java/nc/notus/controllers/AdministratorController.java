package controllers;

import org.apache.log4j.Logger;

import nc.notus.dao.OSSUserDAO;
import nc.notus.dao.impl.OSSUserDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.entity.OSSUser;
import nc.notus.states.UserState;

public class AdministratorController {
	
	private String actionSuccess;
	
	private static Logger logger = Logger.getLogger(DBManager.class.getName());
	
	public void blockUser(int userID) throws DBManagerException {
		DBManager dbManager = null;
		try {
			dbManager = new DBManager();

			OSSUserDAO userDAO = new OSSUserDAOImpl(dbManager);
			OSSUser user = userDAO.find(userID);

			user.setBlocked(UserState.BLOCKED.toInt());
			userDAO.update(user);
			dbManager.commit();
			setActionSuccess("User " + user.getLogin() + " successfully blocked!");
		} catch (DBManagerException e) {
			throw new DBManagerException("User not blocked. Error was occured.");
		} finally {
			dbManager.close();
		}
	}
	
	public void blockUser(int userID, DBManager dbManager) throws DBManagerException {
		try {
			OSSUserDAO userDAO = new OSSUserDAOImpl(dbManager);
			OSSUser user = userDAO.find(userID);

			user.setBlocked(UserState.BLOCKED.toInt());
			userDAO.update(user);
			setActionSuccess("User " + user.getLogin() + " successfully blocked!");
		} catch (DBManagerException e) {
			throw new DBManagerException("User not blocked. Error was occured.");
		}
	}

	public String getActionSuccess() {
		return actionSuccess;
	}

	public void setActionSuccess(String actionSuccess) {
		this.actionSuccess = actionSuccess;
	}


}
