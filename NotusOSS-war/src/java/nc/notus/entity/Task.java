package nc.notus.entity;

/**
 *                                                                              // REVIEW: documentation expected
 * @author Vladimir Ermolenko
 */
public class Task {
    private int id;
    private int serviceOrderID;
    private Integer employeeID; // could be null
    private int roleID;
    private int taskStatusID;

    public Task() {
    }

    public Task(int id, int serviceOrderID, Integer employeeID, int roleID, int taskStatusID) {
        this.id = id;
        this.serviceOrderID = serviceOrderID;
        this.employeeID = employeeID;
        this.roleID = roleID;
        this.taskStatusID = taskStatusID;
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
}
