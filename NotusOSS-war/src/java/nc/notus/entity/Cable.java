/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.entity;

/**
 *
 * @author Vladimir Ermolenko
 */
public class Cable {
    private int id;
    private String cable;

    public Cable() {
    }

    public Cable(int id, String cable) {
        this.id = id;
        this.cable = cable;
    }

    public String getCable() {
        return cable;
    }

    public void setCable(String cable) {
        this.cable = cable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
