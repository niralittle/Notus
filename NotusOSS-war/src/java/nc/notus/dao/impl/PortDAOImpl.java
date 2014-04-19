package nc.notus.dao.impl;

import nc.notus.dao.PortDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.ResultIterator;
import nc.notus.dbmanager.Statement;
import nc.notus.entity.Port;

/**
 * Implementation of DAO for entity Port
 * @author Vladimir Ermolenko
 */
public class PortDAOImpl extends GenericDAOImpl<Port> implements PortDAO {

    public PortDAOImpl(DBManager dbManager) {
        super(dbManager);
    }

    /**
     * Return one free port in system for engineer                             
     * portStatus field in SQL select - it's a flag with 0 value as a free port
     * and with 1 value when port is connected
     * @return one free port or null if allthe ports are busy
     */
    
    @Override
    public Port getFreePort() {                                           
        Port  port = null;
        // portStatus  - it's a flag with 0 as free and with 1 when connected
        String query = "SELECT p.id, p.deviceID, p.portNumber, p.portStatus, p.cableID " +
                       "FROM port p WHERE p.portStatus = 0 AND rownum <=1";
        Statement statement = dbManager.prepareStatement(query);
        ResultIterator ri = statement.executeQuery();
        if (ri.next()) {
            port = new Port();
            port.setId(ri.getInt("id"));
            port.setDeviceID(ri.getInt("deviceID"));
            port.setPortNumber(ri.getInt("portNumber"));
            port.setPortStatus(ri.getInt("portStatus"));
            port.setCableID(ri.getInt("cableID"));
        }
        return port;
    }
    

}
