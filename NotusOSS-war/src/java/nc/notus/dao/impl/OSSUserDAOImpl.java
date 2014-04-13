package nc.notus.dao.impl;

import nc.notus.dao.OSSUserDAO;
import nc.notus.dbmanager.Statement;
import nc.notus.entity.OSSUser;

/**
 * Implementation of DAO for entity OSSUser
 * @author Vladimir Ermolenko
 */
public class OSSUserDAOImpl extends GenericDAOImpl<OSSUser> implements OSSUserDAO {

    /**
     * Implementation of method for block accounts in DB
     * NC.KYIV.2014.WIND.REG.4	Account blocking is performed by an Administrator O
     * @param user
     */

    public void blockUser(OSSUser user) {
        String query = "UPDATE OSSUser SET blocked = 1 WHERE id = ?";
        Statement statement = dbManager.prepareStatement(query);
        statement.setInt(1, user.getId());
        statement.executeUpdate();
    }
}
