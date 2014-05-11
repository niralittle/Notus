package nc.notus.entity;

/**
 * Implements entity ProviderLocation.
 * 
 * @author Vladimir Ermolenko
 */
public class ProviderLocation {
	
	/**
	 * ProviderLocation identifier.
	 */
    private int id;
    
    /**
     * Concrete provider name for concrete location. 
     */
    private String name;
    
    /**
     * Concrete provider location.
     */
    private String location;

    /**
     * Constructs empty provider location.
     */
    public ProviderLocation() {
    }

    /**
     * Construct provider location.
     * 
     * @param id - <tt>ProviderLocation</tt> identifier.
     * @param name - Concrete provider name for location. 
     * @param location - Concrete provider location.
     */
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
