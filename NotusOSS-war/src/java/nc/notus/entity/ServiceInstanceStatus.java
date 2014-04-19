package nc.notus.entity;

/**
 * This class implements entity ServiceInstanceStatus
 * @author Vladimir Ermolenko
 */
public class ServiceInstanceStatus {
    private int id;
    private String status;

    public ServiceInstanceStatus() {
    }

    public ServiceInstanceStatus(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
