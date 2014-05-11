package nc.notus.states;

/**
 * This class enumerates possible states of Port.
 * It could only be 'free' or 'busy' so it referred by intefer value of 0 and 1
 * correspondingly.
 * @author Igor Litvinenko
 */
public enum PortState {
    FREE(0),
    BUSY(1);

    private final int state;

    PortState(int state) {
        this.state = state;
    }
    /**
     * Returns port state
     * @return state
     */
    public int toInt() {
        return state;
    }
}
