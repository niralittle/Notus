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

    /**
     * Gets a list of devices (routers) in system
     * @return list of Device objects
     */
    public List<Device> getDevices() {
        int fieldsNumber = 3;
        int columnCounter = 1; //columns are numbered from 1
        List<Device> deviceList = new ArrayList<Device>();
        Device device = null;
        String queryString = "SELECT * FROM device";
        Statement statement = dbManager.prepareStatement(queryString);
        ResultIterator ri = statement.executeQuery();
        if (!ri.next()) {
            throw new DAOException("No devices found in system");
        }
        do {
            device = new Device();
            for (int i = 0; i < fieldsNumber; i++) {
                device.setId(ri.getInt(columnCounter));
                columnCounter++;
                device.setName(ri.getString(columnCounter));
                columnCounter++;
                device.setPortQuantity(ri.getInt(columnCounter));
                columnCounter = 1;

            }
            deviceList.add(device);
        } while (ri.next());
        return deviceList;
    }
}
