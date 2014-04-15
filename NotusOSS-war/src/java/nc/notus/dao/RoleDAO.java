package nc.notus.dao;

import nc.notus.entity.Role;

/**
 * Interface of DAO for entity Role
 * @author Vladimir Ermolenko
 */
public interface RoleDAO extends GenericDAO<Role> {
    public int getRoleID(String role);
}
