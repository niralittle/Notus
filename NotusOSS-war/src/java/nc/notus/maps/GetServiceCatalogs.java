package nc.notus.maps;

import java.util.ArrayList;
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
    private final int NUMBER_OF_RECORDS = 20;
    private int providerLocationID;

    public GetServiceCatalogs(int providerLocationID) {
        this.providerLocationID = providerLocationID;
    }

    /**
     * Gets serviceCatalogs by providerLocationID via DAO
     * @return serviceCatalogs - list of service catalogs
     */
    public List<ServiceCatalog> getServiceCatalogs() {
        DBManager dbManager = new DBManager();
        List<ServiceCatalog> serviceCatalogs = new ArrayList<ServiceCatalog>();
        try {
            ServiceCatalogDAO catalogDAO = new ServiceCatalogDAOImpl(dbManager);
            serviceCatalogs =
                    catalogDAO.getServiceCatalogByProviderLocationID(providerLocationID, START, NUMBER_OF_RECORDS); // REVIEW: all services for given provider location should be obtained
        } finally {
            dbManager.close();
        }
        return serviceCatalogs;
    }

    /**
     * Gets serviceType by serviceTypeID via DAO
     * @param serviceCatalog - service catalog
     * @return serviceType - type of service catalog
     */
    public ServiceType getServiceType(ServiceCatalog serviceCatalog) {
        DBManager dbManager = new DBManager();
        ServiceTypeDAOImpl type = new ServiceTypeDAOImpl(dbManager);
        ServiceType serviceType = type.find(serviceCatalog.getServiceTypeID());
        dbManager.close();
        return serviceType;
    }
}
