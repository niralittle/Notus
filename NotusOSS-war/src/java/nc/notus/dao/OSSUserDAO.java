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
     * @param user                                                              // REVIEW: no description of param
     */
    void blockUser(OSSUser user);
    
    /**
     * Return user by id with all text fields
     * @param id
     * @return OSSUser
     */
    public OSSUser getUserByID( int id);                                        // REVIEW: method duplicates find() method of CRUD and should be removed
}
