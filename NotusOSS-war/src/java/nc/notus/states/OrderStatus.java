/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.states;

/**
 *
 * @author Igor Litvinenko
 */
public enum OrderStatus {
    ENTERING("entering"),
    PROCESSING("processing"),
    COMPLETED("completed"),
    CANCELLED("cancelled");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
