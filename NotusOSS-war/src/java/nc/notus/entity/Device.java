package nc.notus.entity;

/**
 * Implements entity Device.
 * 
 * @author Vladimir Ermolenko
 */
public class Device {

	/**
	 * <tt>Device</tt> identifier.
	 */
    private int id;
    
    /**
     * <tt>Device</tt> model name.
     */
    private String name;
    
    /**
     * Quantity of ports in the device. 
     */
    private int portQuantity;

    /**
     * Constructs empty <tt>device</tt>.
     */
    public Device() {
    }
    
    /**
     * Constructs a device with passes parameters.
     * 
     * @param id - <tt>Device</tt> identifier.
     * @param name - <tt>Device</tt> model name.
     * @param portQuantity - Quantity of ports.
     */
    public Device(int id, String name, int portQuantity) {
        this.id = id;
        this.name = name;
        this.portQuantity = portQuantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPortQuantity() {
        return portQuantity;
    }

    public void setPortQuantity(int portQuantity) {
        this.portQuantity = portQuantity;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Device)) {
            return false;
        }
        Device dev = (Device) obj;
        if (dev.getId() != this.id) {
            return false;
        }
        if (!dev.getName().equals(this.name)) {
            return false;
        }
        if (dev.getPortQuantity() != this.portQuantity) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id;
        hash = 59 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 59 * hash + this.portQuantity;
        return hash;
    }
}
