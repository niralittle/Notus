/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.dao.impl;

import nc.notus.dao.ReportDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.ResultIterator;
import nc.notus.dbmanager.Statement;
import nc.notus.entity.Device;

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
     * @return device which is most profitable
     */
    @Override
    public Device returnMostProfitableRouter(String startDate, String finishDate) {
        Device device = new Device();

        // The query below needed in review with a lot of complex examples in table!

        String query = "SELECT p.deviceid, sum(sc.price*(TO_DATE(?, 'DD-MM-YYYY')-TO_DATE(?, 'DD-MM-YYYY'))) total " +
                        "FROM serviceorder so " +
                        "LEFT JOIN servicecatalog sc ON so.servicecatalogid  = sc.id " +
                        "LEFT JOIN serviceinstance si ON so.serviceinstanceid = si.id " +
                        "LEFT JOIN serviceinstancestatus sis ON si.serviceinstancestatusid = sis.id " +
                        "LEFT JOIN port p ON si.portid  = p.id " +
                        "LEFT JOIN device d ON p.deviceid = d.id " +
                        "WHERE sis.status = 'Active' " +
                        "AND si.serviceinstancedate BETWEEN TO_DATE(?, 'DD-MM-YYYY') AND TO_DATE(?, 'DD-MM-YYYY') " +
                        "GROUP BY p.deviceid " +
                        "UNION ALL " +
                        "SELECT p.deviceid, sum(sc.price*(si.serviceinstancedate -TO_DATE(?, 'DD-MM-YYYY'))) total " +
                        "FROM serviceorder so " +
                        "LEFT JOIN servicecatalog sc ON so.servicecatalogid  = sc.id " +
                        "LEFT JOIN serviceinstance si ON so.serviceinstanceid = si.id " +
                        "LEFT JOIN serviceinstancestatus sis ON si.serviceinstancestatusid = sis.id " +
                        "LEFT JOIN port p ON si.portid  = p.id " +
                        "LEFT JOIN device d ON p.deviceid = d.id " +
                        "WHERE sis.status = 'Disconnected' " +
                        "AND si.serviceinstancedate BETWEEN TO_DATE(?, 'DD-MM-YYYY') AND TO_DATE(?, 'DD-MM-YYYY') " +
                        "GROUP BY p.deviceid " +
                        "ORDER BY total DESC";
        Statement statement = dbManager.prepareStatement(query);
        statement.setString(1, finishDate);
        statement.setString(2, startDate);
        statement.setString(3, finishDate);
        statement.setString(4, startDate);
        statement.setString(5, startDate);
        statement.setString(6, finishDate);
        statement.setString(7, startDate);
        ResultIterator ri = statement.executeQuery();
        if (ri.next()){
            device.setId(ri.getInt("id"));
            device.setName(ri.getString("name"));
            device.setPortQuantity(ri.getInt("portQuantity"));
        }
        return device;
    }
    
}
