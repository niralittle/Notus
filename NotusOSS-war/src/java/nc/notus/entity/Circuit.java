package nc.notus.entity;

/**
 *                                                                              // REVIEW: documentation expected
 * @author Vladimir Ermolenko
 */
public class Circuit {
    private int id;
    private String ciruit;

    public Circuit() {
    }

    public Circuit(int id, String ciruit) {
        this.id = id;
        this.ciruit = ciruit;
    }

    public String getCiruit() {
        return ciruit;
    }

    public void setCiruit(String ciruit) {
        this.ciruit = ciruit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
