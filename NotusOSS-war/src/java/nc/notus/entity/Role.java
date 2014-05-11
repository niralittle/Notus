package nc.notus.entity;

/**
 * Implements entity Role.
 * 
 * @author Vladimir Ermolenko
 */
public class Role {
	
	/**
	 * Role identifier.
	 */
    private int id;
    
    /**
     * Name of role.
     */
    private String role;

    /**
     * Constructs empty role.
     */
    public Role() {
    	
    }

    /**
     * Constructs role with passed parameters.
     * 
     * @param id - Role identifier.
     * @param role - Role name.
     */
    public Role(int id, String role) {
        this.id = id;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
