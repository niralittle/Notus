package nc.notus.dao.impl;

import nc.notus.dao.OSSUserDAO;
import nc.notus.dbmanager.Statement;
import nc.notus.entity.OSSUser;

/**
 * Implementation of DAO for entity OSSUsre
 * @author Vladimir Ermolenko
 */
public class OSSUserDAOImpl extends GenericDAOImpl<OSSUser> implements OSSUserDAO {

    // REVIEW: documentation expected
    public void blockUser(OSSUser user) {
        String query = "UPDATE OSSUser SET blocked = 1 WHERE id = ?";
        Statement statement = dbManager.prepareStatement(query);
        statement.setInt(1, user.getId());
        statement.executeUpdate();
    }
}
