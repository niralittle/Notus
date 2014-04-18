/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.dao.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import nc.notus.dao.ReportDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.ResultIterator;
import nc.notus.dbmanager.Statement;
import nc.notus.entity.Device;
import nc.notus.entity.ProfitInMonth;
import nc.notus.entity.RoutersUilizationCapacity;
import nc.notus.entity.ServiceInstance;
import nc.notus.entity.ServiceOrder;

/**
 * Implementation of DAO for our reports
 * @author Vladimir Ermolenko
 */
public class ReportDAOImpl implements ReportDAO {
    private DBManager dbManager;
    public ReportDAOImpl(DBManager dbManager) {
         this.dbManager = dbManager;
}
     /**
     * Method that return most profitable router in system
     * @param startDate - start of period
     * @param finishDate - finish of period
     * @return device which is most profitable per period
     */
    @Override
    public Device getMostProfitableRouter(Date startDate, Date finishDate) {
        Device device = new Device();

        // The query below needed in review with a lot of complex examples in table!

        String query = "SELECT p.deviceid, sum(sc.price*(? - ?)) total " +
                        "FROM serviceorder so " +
                        "LEFT JOIN servicecatalog sc ON so.servicecatalogid  = sc.id " +
                        "LEFT JOIN serviceinstance si ON so.serviceinstanceid = si.id " +
                        "LEFT JOIN serviceinstancestatus sis ON si.serviceinstancestatusid = sis.id " +
                        "LEFT JOIN port p ON si.portid  = p.id " +
                        "LEFT JOIN device d ON p.deviceid = d.id " +
                        "WHERE sis.status = 'Active' " +
                        "AND si.serviceinstancedate BETWEEN ? AND ? " +
                        "GROUP BY p.deviceid " +
                        "UNION ALL " +
                        "SELECT p.deviceid, sum(sc.price*(si.serviceinstancedate - ?)) total " +
                        "FROM serviceorder so " +
                        "LEFT JOIN servicecatalog sc ON so.servicecatalogid  = sc.id " +
                        "LEFT JOIN serviceinstance si ON so.serviceinstanceid = si.id " +
                        "LEFT JOIN serviceinstancestatus sis ON si.serviceinstancestatusid = sis.id " +
                        "LEFT JOIN port p ON si.portid  = p.id " +
                        "LEFT JOIN device d ON p.deviceid = d.id " +
                        "WHERE sis.status = 'Disconnected' " +
                        "AND si.serviceinstancedate BETWEEN ? AND ? " +
                        "GROUP BY p.deviceid " +
                        "ORDER BY total DESC";
        Statement statement = dbManager.prepareStatement(query);
        statement.setDate(1, finishDate);
        statement.setDate(2, startDate);
        statement.setDate(3, finishDate);
        statement.setDate(4, startDate);
        statement.setDate(5, startDate);
        statement.setDate(6, finishDate);
        statement.setDate(7, startDate);
        ResultIterator ri = statement.executeQuery();
        if (ri.next()){
            device.setId(ri.getInt("id"));
            device.setName(ri.getString("name"));
            device.setPortQuantity(ri.getInt("portQuantity"));
        }
        return device;
    }

    /**
     * Method that return list of new ServiceOrders per period
     * @param startDate - start of period
     * @param finishDate - finish of period
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return list of new ServiceOrders per period
     */
    @Override
    public List<ServiceOrder> getNewServiceOrders(Date startDate, Date finishDate, int offset, int numberOfRecords) {
        List<ServiceOrder> serviceOrders = new ArrayList<ServiceOrder>();
        String query  = "SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM ( " +
                        "SELECT so.id, so.serviceorderdate, so.serviceorderstatusid, " +
                        "       so.scenarioid, so.userid, so.servicecatalogid, so.serviceinstanceid, so.servicelocation " +
                        "FROM serviceorder so " +
                        "LEFT JOIN scenario s ON so.scenarioid = s.id " +
                        "WHERE so.serviceorderdate BETWEEN ? AND ? " +
                        "AND s.scenario = 'New' " +
                        "ORDER BY so.serviceorderdate " +
                        ") a where ROWNUM <= ? ) " +
                        "WHERE rnum  >= ?";
        Statement statement = dbManager.prepareStatement(query);
        statement.setDate(1, startDate);
        statement.setDate(2, finishDate);
        statement.setInt(3, numberOfRecords);
        statement.setInt(4, offset);
        ResultIterator ri = statement.executeQuery();
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
     * Method that return list of disconnected ServiceInstances per period
     * @param startDate - start of period
     * @param finishDate - finish of period
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return list of disconnected ServiceInstances per period
     */
    @Override
    public List<ServiceInstance> getDisconnectedServiceInstances(Date startDate, Date finishDate, int offset, int numberOfRecords) {
        List<ServiceInstance> serviceInstances = new ArrayList<ServiceInstance>();
        String query  = "SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM ( " +
                        "SELECT si.id, si.serviceinstancedate, si.serviceinstancestatusid, " +
                        "       si.circuitid, si.portid " +
                        "FROM serviceinstance si " +
                        "LEFT JOIN serviceinstancestatus sis ON si.serviceinstancestatusid = sis.id " +
                        "WHERE si.serviceinstancedate BETWEEN ? AND ? " +
                        "AND sis.status = 'Disconnected' " +
                        "ORDER BY si.serviceinstancedate " +
                        ") a where ROWNUM <= ? ) " +
                        "WHERE rnum  >= ?";
        Statement statement = dbManager.prepareStatement(query);
        statement.setDate(1, startDate);
        statement.setDate(2, finishDate);
        statement.setInt(3, numberOfRecords);
        statement.setInt(4, offset);
        ResultIterator ri = statement.executeQuery();
        while (ri.next()){
            ServiceInstance servInstance = new ServiceInstance();
            servInstance.setId(ri.getInt("id"));
            servInstance.setServiceInstanceDate(ri.getDate("serviceinstancedate"));
            servInstance.setServiceInstanceStatusID(ri.getInt("serviceinstancestatusid"));
            servInstance.setCircuitID(ri.getInt("circuitid"));
            servInstance.setPortID(ri.getInt("portid"));
            serviceInstances.add(servInstance);
        }
        return serviceInstances;
    }

    /**
     * Method that return list of objects for routers utilization and capacity report
     * @param startDate - start of period
     * @param finishDate - finish of period
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return list of objects for routers utilization and capacity report
     */
    @Override
    public List<RoutersUilizationCapacity> getRoutersUtilizationCapacityData(Date startDate, Date finishDate, int offset, int numberOfRecords) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Method that return list of objects for profitability by month report
     * @param startDate - start of period
     * @param finishDate - finish of period
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return list of objects for profitability by month report
     */
    @Override
    public List<ProfitInMonth> getProfitByMonth(Date startDate, Date finishDate, int offset, int numberOfRecords) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
