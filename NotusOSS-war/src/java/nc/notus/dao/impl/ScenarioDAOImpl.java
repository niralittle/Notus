package nc.notus.dao.impl;

import org.apache.log4j.Logger;

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
     * @throws DBManagerException 
     */
    @Override
    public int getScenarioID(WorkflowScenario scenario) throws DBManagerException {
    	if (scenario == null) {
    		logger.error("Passed parameter <scenario> is null. ");
    		throw new DBManagerException("Passed parameter <scenario> is null. "
    				+ " Can't proccess the request!");
    	} 
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
			throw new DBManagerException ("The error was occured, " + 
					"contact the administrator");
		} finally {
			statement.close();
		}
		return scenarioId;
    }
}
