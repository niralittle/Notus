package nc.notus.dao.impl;

import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import nc.notus.dao.ServiceOrderDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.dbmanager.ResultIterator;
import nc.notus.dbmanager.Statement;
import nc.notus.entity.ServiceOrder;
import nc.notus.states.InstanceStatus;
import nc.notus.states.OrderStatus;

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

        if (serviceOrderStatus == null || serviceOrderStatus.isEmpty()) {
            throw new DBManagerException("Passed parameter " +
                    "<serviceOrderStatus> is null or empty.  " +
                    "Can't proccess the request!");
        }
        if (numberOfRecords < 1 || offset < 0) {
            throw new DBManagerException("Illegal argument in paging - " +
                    "less than 1. " + " Can't proccess the request!");
        }

        Statement statement = null;
        List<ServiceOrder> serviceOrders = null;
        ResultIterator ri = null;

        String query = "SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM ( " +
                "SELECT so.id, so.serviceorderdate, so.serviceorderstatusid, " +
                "so.scenarioid, so.userid, so.servicecatalogid, " +
                "so.serviceinstanceid, so.servicelocation " +
                "FROM serviceorder so " +
                "LEFT JOIN serviceorderstatus sos ON " +
                "so.serviceorderstatusid = sos.id " +
                "WHERE sos.status = ? " +
                "ORDER BY so.serviceorderdate, so.id " +
                ") a where ROWNUM <= ? ) " +
                "WHERE rnum  > ?";

        try {
            statement = dbManager.prepareStatement(query);
            statement.setString(1, serviceOrderStatus);
            statement.setInt(2, offset + numberOfRecords);
            statement.setInt(3, offset);

            ri = statement.executeQuery();
            serviceOrders = new ArrayList<ServiceOrder>();
            while (ri.next()) {
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
        } catch (DBManagerException exc) {
            throw new DBManagerException("The error was occured, " +
                    "contact the administrator");
        } finally {
            statement.close();
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

        if (scenario == null || scenario.isEmpty()) {
            throw new DBManagerException("Passed parameter <scenario> " +
                    "is null or empty.  Can't proccess the request!");
        }
        if (numberOfRecords < 1 || offset < 0) {
            throw new DBManagerException("Illegal argument in paging - " +
                    "less than 1. Can't proccess the request!");
        }

        Statement statement = null;
        List<ServiceOrder> serviceOrders = null;
        ResultIterator ri = null;


        String query = "SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM ( " +
                "SELECT so.id, so.serviceorderdate, so.serviceorderstatusid, " +
                "so.scenarioid, so.userid, so.servicecatalogid, " +
                "so.serviceinstanceid, so.servicelocation " +
                "FROM serviceorder so " +
                "LEFT JOIN scenario s ON so.scenarioid = s.id " +
                "WHERE s.scenario = ? " +
                "ORDER BY so.serviceorderdate, so.id " +
                ") a where ROWNUM <= ? ) " +
                "WHERE rnum  > ?";
        try {
            statement = dbManager.prepareStatement(query);
            statement.setString(1, scenario);
            statement.setInt(2, offset + numberOfRecords);
            statement.setInt(3, offset);
            ri = statement.executeQuery();
            serviceOrders = new ArrayList<ServiceOrder>();
            while (ri.next()) {
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
        } catch (DBManagerException exc) {
            throw new DBManagerException("The error was occured, " +
                    "contact the administrator");
        } finally {
            statement.close();
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
    public List<ServiceOrder> getSOByStatus(int userID, int serviceOrderStatus,
            int offset, int numberOfRecords) throws DBManagerException {
        if (numberOfRecords < 1 || offset < 0) {
            throw new DBManagerException("Illegal argument in paging - less " +
                    "than 1. Can't proccess the request!");
        }
        Statement statement = null;
        List<ServiceOrder> serviceOrders = null;
        ResultIterator ri = null;

        String query = "SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM (" +
                "SELECT so.id, so.serviceorderdate, so.serviceorderstatusid," +
                "so.scenarioid, so.userid, so.servicecatalogid," +
                "so.serviceinstanceid, so.servicelocation " +
                "FROM serviceorder so " +
                "WHERE so.userid = ? AND so.serviceorderstatusid = ? " +
                "ORDER BY so.serviceorderdate, so.id) a " +
                "WHERE ROWNUM <= ? )  " +
                "WHERE rnum  > ? ";
        try {
            statement = dbManager.prepareStatement(query);
            statement.setInt(1, userID);
            statement.setInt(2, serviceOrderStatus);
            statement.setInt(3, offset + numberOfRecords);
            statement.setInt(4, offset);
            ri = statement.executeQuery();
            serviceOrders = new ArrayList<ServiceOrder>();
            while (ri.next()) {
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
        } catch (DBManagerException exc) {
            throw new DBManagerException("The error was occured, " +
                    "contact the administrator");
        } finally {
            statement.close();
        }
        return serviceOrders;
    }

    /**
     * Method that returns number of ServiceOrders with specified status and userID
     * @param userID
     * @param serviceOrderStatus for searching
     * @return number of ServiceOrders with specified status and userID
     */
    @Override
    public int countAllSOByStatus(int userID, int serviceOrderStatus) throws DBManagerException {
        Statement statement = null;
        ResultIterator ri;

        String query = "SELECT COUNT(*) total FROM ( SELECT a.*, ROWNUM rnum FROM (" +
                "SELECT so.id, so.serviceorderdate, so.serviceorderstatusid," +
                "so.scenarioid, so.userid, so.servicecatalogid," +
                "so.serviceinstanceid, so.servicelocation " +
                "FROM serviceorder so " +
                "WHERE so.userid = ? AND so.serviceorderstatusid = ? ) a )";
        try {
            statement = dbManager.prepareStatement(query);
            statement.setInt(1, userID);
            statement.setInt(2, serviceOrderStatus);
            ri = statement.executeQuery();
            if (ri.next()) {
                return ri.getInt("total");
            }
            return 0;
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
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
    public ServiceOrder getServiceOrderBySIId(int serviceInstanceId)
            throws DBManagerException {
        ServiceOrder serviceOrder = null;
        Statement statement = null;
        ResultIterator ri = null;

        StringBuilder query = new StringBuilder();
        query.append("SELECT so.id, so.serviceOrderDate, so.scenarioID, ");
        query.append("so.userId, so.serviceCatalogId, so.serviceLocation, ");
        query.append("so.serviceOrderStatusId ");
        query.append("FROM SERVICEORDER so ");
        query.append("WHERE so.serviceInstanceId = ?");
        try {
            statement = dbManager.prepareStatement(query.toString());
            statement.setInt(1, serviceInstanceId);
            ri = statement.executeQuery();
            while (ri.next()) {
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
        } catch (DBManagerException exc) {
            throw new DBManagerException("The error was occured, " + "contact the administrator");
        } finally {
            statement.close();
        }
        return serviceOrder;
    }

    /**
     * Method that returns number of ServiceInstances with status Active
     * that user with specified ID has
     * @param userID
     * @return number of active instances of this user
     */
    public int countUsersActiveSIs(int userID) { 
        Statement statement = null;
        ResultIterator ri;

        String query = "SELECT COUNT(*) total " +
                       "FROM (SELECT so.serviceinstanceid " +
                              "FROM serviceorder so, serviceinstance si " +
                              "WHERE so.userid = ? " +
                              "AND so.serviceorderstatusid = ? " +
                              "AND so.serviceinstanceid = si.id " +
                              "AND si.serviceinstancestatusid = ?)";
        try {
            statement = dbManager.prepareStatement(query);
            statement.setInt(1, userID);
            statement.setInt(2, OrderStatus.COMPLETED.toInt());
            statement.setInt(3, InstanceStatus.ACTIVE.toInt());
            ri = statement.executeQuery();
            if (ri.next()) {
                return ri.getInt("total");
            }
        } catch (DBManagerException ex) {
            Logger.getLogger(ServiceOrderDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
        return 0;
    }
}
