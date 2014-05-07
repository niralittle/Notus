package nc.notus.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import nc.notus.dao.ProviderLocationDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.dbmanager.ResultIterator;
import nc.notus.dbmanager.Statement;
import nc.notus.entity.ProviderLocation;

/**
 * Implementation of DAO for entity ProviderLocation
 * @author Vladimir Ermolenko
 */
public class ProviderLocationDAOImpl extends GenericDAOImpl<ProviderLocation> implements ProviderLocationDAO {

	private static Logger logger = Logger.getLogger(ProviderLocationDAOImpl.class.getName());
	
    public ProviderLocationDAOImpl(DBManager dbManager) {
        super(dbManager);
    }
    
    /**
     * Method returns list of all (numberOfRecords) provided locations with paging
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return list of providerLocations
     * @throws DBManagerException 
     */
    @Override
    public List<ProviderLocation> getProviderLocations(int offset, int numberOfRecords) throws DBManagerException {
    	if(numberOfRecords < 1 || offset < 1) {
    		logger.error("Illegal argument in paging - less than 1.");
    		throw new DBManagerException("Illegal argument in paging - less than 1. "
    				+ " Can't proccess the request!");
    	}
    	Statement statement = null;
    	ResultIterator ri = null;
    	List<ProviderLocation> providerLocations = null;
    	
    	String query  = "SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM (" +
                "SELECT pl.id, pl.name, pl.location FROM providerlocation pl" +
                ") a where ROWNUM <= ? )" +
                "WHERE rnum  >= ?";
		try {
			statement = dbManager.prepareStatement(query);
			statement.setInt(1, numberOfRecords);
			statement.setInt(2, offset);

			ri = statement.executeQuery();
			providerLocations = new ArrayList<ProviderLocation>();

			while (ri.next()) {
				ProviderLocation provLoc = new ProviderLocation();
				provLoc.setId(ri.getInt("id"));
				provLoc.setName(ri.getString("name"));
				provLoc.setLocation(ri.getString("location"));
				providerLocations.add(provLoc);
			}
		} catch (DBManagerException exc) {
			throw new DBManagerException ("The error was occured, " + 
					"contact the administrator");
		} finally {
			statement.close();
		}
        return providerLocations;
    }

}
