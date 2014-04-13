package nc.notus.dao.impl;

import nc.notus.dao.CableDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.Cable;

/**
 * Implementation of DAO for entity Cable
 * @author Igor Litvinenko
 */
public class CableDAOImpl extends GenericDAOImpl<Cable> implements CableDAO {

    public CableDAOImpl(DBManager dbManager) {
        super(dbManager);
    }

}
