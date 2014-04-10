/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.entity;

/**
 *
 * @author Vladimir Ermolenko
 */
public class Port {
    private int id;
    private int deviceID;
    private int portNumber;
    private int portStatus;
    private int cableID;

    public Port() {
    }

    public Port(int id, int deviceID, int portNumber, int portStatus, int cableID) {
        this.id = id;
        this.deviceID = deviceID;
        this.portNumber = portNumber;
        this.portStatus = portStatus;
        this.cableID = cableID;
    }

    public int getCableID() {
        return cableID;
    }

    public void setCableID(int cableID) {
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
