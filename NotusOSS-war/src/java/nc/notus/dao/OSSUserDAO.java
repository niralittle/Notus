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
     * Return user by id with all text fields, not duplicated by findByID() method
     * @param id - id number of user
     * @return OSSUser object
     */
    public OSSUser getUserByID( int id);                                        
}
