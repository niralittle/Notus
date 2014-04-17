package nc.notus.dao;

import nc.notus.entity.ServiceCatalog;

/**
 * Interface of DAO for entity ServiceCatalog.
 * @author Vladimir Ermolenko
 */
public interface ServiceCatalogDAO extends GenericDAO<ServiceCatalog> {

    /**
     * Method to obtain services and prices(ServicCatalog) by providerLocationID
     * @param id
     * @return ServiceCatalog object
     */
    public ServiceCatalog getServiceCatalogByProviderLocationID(int id);
}
