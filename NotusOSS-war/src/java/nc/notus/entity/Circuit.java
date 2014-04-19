package nc.notus.entity;

/**
 * This class implements entity Circuit
 * @author Vladimir Ermolenko
 */
public class Circuit {
    private int id;
    private String circuit;

    public Circuit() {
    }

    public Circuit(int id, String circuit) {
        this.id = id;
        this.circuit = circuit;
    }

    public String getCircuit() {
        return circuit;
    }

    public void setCircuit(String circuit) {
        this.circuit = circuit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
