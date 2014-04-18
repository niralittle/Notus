package nc.notus.reports;

import nc.notus.dao.ReportDAO;
import nc.notus.dao.impl.ReportDAOImpl;
import java.sql.Date;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.Device;

/**
 *                                                                              // REVIEW: documentation expected
 * @author Andrey Ilin
 */
public class ReportGenerator {

    /* Report name */
    private String reportName;
    /* Separates columns in reportData row strings */
    private final String COLUMN_SEPARATOR = "#";

    /* Dates for report request */
    private Date startDate = null;
    private Date finishDate = null;

    /* Report data stored here                                                  // REVIEW: step bakc one line
     * Data is stored as strings that represents table rows
     * Columns in this rows are separated with COLUMN_SEPARATOR
     * First element is a row of column names
     */
    private String[] reportData;
    private ReportType reportType;

    public static enum ReportType {

        MOST_PROFITABLE_ROUTER, DISCONNECT_ORDERS_PER_PERIOD,
        NEW_ORDERS_PER_PERIOD, ROUTERS_UTILIZATION_CAPACITY,
        PROFITABILITY_BY_MONTH
    };

    public String getReportName() {                                             // REVIEW: documentation expected
        return this.reportName;
    }

    public ReportType getReportType() {                                         // REVIEW: documentation expected
        return this.reportType;
    }

    public ReportGenerator(ReportType type, String startDateString,             // REVIEW: documentation expected
            String finishDateString) {

        this.startDate = Date.valueOf(startDateString);
        this.finishDate = Date.valueOf(finishDateString);
        this.reportType = type;

        String[] test = {"id#name#quant", "1#hello world#10"}; //TEST
        reportData = test; //TEST
        this.reportName = "Most profitable router"; //TEST

        //reportData = getReportData(type); UNCOMMENT WHEN FINISH

    }

    public String getReportHTML() {                                             // REVIEW: documentation expected
        String HTMLString = null;
        StringBuilder HTMLReportBuilder = new StringBuilder();
        HTMLReportBuilder.append("<table border='1' width='50%' cellpadding='5'>");
//        HTMLReportBuilder.append("<tr><b>");
//        HTMLReportBuilder.append(reportName);
//        HTMLReportBuilder.append("</b></tr>");
        for (String row : reportData) {
            String[] columns = row.split(COLUMN_SEPARATOR);
            HTMLReportBuilder.append("<tr>");
            for (int i = 0; i < columns.length; i++) {
                HTMLReportBuilder.append("<td>");
                HTMLReportBuilder.append(columns[i]);
                HTMLReportBuilder.append("</td>");
            }
            HTMLReportBuilder.append("</tr>");
        }
        HTMLReportBuilder.append("</table>");
        HTMLString = HTMLReportBuilder.toString();
        return HTMLString.toString();
    }

    private String[] getReportData(ReportType type) {
        String[] rows = null;
        DBManager dbManager = new DBManager();                                  // REVIEW: try-finally block should be used
        ReportDAO rd = new ReportDAOImpl(dbManager);
        switch (type) {
            case MOST_PROFITABLE_ROUTER:
                this.reportName = "Most profitable router";
                rows = new String[2];

                /* First row with column headers
                 * 1.id 2.name 3.portQuantity
                 */
                rows[0] = "Router id" + COLUMN_SEPARATOR + "Router name" +
                        COLUMN_SEPARATOR + "Port quantity";
                Device dev = rd.getMostProfitableRouter(startDate, finishDate);
                rows[1] = dev.getId() + COLUMN_SEPARATOR + dev.getName() +
                        COLUMN_SEPARATOR + dev.getPortQuantity();
                break;
        }
        dbManager.close();
        return rows;
    }
}
