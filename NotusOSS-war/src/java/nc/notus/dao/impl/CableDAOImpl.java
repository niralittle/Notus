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
 * Implementation of DAO for entity Cable
 * @author Igor Litvinenko
 */
public class CableDAOImpl extends GenericDAOImpl implements CableDAO {

    public CableDAOImpl() throws DAOException { }

    @Override
    public String getCableName(int cableID) throws DAOException {
        Connection conn = null;
        try {
            conn = getConnection();

            String queryString = "SELECT cable FROM cable WHERE id = ?";
            PreparedStatement selectCableStatement =
                                            conn.prepareStatement(queryString);
            selectCableStatement.setInt(1, cableID);
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
