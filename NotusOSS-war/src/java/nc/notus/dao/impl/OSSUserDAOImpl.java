package nc.notus.dao.impl;

import java.util.List;
import nc.notus.dao.DAOException;
import nc.notus.dao.OSSUserDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.ResultIterator;
import nc.notus.dbmanager.Statement;
import nc.notus.entity.OSSUser;
import nc.notus.states.UserRole;

/**
 * Implementation of DAO for entity OSSUser
 * @author Vladimir Ermolenko
 */
public class OSSUserDAOImpl extends GenericDAOImpl<OSSUser> implements OSSUserDAO {

    public OSSUserDAOImpl(DBManager dbManager) {
        super(dbManager);
    }

    /**
     * Blocking user procedure (update of db)
     * @param user user to be blocked
     */
    @Override
    public void blockUser(OSSUser user) {
        String query = "UPDATE OSSUser SET blocked = 1 WHERE id = ?";
        Statement statement = dbManager.prepareStatement(query);
        statement.setInt(1, user.getId());
        statement.executeUpdate();
    }

    /**
     * Implementation of checking if user with specified login already exists
     * in system.
     *
     * @param login
     *            login to check
     * @return true - if exist, false - otherwise
     * @throws NullPointerException
     *             if null reference passes
     * @author Panchenko Dmytro
     */
    @Override
    public boolean isLoginDuplicate(String login) {
        if (login == null) {
            throw new DAOException("Null reference invoke.");
        }
        String queryString = "SELECT login FROM OSSUSER WHERE login = ?";

        Statement statement = dbManager.prepareStatement(queryString);
        statement.setString(1, login);

        ResultIterator ri = statement.executeQuery();

        // check if statement returned any value
        if (ri.next()) {
            // login exists
            return true;
        }
        // login is unique
        return false;
    }

    /**
     * Implementation of checking if specified email already exists in system.
     *
     * @param email
     *            email to check
     * @return true - if exist, false - otherwise
     * @throws NullPointerException
     *             if null reference passes
     *
     * @author Panchenko Dmytro
     */
    @Override
    public boolean isEmailDuplicate(String email) {
        if (email == null) {
            throw new DAOException("Null reference invoke.");
        }
        String queryString = "SELECT email FROM OSSUSER WHERE email = ?";

        Statement statement = dbManager.prepareStatement(queryString);
        statement.setString(1, email);

        ResultIterator ri = statement.executeQuery();

        // check if statement returned any value
        if (ri.next()) {
            // email exists
            return true;
        }
        // email is unique
        return false;
    }

    @Override
    public List<String> getGroupEmails(UserRole role) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getUserEmail(int userID) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
