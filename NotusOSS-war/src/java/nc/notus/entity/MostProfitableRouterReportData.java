package nc.notus.entity;

/**
 * Implements most profitable router entity.
 * 
 * @author Andrey Ilin
 */
public class MostProfitableRouterReportData {

	/**
	 * <tt>MostProfitableRouterReportData</tt> identifier.
	 */
    int id;
    
    /**
     * <tt>MostProfitableRouterReportData</tt> model name.
     */
    String name;
    
    /**
     * Quantity of port in the device.
     */
    int portQuantity;
    
    /**
     * Profit of device.
     */
    int profit;

    /**
     * Constructs empty <tt>MostProfitableRouterReportData</tt>.
     */
    public MostProfitableRouterReportData() {

    }

    /**
     * Constructs router.
     * 
     * @param id - device identifier.
     * @param name - device model name.
     * @param portQuantity - quantity of port in the device.
     * @param profit - profit of the device.
     */
    public MostProfitableRouterReportData(int id, String name, int portQuantity, int profit) {
        this.id = id;
        this.name = name;
        this.portQuantity = portQuantity;
        this.profit = profit;
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

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

}
