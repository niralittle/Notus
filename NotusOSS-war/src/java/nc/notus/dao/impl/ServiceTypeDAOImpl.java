package nc.notus.dao.impl;

import nc.notus.dao.ServiceTypeDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.ServiceType;

/**
 * Implementation of DAO for entity ServiceType
 * @author Vladimir Ermolenko
 */
public class ServiceTypeDAOImpl extends GenericDAOImpl<ServiceType> implements ServiceTypeDAO{

    public ServiceTypeDAOImpl(DBManager dbManager) {
        super(dbManager);
    }

}
