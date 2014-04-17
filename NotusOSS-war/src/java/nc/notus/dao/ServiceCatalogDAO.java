package nc.notus.dao;

import java.util.List;
import nc.notus.entity.ServiceCatalog;

/**
 * Interface of DAO for entity ServiceCatalog.
 * @author Vladimir Ermolenko
 */
public interface ServiceCatalogDAO extends GenericDAO<ServiceCatalog> {

    /**
     * Method to obtain services and prices(ServiceCatalog) by providerLocationID
     * @param id - id of provider location
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return ServiceCatalog list of objects
     */
    public List<ServiceCatalog> getServiceCatalogByProviderLocationID(int id, int offset, int numberOfRecords);
}
