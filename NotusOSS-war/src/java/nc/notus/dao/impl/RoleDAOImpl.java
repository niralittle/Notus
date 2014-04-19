package nc.notus.dao.impl;

import nc.notus.dao.RoleDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.Role;

/**
 * Implementation of DAO for entity Role
 * @author Vladimir Ermolenko
 */
public class RoleDAOImpl extends GenericDAOImpl<Role> implements RoleDAO {

    public RoleDAOImpl(DBManager dbManager) {
        super(dbManager);
    }


}
