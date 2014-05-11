package nc.notus.entity;

/**
 * Implements entity ProfitInMonth.
 * 
 * @author Vladimir Ermolenko
 */
public class ProfitInMonth {
	
	/**
	 * Devices profit by month.
	 */
    private int profit;
    
    /**
     * Month in which devices profit has calculating.
     */
    private String month;

    /**
     * Constructs empty <tt>ProfitInMont</tt>.
     */
    public ProfitInMonth() {
    	
    }

    /**
     * Constructs <tt>ProfitInMont</tt> with passed parameters. 
     * 
     * @param profit - Devices profit by month.
     * @param month - Month in which devices profit has calculating.
     */
    public ProfitInMonth(int profit, String month) {
        this.profit = profit;
        this.month = month;
    }
  
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }
}
