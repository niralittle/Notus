package nc.notus.entity;

/**
 * This class implements entity Scenario that represents 
 * type of service order is system such as NEW and DISCONNECT.
 * 
 * @author Vladimir Ermolenko
 */
public class Scenario {
	
	/**
	 * Scenario identifier.
	 */
    private int id;
    
    /**
     * Scenario name.
     */
    private String scenario;

    /**
     * Constructs empty a scenario.
     */
    public Scenario() {
    	
    }

    /**
     * Constructs scenario with passes parameters.
     * 
     * @param id - Scenario identifier.
     * @param scenario - Scenario name.
     */
    public Scenario(int id, String scenario) {
        this.id = id;
        this.scenario = scenario;
    }
  
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getScenario() {
        return scenario;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }
}
