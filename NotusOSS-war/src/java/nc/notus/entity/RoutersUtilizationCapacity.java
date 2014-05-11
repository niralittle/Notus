package nc.notus.entity;

/**
 * This class implements entity RoutersUtilizationCapacity.
 * 
 * @author Vladimir Ermolenko
 */
public class RoutersUtilizationCapacity {

	/**
	 * Device identifier.
	 */
    private int deviceId;
    
    /**
	 * Name of device.
	 */
    private String deviceName;
    
    /**
     * Amount of ports in the device.
     */
    private int capacity;
    
    /**
     * Device utilization.
     */
    private float utilization;

    /**
     * Constructs empty RoutersUtilizationCapacity report.
     */
    public RoutersUtilizationCapacity() {
    }
    
    /**
     * Constructs <tt>RoutersUtilizationCapacity</tt> with passes parameters.
     * 
     * @param deviceId - Device identifier.
     * @param deviceName - Name of device.
     * @param capacity - Amount of ports in the device.
     * @param utilization - Device utilization.
     */
    public RoutersUtilizationCapacity(int deviceId, String deviceName, int capacity, int utilization) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.capacity = capacity;
        this.utilization = utilization;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public float getUtilization() {
        return utilization;
    }

    public void setUtilization(float utilization) {
        this.utilization = utilization;
    }
}
