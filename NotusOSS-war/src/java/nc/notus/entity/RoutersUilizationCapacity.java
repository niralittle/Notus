package nc.notus.entity;

/**
 * Contain object for NC.KYIV.2014.WIND.REP.3 report: Routers utilization and capacity %// REVIEW: FR shouldn't be here
 * @author Vladimir Ermolenko
 */

public class RoutersUilizationCapacity {
    private String deviceName;
    private int capacity;
    private int utilization;

    public RoutersUilizationCapacity() {
    }

    public RoutersUilizationCapacity(String deviceName, int capacity, int utilization) {
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
