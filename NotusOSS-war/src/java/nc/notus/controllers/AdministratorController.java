package nc.notus.controllers;

import nc.notus.dao.OSSUserDAO;
import nc.notus.dao.impl.OSSUserDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.entity.OSSUser;
import nc.notus.states.UserRole;
import nc.notus.states.UserState;

/**
 * Provide actions of Administrator role. 
 *
 * @author Pacnhenko Dmytro
 */
public class AdministratorController extends AbstractController {

    /**
     *
     * @param dbManager
     */
    public AdministratorController(DBManager dbManager) {
        super(dbManager);
    }

    /**
     *
     */
    public AdministratorController() {
        super();
    }

    /**
     * Register new engineer in system.
     *
     * @param login engineer login
     * @param password engineer password 
     * @param email engineer email
     * @param firstName engineer first name
     * @param lastName engineer last name
     * @param roleID engineer role {@link UserRole}
     * @throws DBManagerException
     */
    public int registerNewEngineer(String login, String password, String email,
            String firstName, String lastName, int roleID) throws DBManagerException {
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
            user.setRoleID(roleID);

            userID = (Integer) userDAO.add(user);
 
            if (isInternal) {
                dbManager.commit();
            }
        } catch (DBManagerException wfExc) {
            if (isInternal) {
                dbManager.rollback();
            }
            throw new DBManagerException("Error while proceed to disconnect");
        } finally {
            if (isInternal) {
                dbManager.close();
            }
        }
        return userID;
    }

    /**
     *
     * @param userID
     * @throws DBManagerException
     */
    public void blockUser(int userID) throws DBManagerException {
    	boolean isUser = false;
    	try {
            if (isInternal) {
                dbManager = new DBManager();
            }
            OSSUserDAO userDAO = new OSSUserDAOImpl(dbManager);
            OSSUser user = userDAO.find(userID);
            
            int userRoleID = user.getRoleID();
			if (userRoleID == UserRole.CUSTOMER_USER.toInt()) {
				user.setBlocked(UserState.BLOCKED.toInt());
				userDAO.update(user);
				if (isInternal) {
					dbManager.commit();
				}
				isUser = true;
			}
        } catch (DBManagerException wfExc) {
            if (isInternal) {
                dbManager.rollback();
            }
            throw new DBManagerException("Error while user blocking. ");
        } finally {
            if (isInternal) {
                dbManager.close();
            }
        }
    	if(!isUser) {
    		throw new DBManagerException("You can block customer users only.");
    	}
    }
}

