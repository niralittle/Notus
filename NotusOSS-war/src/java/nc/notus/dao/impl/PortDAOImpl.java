package nc.notus.dao.impl;

import nc.notus.dao.PortDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.Port;

/**
 * Implementation of DAO for entity Port
 * @author Andrey Ilin
 */
public class PortDAOImpl extends GenericDAOImpl<Port> implements PortDAO {

    public PortDAOImpl(DBManager dbManager) {
        super(dbManager);
    }

}
