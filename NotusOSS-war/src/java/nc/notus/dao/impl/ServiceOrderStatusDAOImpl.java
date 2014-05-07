package nc.notus.dao.impl;

import nc.notus.dao.ServiceOrderStatusDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.dbmanager.ResultIterator;
import nc.notus.dbmanager.Statement;
import nc.notus.entity.ServiceOrderStatus;
import nc.notus.states.OrderStatus;

/**
 * Implementation of DAO for entity ServiceOrderStatus
 * @author Vladimir Ermolenko & Pacnhenko Dmytro
 */
public class ServiceOrderStatusDAOImpl extends GenericDAOImpl<ServiceOrderStatus> implements ServiceOrderStatusDAO {
	
    public ServiceOrderStatusDAOImpl(DBManager dbManager) {
        super(dbManager);
    }

    /**
     * Method returns id of status of ServiceOrder
     * @param ServiceOrderStatusName
     * @return id of our ServiceOrder's status
     * @throws DAOException if id was not found
     */
    @Override
    public int getServiceOrderStatusID(OrderStatus status) throws DBManagerException {
    	if (status == null) {
    		throw new DBManagerException("Passed parameter <status> is null."
    				+ " Can't proccess null reference!");
    	} 
    	Statement statement = null;
    	ResultIterator ri = null;
    	
    	String serviceOrderStatusName = status.toString();
        String queryString = "SELECT sos.id, sos.status " +
                			  "FROM serviceorderstatus sos WHERE sos.status = ?";
		try {
			statement = dbManager.prepareStatement(queryString);
			statement.setString(1, serviceOrderStatusName);
			
			ri = statement.executeQuery();
			if (ri.next()) {
				return ri.getInt("id");
			} else {
				throw new DBManagerException(
						"Given Order Status was not found in DB: "
								+ status.toString());
			}
		} catch (DBManagerException exc) {
			throw new DBManagerException("The error was occured, "
					+ "contact the administrator");
		} finally {
			statement.close();
		}
    }
}
