/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.dao;

import nc.notus.entity.Role;

/**
 * Interface of DAO for entity Role
 * @author Vladimir Ermolenko
 */
public interface RoleDAO extends GenericDAO {
    Role read(int id) throws DAOException;
}
