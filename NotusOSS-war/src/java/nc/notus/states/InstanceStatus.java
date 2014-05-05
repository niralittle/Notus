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
    DISCONNECTED("Disconnected"),
    PENDING_TO_DISCONNECT("Pending to disconnect");

    private final String status;

    InstanceStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
