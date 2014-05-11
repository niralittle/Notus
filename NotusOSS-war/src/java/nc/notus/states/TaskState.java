package nc.notus.states;

/**
 * This class enumerates possible status of Tasks
 * @author Vladimir Ermolenko
 */
public enum TaskState {
    ACTIVE("Active"),
    SUSPENDED("Suspended"),
    COMPLETED("Completed");

    private TaskState(String state) {
        this.state = state;
    }

    private final String state;
    /**
     * Returns task state
     */
    @Override
    public String toString() {
        return state;
    }

}
