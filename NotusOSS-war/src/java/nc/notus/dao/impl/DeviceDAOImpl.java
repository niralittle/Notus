package nc.notus.dao.impl;

import java.util.ArrayList;
import java.util.List;
import nc.notus.dao.DAOException;
import nc.notus.dao.DeviceDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.ResultIterator;
import nc.notus.dbmanager.Statement;
import nc.notus.entity.Device;

/**
 * Implementation of DAO for entity Device
 * @author Andrey Ilin
 */
public class DeviceDAOImpl extends GenericDAOImpl<Device> implements DeviceDAO {

    public DeviceDAOImpl(DBManager dbManager) {
        super(dbManager);
    }

    /**
     * Gets a list of devices (routers) in system
     * @return list of Device objects
     */
    @Override
    public List<Device> getAllDevices() {                                       
        List<Device> deviceList = new ArrayList<Device>();
        Device device = null;
        String queryString = "SELECT id, name FROM device";
        Statement statement = dbManager.prepareStatement(queryString);
        ResultIterator ri = statement.executeQuery();
        while(ri.next()) {
            device = new Device();
            device.setId(ri.getInt("id"));
            device.setName(ri.getString("name"));
            device.setPortQuantity(ri.getInt("portQuantity"));
            deviceList.add(device);
        }
        return deviceList;
    }
}
