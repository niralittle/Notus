package nc.notus.entity;

/**
 * Entity that represents data about new service order for report.
 * @author Andrey Ilin
 */
public class ServiceOrderReportData {

    private int id;
    private String date;
    private String serviceLocation;
    private String serviceName;
    private int price;
    private String providerLocationName;
    private String providerLocation;

    public ServiceOrderReportData() {
    }

    public ServiceOrderReportData(int id, String date, String serviceLocation,
            String service, int price, String providerLocationName,
            String providerLocation) {
        this.id = id;
        this.date = date;
        this.serviceLocation = serviceLocation;
        this.serviceName = service;
        this.price = price;
        this.providerLocationName = providerLocationName;
        this.providerLocation = providerLocation;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getProviderLocation() {
        return providerLocation;
    }

    public void setProviderLocation(String providerLocation) {
        this.providerLocation = providerLocation;
    }

    public String getProviderLocationName() {
        return providerLocationName;
    }

    public void setProviderLocationName(String providerLocationName) {
        this.providerLocationName = providerLocationName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceLocation() {
        return serviceLocation;
    }

    public void setServiceLocation(String serviceLocation) {
        this.serviceLocation = serviceLocation;
    }
    

}
