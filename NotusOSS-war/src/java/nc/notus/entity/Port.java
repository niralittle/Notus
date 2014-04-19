package nc.notus.entity;

/**
 *                                                                              // REVIEW: documentation expected
 * @author Vladimir Ermolenko
 */
public class Port {
    private int id;
    private int deviceID;
    private int portNumber;
    private int portStatus;
    private Integer cableID; // could be null

    public Port() {
    }

    public Port(int id, int deviceID, int portNumber, int portStatus, Integer cableID) {
        this.id = id;
        this.deviceID = deviceID;
        this.portNumber = portNumber;
        this.portStatus = portStatus;
        this.cableID = cableID;
    }

    public Integer getCableID() {
        return cableID;
    }

    public void setCableID(Integer cableID) {
        this.cableID = cableID;
    }

    public int getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(int deviceID) {
        this.deviceID = deviceID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public int getPortStatus() {
        return portStatus;
    }

    public void setPortStatus(int portStatus) {
        this.portStatus = portStatus;
    }
}
