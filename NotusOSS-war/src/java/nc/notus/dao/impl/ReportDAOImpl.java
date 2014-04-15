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
    public Device returnMostProfitableRouter() {
        Device device = new Device();
        String query = "SELECT d.id, d.name, d.portQuantity" +
                       "FROM device d" +
                       "WHERE rownum <=1"; // SELECT is not ready yet!
        Statement statement = dbManager.prepareStatement(query);
        ResultIterator ri = statement.executeQuery();
        if (ri.next()){
            device.setId(ri.getInt("id"));
            device.setName(ri.getString("name"));
            device.setPortQuantity(ri.getInt("portQuantity"));
        }
        return device;
    }
    
}
