package nc.notus.dao.impl;

import java.util.ArrayList;
import java.util.List;


import nc.notus.dao.DeviceDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.dbmanager.ResultIterator;
import nc.notus.dbmanager.Statement;
import nc.notus.entity.Device;

/**
 * Implementation of DAO for entity Device
 * @author Andrey Ilin & Panchenko Dmytro
 */
public class DeviceDAOImpl extends GenericDAOImpl<Device> implements DeviceDAO {

    public DeviceDAOImpl(DBManager dbManager) {
        super(dbManager);
    }

    /**
     * Gets a list of devices (routers) in system                              
     * @return list of Device objects
     * @throws DBManagerException 
     */
    @Override
    public List<Device> getAllDevices() throws DBManagerException {
    	List<Device> deviceList = null;
    	Device device = null;
    	Statement statement = null;
       
        String queryString = "SELECT id, name FROM device";
		try {
			statement = dbManager.prepareStatement(queryString);
			ResultIterator ri = statement.executeQuery();

			deviceList = new ArrayList<Device>();
			while (ri.next()) {
				device = new Device();
				device.setId(ri.getInt("id"));
				device.setName(ri.getString("name"));
				device.setPortQuantity(ri.getInt("portQuantity"));
				deviceList.add(device);
			}
		} catch (DBManagerException exc) {
			throw new DBManagerException("The error was occured," + 
								"contact the administrator");
		} finally {
			statement.close();
		}      
        return deviceList;
    }
}
