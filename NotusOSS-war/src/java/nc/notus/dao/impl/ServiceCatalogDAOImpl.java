package nc.notus.dao.impl;

import java.util.ArrayList;
import java.util.List;
import nc.notus.dao.ServiceCatalogDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.ResultIterator;
import nc.notus.dbmanager.Statement;
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
     * Method to obtain services and prices(ServiceCatalog) by providerLocationID
     * @param id - id of provider location
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return ServiceCatalog list of objects
     */
    @Override
    public List<ServiceCatalog> getServiceCatalogByProviderLocationID(int id, int offset, int numberOfRecords) {
        List<ServiceCatalog> serviceCatalogs = new ArrayList<ServiceCatalog>();
        String query  = "SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM (" +      // REVIEW: watch red line
                "SELECT sc.id, sc.providerlocationid, sc.servicetypeid, sc.price FROM servicecatalog sc " +
                "WHERE sc.providerlocationid = ?) a where ROWNUM <= ? )" +
                "WHERE rnum  >= ?";
        Statement statement = dbManager.prepareStatement(query);
        statement.setInt(1, id);
        statement.setInt(2, numberOfRecords);
        statement.setInt(3, offset);
        ResultIterator ri = statement.executeQuery();
        while (ri.next()){
            ServiceCatalog servCat = new ServiceCatalog();
            servCat.setId(ri.getInt("id"));
            servCat.setProviderLocationID(ri.getInt("providerlocationid"));
            servCat.setServiceTypeID(ri.getInt("servicetypeid"));
            servCat.setPrice(ri.getInt("price"));
            serviceCatalogs.add(servCat);
        }
        return serviceCatalogs;
    }
}
