package nc.notus.dao.impl;

import nc.notus.dao.CircuitDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.Circuit;

/**
 * Implementation of DAO for entity Circuit
 * @author Vladimir Ermolenko
 */
public class CircuitDAOImpl extends GenericDAOImpl<Circuit> implements CircuitDAO{

    public CircuitDAOImpl(DBManager dbManager) {
        super(dbManager);
    }

}
