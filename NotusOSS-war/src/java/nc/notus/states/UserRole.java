package nc.notus.states;

/**
 * This class enumarates possible User Roles in System.
 * User role should be referred by role ID (because of the role mapping)
 * @author Igor Litvinenko
 */
public enum UserRole {
    ADMINISTRATOR(1),
    SUPPORT_ENGINEER(2),
    PROVISION_ENGINEER(3),
    INSTALLATION_ENGINEER(4),
    CUSTOMER_USER(5);

    private final int role;

    UserRole(int role) {
        this.role = role;
    }
    /**
     * Returns role id
     * @return id
     */
    public int toInt() {
        return role;
    }
}
