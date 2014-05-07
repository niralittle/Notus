package nc.notus.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import nc.notus.dao.OSSUserDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.dbmanager.ResultIterator;
import nc.notus.dbmanager.Statement;
import nc.notus.entity.OSSUser;
import nc.notus.states.UserRole;
import nc.notus.states.UserState;

/**
 * Implementation of DAO for entity OSSUser
 * @author Vladimir Ermolenko & Panchenko Dmytro
 */
public class OSSUserDAOImpl extends GenericDAOImpl<OSSUser> implements OSSUserDAO {

	private static Logger logger = Logger.getLogger(OSSUserDAOImpl.class.getName());
	
    public OSSUserDAOImpl(DBManager dbManager) {
        super(dbManager);
    }
    
    /**
     * Blocking user procedure (update of db)
     * @param user user to be blocked
     * @throws DBManagerException 
     */
    @Override
    public void blockUser(OSSUser user) throws DBManagerException {
    	if (user == null) {
    		logger.error("Passed parameter <user> is null. ");
    		throw new DBManagerException("Passed parameter <user> is null."
    				+ " Can't proccess null reference!");
    	} 
		Statement statement = null;
		String query = "UPDATE OSSUser SET blocked = 1 WHERE id = ?";
		try {
			statement = dbManager.prepareStatement(query);
			statement.setInt(1, user.getId());
			statement.executeUpdate();
		} catch (DBManagerException exc) {
			throw new DBManagerException ("The error was occured, " + 
						"contact the administrator");
		} finally {
			statement.close();
		}
    }

