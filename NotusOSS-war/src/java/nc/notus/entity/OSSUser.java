package nc.notus.entity;

import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Vladimir Ermolenko
 */
public class OSSUser {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String login;
    private String password;
    private int blocked;
    private int roleID;

    public OSSUser() {
    }

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
