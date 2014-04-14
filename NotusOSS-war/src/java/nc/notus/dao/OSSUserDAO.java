package nc.notus.dao;

import nc.notus.entity.OSSUser;

/**
 * Interface of DAO for entity OSSUser
 * @author Vladimir Ermolenko
 */
public interface OSSUserDAO extends GenericDAO<OSSUser> {

    /**
     * Implementation of method for block accounts in DB
     * NC.KYIV.2014.WIND.REG.4	Account blocking is performed by an Administrator O
     * @param user - our user of type OSSUser
     */
    void blockUser(OSSUser user);  
    
    /**
     * Check if user with specified login is already exist in system
     * @param login login to check
     * @return true - if exist, false - otherwise
     */
    boolean isExist(String login);
}
