package nc.notus.entity;

import nc.notus.states.PortState;

/**
 * This class implements entity Port.
 * 
 * @author Vladimir Ermolenko
 */
public class Port {
	
	/**
	 * <tt>Port></tt> identifier.
	 */
    private int id;
    
    /**
     * Identifier of the device that contains current <tt>Port></tt>.
     */
    private int deviceID;
    
    /**
     * Port number in the device.
     */
    private int portNumber;
    
    /**
     * Port state such as BUZY or FREE.
     * 
     * @see {@link PortState}
     */
    private int portStatus;
    
    /**
     * Cable identifier that connected to the port.
     */
    private Integer cableID; // could be null

    /**
     * Construct empty <tt>Port</tt>
     */
    public Port() {
    }

    /**
     * Construct <tt>Port</tt>
     * 
     * @param id - <tt>Port></tt> identifier.
     * @param deviceID - Identifier of the device that contains current <tt>Port></tt>.
     * @param portNumber - Port number in the device.
     * @param portStatus - Port state such as BUZY or FREE.
     * @param cableID - Cable identifier that plug into the port.
     */
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
