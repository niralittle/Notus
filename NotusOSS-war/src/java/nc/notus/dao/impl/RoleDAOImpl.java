/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import nc.notus.dao.RoleDAO;
import nc.notus.dao.DAOException;
import nc.notus.entity.Role;

/**
 * Implementation of DAO for entity Role
 * @author Vladimir Ermolenko
 */
public class RoleDAOImpl extends GenericDAOImpl implements RoleDAO {

    public RoleDAOImpl() throws DAOException { }

    public void create(Role role) throws DAOException{
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO role VALUES(?, ?)");
            ps.setInt(1, role.getId());
            ps.setString(2, role.getRole());
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
    public Role read(int id) throws DAOException {
        Connection conn = null;
        Role role = new Role();
        try {
            conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT id, role FROM role WHERE id=?");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                role.setId(rs.getInt("id"));
                role.setRole(rs.getString("role"));
            }
            pstmt.close();
        } catch (SQLException exc) {
            throw new DAOException("SQL Exception.", exc);
        } finally {
            if (conn != null) {
                releaseConnection(conn);
            }
        }
        return role;
    }
}
