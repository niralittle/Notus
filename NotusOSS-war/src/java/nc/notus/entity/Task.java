package nc.notus.entity;

/**
 * This class implements entity Task
 * @author Vladimir Ermolenko
 */
public class Task {
    private int id;
    private int serviceOrderID;
    private Integer employeeID; // could be null
    private int roleID;
    private int taskStatusID;
    private long hash; // to prevent Lost Updates for implementing 
                       //Optimistic Locking using hashes in tasks updates

    public Task() {
    }

    public Task(int id, int serviceOrderID, Integer employeeID, int roleID, int taskStatusID, long hash) {
        this.id = id;
        this.serviceOrderID = serviceOrderID;
        this.employeeID = employeeID;
        this.roleID = roleID;
        this.taskStatusID = taskStatusID;
        this.hash = hash;
    }

    public Integer getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Integer employeeID) {
        this.employeeID = employeeID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public int getServiceOrderID() {
        return serviceOrderID;
    }

    public void setServiceOrderID(int serviceOrderID) {
        this.serviceOrderID = serviceOrderID;
    }

    public int getTaskStatusID() {
        return taskStatusID;
    }

    public void setTaskStatusID(int taskStatusID) {
        this.taskStatusID = taskStatusID;
    }

    public long getHash() {
            return hash;
    }

    public void setHash(long hash) {
        this.hash = hash;
    }
}
