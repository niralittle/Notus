package nc.notus.reports;

import java.sql.Date;
import nc.notus.dao.ReportDAO;
import nc.notus.dao.impl.ReportDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.Device;

/**
 * Represents report of most profitable router.
 * @author Andrey Ilin
 */
public class MostProfitableRouterReport implements Report {

    /* Report name */
    private String reportName;

    /* Separates columns in reportData row strings */
    private final String COLUMN_SEPARATOR = "#";

    /* Dates for report request */
    private Date startDate = null;
    private Date finishDate = null;

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
     */
    public MostProfitableRouterReport(String reportName, String startDate,
            String finishDate) {
        this.reportName = reportName;
        this.startDate = Date.valueOf(startDate);
        this.finishDate = Date.valueOf(finishDate);
        getDataFromDatabase();
    }

    private void getDataFromDatabase() {
        DBManager dbManager = new DBManager();
        try {
            ReportDAO reportDAO = new ReportDAOImpl(dbManager);

            this.reportName = "Most profitable router";

            /* 1. Column headers 2. Device data */
            this.reportData = new String[2];

            /* Column headers */
            this.reportData[0] = "Router ID" + COLUMN_SEPARATOR + "Router name" +
                    COLUMN_SEPARATOR + "Port quantity";

            /* Data */
            Device dev = reportDAO.getMostProfitableRouter(startDate, finishDate);
            this.reportData[1] = dev.getId() + COLUMN_SEPARATOR + dev.getName() +
                    COLUMN_SEPARATOR + dev.getPortQuantity();
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

    @Override
    public String[] getNextDataPage() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
