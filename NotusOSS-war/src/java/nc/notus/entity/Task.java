/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.entity;

/**
 *
 * @author Vladimir Ermolenko
 */
public class Task {
    private int id;
    private int serviceOrderID;
    private int employeeID;
    private int roleID;
    private int taskStatusID;

    public Task() {
    }

    public Task(int id, int serviceOrderID, int employeeID, int roleID, int taskStatusID) {
        this.id = id;
        this.serviceOrderID = serviceOrderID;
        this.employeeID = employeeID;
        this.roleID = roleID;
        this.taskStatusID = taskStatusID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
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
