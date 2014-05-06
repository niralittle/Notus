package nc.notus.dao.impl;

import java.util.ArrayList;
import java.util.List;
import nc.notus.dao.ServiceOrderDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.dbmanager.ResultIterator;
import nc.notus.dbmanager.Statement;
import nc.notus.entity.ServiceOrder;

/**
 * Implementation of DAO for entity ServiceOrder
 * @author Vladimir Ermolenko
 */
public class ServiceOrderDAOImpl extends GenericDAOImpl<ServiceOrder>
        implements ServiceOrderDAO {

    public ServiceOrderDAOImpl(DBManager dbManager) {
        super(dbManager);
    }

    /**
     * Method that returns list of ServiceOrders with selected status
     * @param serviceOrderStatus for serching
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return list ServiceOrders with selected status
     */
    @Override
    public List<ServiceOrder> getServiceOrdersByStatus(String serviceOrderStatus,
                                        int offset, int numberOfRecords) throws DBManagerException {
        String query  = "SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM ( " +
                        "SELECT so.id, so.serviceorderdate, so.serviceorderstatusid, " +
                        "so.scenarioid, so.userid, so.servicecatalogid, " +
                        "so.serviceinstanceid, so.servicelocation " +
                        "FROM serviceorder so " +
                        "LEFT JOIN serviceorderstatus sos ON " +
                        "so.serviceorderstatusid = sos.id " +
                        "WHERE sos.status = ? " +
                        "ORDER BY so.serviceorderdate " +
                        ") a where ROWNUM <= ? ) " +
                        "WHERE rnum  >= ?";
        Statement statement = dbManager.prepareStatement(query);
        statement.setString(1, serviceOrderStatus);
        statement.setInt(2, numberOfRecords);
        statement.setInt(3, offset);
        ResultIterator ri = statement.executeQuery();
        List<ServiceOrder> serviceOrders = new ArrayList<ServiceOrder>();
        while (ri.next()){
            ServiceOrder servOrder = new ServiceOrder();
            servOrder.setId(ri.getInt("id"));
            servOrder.setServiceOrderDate(ri.getDate("serviceorderdate"));
            servOrder.setServiceOrderStatusID(ri.getInt("serviceorderstatusid"));
            servOrder.setScenarioID(ri.getInt("scenarioid"));
            servOrder.setUserID(ri.getInt("userid"));
            servOrder.setServiceCatalogID(ri.getInt("servicecatalogid"));
            servOrder.setServiceInstanceID(ri.getInt("serviceinstanceid"));
            servOrder.setServiceLocation(ri.getString("servicelocation"));
            serviceOrders.add(servOrder);
        }
        return serviceOrders;
    }

    /**
     * Method that returns list of ServiceOrders with selected scenario
     * @param scenario for searching
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return list ServiceOrders with selected scenario
     */
    @Override
    public List<ServiceOrder> getServiceOrdersByScenario(String scenario,
                                        int offset, int numberOfRecords) throws DBManagerException {
        String query  = "SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM ( " +
                        "SELECT so.id, so.serviceorderdate, so.serviceorderstatusid, " +
                        "so.scenarioid, so.userid, so.servicecatalogid, " +
                        "so.serviceinstanceid, so.servicelocation " +
                        "FROM serviceorder so " +
                        "LEFT JOIN scenario s ON so.scenarioid = s.id " +
                        "WHERE s.scenario = ? " +
                        "ORDER BY so.serviceorderdate " +
                        ") a where ROWNUM <= ? ) " +
                        "WHERE rnum  >= ?";
        Statement statement = dbManager.prepareStatement(query);
        statement.setString(1, scenario);
        statement.setInt(2, numberOfRecords);
        statement.setInt(3, offset);
        ResultIterator ri = statement.executeQuery();
        List<ServiceOrder> serviceOrders = new ArrayList<ServiceOrder>();
        while (ri.next()){
            ServiceOrder servOrder = new ServiceOrder();
            servOrder.setId(ri.getInt("id"));
            servOrder.setServiceOrderDate(ri.getDate("serviceorderdate"));
            servOrder.setServiceOrderStatusID(ri.getInt("serviceorderstatusid"));
            servOrder.setScenarioID(ri.getInt("scenarioid"));
            servOrder.setUserID(ri.getInt("userid"));
            servOrder.setServiceCatalogID(ri.getInt("servicecatalogid"));
            servOrder.setServiceInstanceID(ri.getInt("serviceinstanceid"));
            servOrder.setServiceLocation(ri.getString("servicelocation"));
            serviceOrders.add(servOrder);
        }
        return serviceOrders;
    }

    /**
     * Method that returns list of ServiceOrders with selected status
     * @param userID
     * @param serviceOrderStatus for searching
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return list ServiceOrders with selected status
     */
    @Override
    public List<ServiceOrder> getSOByStatus(int userID, int serviceOrderStatus, int offset, int numberOfRecords) throws DBManagerException {
        String query  = "SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM (" +
                        "SELECT so.id, so.serviceorderdate, so.serviceorderstatusid," +
                        "so.scenarioid, so.userid, so.servicecatalogid," +
                        "so.serviceinstanceid, so.servicelocation " +
                        "FROM serviceorder so " +
                        "WHERE so.userid = ? AND so.serviceorderstatusid = ? " +
                        "ORDER BY so.serviceorderdate) a " +
                        "WHERE ROWNUM <= ? )  " +
                        "WHERE rnum  >= ? ";
        Statement statement = dbManager.prepareStatement(query);
        statement.setInt(1, userID);
        statement.setInt(2, serviceOrderStatus);
        statement.setInt(3, numberOfRecords);
        statement.setInt(4, offset);
        ResultIterator ri = statement.executeQuery();
        List<ServiceOrder> serviceOrders = new ArrayList<ServiceOrder>();
        while (ri.next()){
            ServiceOrder servOrder = new ServiceOrder();
            servOrder.setId(ri.getInt("id"));
            servOrder.setServiceOrderDate(ri.getDate("serviceorderdate"));
            servOrder.setServiceOrderStatusID(ri.getInt("serviceorderstatusid"));
            servOrder.setScenarioID(ri.getInt("scenarioid"));
            servOrder.setUserID(ri.getInt("userid"));
            servOrder.setServiceCatalogID(ri.getInt("servicecatalogid"));
            servOrder.setServiceInstanceID(ri.getInt("serviceinstanceid"));
            servOrder.setServiceLocation(ri.getString("servicelocation"));
            serviceOrders.add(servOrder);
        }
        return serviceOrders;
    }
    
     /**
     * Return service order by specified SI id.
     * 
     * @param serviceInstanceId - passes SI id
     * @return instance of SO or null if not found
     * 
     * @author Panchenko Dmytro
     */
    @Override
    public ServiceOrder getServiceOrderBySIId(int serviceInstanceId) throws DBManagerException {
    	ServiceOrder serviceOrder = null;
    	
    	StringBuilder query = new StringBuilder();
    	query.append("SELECT so.id, so.serviceOrderDate, so.scenarioID, ");
    	query.append("so.userId, so.serviceCatalogId, so.serviceLocation, ");
    	query.append("so.serviceOrderStatusId ");
    	query.append("FROM SERVICEORDER so ");
    	query.append("WHERE so.serviceInstanceId = ?");
    	
        Statement statement = dbManager.prepareStatement(query.toString());
        statement.setInt(1, serviceInstanceId);
        ResultIterator ri = statement.executeQuery();
        while(ri.next()) {
        	serviceOrder = new ServiceOrder();
        	
        	serviceOrder.setId(ri.getInt("id"));
        	serviceOrder.setServiceOrderDate(ri.getDate("serviceOrderDate"));
        	serviceOrder.setScenarioID(ri.getInt("scenarioID"));
        	serviceOrder.setServiceOrderStatusID(ri.getInt("serviceOrderStatusId"));
        	serviceOrder.setUserID(ri.getInt("userId"));
        	serviceOrder.setServiceCatalogID(ri.getInt("serviceCatalogID"));
        	serviceOrder.setServiceLocation(ri.getString("serviceLocation"));
        	serviceOrder.setServiceInstanceID(serviceInstanceId);  	
        }
        return serviceOrder;
    }

}
