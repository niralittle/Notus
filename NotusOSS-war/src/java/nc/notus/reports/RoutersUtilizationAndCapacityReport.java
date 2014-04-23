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
public class RoutersUtilizationAndCapacityReport implements Report {
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
                    finishDate, pageNumber * recordsPerPage, pageNumber);
            this.reportData = new String[routersUtilCap.size() + 1]; // +1 for column headers

            /* Column headers */
            this.reportData[0] = "Router name" + COLUMN_SEPARATOR + "Utilization" +
                    COLUMN_SEPARATOR + "Capacity";

            /* Data */
            for (int i = 1; i < this.reportData.length; i++) {
                this.reportData[i] = routersUtilCap.get(i).getDeviceName() +
                        COLUMN_SEPARATOR + routersUtilCap.get(i).getUtilization() +
                        COLUMN_SEPARATOR + routersUtilCap.get(i).getCapacity();
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

    @Override
    public String[] getNextDataPage() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
