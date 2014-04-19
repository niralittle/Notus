package nc.notus.dao.impl;

import nc.notus.dao.DAOException;
import nc.notus.dao.ServiceInstanceStatusDAO;
import nc.notus.dbmanager.DBManager;
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
    public int getServiceInstanceStatusID(InstanceStatus status) {
        String serviceInstanceStatusName = status.toString();
    	String queryString = "SELECT sis.id, sis.status " +
                             "FROM ServiceInstanceStatus sis " +
                             "WHERE sis.status = ?";
	Statement statement = dbManager.prepareStatement(queryString);
	statement.setString(1, serviceInstanceStatusName);
	ResultIterator ri = statement.executeQuery();
        if (ri.next()){
            return ri.getInt("id");
        } else {
            throw new DAOException("Given SI Status was not found in DB: " +
                    status.toString());
        }
    }
}
