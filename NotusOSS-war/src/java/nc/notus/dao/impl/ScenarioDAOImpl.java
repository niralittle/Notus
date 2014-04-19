package nc.notus.dao.impl;

import nc.notus.dao.ScenarioDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.Scenario;

/**
 * Implementation of DAO for entity Scenario
 * @author Vladimir Ermolenko
 */
public class ScenarioDAOImpl extends GenericDAOImpl<Scenario> implements ScenarioDAO {

    public ScenarioDAOImpl(DBManager dbManager) {
        super(dbManager);
    }

}
