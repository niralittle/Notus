package nc.notus.entity;

/**
 * This class implements entity ServiceCatalog
 * @author Vladimir Ermolenko
 */
public class ServiceCatalog {
    private int id;
    private int providerLocationID;
    private int serviceTypeID;
    private int price;

    public ServiceCatalog() {
    }

    public ServiceCatalog(int id, int providerLocationID, int serviceTypeID, int price) {
        this.id = id;
        this.providerLocationID = providerLocationID;
        this.serviceTypeID = serviceTypeID;
        this.price = price;
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

    public int getProviderLocationID() {
        return providerLocationID;
    }

    public void setProviderLocationID(int providerLocationID) {
        this.providerLocationID = providerLocationID;
    }

    public int getServiceTypeID() {
        return serviceTypeID;
    }

    public void setServiceTypeID(int serviceTypeID) {
        this.serviceTypeID = serviceTypeID;
    }

}
