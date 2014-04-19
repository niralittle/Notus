package nc.notus.entity;

/**
 * This class implements entity RoutersUtilizationCapacity
 * @author Vladimir Ermolenko
 */

public class RoutersUtilizationCapacity {
    private String deviceName;
    private int capacity;
    private int utilization;

    public RoutersUtilizationCapacity() {
    }

    public RoutersUtilizationCapacity(String deviceName, int capacity, int utilization) {
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

    public int getUtilization() {
        return utilization;
    }

    public void setUtilization(int utilization) {
        this.utilization = utilization;
    }
}
