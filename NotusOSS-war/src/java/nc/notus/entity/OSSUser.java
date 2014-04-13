/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.entity;

// library you can download om GoogleDisk in Development folder
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
    private String role;
    private int roleID;

    public OSSUser() {
    }

    public OSSUser(int id, String firstName, String lastName, String email, String login, String password, int blocked, int roleID) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.login = login;
        this.password = password;
        this.blocked = blocked;
        this.roleID = roleID;
    }
    
     public OSSUser(String firstName, String lastName, String email, String login, String password, int blocked, int roleID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.login = login;
        this.password = DigestUtils.sha256Hex(password);
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

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
