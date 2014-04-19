package nc.notus.entity;

/**
 * This class implements entity TaskStatus
 * @author Vladimir Ermolenko
 */
public class TaskStatus {
    private int id;
    private String status;

    public TaskStatus() {
    }

    public TaskStatus(int id, String status) {
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
