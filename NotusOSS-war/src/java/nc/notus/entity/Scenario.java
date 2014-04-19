package nc.notus.entity;

/**
 * This class implements entity Scenario
 * @author Vladimir Ermolenko
 */
public class Scenario {
    private int id;
    private String scenario;

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

    public Scenario() {
    }

    public Scenario(int id, String scenario) {
        this.id = id;
        this.scenario = scenario;
    }

}
