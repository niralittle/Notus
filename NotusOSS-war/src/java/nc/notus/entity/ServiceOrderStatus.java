/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.entity;

/**
 *
 * @author Vladimir Ermolenko
 */
public class ServiceOrderStatus {
    private int id;
    private String status;

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

    public ServiceOrderStatus() {
    }

    public ServiceOrderStatus(int id, String status) {
        this.id = id;
        this.status = status;
    }
}
