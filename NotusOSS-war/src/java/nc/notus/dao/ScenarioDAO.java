package nc.notus.dao;

import nc.notus.entity.Scenario;
import nc.notus.states.WorkflowScenario;

/**
 * Interface of DAO for entity Scenario
 * @author Vladimir Ermolenko
 */
public interface ScenarioDAO extends GenericDAO<Scenario> {

    /**
     * Method returns ID of given Scenario
     * @param scenario Scenario to search ID for
     * @return ID of Scenario
     * @throws DAOException if Scenario was not found
     */
    public int getScenarioID(WorkflowScenario scenario);
}
