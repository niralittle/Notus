package nc.notus.states;

/**
 * This class enumerates possible status of Service Instance.
 * SI status should be referred by name in order to search for status with
 * corresponding name in DB.
 * @author Igor Litvinenko
 */
public enum InstanceStatus {
    PLANNED("Planned", 1),
    ACTIVE("Active", 2),
    DISCONNECTED("Disconnected",3),
    PENDING_TO_DISCONNECT("Pending to disconnect", 4);
    
    private final String status;
    private final int id;
    

    InstanceStatus(String status, int id) {
        this.status = status;
        this.id = id;
    }

    @Override
    public String toString() {
        return status;
    }
    
    public int toInt() {
    	return id;
    }
}
