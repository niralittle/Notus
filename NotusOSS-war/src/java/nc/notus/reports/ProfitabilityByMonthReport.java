package nc.notus.reports;

import java.io.IOException;
import java.io.Writer;
import java.sql.Date;
import java.util.List;
import nc.notus.dao.ReportDAO;
import nc.notus.dao.impl.ReportDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.ProfitInMonth;

/**
 * Represents profitability by month report.
 * @author Andrey Ilin
 */
public class ProfitabilityByMonthReport extends AbstractReport {

    /* Report name */
    private String reportName;

    /* Separates columns in reportData row strings */
    private final String COLUMN_SEPARATOR = "#";

    /* Dates for report request */
    private Date month = null;

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
    public ProfitabilityByMonthReport(String reportName, String month) {
        this.month = Date.valueOf(month);
        this.reportName = reportName;
        getDataFromDatabase();
    }

    private void getDataFromDatabase() {
        DBManager dbManager = new DBManager();
        try {
            ReportDAO reportDAO = new ReportDAOImpl(dbManager);
            this.reportName = "Profitability by month";
            List<ProfitInMonth> profit = reportDAO.getProfitByMonth(month);
            this.reportData = new String[profit.size() + 1];

            /* Column headers */
            this.reportData[0] = "Month" + COLUMN_SEPARATOR + "Profit";

            /* Data */
            for (int i = 1; i < this.reportData.length; i++) {
                this.reportData[i] = profit.get(i - 1).getMonth() +
                        COLUMN_SEPARATOR + profit.get(i - 1).getProfit();
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

    @Override
    public void getFileData(Writer writer, String fileSeparator) throws IOException {
        
    }
}
