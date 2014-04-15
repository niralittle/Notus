/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.notus.dao.impl;

import nc.notus.dao.RoleDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.ResultIterator;
import nc.notus.dbmanager.Statement;
import nc.notus.entity.Role;

/**
 * Implementation of DAO for entity Role
 * @author Vladimir Ermolenko
 */
public class RoleDAOImpl extends GenericDAOImpl<Role> implements RoleDAO {

    public RoleDAOImpl(DBManager dbManager) {
        super(dbManager);
    }

    /**
     * Method return id by its role name in system
     * @param role
     * @return id of role
     */
    @Override
    public int getRoleID(String role) {
        if (role == null) {
    		throw new NullPointerException("Null reference invoked!");
    	}
    	String queryString = "SELECT r.id, r.role FROM role r WHERE r.role = ?";
	Statement statement = dbManager.prepareStatement(queryString);
	statement.setString(1, role);
	ResultIterator ri = statement.executeQuery();
        if (ri.next()){
            return ri.getInt("id");
        }
        return 0;
    }
}
