/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import nc.notus.dao.DAOException;
import nc.notus.dao.OSSUserDAO;
import nc.notus.entity.OSSUser;

/**
 * Implementation of DAO for entity OSSUsre
 * @author Vladimir Ermolenko
 */
public class OSSUserDAOImpl extends GenericDAOImpl implements OSSUserDAO {

    public OSSUserDAOImpl() throws DAOException {
    }

    public void blockUser(OSSUser user) throws DAOException {
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE OSS_User SET blocked = 1 WHERE id = ?");
            ps.setInt(1, user.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException exc) {
            throw new DAOException("SQL Exception.", exc);
        } finally {
            if (conn != null) {
                releaseConnection(conn);
            }
        }
    }



}
