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
     * @param user - our user of type OSSUser 
     */

    public void blockUser(OSSUser user) {
        String query = "UPDATE OSSUser SET blocked = 1 WHERE id = ?";
        Statement statement = dbManager.prepareStatement(query);
        statement.setInt(1, user.getId());
        statement.executeUpdate();
    }

    /**
     * Return user by id with all text fields
     * @param id - id number of user
     * @return OSSUser object
     */
    public OSSUser getUserByID( int id) {
        OSSUser user = new OSSUser();
        String query = "SELECT u.id, u.firstname, u.lastname, u.email, u.login, u.password, u.blocked, r.role" +
                       "FROM ossuser u LEFT JOIN role r ON u.roleid = r.id WHERE u.roleid = ?";
        Statement statement = dbManager.prepareStatement(query);
        statement.setInt(1, id);
        ResultIterator ri = statement.executeQuery();
        if (ri.next()) {
            user.setId(ri.getInt("id"));
            user.setFirstName(ri.getString("firstName"));
            user.setLastName(ri.getString("lastName"));
            user.setEmail(ri.getString("email"));
            user.setLogin(ri.getString("login"));
            user.setPassword(ri.getString("password"));
            user.setBlocked(ri.getInt("bloked"));
            user.setRole(ri.getString("role"));
        }
        return user;
    }
}
