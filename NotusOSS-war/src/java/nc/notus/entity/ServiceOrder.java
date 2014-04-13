package nc.notus.entity;

/**
 *
 * @author Vladimir Ermolenko
 */
public class ServiceOrder {

    private int id;
    private String serviceOrderDate;
    private int serviceOrderStatusID;
    private int scenarioID;
    private int userID;
    private int serviceCatalogID;
    private int serviceInstanceID;
    private String serviceLocation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScenarioID() {
        return scenarioID;
    }

    public void setScenario(int scenarioID) {
        this.scenarioID = scenarioID;
    }

    public int getServiceCatalogID() {
        return serviceCatalogID;
    }

    public void setServiceCatalogID(int serviceCatalogID) {
        this.serviceCatalogID = serviceCatalogID;
    }

    public int getServiceInstanceID() {
        return serviceInstanceID;
    }

    public void setServiceInstanceID(int serviceInstanceID) {
        this.serviceInstanceID = serviceInstanceID;
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

    public ServiceOrder() {
    }

    public ServiceOrder(int id, String serviceOrderDate, int serviceOrderStatusID, int scenarioID, int userID, int serviceCatalogID, int serviceInstanceID, String serviceLocation) {
        this.id = id;
        this.serviceOrderDate = serviceOrderDate;
        this.serviceOrderStatusID = serviceOrderStatusID;
        this.scenarioID = scenarioID;
        this.userID = userID;
        this.serviceCatalogID = serviceCatalogID;
        this.serviceInstanceID = serviceInstanceID;
        this.serviceLocation = serviceLocation;
    }
}
