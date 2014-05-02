package nc.notus.entity;

/**
 * This class implements entity RoutersUtilizationCapacity
 * @author Vladimir Ermolenko
 */
public class RoutersUtilizationCapacity {

    private int deviceId;
    private String deviceName;
    private int capacity;
    private float utilization;

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public RoutersUtilizationCapacity() {
    }

    public RoutersUtilizationCapacity(int deviceId, String deviceName, int capacity, int utilization) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.capacity = capacity;
        this.utilization = utilization;
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
