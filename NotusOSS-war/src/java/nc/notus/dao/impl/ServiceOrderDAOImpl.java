package nc.notus.dao.impl;

import java.util.ArrayList;
import java.util.List;
import nc.notus.dao.ServiceOrderDAO;
import nc.notus.dbmanager.DBManager;
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
                                        int offset, int numberOfRecords) {
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
}
