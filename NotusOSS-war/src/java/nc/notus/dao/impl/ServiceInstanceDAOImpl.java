package nc.notus.dao.impl;

import nc.notus.dao.ServiceInstanceDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.ServiceInstance;

/**
 * Implementation of DAO for entity ServiceInstance
 * @author Andrey Ilin
 */
public class ServiceInstanceDAOImpl extends GenericDAOImpl<ServiceInstance>
        implements ServiceInstanceDAO {

    public ServiceInstanceDAOImpl(DBManager dbManager) {
        super(dbManager);
    }

}
