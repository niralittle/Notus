package nc.notus.dao.impl;

import java.util.ArrayList;
import java.util.List;

import nc.notus.dao.ServiceInstanceDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.dbmanager.ResultIterator;
import nc.notus.dbmanager.Statement;
import nc.notus.entity.ServiceInstance;

/**
 * Implementation of DAO for entity ServiceInstance
 * @author Vladimir Ermolenko
 */
public class ServiceInstanceDAOImpl extends GenericDAOImpl<ServiceInstance>
        implements ServiceInstanceDAO {

	
    public ServiceInstanceDAOImpl(DBManager dbManager) {
        super(dbManager);
    }

    /**
     * Method that returns list of ServiceInstances for userID
     * @param userID
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return list ServiceInstances
     */
    @Override
    public List<ServiceInstance> getServiceInstancesByUserID(int userID, int offset, int numberOfRecords) throws DBManagerException {
    	if(numberOfRecords < 1 || offset < 1) {
    		throw new DBManagerException("Illegal argument in paging - less than 1. "
    				+ " Can't proccess the request!");
    	}    
    	Statement statement = null;
    	List<ServiceInstance> serviceInstances = null;
    	ResultIterator ri = null;
    	
    	String query  = "SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM ( " +
                            "SELECT si.id, si.serviceinstancedate, " +
                            "si.serviceinstancestatusid, si.circuitid, si.portid " +
                            "FROM serviceinstance si " +
                            "LEFT JOIN serviceorder so ON so.serviceinstanceid = si.id " +
                            "LEFT JOIN serviceinstancestatus sis ON si.serviceinstancestatusid = sis.id " +
                            "WHERE so.userid = ? " +
                            "AND sis.status = 'Active' " +
                            "ORDER BY so.serviceorderdate " +
                            ") a where ROWNUM <= ? ) " +
                            "WHERE rnum  >= ?";
		try {
			statement = dbManager.prepareStatement(query);
			statement.setInt(1, userID);
			statement.setInt(2, numberOfRecords);
			statement.setInt(3, offset);

			ri = statement.executeQuery();
			serviceInstances = new ArrayList<ServiceInstance>();
			while (ri.next()) {
				ServiceInstance servInstance = new ServiceInstance();
				servInstance.setId(ri.getInt("id"));
				servInstance.setServiceInstanceDate(ri.getDate("serviceinstancedate"));
				servInstance.setServiceInstanceStatusID(ri.getInt("serviceinstancestatusid"));
				servInstance.setCircuitID(ri.getInt("circuitid"));
				servInstance.setPortID(ri.getInt("portid"));
				serviceInstances.add(servInstance);
			}
		} catch (DBManagerException exc) {
			throw new DBManagerException("The error was occured, "
					+ "contact the administrator");
		} finally {
			statement.close();
		}
		return serviceInstances;
	}
    
    /**
     * Method that returns list of ServiceInstances for userID
     * @param userID
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return list ServiceInstances
     */
    @Override
    public List<ServiceInstance> getPendingToDisconnect(int userID, int offset, int numberOfRecords) throws DBManagerException {
    	if(numberOfRecords < 1 || offset < 1) {
    		throw new DBManagerException("Illegal argument in paging - less than 1. "
    				+ " Can't proccess the request!");
    	}    
    	Statement statement = null;
    	List<ServiceInstance> serviceInstances = null;
    	ResultIterator ri = null;
    	
    	String query  = "SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM ( " +
                            "SELECT si.id, si.serviceinstancedate, " +
                            "si.serviceinstancestatusid, si.circuitid, si.portid " +
                            "FROM serviceinstance si " +
                            "LEFT JOIN serviceorder so ON so.serviceinstanceid = si.id " +
                            "LEFT JOIN serviceinstancestatus sis ON si.serviceinstancestatusid = sis.id " +
                            "WHERE so.userid = ? " +
                            "AND sis.status = 'Pending to disconnect' " +
                            "ORDER BY so.serviceorderdate " +
                            ") a where ROWNUM <= ? ) " +
                            "WHERE rnum  >= ?";
		try {
			statement = dbManager.prepareStatement(query);
			statement.setInt(1, userID);
			statement.setInt(2, numberOfRecords);
			statement.setInt(3, offset);

			ri = statement.executeQuery();
			serviceInstances = new ArrayList<ServiceInstance>();
			while (ri.next()) {
				ServiceInstance servInstance = new ServiceInstance();
				servInstance.setId(ri.getInt("id"));
				servInstance.setServiceInstanceDate(ri.getDate("serviceinstancedate"));
				servInstance.setServiceInstanceStatusID(ri.getInt("serviceinstancestatusid"));
				servInstance.setCircuitID(ri.getInt("circuitid"));
				servInstance.setPortID(ri.getInt("portid"));
				serviceInstances.add(servInstance);
			}
		} catch (DBManagerException exc) {
			throw new DBManagerException("The error was occured, "
					+ "contact the administrator");
		} finally {
			statement.close();
		}
		return serviceInstances;
	}


}
