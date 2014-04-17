package nc.notus.dao.impl;

import nc.notus.dao.ServiceCatalogDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.ServiceCatalog;

/**
 * Implementation of DAO for entity ServiceCatalog
 * @author Vladimir Ermolenko
 */
public class ServiceCatalogDAOImpl extends GenericDAOImpl<ServiceCatalog>
        implements ServiceCatalogDAO {

    public ServiceCatalogDAOImpl(DBManager dbManager) {
        super(dbManager);
    }

    /**
     * Method to obtain services and prices(ServicCatalog) by providerLocationID
     * @param id
     * @return ServiceCatalog object
     */
    @Override
    public ServiceCatalog getServiceCatalogByProviderLocationID(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
