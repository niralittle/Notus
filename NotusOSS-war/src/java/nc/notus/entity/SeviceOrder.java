/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.entity;

/**
 *
 * @author Vladimir Ermolenko
 */
public class SeviceOrder {
    private int id;
    private String serviceOrderDate;
    private int serviceOrderStatus;
    private int scenario;
    private int userID;
    private int serviceCatalogID;
    private int serviceInctanceID;
    private String serviceLocation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScenario() {
        return scenario;
    }

    public void setScenario(int scenario) {
        this.scenario = scenario;
    }

    public int getServiceCatalogID() {
        return serviceCatalogID;
    }

    public void setServiceCatalogID(int serviceCatalogID) {
        this.serviceCatalogID = serviceCatalogID;
    }

    public int getServiceInctanceID() {
        return serviceInctanceID;
    }

    public void setServiceInctanceID(int serviceInctanceID) {
        this.serviceInctanceID = serviceInctanceID;
    }

    public String getServiceLocation() {
        return serviceLocation;
    }

    public void setServiceLocation(String serviceLocation) {
        this.serviceLocation = serviceLocation;
    }

    public String getServiceOrderDate() {
        return serviceOrderDate;
    }

    public void setServiceOrderDate(String serviceOrderDate) {
        this.serviceOrderDate = serviceOrderDate;
    }

    public int getServiceOrderStatus() {
        return serviceOrderStatus;
    }

    public void setServiceOrderStatus(int serviceOrderStatus) {
        this.serviceOrderStatus = serviceOrderStatus;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public SeviceOrder() {
    }

    public SeviceOrder(int id, String serviceOrderDate, int serviceOrderStatus, int scenario, int userID, int serviceCatalogID, int serviceInctanceID, String serviceLocation) {
        this.id = id;
        this.serviceOrderDate = serviceOrderDate;
        this.serviceOrderStatus = serviceOrderStatus;
        this.scenario = scenario;
        this.userID = userID;
        this.serviceCatalogID = serviceCatalogID;
        this.serviceInctanceID = serviceInctanceID;
        this.serviceLocation = serviceLocation;
    }
}
