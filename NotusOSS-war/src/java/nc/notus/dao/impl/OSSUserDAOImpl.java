package nc.notus.dao.impl;

import java.util.ArrayList;
import java.util.List;

import nc.notus.dao.DAOException;
import nc.notus.dao.OSSUserDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.ResultIterator;
import nc.notus.dbmanager.Statement;
import nc.notus.entity.OSSUser;
import nc.notus.states.UserRole;
import nc.notus.states.UserState;

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
     * Method returns list of users with similar login with paging
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @param login of user
     * @return list of users with similar login
     */
    @Override
    public List<OSSUser> getUsersByLogin(String login, int offset, int numberOfRecords) {
        String query  = "SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM (" +
                        "SELECT u.id, u.firstname, u.lastname, u.email, u.login, " +
                        "u.password, u.blocked, u.roleid " +
                        "FROM ossuser u " +
                        "LEFT JOIN role r ON u.roleid = r.id " +
                        "WHERE r.role  = 'Customer User' " +
                        "AND u.login LIKE ? " +
                        "ORDER BY u.firstname " +
                        " ) a where ROWNUM <= ? )" +
                        "WHERE rnum  >= ?";
        Statement statement = dbManager.prepareStatement(query);
        statement.setString(1, ("%" + login + "%"));
        statement.setInt(2, numberOfRecords);
        statement.setInt(3, offset);
        ResultIterator ri = statement.executeQuery();
        List<OSSUser> users = new ArrayList<OSSUser>();
        while (ri.next()){
            OSSUser us = new OSSUser();
            us.setId(ri.getInt("id"));
            us.setFirstName(ri.getString("firstname"));
            us.setLastName(ri.getString("lastname"));
            us.setEmail(ri.getString("email"));
            us.setLogin(ri.getString("login"));
            us.setPassword(ri.getString("password"));
            us.setBlocked(ri.getInt("blocked"));
            us.setRoleID(ri.getInt("roleid"));
            users.add(us);
        }
        return users;
    }
    /**
     * Method returns list of users with similar lastname with paging
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return list of users with similar login
     */

    @Override
    public List<OSSUser> getUsersByLastName(String lastname, int offset, int numberOfRecords) {
        String query  = "SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM (" +
                        "SELECT u.id, u.firstname, u.lastname, u.email, u.login, " +
                        "u.password, u.blocked, u.roleid " +
                        "FROM ossuser u " +
                        "LEFT JOIN role r ON u.roleid = r.id " +
                        "WHERE r.role  = 'Customer User' " +
                        "AND u.lastname LIKE ? " +
                        "ORDER BY u.firstname " +
                        " ) a where ROWNUM <= ? )" +
                        "WHERE rnum  >= ?";
        Statement statement = dbManager.prepareStatement(query);
        statement.setString(1, ("%" + lastname + "%"));
        statement.setInt(2, numberOfRecords);
        statement.setInt(3, offset);
        ResultIterator ri = statement.executeQuery();
        List<OSSUser> users = new ArrayList<OSSUser>();
        while (ri.next()){
            OSSUser us = new OSSUser();
            us.setId(ri.getInt("id"));
            us.setFirstName(ri.getString("firstname"));
            us.setLastName(ri.getString("lastname"));
            us.setEmail(ri.getString("email"));
            us.setLogin(ri.getString("login"));
            us.setPassword(ri.getString("password"));
            us.setBlocked(ri.getInt("blocked"));
            us.setRoleID(ri.getInt("roleid"));
            users.add(us);
        }
        return users;
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
    public boolean isExist(String login) {
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

    // TODO: documentation
    @Override
    public List<String> getGroupEmails(UserRole role) {
        String query = "SELECT u.email " +
                       "FROM OSSUser u " +
                       "WHERE u.roleID = ?";
        Statement statement = dbManager.prepareStatement(query);
        statement.setInt(1, role.toInt());
        ResultIterator ri = statement.executeQuery();
        List<String> emailList = new ArrayList<String>();
        while (ri.next()) {
            emailList.add(ri.getString("email"));
        }
        return emailList;
    }

    /**
     * Method returns list of users with similar email with paging
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @param email of user
     * @return list of users with similar email
     */
    public List<OSSUser> getUsersByEmail(String email, int offset, int numberOfRecords) {
        String query  = "SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM (" +
                        "SELECT u.id, u.firstname, u.lastname, u.email, u.login, " +
                        "u.password, u.blocked, u.roleid " +
                        "FROM ossuser u " +
                        "LEFT JOIN role r ON u.roleid = r.id " +
                        "WHERE r.role  = 'Customer User' " +
                        "AND u.email LIKE ? " +
                        "ORDER BY u.firstname " +
                        " ) a where ROWNUM <= ? )" +
                        "WHERE rnum  >= ?";
        Statement statement = dbManager.prepareStatement(query);
        statement.setString(1, ("%" + email + "%"));
        statement.setInt(2, numberOfRecords);
        statement.setInt(3, offset);
        ResultIterator ri = statement.executeQuery();
        List<OSSUser> users = new ArrayList<OSSUser>();
        while (ri.next()){
            OSSUser us = new OSSUser();
            us.setId(ri.getInt("id"));
            us.setFirstName(ri.getString("firstname"));
            us.setLastName(ri.getString("lastname"));
            us.setEmail(ri.getString("email"));
            us.setLogin(ri.getString("login"));
            us.setPassword(ri.getString("password"));
            us.setBlocked(ri.getInt("blocked"));
            us.setRoleID(ri.getInt("roleid"));
            users.add(us);
        }
        return users;
    }

    /**
     * Method returns users with specific login
     * @param login - login
     * @return users with specific login
     * or NULL if not found
     */
    @Override
    public OSSUser getUserByLogin(String login) {
        String query  =  "SELECT u.id, u.firstname, u.lastname, u.email, u.login, " +
                         "u.password, u.blocked, u.roleid " +
                         "FROM ossuser u " +
                         "WHERE u.login = ? ";
        Statement statement = dbManager.prepareStatement(query);
        statement.setString(1, (login));
        ResultIterator ri = statement.executeQuery();
        OSSUser user = null;
        if (ri.next()){
            user = new OSSUser();
            user.setId(ri.getInt("id"));
            user.setFirstName(ri.getString("firstname"));
            user.setLastName(ri.getString("lastname"));
            user.setEmail(ri.getString("email"));
            user.setLogin(ri.getString("login"));
            user.setPassword(ri.getString("password"));
            user.setBlocked(ri.getInt("blocked"));
            user.setRoleID(ri.getInt("roleid"));
        }
        return user;
    }

	@Override
	public boolean isBlocked(String login) {
		int blocked = 0;
		StringBuilder query = new StringBuilder();
		query.append("SELECT blocked FROM OSSUSER WHERE login = ? ");
		
		Statement statement = dbManager.prepareStatement(query.toString());
		statement.setString(1, login);
		
		ResultIterator ri = statement.executeQuery();
		if(ri.next()) {
			blocked = ri.getInt("blocked");
		}
		if (blocked == UserState.BLOCKED.toInt()) {
			return true;
		} else {
			return false;
		}
	}
}
