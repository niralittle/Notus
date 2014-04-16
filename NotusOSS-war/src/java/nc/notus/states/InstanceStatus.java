/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.states;

/**
 * This class enumerates possible status of Service Instance.
 * SI status should be referred by name in order to search for status with
 * corresponding name in DB.
 * @author Igor Litvinenko
 */
public enum InstanceStatus {
    PLANNED("Planned"),
    ACTIVE("Active"),
    DISCONNECTED("Disconnected");

    private final String status;

    InstanceStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
