package nc.notus.controllers;

import org.apache.log4j.Logger;

import nc.notus.dao.OSSUserDAO;
import nc.notus.dao.impl.OSSUserDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.entity.OSSUser;
import nc.notus.states.UserState;

/**
 *
 * @author Dima
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
    private static Logger logger = Logger.getLogger(DBManager.class.getName());

    /**
     *
     * @param login
     * @param password
     * @param email
     * @param firstName
     * @param lastName
     * @param roleID
     * @return
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
            throw new DBManagerException("Error while proceed to disconnect", wfExc);
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
        try {
            if (isInternal) {
                dbManager = new DBManager();
            }
            OSSUserDAO userDAO = new OSSUserDAOImpl(dbManager);
            OSSUser user = userDAO.find(userID);
            user.setBlocked(UserState.BLOCKED.toInt());
            userDAO.update(user);
            if (isInternal) {
                dbManager.commit();
            }
        } catch (DBManagerException wfExc) {
            if (isInternal) {
                dbManager.rollback();
            }
            throw new DBManagerException("Error while user blocking. ", wfExc);
        } finally {
            if (isInternal) {
                dbManager.close();
            }
        }
    }
}

