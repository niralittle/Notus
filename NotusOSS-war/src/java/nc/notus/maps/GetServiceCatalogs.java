package nc.notus.maps;

import java.util.List;
import nc.notus.dao.ServiceCatalogDAO;
import nc.notus.dao.impl.ServiceCatalogDAOImpl;
import nc.notus.dao.impl.ServiceTypeDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.ServiceCatalog;
import nc.notus.entity.ServiceType;

/**
 * Class for getting Service Catalogs via DAO
 * @author Alina
 */
public class GetServiceCatalogs {

    private final int START = 1;
    private final int LAST_RECORD = 20;
    private int providerLocationID;

    public GetServiceCatalogs(int providerLocationID) {
        this.providerLocationID = providerLocationID;
    }
    /*                                                                          // REVIEW: documentation with /** expected
     * Gets serviceCatalogs by providerLocationID via DAO
     */

    public List<ServiceCatalog> getServiceCatalogs() {
        DBManager dbManager = new DBManager();
        ServiceCatalogDAO catalogDAO = new ServiceCatalogDAOImpl(dbManager);
        List<ServiceCatalog> serviceCatalogs =
                catalogDAO.getServiceCatalogByProviderLocationID(providerLocationID, START, LAST_RECORD);
        dbManager.close();
        return serviceCatalogs;
    }
    /*
     * Gets serviceType by serviceTypeID via DAO                                // REVIEW: documentation with /** and params description expected
     */

    public ServiceType getServiceType(ServiceCatalog serviceCatalog) {
        DBManager dbManager = new DBManager();
        ServiceTypeDAOImpl type = new ServiceTypeDAOImpl(dbManager);
        ServiceType serviceType = type.find(serviceCatalog.getServiceTypeID());
        dbManager.close();
        return serviceType;
    }
}
