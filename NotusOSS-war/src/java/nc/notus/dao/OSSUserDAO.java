package nc.notus.dao;

import java.util.List;
import nc.notus.entity.OSSUser;
import nc.notus.states.UserRole;

/**
 * Interface of DAO for entity OSSUser
 * @author Vladimir Ermolenko
 */
public interface OSSUserDAO extends GenericDAO<OSSUser> {

    /**
     * Implementation of method for block accounts in DB
     * @param user - our user of type OSSUser
     */
    void blockUser(OSSUser user);

    /**
     * Method returns list of users with similar login with paging
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return list of users with similar login
     */
    public List<OSSUser> getUsersByLogin(String login, int offset, int numberOfRecords);

    /**
     * Method returns list of users with similar email with paging
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return list of users with similar email
     */
    public List<OSSUser> getUsersByEmail(String Email, int offset, int numberOfRecords);
    
    /**
     * Check if user with specified login is already exist in system
     * 
     * @param login login to check
     * @return true - if exist, false - otherwise
     * @throws NullPointerException if null reference
     */
    boolean isLoginDuplicate(String login);
    
    /**
     * Check if specified email is already exist in system
     * 
     * @param email email to check
     * @return true - if exist, false - otherwise
     */
    boolean isEmailDuplicate(String email);

    // TODO: documentation
    List<String> getGroupEmails(UserRole role);
}
