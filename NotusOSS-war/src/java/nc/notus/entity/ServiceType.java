package nc.notus.entity;

/**
 * This class implements entity ServiceType
 * @author Vladimir Ermolenko
 */
public class ServiceType {
    private int id;
    private String service;

    public ServiceType() {
    }

    public ServiceType(int id, String service) {
        this.id = id;
        this.service = service;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

}
