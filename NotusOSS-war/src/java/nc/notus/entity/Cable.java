package nc.notus.entity;

/**
 * Implements entity Cable.
 * 
 * @author Vladimir Ermolenko
 */
public class Cable {
	
	/**
	 * Cable identificator .
	 */
    private int id;
    
    /**
     * Cable name.
     */
    private String cable;

    /**
     * Default constructor.
     */
    public Cable() {
    }

    /**
     * Constructs <tt>Cable</tt> object.
     * 
     * @param id - cable identificator.
     * @param cable - cable name.
     */
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
