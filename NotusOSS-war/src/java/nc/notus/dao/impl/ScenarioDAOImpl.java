package nc.notus.dao.impl;

import nc.notus.dao.DAOException;
import nc.notus.dao.ScenarioDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.ResultIterator;
import nc.notus.dbmanager.Statement;
import nc.notus.entity.Scenario;
import nc.notus.states.WorkflowScenario;

/**
 * Implementation of DAO for entity Scenario
 * @author Vladimir Ermolenko
 */
public class ScenarioDAOImpl extends GenericDAOImpl<Scenario> implements ScenarioDAO {

    public ScenarioDAOImpl(DBManager dbManager) {
        super(dbManager);
    }

    /**
     * Method returns ID of given Scenario
     * @param scenario Scenario to search ID for
     * @return ID of Scenario
     * @throws DAOException if Scenario was not found
     */
    @Override
    public int getScenarioID(WorkflowScenario scenario) {
        String queryString = "SELECT s.id, s.scenario " +
                "FROM scenario s WHERE s.scenario = ?";
        Statement statement = dbManager.prepareStatement(queryString);
        statement.setString(1, scenario.toString());
        ResultIterator ri = statement.executeQuery();
        if (ri.next()) {
            return ri.getInt("id");
        } else {
            throw new DAOException("Given Scenario was not found in DB: " +
                    scenario.toString());
        }
    }
}
