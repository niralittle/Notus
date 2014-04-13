package nc.notus.dao.impl;

import java.util.ArrayList;
import java.util.List;
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
     * Return list of all our free ports in system
     * portStatus field in SQL select - it's a flag with 0 value as a free port and with 1 value when port is connected
     * @return list of all free ports
     */
    public List<Port> getFreePort() {                                           
        List<Port> fp = new ArrayList<Port>();
        Port  port = new Port();                                                     
        String query = "SELECT p.id, p.deviceID, p.portNumber, p.portStatus, p.cableID" +
                       "FROM port p WHERE p.portStatus = 0";// portStatus  - it's a flag with 0 as free and with 1 when connected
        Statement statement = dbManager.prepareStatement(query);
        ResultIterator ri = statement.executeQuery();
        while (ri.next()) {
            port.setId(ri.getInt("id"));
            port.setDeviceID(ri.getInt("deviceID"));
            port.setPortNumber(ri.getInt("portNumber"));
            port.setPortStatus(ri.getInt("portStatus"));
            port.setCableID(ri.getInt("cableID"));
            fp.add(port);
        }
        return fp;
    }
    

}
