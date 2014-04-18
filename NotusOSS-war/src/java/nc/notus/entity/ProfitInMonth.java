/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.entity;

/**
 * Contain object for NC.KYIV.2014.WIND.REP.3 report: Profitability by month
 * @author Vladimir Ermolenko
 */
public class ProfitInMonth {
    private int profit;
    private String month;

    public ProfitInMonth(int profit, String month) {
        this.profit = profit;
        this.month = month;
    }

    public ProfitInMonth() {
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
