package nc.notus.entity;

import java.sql.Date;

/**
 * This class implements entity ServiceOrder
 * @author Vladimir Ermolenko
 */
public class ServiceOrder {

    private int id;
    private Date serviceOrderDate;
    private int serviceOrderStatusID;
    private int scenarioID;
    private int userID;
    private int serviceCatalogID;
    private Integer serviceInstanceID; // could be null
    private String serviceLocation;


    public ServiceOrder() {
    }

    public ServiceOrder(int id, Date serviceOrderDate, int serviceOrderStatusID,
                            int scenarioID, int userID, int serviceCatalogID,
                            Integer serviceInstanceID, String serviceLocation) {
        this.id = id;
        this.serviceOrderDate = serviceOrderDate;
        this.serviceOrderStatusID = serviceOrderStatusID;
        this.scenarioID = scenarioID;
        this.userID = userID;
        this.serviceCatalogID = serviceCatalogID;
        this.serviceInstanceID = serviceInstanceID;
        this.serviceLocation = serviceLocation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScenarioID() {
        return scenarioID;
    }

    public void setScenarioID(int scenarioID) {
        this.scenarioID = scenarioID;
    }

    public int getServiceCatalogID() {
        return serviceCatalogID;
    }

    public void setServiceCatalogID(int serviceCatalogID) {
        this.serviceCatalogID = serviceCatalogID;
    }

    public Integer getServiceInstanceID() {
        return serviceInstanceID;
    }

    public void setServiceInstanceID(Integer serviceInstanceID) {
        this.serviceInstanceID = serviceInstanceID;
    }

    public String getServiceLocation() {
        return serviceLocation;
    }

    public void setServiceLocation(String serviceLocation) {
        this.serviceLocation = serviceLocation;
    }

    public Date getServiceOrderDate() {
        return serviceOrderDate;
    }

    public void setServiceOrderDate(Date serviceOrderDate) {
        this.serviceOrderDate = serviceOrderDate;
    }

    public int getServiceOrderStatusID() {
        return serviceOrderStatusID;
    }

    public void setServiceOrderStatusID(int serviceOrderStatus) {
        this.serviceOrderStatusID = serviceOrderStatus;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
