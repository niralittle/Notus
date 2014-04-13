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

    /**
     * Return list of all our free ports in system
     * @return list of all free ports
     */
    public List<Port> getFreePort() {                                           // REVIEW: method should be renamed to getFreePorts()
        List<Port> fp = new ArrayList<Port>();
        Port  port = null;                                                      // REVIEW: port was not instantiated below
        String query = "SELECT p.id, p.deviceID, p.portNumber, p.portStatus, p.cableID" +
                       "FROM port p WHERE p.portStatus = 0";// portStatus  - it's a flag with 0 - free and with 1  - connected // REVIEW: comment formatting should be improved
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

    public PortDAOImpl(DBManager dbManager) {                                   // REVIEW: constructor should be placed at the top
        super(dbManager);
    }
}
