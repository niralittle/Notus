package nc.notus.maps;

import java.util.List;
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
    private int providerLocationID;
    private DBManager dbManager;                                                // REVIEW: DBManager wasn't closed (!)

    public GetServiceCatalogs(int providerLocationID) {
        this.providerLocationID = providerLocationID;
        dbManager = new DBManager();
    }
    /*                                                                          // REVIEW: documentation with /** expected
     * Gets serviceCatalogs by providerLocationID via DAO
     */
    public List<ServiceCatalog> getServiceCatalogs(){
        ServiceCatalogDAOImpl catalogDAO = new ServiceCatalogDAOImpl(dbManager);
        List<ServiceCatalog> serviceCatalogs =
                catalogDAO.getServiceCatalogByProviderLocationID(providerLocationID, 1, 20);// REVIEW: magic number found
        return serviceCatalogs;
    }
    /*
     * Gets serviceType by serviceTypeID via DAO                                // REVIEW: documentation with /** and params description expected
     */
    public ServiceType getServiceType(ServiceCatalog serviceCatalog){
        ServiceTypeDAOImpl type = new ServiceTypeDAOImpl(dbManager);
        ServiceType serviceType = type.find(serviceCatalog.getServiceTypeID());
        return serviceType;
    }

}
