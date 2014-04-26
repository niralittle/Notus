/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.notus.reports;

/**
 * Abstract representation of a report. 
 * All system reports should extend this class
 * @author Andrey Ilin
 */
public abstract class AbstractReport {

    public abstract String[] getReportData();

    public abstract String getReportName();

    protected void getNextDataPage() {
    }

    protected void getPreviousDataPage() {
    }
}
