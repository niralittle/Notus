/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.entity;

/**
 *
 * @author Vladimir Ermolenko
 */
public class ServiceInstance {
    private int id;
    private String serviceInstanceDate;
    private int serviceInstanceStatusID;
    private int circuitID;
    private int portID;

    public ServiceInstance() {
    }

    public ServiceInstance(int id, String serviceInstanceDate, int serviceInstanceStatusID, int circuitID, int portID) {
        this.id = id;
        this.serviceInstanceDate = serviceInstanceDate;
        this.serviceInstanceStatusID = serviceInstanceStatusID;
        this.circuitID = circuitID;
        this.portID = portID;
    }

    public int getCircuitID() {
        return circuitID;
    }

    public void setCircuitID(int circuitID) {
        this.circuitID = circuitID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPortID() {
        return portID;
    }

    public void setPortID(int portID) {
        this.portID = portID;
    }

    public String getServiceInstanceDate() {
        return serviceInstanceDate;
    }

    public void setServiceInstanceDate(String serviceInstanceDate) {
        this.serviceInstanceDate = serviceInstanceDate;
    }

    public int getServiceInstanceStatusID() {
        return serviceInstanceStatusID;
    }

    public void setServiceInstanceStatusID(int serviceInstanceStatusID) {
        this.serviceInstanceStatusID = serviceInstanceStatusID;
    }
}
