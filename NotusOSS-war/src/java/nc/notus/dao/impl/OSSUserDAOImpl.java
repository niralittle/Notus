package nc.notus.dao.impl;

import nc.notus.dao.OSSUserDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.ResultIterator;
import nc.notus.dbmanager.Statement;
import nc.notus.entity.OSSUser;

/**
 * Implementation of DAO for entity OSSUser
 * @author Vladimir Ermolenko
 */
public class OSSUserDAOImpl extends GenericDAOImpl<OSSUser> implements OSSUserDAO {

    public OSSUserDAOImpl(DBManager dbManager) {
        super(dbManager);
    }

    /**
     * Implementation of method for block accounts in DB
     * NC.KYIV.2014.WIND.REG.4	Account blocking is performed by an Administrator O
     * @param user: our user of type OSSUser
     */

    public void blockUser(OSSUser user) {
        String query = "UPDATE OSSUser SET blocked = 1 WHERE id = ?";
        Statement statement = dbManager.prepareStatement(query);
        statement.setInt(1, user.getId());
        statement.executeUpdate();
    }
    
    @Override
    public boolean isExist(String login) {
    	if (login == null) {
    		throw new NullPointerException("Null reference invoke.");
    	}
    	String queryString = "SELECT lastname FROM OSSUSER WHERE login = ?";
	    Statement statement = dbManager.prepareStatement(queryString);
	    statement.setString(1, login);
	    ResultIterator ri = statement.executeQuery();
	    return ri != null ? true : false;

    }
}
