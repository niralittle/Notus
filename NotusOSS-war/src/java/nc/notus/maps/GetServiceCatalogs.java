package nc.notus.maps;

import java.util.ArrayList;
import java.util.List;
import nc.notus.dao.ServiceCatalogDAO;
import nc.notus.dao.ServiceTypeDAO;
import nc.notus.dao.impl.ServiceCatalogDAOImpl;
import nc.notus.dao.impl.ServiceTypeDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.entity.ServiceCatalog;
import nc.notus.entity.ServiceType;

/**
 * Class for getting Service Catalogs via DAO
 * @author Alina
 */
public class GetServiceCatalogs {

    private final int OFFSET = 0;
    private final int NUMBER_OF_RECORDS = 20;
    private int providerLocationID;

    public GetServiceCatalogs(int providerLocationID) {
        this.providerLocationID = providerLocationID;
    }

    /**
     * Gets serviceCatalogs by providerLocationID via DAO
     * @return serviceCatalogs - list of service catalogs
     */
    public List<ServiceCatalog> getServiceCatalogs() throws DBManagerException {
        DBManager dbManager = new DBManager();
        List<ServiceCatalog> serviceCatalogs = new ArrayList<ServiceCatalog>();
        try {
            ServiceCatalogDAO catalogDAO = new ServiceCatalogDAOImpl(dbManager);
            serviceCatalogs =
                    catalogDAO.getServiceCatalogByProviderLocationID(providerLocationID, OFFSET, NUMBER_OF_RECORDS);
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
    public ServiceType getServiceType(ServiceCatalog serviceCatalog) throws DBManagerException {
        DBManager dbManager = new DBManager();
        ServiceTypeDAO type = new ServiceTypeDAOImpl(dbManager);
        ServiceType serviceType = new ServiceType();
        try {
            serviceType = type.find(serviceCatalog.getServiceTypeID());
        } finally{
            dbManager.close();
        }
        return serviceType;
    }
}
