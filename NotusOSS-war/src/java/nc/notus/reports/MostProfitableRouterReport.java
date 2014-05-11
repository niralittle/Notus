package nc.notus.reports;

import java.io.IOException;
import java.io.Writer;

import nc.notus.dao.ReportDAO;
import nc.notus.dao.impl.ReportDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.entity.MostProfitableRouterReportData;

/**
 * Represents report of most profitable router.
 * @author Andrey Ilin
 */
public class MostProfitableRouterReport extends AbstractReport {

    /* Report name */
    private String reportName;

    /* Separates columns in reportData row strings */
    private final String COLUMN_SEPARATOR = "#";

    /*
     * Report data stored here                                                  
     * Data is stored as strings that represents table rows
     * Columns in this rows are separated with COLUMN_SEPARATOR
     * First element is a row of column names
     */
    private String[] reportData;

    /**
     * Creates a report instance with given name
     * @param reportName
     * @throws DBManagerException 
     */
    public MostProfitableRouterReport(String reportName) throws DBManagerException {
        this.reportName = reportName;
        getDataFromDatabase();
    }

    private void getDataFromDatabase() throws DBManagerException {
        DBManager dbManager = new DBManager();
        try {
            ReportDAO reportDAO = new ReportDAOImpl(dbManager);

            this.reportName = "Most profitable router";

            /* Data */
            MostProfitableRouterReportData dev = reportDAO.getMostProfitableRouter();
            if (dev != null) {

                /* 1. Column headers 2. Device data */
                this.reportData = new String[2];

                /* Column headers */
                this.reportData[0] = "Router ID" + COLUMN_SEPARATOR + "Router name" +
                        COLUMN_SEPARATOR + "Port quantity" + COLUMN_SEPARATOR +
                        "Profit";

                this.reportData[1] = dev.getId() + COLUMN_SEPARATOR + dev.getName() +
                        COLUMN_SEPARATOR + dev.getPortQuantity() + COLUMN_SEPARATOR +
                        dev.getProfit();
            } else {
                this.reportData = new String[1];
                this.reportData[0] = "Router ID" + COLUMN_SEPARATOR + "Router name" +
                        COLUMN_SEPARATOR + "Port quantity" + COLUMN_SEPARATOR +
                        "Profit";
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
        return this.reportData;
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
            reportDAO.getMostProfitableRouter(writer, fileSeparator);
        } finally {
            dbManager.close();
        }
    }

    /**
     * Gets number of records per page
     * @return number of records per page
     */
    @Override
    public int getRecordsNumberPerPage() {
        return 1; //no pagination
    }
}
