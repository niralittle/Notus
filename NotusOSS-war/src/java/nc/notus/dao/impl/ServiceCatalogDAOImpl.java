package nc.notus.dao.impl;

import nc.notus.dao.ServiceCatalogDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.ServiceCatalog;

/**
 * Implementation of DAO for entity ServiceCatalog
 * @author Andrey Ilin
 */
public class ServiceCatalogDAOImpl extends GenericDAOImpl<ServiceCatalog>
        implements ServiceCatalogDAO {

    public ServiceCatalogDAOImpl(DBManager dbManager) {
        super(dbManager);
    }

}
