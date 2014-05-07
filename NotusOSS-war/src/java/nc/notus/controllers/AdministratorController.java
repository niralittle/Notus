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
	private DBManager dbManager;
	
	public AdministratorController() {

	}
	public AdministratorController(DBManager dbManager) {
		this.dbManager = dbManager;
	}
	
	private static Logger logger = Logger.getLogger(DBManager.class.getName());
	
	
	public int registerNewEngineer(String login, String password, String email,
			String firstName, String lastName, int roleID) throws DBManagerException {
		
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
			user.setRoleID(roleID);

			userID = (Integer) userDAO.add(user);
			actionSuccess = "New engineer - " + login + " successfully registered.";
		} catch (DBManagerException exc) {
			throw new DBManagerException("Error while register in system.");
		}
		return userID;

	}	
	
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
