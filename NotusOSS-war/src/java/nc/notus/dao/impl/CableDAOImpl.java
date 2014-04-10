/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import nc.notus.dao.DAOException;
import nc.notus.dao.CableDAO;

/**
 *
 * @author Igor Litvinenko
 */
public class CableDAOImpl extends GenericDAOImpl implements CableDAO {

    public CableDAOImpl() throws DAOException { }

    @Override
    public String getCableName(int cableID) throws DAOException {
        Connection conn = null;
        try {
            conn = getConnection();

            String queryString = "SELECT '42' FROM DUAL";
            PreparedStatement selectCableStatement =
                                            conn.prepareStatement(queryString);
            ResultSet rs = selectCableStatement.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException exc) {
            throw new DAOException("SQL Exception.", exc);
        } finally {
            if (conn != null) {
                releaseConnection(conn);
            }
        }
    }

}
