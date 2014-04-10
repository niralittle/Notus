/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.entity;

/**
 *
 * @author Vladimir Ermolenko
 */
public class Device {
    private int id;
    private String name;
    private int portQuantity;

    public Device() {
    }

    public Device(int id, String name, int portQuantity) {
        this.id = id;
        this.name = name;
        this.portQuantity = portQuantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPortQuantity() {
        return portQuantity;
    }

    public void setPortQuantity(int portQuantity) {
        this.portQuantity = portQuantity;
    }
}
