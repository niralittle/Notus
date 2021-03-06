package nc.notus.dao.impl;

import nc.notus.dao.PortDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
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
    * Returns a free port of system for an engineer.
    * portStatus field in SELECT is a flag with value
    * '0' for an available port and '1' for a connected port
    * @return one free port (Port instance) or NULL
    * if there is no available one
 * @throws DBManagerException 
    */ 
    @Override
    public Port getFreePort() throws DBManagerException {                                           
        Port  port = null;
        Statement statement = null;
        ResultIterator ri = null;
        
        String query = "SELECT p.id, p.deviceID, p.portNumber, p.portStatus, p.cableID " +
                       "FROM port p WHERE p.portStatus = 0 AND rownum <=1";
		try {
			statement = dbManager.prepareStatement(query);
			ri = statement.executeQuery();
			if (ri.next()) {
				port = new Port();
				port.setId(ri.getInt("id"));
				port.setDeviceID(ri.getInt("deviceID"));
				port.setPortNumber(ri.getInt("portNumber"));
				port.setPortStatus(ri.getInt("portStatus"));
				port.setCableID(ri.getInt("cableID"));
			}
		} catch (DBManagerException exc) {
			throw new DBManagerException ("The error was occured, " + 
					"contact the administrator");
		} finally {
			statement.close();
		}
        return port;
    }
    
}
