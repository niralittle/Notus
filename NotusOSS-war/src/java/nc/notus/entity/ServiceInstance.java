package nc.notus.entity;

import java.sql.Date;

/**
 * This class implements entity ServiceInstance
 * @author Vladimir Ermolenko
 */
public class ServiceInstance {
    private int id;
    private Date serviceInstanceDate;
    private int serviceInstanceStatusID;
    private Integer circuitID; // could be null
    private Integer portID;    // could be null

    public ServiceInstance() {
    }

    public ServiceInstance(int id, Date serviceInstanceDate, int serviceInstanceStatusID, Integer circuitID, Integer portID) {
        this.id = id;
        this.serviceInstanceDate = serviceInstanceDate;
        this.serviceInstanceStatusID = serviceInstanceStatusID;
        this.circuitID = circuitID;
        this.portID = portID;
    }

    public Integer getCircuitID() {
        return circuitID;
    }

    public void setCircuitID(Integer circuitID) {
        this.circuitID = circuitID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getPortID() {
        return portID;
    }

    public void setPortID(Integer portID) {
        this.portID = portID;
    }

    public Date getServiceInstanceDate() {
        return serviceInstanceDate;
    }

    public void setServiceInstanceDate(Date serviceInstanceDate) {
        this.serviceInstanceDate = serviceInstanceDate;
    }

    public int getServiceInstanceStatusID() {
        return serviceInstanceStatusID;
    }

    public void setServiceInstanceStatusID(int serviceInstanceStatusID) {
        this.serviceInstanceStatusID = serviceInstanceStatusID;
    }
}
