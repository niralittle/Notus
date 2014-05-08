package nc.notus.reports;

import java.io.Writer;
import java.sql.Date;
import java.util.List;
import nc.notus.dao.ReportDAO;
import nc.notus.dao.impl.ReportDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.entity.ServiceOrderReportData;

/**
 * Represents report about disconnected orders per period
 * @author Andrey Ilin
 */
public class DisconnectOrdersPerPeriodReport extends AbstractReport {

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
     * First element is a row of column headers
     */
    private String[] reportData;

    /**
     * Creates a report instance with given name and specific data term.
     * Term is the interval between dates given as a parameters.
     * @param reportName report name
     * @param startDate  start date of the report term
     * @param finishDate end date of the report term
     * @throws DBManagerException
     */
    public DisconnectOrdersPerPeriodReport(String reportName, String startDate,
            String finishDate) throws DBManagerException {
        this.startDate = Date.valueOf(startDate);
        this.finishDate = Date.valueOf(finishDate);
        this.reportName = reportName;
        getDataFromDatabase();
    }

    /**
     * Gets data for report from database based on report type.
     * All data is stored in reportData private class variable.
     * @throws DBManagerException
     */
    private void getDataFromDatabase() throws DBManagerException {
        this.reportName = "Disconnect orders per period";
        DBManager dbManager = new DBManager();
        try {
            ReportDAO reportDAO = new ReportDAOImpl(dbManager);
            List<ServiceOrderReportData> orders = reportDAO.getDisconnectServiceOrders(
                    startDate, finishDate, pageNumber * recordsPerPage, recordsPerPage);
            this.reportData = new String[orders.size() + 1]; // +1 for column headers

            /* Column headers */
            this.reportData[0] = "Order ID" + COLUMN_SEPARATOR + "Order date" +
                    COLUMN_SEPARATOR + "Service location" + COLUMN_SEPARATOR +
                    "Service name" + COLUMN_SEPARATOR + "Price" + COLUMN_SEPARATOR +
                    "Provider location name" + COLUMN_SEPARATOR +
                    "Provider location";

            /* Data */
            for (int i = 1; i < this.reportData.length; i++) {
                this.reportData[i] = orders.get(i - 1).getId() + COLUMN_SEPARATOR +
                        orders.get(i - 1).getDate() + COLUMN_SEPARATOR +
                        orders.get(i - 1).getServiceLocation() + COLUMN_SEPARATOR +
                        orders.get(i - 1).getServiceName() + COLUMN_SEPARATOR +
                        orders.get(i - 1).getPrice() + COLUMN_SEPARATOR +
                        orders.get(i - 1).getProviderLocationName() + COLUMN_SEPARATOR +
                        orders.get(i - 1).getProviderLocation();
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
     * @return true - if this page is filled with specified number of records
     * and false - if not.
     * @throws DBManagerException
     */
    @Override
    public boolean getNextDataPage() throws DBManagerException {
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
     * @throws DBManagerException 
     */
    @Override
    public boolean getPreviousDataPage() throws DBManagerException {
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
     * Sets a current page of report as selected
     * @param pageIndex page to select
     * @throws DBManagerException
     */
    @Override
    public void setCurrentPageIndex(int pageIndex) throws DBManagerException {
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

    /**
     * Checks existence of next page for report
     * @return true if next page exists and false if not
     * @throws DBManagerException
     */
    @Override
    public boolean checkNextPage() throws DBManagerException {
        DBManager dbManager = new DBManager();
        try {
            ReportDAO reportDAO = new ReportDAOImpl(dbManager);
            List<ServiceOrderReportData> orders = reportDAO.getDisconnectServiceOrders(startDate,
                    finishDate, (pageNumber + 1) * recordsPerPage, 1);
            if (orders.size() == 0) {
                return false;
            } else {
                return true;
            }
        } finally {
            dbManager.close();
        }
    }

    /**
     * Writes all amount of report data to character stream.
     * Then data can be written to file.
     * Strings written at Writer are representation of report row.
     * Report rows are separated to columns with fileSeparator.
     * @param writer Writer object
     * @param fileSeparator data column separator
     * @throws DBManagerException
     */
    @Override
    public void getFileData(Writer writer, String fileSeparator)
            throws DBManagerException {
        DBManager dbManager = new DBManager();
        try {
            ReportDAO reportDAO = new ReportDAOImpl(dbManager);
            reportDAO.getDisconnectServiceOrders(writer, fileSeparator, startDate, finishDate);
        } finally {
            dbManager.close();
        }
    }
}
