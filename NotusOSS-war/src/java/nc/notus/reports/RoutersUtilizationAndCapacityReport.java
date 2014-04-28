package nc.notus.reports;

import java.sql.Date;
import java.util.List;
import nc.notus.dao.ReportDAO;
import nc.notus.dao.impl.ReportDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.RoutersUtilizationCapacity;

/**
 * Represents utilization and capacity report.
 * @author Ilin Andrey
 */
public class RoutersUtilizationAndCapacityReport extends AbstractReport {
    /* Report name */

    private String reportName;

    /* Separates columns in reportData row strings */
    private final String COLUMN_SEPARATOR = "#";

    /* Dates for report request */
    private Date startDate = null;
    private Date finishDate = null;
    private int pageNumber = 0;
    private int recordsPerPage = 10;

    /*
     * Report data stored here
     * Data is stored as strings that represents table rows
     * Columns in this rows are separated with COLUMN_SEPARATOR
     * First element is a row of column names
     */
    private String[] reportData;

    /**
     * Creates a report instance with given name
     * @param reportName report name
     */
    public RoutersUtilizationAndCapacityReport(String reportName, String startDate,
            String finishDate) {
        this.startDate = Date.valueOf(startDate);
        this.finishDate = Date.valueOf(finishDate);
        this.reportName = reportName;
        getDataFromDatabase();
    }

    private void getDataFromDatabase() {
        this.reportName = "Routers utilization and capacity";
        DBManager dbManager = new DBManager();
        try {
            ReportDAO reportDAO = new ReportDAOImpl(dbManager);
            List<RoutersUtilizationCapacity> routersUtilCap =
                    reportDAO.getRoutersUtilizationCapacityData(startDate,
                    finishDate, pageNumber * recordsPerPage, recordsPerPage);
            this.reportData = new String[routersUtilCap.size() + 1]; // +1 for column headers

            /* Column headers */
            this.reportData[0] = "Router name" + COLUMN_SEPARATOR + "Utilization" +
                    COLUMN_SEPARATOR + "Capacity";

            /* Data */
            for (int i = 1; i < this.reportData.length; i++) {
                this.reportData[i] = routersUtilCap.get(i - 1).getDeviceName() +
                        COLUMN_SEPARATOR + routersUtilCap.get(i - 1).getUtilization() +
                        COLUMN_SEPARATOR + routersUtilCap.get(i - 1).getCapacity();
            }
        } finally {
            dbManager.close();
        }
    }

    /**
     * Get data for report from database and stores it in the private
     * variable.
     */
    @Override
    public String[] getReportData() {
        return reportData;
    }

    /**
     * Gets report name
     * @return report name
     */
    @Override
    public String getReportName() {
        return this.reportName;
    }

    /**
     * Gets a next data page for report
     * @return true - if this page is complete with specified number of records
     * and false - if not.
     */
    @Override
    public boolean getNextDataPage() {
        pageNumber++;
        getDataFromDatabase();
        if (reportData.length > 1 && reportData.length == recordsPerPage + 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets a previous data page for report
     * @return true - if this page isn't last and false - if not.
     */
    @Override
    public boolean getPreviousDataPage() {
        if (pageNumber > 0) {
            pageNumber--;
            getDataFromDatabase();
            if (pageNumber == 0) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * Sets a current page of report selected
     * @param pageIndex page to select
     */
    @Override
    public void setCurrentPageIndex(int pageIndex) {
        this.pageNumber = pageIndex;
        if (pageIndex > 0) {
            getDataFromDatabase();
        }
    }

    /**
     * Gets a currently selected page index
     * @return index of selected page
     */
    @Override
    public int getCurrentPageIndex() {
        return this.pageNumber;
    }

    @Override
    public boolean checkNextPage() {
        DBManager dbManager = new DBManager();
        try {
            ReportDAO reportDAO = new ReportDAOImpl(dbManager);
            List<RoutersUtilizationCapacity> routersUtilCap = reportDAO.getRoutersUtilizationCapacityData(startDate,
                    finishDate, (pageNumber + 1) * recordsPerPage, 1);
            if (routersUtilCap.size() == 0) {
                return false;
            } else {
                return true;
            }
        } finally {
            dbManager.close();
        }
    }
}
