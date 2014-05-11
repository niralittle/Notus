package nc.notus.entity;

import nc.notus.states.UserRole;

/**
 * Library used to encrypt password to SHA-256 hash.
 */
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Implements entity OSSUser.
 * 
 * @author Vladimir Ermolenko
 */
public class OSSUser {
	
	/**
	 * <tt>OSSUser</tt> identifier.
	 */
    private int id;
    
    /**
     * <tt>OSSUser</tt> first name.
     */
    private String firstName;
    
    /**
     * <tt>OSSUser</tt> last name.
     */
    private String lastName;
    
    /**
     * <tt>OSSUser</tt> email.
     */
    private String email;
    
    /**
     * <tt>OSSUser</tt> login to authentication in system.
     */
    private String login;
    
    /**
     * <tt>OSSUser</tt> password used to authentication in system.
     */
    private String password;
    
    /**
     * Indicates that <tt>OSSUser</tt> blocked or not.
     */
    private int blocked;
    
    /**
     * <tt>OSSUser</tt> role in system.
     * 
     * @see {@link UserRole}
     */
    private int roleID;

    /**
     * Constructs empty <tt>OSSUser</tt>.
     */
    public OSSUser() {
    }

    /**
     * Constructs <tt>OSSUser</tt>.
     * 
     * @param id - <tt>OSSUser</tt> identifier.
     * @param firstName - <tt>OSSUser</tt> first name.
     * @param lastName - <tt>OSSUser</tt> last name.
     * @param email <tt>OSSUser</tt> email.
     * @param login - <tt>OSSUser</tt> login.
     * @param password - <tt>OSSUser</tt> clear-text password.
     * 					Set password in SHA-256 encryption format.
     * @param blocked - Indicates that <tt>OSSUser</tt> blocked or not.
     * @param roleID - tt>OSSUser</tt> role in system.
     */
    public OSSUser(int id, String firstName, String lastName, String email,
                    String login, String password, int blocked, int roleID) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.login = login;
        this.setPassword(password);
        this.blocked = blocked;
        this.roleID = roleID;
    }
    
    /**
     * Construct tt>OSSUser</tt> without specifying idetfier.
     * 
     * @param firstName - <tt>OSSUser</tt> first name.
     * @param lastName - <tt>OSSUser</tt> last name.
     * @param email <tt>OSSUser</tt> email.
     * @param login - <tt>OSSUser</tt> login.
     * @param password - <tt>OSSUser</tt> clear-text password.
     * 					Set password in SHA-256 encryption format.
     * @param blocked - Indicates that <tt>OSSUser</tt> blocked or not.
     * @param roleID - tt>OSSUser</tt> role in system.
     */
     public OSSUser(String firstName, String lastName, String email,
                    String login, String password, int blocked, int roleID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.login = login;
        this.setPassword(password);
        this.blocked = blocked;
        this.roleID = roleID;
    }

    public int getBlocked() {
        return blocked;
    }

    public void setBlocked(int blocked) {
        this.blocked = blocked;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    /**
     * This method calculates SHA-256 hash of given clear-text password and
     * writes it into class variable.
     * 
     * @param password clear-text password
     */
    public void setPassword(String password) {
        this.password =  DigestUtils.sha256Hex(password);
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }
}
