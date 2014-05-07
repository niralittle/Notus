package nc.notus.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import nc.notus.dao.ServiceCatalogDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.dbmanager.ResultIterator;
import nc.notus.dbmanager.Statement;
import nc.notus.entity.ServiceCatalog;

/**
 * Implementation of DAO for entity ServiceCatalog
 * @author Vladimir Ermolenko & Panchenko Dmytro
 */
public class ServiceCatalogDAOImpl extends GenericDAOImpl<ServiceCatalog>
        implements ServiceCatalogDAO {

	private static Logger logger = Logger.getLogger(ServiceCatalogDAOImpl.class.getName());
	
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
    public List<ServiceCatalog> getServiceCatalogByProviderLocationID(int id, 
            int offset, int numberOfRecords) throws DBManagerException  {
       
		if (numberOfRecords < 1 || offset < 1) {
			logger.error("Illegal argument in paging - less than 1.");
			throw new DBManagerException("Illegal argument in paging - less than 1. "
							+ " Can't proccess the request!");
		}

		List<ServiceCatalog> serviceCatalogs = null;
		Statement statement = null;
		ResultIterator ri = null;

		String query = "SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM ("
				+ "SELECT sc.id, sc.providerlocationid, sc.servicetypeid, sc.price "
				+ "FROM servicecatalog sc "
				+ "WHERE sc.providerlocationid = ?) a where ROWNUM <= ? )"
				+ "WHERE rnum  >= ?";
		try {
			statement = dbManager.prepareStatement(query);
			statement.setInt(1, id);
			statement.setInt(2, numberOfRecords);
			statement.setInt(3, offset);
			ri = statement.executeQuery();
			serviceCatalogs = new ArrayList<ServiceCatalog>();
			while (ri.next()) {
				ServiceCatalog servCat = new ServiceCatalog();
				servCat.setId(ri.getInt("id"));
				servCat.setProviderLocationID(ri.getInt("providerlocationid"));
				servCat.setServiceTypeID(ri.getInt("servicetypeid"));
				servCat.setPrice(ri.getInt("price"));
				serviceCatalogs.add(servCat);
			}
		} catch (DBManagerException exc) {
			throw new DBManagerException("The error was occured, "
					+ "contact the administrator");
		} finally {
			statement.close();
		}
		return serviceCatalogs;
	}
}