    /**
     * Method returns list of users with similar login with paging
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @param login of user
     * @return list of users with similar login
     * @throws DBManagerException 
     */
    @Override
    public List<OSSUser> getUsersByLogin(String login,  int numberOfRecords, int offset) throws DBManagerException {
    	if (login == null || login.isEmpty()) {
    		logger.error("Passed parameter <login> is null or empty. ");
    		throw new DBManagerException("Passed parameter <login> is null or empty"
    				+ " Can't proccess the request!");
    	} 
    	if(numberOfRecords < 1 || offset < 1) {
    		logger.error("Illegal argument in paging - less than 1.");
    		throw new DBManagerException("Illegal argument in paging - less than 1. "
    				+ " Can't proccess the request!");
    	}
    	Statement statement = null;
    	List<OSSUser> users = null;
    	ResultIterator ri  = null;
    	String query  = "SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM (" +
                        "SELECT u.id, u.firstname, u.lastname, u.email, u.login, " +
                        "u.password, u.blocked, u.roleid " +
                        "FROM ossuser u " +
                        "LEFT JOIN role r ON u.roleid = r.id " +
                        "WHERE r.role  = 'Customer User' " +
                        "AND u.login LIKE ? " +
                        "ORDER BY u.firstname " +
                        " ) a where ROWNUM <= ? )" +
                        "WHERE rnum  > ?";
		try {
			statement = dbManager.prepareStatement(query);
			statement.setString(1, ("%" + login + "%"));
			statement.setInt(2, numberOfRecords);
			statement.setInt(3, offset);
			
			ri = statement.executeQuery();
			users = new ArrayList<OSSUser>();
			
			while (ri.next()) {
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
		} catch (DBManagerException exc) {
			throw new DBManagerException ("The error was occured, " + 
					"contact the administrator");
		} finally {
			statement.close();
		}
        return users;
    }
    /**
     * Method returns list of users with similar lastname with paging
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return list of users with similar login
     * @throws DBManagerException 
     */

    @Override
    public List<OSSUser> getUsersByLastName(String lastname,  int numberOfRecords, int offset) throws DBManagerException {
    	if (lastname == null || lastname.isEmpty()) {
    		logger.error("Passed parameter <lastname> is null or empty. ");
    		throw new DBManagerException("Passed parameter <lastname> is null or empty"
    				+ " Can't proccess the request!");
    	} 
    	if(numberOfRecords < 1 || offset < 1) {
    		logger.error("Illegal argument in paging - less than 1.");
    		throw new DBManagerException("Illegal argument in paging - less than 1. "
    				+ " Can't proccess the request!");
    	}
    	Statement statement = null;
    	List<OSSUser> users = null;
    	ResultIterator ri  = null;
    	String query  = "SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM (" +
                        "SELECT u.id, u.firstname, u.lastname, u.email, u.login, " +
                        "u.password, u.blocked, u.roleid " +
                        "FROM ossuser u " +
                        "LEFT JOIN role r ON u.roleid = r.id " +
                        "WHERE r.role  = 'Customer User' " +
                        "AND u.lastname LIKE ? " +
                        "ORDER BY u.firstname " +
                        " ) a where ROWNUM <= ? )" +
                        "WHERE rnum  > ?";
		try {
			statement = dbManager.prepareStatement(query);
			statement.setString(1, ("%" + lastname + "%"));
			statement.setInt(2, numberOfRecords);
			statement.setInt(3, offset);

			ri = statement.executeQuery();
			users = new ArrayList<OSSUser>();

			while (ri.next()) {
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
		} catch (DBManagerException exc) {
			throw new DBManagerException ("The error was occured, " + 
					"contact the administrator");
		} finally {
			statement.close();
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
     * @throws DBManagerException 
     */
    @Override
    public boolean isExist(String login) throws DBManagerException {
    	if (login == null || login.isEmpty()) {
    		logger.error("Passed parameter <login> is null or empty. ");
    		throw new DBManagerException("Passed parameter <login> is null or empty"
    				+ " Can't proccess the request!");
    	} 
    	Statement statement = null;
    	ResultIterator ri = null;
    	boolean isExist = false;
    	
        String queryString = "SELECT login FROM OSSUSER WHERE login = ?";
		try {
			statement = dbManager.prepareStatement(queryString);
			statement.setString(1, login);

			ri = statement.executeQuery();

			// check if statement returned any value
			if (ri.next()) {
				isExist = true;			// login exists
			} else {
				isExist = false;		// login not exists
			}
		} catch (DBManagerException exc) {
			throw new DBManagerException ("The error was occured, " + 
					"contact the administrator");
		} finally {
			statement.close();
		}
		return isExist;
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
     * @throws DBManagerException 
     */
    @Override
    public boolean isEmailDuplicate(String email) throws DBManagerException {
    	if (email == null || email.isEmpty()) {
    		logger.error("Passed parameter <email> is null or empty. ");
    		throw new DBManagerException("Passed parameter <email> is null or empty"
    				+ " Can't proccess the request!");
    	} 
    	Statement statement = null;
    	ResultIterator ri = null;
    	boolean isExist = false;
    	
        String queryString = "SELECT email FROM OSSUSER WHERE email = ?";
        try {
			statement = dbManager.prepareStatement(queryString);
			statement.setString(1, email);

			ri = statement.executeQuery();

			// check if statement returned any value
			if (ri.next()) {
				isExist = true;			// email exists
			} else {
				isExist = false;		// email not exists
			}
		} catch (DBManagerException exc) {
			throw new DBManagerException ("The error was occured, " + 
					"contact the administrator");
		} finally {
			statement.close();
		}
		return isExist;
    }

    @Override
    public List<String> getGroupEmails(UserRole role) throws DBManagerException {
    	if (role == null) {
    		logger.error("Passed parameter <role> is null.");
    		throw new DBManagerException("Passed parameter <role> is null. "
    				+ " Can't proccess the request!");
    	} 
    	
    	Statement statement = null;
    	List<String> emailList = null;
    	
    	String query = "SELECT u.email " +
                       "FROM OSSUser u " +
                       "WHERE u.roleID = ?";
		try {
			statement = dbManager.prepareStatement(query);
			statement.setInt(1, role.toInt());

			ResultIterator ri = statement.executeQuery();
			emailList = new ArrayList<String>();
			while (ri.next()) {
				emailList.add(ri.getString("email"));
			}
		} catch (DBManagerException exc) {
			throw new DBManagerException ("The error was occured, " + 
					"contact the administrator");
		} finally {
			statement.close();
		}
        return emailList;
    }

    /**
     * Method returns list of users with similar email with paging
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @param email of user
     * @return list of users with similar email
     * @throws DBManagerException 
     */
    public List<OSSUser> getUsersByEmail(String email,  int numberOfRecords, int offset) throws DBManagerException {
    	if (email == null || email.isEmpty()) {
    		logger.error("Passed parameter <email> is null or empty. ");
    		throw new DBManagerException("Passed parameter <email> is null or empty"
    				+ " Can't proccess the request!");
    	} 
    	if(numberOfRecords < 1 || offset < 1) {
    		logger.error("Illegal argument in paging - less than 1.");
    		throw new DBManagerException("Illegal argument in paging - less than 1. "
    				+ " Can't proccess the request!");
    	}
    	Statement statement = null;
    	ResultIterator ri = null;
    	List<OSSUser> users = null;
    	String query  = "SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM (" +
                        "SELECT u.id, u.firstname, u.lastname, u.email, u.login, " +
                        "u.password, u.blocked, u.roleid " +
                        "FROM ossuser u " +
                        "LEFT JOIN role r ON u.roleid = r.id " +
                        "WHERE r.role  = 'Customer User' " +
                        "AND u.email LIKE ? " +
                        "ORDER BY u.firstname " +
                        " ) a where ROWNUM <= ? )" +
                        "WHERE rnum  > ?";
		try {
			statement = dbManager.prepareStatement(query);
			statement.setString(1, ("%" + email + "%"));
			statement.setInt(2, numberOfRecords);
			statement.setInt(3, offset);
			
			ri = statement.executeQuery();
			users = new ArrayList<OSSUser>();
			
			while (ri.next()) {
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
		} catch (DBManagerException exc) {
			throw new DBManagerException ("The error was occured, " + 
					"contact the administrator");
		} finally {
			statement.close();
		}
        return users;
    }

    /**
     * Method returns users with specific login
     * @param login - login
     * @return users with specific login
     * or NULL if not found
     * @throws DBManagerException 
     */
    @Override
    public OSSUser getUserByLogin(String login) throws DBManagerException {
    	if (login == null || login.isEmpty()) {
    		logger.error("Passed parameter <login> is null or empty. ");
    		throw new DBManagerException("Passed parameter <login> is null or empty"
    				+ " Can't proccess the request!");
    	} 
    	Statement statement = null;
    	ResultIterator ri = null;
    	OSSUser user = null;
        String query  =  "SELECT u.id, u.firstname, u.lastname, u.email, u.login, " +
                         "u.password, u.blocked, u.roleid " +
                         "FROM ossuser u " +
                         "WHERE u.login = ? ";
		try {
			statement = dbManager.prepareStatement(query);
			statement.setString(1, (login));
			ri = statement.executeQuery();

			if (ri.next()) {
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
		} catch (DBManagerException exc) {
			throw new DBManagerException ("The error was occured, " + 
					"contact the administrator");
		} finally {
			statement.close();
		}
        return user;
    }

	@Override
	public boolean isBlocked(String login) throws DBManagerException {
		if (login == null || login.isEmpty()) {
    		logger.error("Passed parameter <login> is null or empty. ");
    		throw new DBManagerException("Passed parameter <login> is null or empty"
    				+ " Can't proccess the request!");
    	} 
		boolean isBlocked = false;
		int blockId = 1;
		Statement statement = null;
		ResultIterator ri = null;

		StringBuilder query = new StringBuilder();
		query.append("SELECT blocked FROM OSSUSER WHERE login = ? ");

		try {
			statement = dbManager.prepareStatement(query.toString());
			statement.setString(1, login);

			ri = statement.executeQuery();
			if (ri.next()) {
				blockId = ri.getInt("blocked");
			}
			if (blockId == UserState.BLOCKED.toInt()) {
				isBlocked = true;
			} else {
				isBlocked = false;
			}
		} catch (DBManagerException exc) {
			throw new DBManagerException ("The error was occured, " + 
					"contact the administrator");
		} finally {
			statement.close();
		}
		return isBlocked;
	}

    /**
     * Method returns list of users of specific role with paging
     * @param roleID - ID of role in system
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return list of users with similar login
     * @throws DBManagerException 
     */
    @Override
    public List<OSSUser> getUsersByRoleID(int roleID, int offset, int numberOfRecords) throws DBManagerException {
    	if(numberOfRecords < 1 || offset < 1) {
    		logger.error("Illegal argument in paging - less than 1.");
    		throw new DBManagerException("Illegal argument in paging - less than 1. "
    				+ " Can't proccess the request!");
    	}
    	
    	Statement statement = null;
    	ResultIterator ri = null;
    	List<OSSUser> users = null;
    	String query  = "SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM (" +
                        "SELECT u.id, u.firstname, u.lastname, u.email, u.login, " +
                        "u.password, u.blocked, u.roleid " +
                        "FROM ossuser u " +
                        "WHERE u.roleid  = ? " +
                        "ORDER BY u.firstname " +
                        " ) a where ROWNUM <= ? )" +
                        "WHERE rnum  >= ?";
		try {
			statement = dbManager.prepareStatement(query);
			statement.setInt(1, roleID);
			statement.setInt(2, numberOfRecords);
			statement.setInt(3, offset);
			
			ri = statement.executeQuery();
			users = new ArrayList<OSSUser>();
			
			while (ri.next()) {
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
		} catch (DBManagerException exc) {
			throw new DBManagerException ("The error was occured, " + 
					"contact the administrator");
		} finally {
			statement.close();
		}
        return users;
    }

    /**
     * Method returns count of users by specific role
     * @param roleID - specific role id
     * @return count of users
     * @throws DBManagerException 
     */
    @Override
    public long countAssignedByRoleID(int roleID) throws DBManagerException {
        long count = 0;
        Statement statement = null;
        ResultIterator ri = null;
        
        String query  = "SELECT COUNT(*) total " +
	                "FROM ossuser u " +
	                "WHERE u.roleid = ? ";
		try {
			statement = dbManager.prepareStatement(query);
			statement.setInt(1, roleID);
			
			ri = statement.executeQuery();
			if (ri.next()) {
				count = ri.getLong("total");
			}
		} catch (DBManagerException exc) {
			throw new DBManagerException ("The error was occured, " + 
					"contact the administrator");
		} finally {
			statement.close();
		}
        return count;
    }
}
