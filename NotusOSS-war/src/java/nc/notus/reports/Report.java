/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.reports;

/**
 * Interace of a report. All system reports should implement this interface.
 * @author Andrey Ilin
 */
public interface Report {
    String[] getReportData();
    String getReportName();
    String[] getNextDataPage();

}
