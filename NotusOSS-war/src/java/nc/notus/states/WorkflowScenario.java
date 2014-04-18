package nc.notus.states;

/**
 * This class enumerates possible scenarios of workflow
 * Scenarios should be referred by name in order to search for Scenario with
 * corresponding name in DB.
 * @author Igor Litvinenko
 */
public enum WorkflowScenario {
    NEW("New"),
    MODIFY("Modify"),
    DISCONNECT("Disconnect");

    private final String scenario;

    WorkflowScenario(String scenario) {
        this.scenario = scenario;
    }

    @Override
    public String toString() {
        return scenario;
    }
}
