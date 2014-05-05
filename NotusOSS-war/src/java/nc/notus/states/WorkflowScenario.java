package nc.notus.states;

/**
 * This class enumerates possible scenarios of workflow
 * Scenarios should be referred by name in order to search for Scenario with
 * corresponding name in DB.
 * @author Igor Litvinenko & Panchenko Dmytro
 */
public enum WorkflowScenario {
    NEW("New",1),
    MODIFY("Modify",2),
    DISCONNECT("Disconnect",3);

    private final String scenario;
    private final int scenarioId;
    
    WorkflowScenario(String scenario, int scenarioId) {
        this.scenario = scenario;
        this.scenarioId = scenarioId;
    }

    public int toInt() {
    	return scenarioId;
    }
    
    @Override
    public String toString() {
        return scenario;
    }
}
