package nc.notus.dao.impl;

import nc.notus.dao.ServiceInstanceStatusDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.dbmanager.ResultIterator;
import nc.notus.dbmanager.Statement;
import nc.notus.entity.ServiceInstanceStatus;
import nc.notus.states.InstanceStatus;

/**
 * Implementation of DAO for entity ServiceInstance                                                                             // REVIEW: documentation expected
 * @author Igor Litvinenko
 */
public class ServiceInstanceStatusDAOImpl extends GenericDAOImpl<ServiceInstanceStatus> 
        implements ServiceInstanceStatusDAO {
	
    public ServiceInstanceStatusDAOImpl(DBManager dbManager) {
        super(dbManager);
    }

    /**
     * Method returns ID of status of ServiceInstance
     * @param InstanceStatus status of Service Instance
     * @return ID of given SI Status
     */
    @Override
    public int getServiceInstanceStatusID(InstanceStatus status) throws DBManagerException {
    	if (status == null) {
    		throw new DBManagerException("Passed parameter <status> is null "
    				+ " Can't proccess the request!");
    	} 
    	Statement statement = null;
    	ResultIterator ri = null;
    	String serviceInstanceStatusName = status.toString();
		String queryString = "SELECT sis.id, sis.status " +
                                     "FROM ServiceInstanceStatus sis " +
                                     "WHERE sis.status = ?";
		try {
			statement = dbManager.prepareStatement(queryString);
			statement.setString(1, serviceInstanceStatusName);
			ri = statement.executeQuery();
			if (ri.next()) {
				return ri.getInt("id");
			} else {
				throw new DBManagerException("Passed service instance id don't exist!");
			}
		} catch (DBManagerException exc) {
			throw new DBManagerException("The error was occured, "
					+ "contact the administrator");
		} finally {
			statement.close();
		}
	}
}
