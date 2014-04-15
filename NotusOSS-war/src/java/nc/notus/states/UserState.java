package nc.notus.states;

/**
 * This class enumerates possible states of User.
 * @author Igor Litvinenko
 */
public enum UserState {
    ACTIVE(0),
    BLOCKED(1);

    private final int state;

    UserState(int state) {
        this.state = state;
    }

    public int toInt() {
        return state;
    }
}
