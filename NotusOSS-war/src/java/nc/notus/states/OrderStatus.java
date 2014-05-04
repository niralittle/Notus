package nc.notus.states;

/**
 * This class enumerates possible status of Service Order.
 * Order status should be referred by name in order to search for status with
 * corresponding name in DB.
 * @author Igor Litvinenko
 */
public enum OrderStatus {
    ENTERING("Entering",1),
    CANCELLED("Cancelled",2),
    PROCESSING("Processing",3),
    COMPLETED("Completed",4);

    private final String status;
    private final int statusId;

    OrderStatus(String status, int statusId) {
        this.status = status;
        this.statusId = statusId;
    }

    public int toInt() {
    	return statusId;
    }
    
    @Override
    public String toString() {
        return status;
    }
}
