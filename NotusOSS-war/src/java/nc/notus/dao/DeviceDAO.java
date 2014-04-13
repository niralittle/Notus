package nc.notus.dao;

import java.util.List;
import nc.notus.entity.Device;

/**
 * Interface of DAO for entity Device
 * @author Andrey Ilin
 */
public interface DeviceDAO extends GenericDAO<Device> {

    /**
     * Gets a list of devices (routers) in system
     * @return list of Device objects
     */
    List<Device> getAllDevices();
}
