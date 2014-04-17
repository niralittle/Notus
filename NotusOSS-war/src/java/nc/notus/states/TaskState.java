/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.states;

/**
 * This class enumerates possible status of Tasks
 * @author Vladimir Ermolenko
 */
public enum TaskState {
    SUSPENDED("Suspended"),
    COMPLETED("Completed");

    private TaskState(String state) {
        this.state = state;
    }

    private final String state;

    @Override
    public String toString() {
        return state;
    }

}
