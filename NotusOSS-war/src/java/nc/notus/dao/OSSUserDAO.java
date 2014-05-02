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
     * @param login of user
     * @return list of users with similar login
     */
    public List<OSSUser> getUsersByLogin(String login, int offset, int numberOfRecords);

    /**
     * Method returns list of users of specific role with paging
     * @param roleID - ID of role in system
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return list of users with similar login
     */
    public List<OSSUser> getUsersByRoleID(int roleID, int offset, int numberOfRecords);

    /**
     * Method returns users with specific login
     * @param login - login
     * @return users with specific login
     * or NULL if not found
     */
    public OSSUser getUserByLogin(String login);

    /**
     * Method returns list of users with similar email with paging
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @param email of user
     * @return list of users with similar email
     */
    public List<OSSUser> getUsersByEmail(String Email, int offset, int numberOfRecords);

    /**
     * Method returns list of users with similar lastname with paging
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @param lastname of user
     * @return list of users with similar login
     */
    public List<OSSUser> getUsersByLastName(String lastname, int offset, int numberOfRecords);
    
    /**
     * Check if user blocked.
     * 
     * @param login login to check
     * @return
     */
    boolean isBlocked(String login);
    
    /**
     * Check if user with specified login is already exist in system
     * 
     * @param login login to check
     * @return true - if exist, false - otherwise
     * @throws NullPointerException if null reference
     */
    boolean isExist(String login);
    
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
