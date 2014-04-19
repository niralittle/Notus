package nc.notus.entity;

/**
 *                                                                              // REVIEW: documentation expected
 * @author Vladimir Ermolenko
 */
public class ProviderLocation {
    private int id;
    private String name;
    private String location;

    public ProviderLocation() {
    }

    public ProviderLocation(int id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
