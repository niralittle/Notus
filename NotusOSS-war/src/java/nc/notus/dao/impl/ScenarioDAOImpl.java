package nc.notus.dao.impl;

import org.apache.log4j.Logger;

import nc.notus.dao.DAOException;
import nc.notus.dao.ScenarioDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.dbmanager.ResultIterator;
import nc.notus.dbmanager.Statement;
import nc.notus.entity.Scenario;
import nc.notus.states.WorkflowScenario;

/**
 * Implementation of DAO for entity Scenario
 * @author Vladimir Ermolenko & Panchenko Dmytro
 */
public class ScenarioDAOImpl extends GenericDAOImpl<Scenario> implements ScenarioDAO {

	private static Logger logger = Logger.getLogger(ScenarioDAOImpl.class.getName());
	
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
    	Statement statement = null;
    	ResultIterator ri = null;
    	int scenarioId = 0;
    	
    	String queryString = "SELECT s.id, s.scenario " +
                "FROM scenario s WHERE s.scenario = ?";
		try {
			statement = dbManager.prepareStatement(queryString);
			statement.setString(1, scenario.toString());

			ri = statement.executeQuery();
			if (ri.next()) {
				scenarioId = ri.getInt("id");
			}
		} catch (DBManagerException exc) {
			logger.error(exc.getMessage(), exc);
		} finally {
			statement.close();
		}
		return scenarioId;
    }
}
