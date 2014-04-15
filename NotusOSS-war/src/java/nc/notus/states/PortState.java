package nc.notus.states;

/**
 *
 * @author Igor Litvinenko
 */
public enum PortState {
    FREE(0),
    BUSY(1);

    private final int state;

    PortState(int state) {
        this.state = state;
    }

    public int toInt() {
        return state;
    }
}
