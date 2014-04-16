package nc.notus.states;

/**
 * This class enumerates possible status of Service Order.
 * Order status should be referred by name in order to search for status with
 * corresponding name in DB.
 * @author Igor Litvinenko
 */
public enum OrderStatus {
    ENTERING("Entering"),
    PROCESSING("Processing"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
