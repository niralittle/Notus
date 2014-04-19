package nc.notus.dao.impl;

import java.util.ArrayList;
import java.util.List;
import nc.notus.dao.DAOException;
import nc.notus.dao.ServiceOrderDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.ResultIterator;
import nc.notus.dbmanager.Statement;
import nc.notus.entity.ServiceOrder;

/**
 * Implementation of DAO for entity ServiceOrder
 * @author Andrey Ilin
 */
public class ServiceOrderDAOImpl extends GenericDAOImpl<ServiceOrder>
        implements ServiceOrderDAO {

    public ServiceOrderDAOImpl(DBManager dbManager) {
        super(dbManager);
    }
                                                                                // REVIEW: documentation expected
    public List<ServiceOrder> getServiceOrdersByScenario(String scenario) {
        List<ServiceOrder> deviceList = new ArrayList<ServiceOrder>();          // REVIEW: implementation too far from usage
        String queryString = "SELECT ServiceOrder.id, serviceOrderDate, " +
                "serviceOrderStatusID, scenarioID, userID, serviceCatalogID, " +
                "serviceInstanceID, serviceLocation " +
                "FROM ServiceOrder " +
                "INNER JOIN Scenario ON " +
                "ScenarioID = Scenario.id " +
                "WHERE scenario = '?'";                                         // REVIEW: maybe error: ? in brackets('?')
        Statement statement = dbManager.prepareStatement(queryString);
        statement.setString(1, scenario);
        ResultIterator ri = statement.executeQuery();
        if (!ri.next()) {
            throw new DAOException("No service orders with specified " +
                    "scenario were found in system");
        }
        do {
            ServiceOrder so = new ServiceOrder();
            so.setId(ri.getInt("id"));
            so.setServiceOrderDate(ri.getDate("serviceOrderDate"));
            so.setServiceOrderStatusID(ri.getInt("serviceOrderStatusID"));
            so.setScenarioID(ri.getInt("scenarioID"));
            so.setUserID(ri.getInt("userID"));
            so.setServiceCatalogID(ri.getInt("catalogID"));
            so.setServiceInstanceID(ri.getInt("serviceInstanceID"));
            so.setServiceLocation(ri.getString("serviceLocation"));
            deviceList.add(so);
        } while (ri.next());
        return deviceList;
    }

    /**
     * Gets a list of service orders in system with specific service status.
     * @param serviceStatus service status used to return specific list.
     * @return List of ServiceOrder objects.
     */
    @Override
    public List<ServiceOrder> getServiceOrdersByStatus(String serviceStatus) {

        List<ServiceOrder> deviceList = new ArrayList<ServiceOrder>();          // REVIEW: implementation too far from usage
        String queryString = "SELECT ServiceOrder.id, serviceOrderDate," +
                " serviceOrderStatusID, scenarioID, userID, serviceCatalogID, " +
                "serviceInstanceID, serviceLocation " +
                "FROM ServiceOrder " +
                "INNER JOIN ServiceOrderStatus ON " +
                "serviceOrderStatusID = ServiceOrderStatus.id" +
                "WHERE status = '?'";                                           // REVIEW: maybe error: ? in brackets('?')

        Statement statement = dbManager.prepareStatement(queryString);
        statement.setString(1, serviceStatus);
        ResultIterator ri = statement.executeQuery();

        if (!ri.next()) {
            throw new DAOException("No service orders with specified " +
                    "service status were found in system");
        }
        do {
            ServiceOrder so = new ServiceOrder();
            so.setId(ri.getInt("id"));
            so.setServiceOrderDate(ri.getDate("serviceOrderDate"));
            so.setServiceOrderStatusID(ri.getInt("serviceOrderStatusID"));
            so.setScenarioID(ri.getInt("scenarioID"));
            so.setUserID(ri.getInt("userID"));
            so.setServiceCatalogID(ri.getInt("catalogID"));
            so.setServiceInstanceID(ri.getInt("serviceInstanceID"));
            so.setServiceLocation(ri.getString("serviceLocation"));
            deviceList.add(so);
        } while (ri.next());
        return deviceList;
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
                                        int offset, int numberOfRecords) {
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
        statement.setInt(1, numberOfRecords);
        statement.setInt(2, offset);
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
}
