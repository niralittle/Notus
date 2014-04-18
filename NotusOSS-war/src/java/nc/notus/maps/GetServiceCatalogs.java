/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.maps;

import java.util.List;
import nc.notus.dao.impl.ServiceCatalogDAOImpl;
import nc.notus.dao.impl.ServiceTypeDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.ServiceCatalog;
import nc.notus.entity.ServiceType;

/**
 *
 * @author Alina
 */
public class GetServiceCatalogs {
    private int providerLocationID;
    private DBManager dbManager;

    public GetServiceCatalogs(int providerLocationID) {
        this.providerLocationID = providerLocationID;
        dbManager = new DBManager();
    }
    public List<ServiceCatalog> getServiceCatalogs(){
        ServiceCatalogDAOImpl catalogDAO = new ServiceCatalogDAOImpl(dbManager);
        List<ServiceCatalog> serviceCatalogs =
                catalogDAO.getServiceCatalogByProviderLocationID(providerLocationID, 1, 20);
        return serviceCatalogs;
    }
    public ServiceType getServiceType(ServiceCatalog serviceCatalog){
        ServiceTypeDAOImpl type = new ServiceTypeDAOImpl(dbManager);
        ServiceType serviceType = type.find(serviceCatalog.getServiceTypeID());
        return serviceType;
    }

}
