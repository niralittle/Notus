package nc.notus.entity;

/**
 * This class implements entity Circuit
 * 
 * @author Vladimir Ermolenko
 */
public class Circuit {
		
	/**
	 * Circuit identifier.
	 */
    private int id;
    
    /**
     * Circuit name
     */
    private String circuit;

    /**
     * Constructs empty <tt>Circuit</tt>.
     */
    public Circuit() {
    }

    /**
     * Constructs <tt>Circuit</tt>.
     * 
     * @param id - <tt>Circuit</tt> identifier.
     * @param circuit - <tt>Circuit</tt> configuration.
     */
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
