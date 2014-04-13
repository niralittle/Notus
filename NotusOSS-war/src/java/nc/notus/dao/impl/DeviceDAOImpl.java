package nc.notus.dao.impl;

import java.util.ArrayList;
import java.util.List;
import nc.notus.dao.DAOException;
import nc.notus.dao.DeviceDAO;
import nc.notus.dbmanager.ResultIterator;
import nc.notus.dbmanager.Statement;
import nc.notus.entity.Device;

/**
 * Implementation of DAO for entity Device
 * @author Andrey Ilin
 */
public class DeviceDAOImpl extends GenericDAOImpl<Device> implements DeviceDAO {

    private final int FIRST_COLUMN = 1;

    /**
     * Gets a list of devices (routers) in system
     * @return list of Device objects
     */
    public List<Device> getDevices() {
        int columnCounter = FIRST_COLUMN; //columns are numbered from 1
        List<Device> deviceList = new ArrayList<Device>();
        Device device = null;
        String queryString = "SELECT * FROM device";
        Statement statement = dbManager.prepareStatement(queryString);
        ResultIterator ri = statement.executeQuery();
        if (!ri.next()) {
            throw new DAOException("No devices were found in system");
        }
        do {
            device = new Device();
            device.setId(ri.getInt(columnCounter));
            columnCounter++;
            device.setName(ri.getString(columnCounter));
            columnCounter++;
            device.setPortQuantity(ri.getInt(columnCounter));
            columnCounter = FIRST_COLUMN;
            deviceList.add(device);
        } while (ri.next());
        return deviceList;
    }
}
