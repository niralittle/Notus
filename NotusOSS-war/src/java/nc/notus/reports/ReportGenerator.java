package nc.notus.reports;

import nc.notus.dao.ReportDAO;
import nc.notus.dao.impl.ReportDAOImpl;
import java.sql.Date;
import java.util.ArrayList;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.Device;
import nc.notus.entity.ProfitInMonth;
import nc.notus.entity.ServiceOrder;
import nc.notus.entity.RoutersUtilizationCapacity;
import nc.notus.entity.ServiceInstance;

/**
 * This class is a abstract representation of system 
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

    /*
     * Report data stored here                                                  // JCC block-comments
     * Data is stored as strings that represents table rows
     * Columns in this rows are separated with COLUMN_SEPARATOR
     * First element is a row of column names
     */
    private String[] reportData;
    private ReportType reportType;
    private int recordsPerPage;
    private int pageNumber; // numbered from 0

    public static enum ReportType {

        MOST_PROFITABLE_ROUTER, DISCONNECT_ORDERS_PER_PERIOD,
        NEW_ORDERS_PER_PERIOD, ROUTERS_UTILIZATION_CAPACITY,
        PROFITABILITY_BY_MONTH
    };

    /**
     * Returns name of current report
     * @return name of current report
     */
    public String getReportName() {
        return this.reportName;
    }

    /**
     * Returns a report type specified in ReportType enum.
     * @return report type
     */
    public ReportType getReportType() {
        return this.reportType;
    }

    /**
     * Creates a new instance of ReportGenerator for specific report type for
     * specified date term
     * @param type report's type
     * @param startDateString  date report term starts with
     * @param finishDateString date report term ends with
     */
    public ReportGenerator(ReportType type, String startDateString,
            String finishDateString) {

        this.startDate = Date.valueOf(startDateString);
        this.finishDate = Date.valueOf(finishDateString);
        this.reportType = type;
        this.recordsPerPage = 10;
        String[] test = {"id#name#quant", "1#hello world#10"}; //TEST
        reportData = test; //TEST
        this.reportName = "Most profitable router"; //TEST

        //reportData = getReportData(type); UNCOMMENT WHEN FINISH

    }

    /**
     * Returns a report as html format string
     * @return html format string
     */
    public String getReportHTML() {
        String HTMLString = null;
        StringBuilder HTMLReportBuilder = new StringBuilder();
        HTMLReportBuilder.append("<table border='1' width='50%' cellpadding='5'>");
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

    /**
     * Retrieve data for specified report type from database
     * @param type report type
     * @return specially formatted string array with report data
     */
    private String[] getReportData(ReportType type) {
        ArrayList<ServiceOrder> orders = null;
        ArrayList<ServiceInstance> instances = null;
        ArrayList<RoutersUtilizationCapacity> routersUtilCap = null;
        ArrayList<ProfitInMonth> profit = null;
        String[] rows = null;
        DBManager dbManager = new DBManager();
        try {
            ReportDAO rd = new ReportDAOImpl(dbManager);
            switch (type) {
                case MOST_PROFITABLE_ROUTER:
                    this.reportName = "Most profitable router";

                    /* 1. Column headers 2. Device data */
                    rows = new String[2];

                    /* Column headers */
                    rows[0] = "Router ID" + COLUMN_SEPARATOR + "Router name" +
                            COLUMN_SEPARATOR + "Port quantity";

                    /* Data */
                    Device dev = rd.getMostProfitableRouter(startDate, finishDate);
                    rows[1] = dev.getId() + COLUMN_SEPARATOR + dev.getName() +
                            COLUMN_SEPARATOR + dev.getPortQuantity();
                    break;
                case DISCONNECT_ORDERS_PER_PERIOD:
                    this.reportName = "Disconnect orders per period";
                    instances = (ArrayList<ServiceInstance>) rd.getDisconnectedServiceInstances(
                            startDate, finishDate, pageNumber * recordsPerPage, recordsPerPage);
                    rows = new String[orders.size() + 1]; // +1 for column headers

                    /* Column headers */
                    rows[0] = "Order ID" + COLUMN_SEPARATOR + "Order date";

                    /* Data */
                    for (int i = 1; i < rows.length; i++) {
                        rows[i] = instances.get(i).getId() + COLUMN_SEPARATOR +
                                instances.get(i).getServiceInstanceDate();
                    }
                case NEW_ORDERS_PER_PERIOD:
                    this.reportName = "New orders per period";
                    orders = (ArrayList<ServiceOrder>) rd.getNewServiceOrders(
                            startDate, finishDate, pageNumber * recordsPerPage, pageNumber);
                    rows = new String[orders.size() + 1]; // +1 for column headers

                    /* Column headers */
                    rows[0] = "Order ID" + COLUMN_SEPARATOR + "Order date";

                    /* Data */
                    for (int i = 1; i < rows.length; i++) {
                        rows[i] = orders.get(i).getId() + COLUMN_SEPARATOR +
                                orders.get(i).getServiceOrderDate();
                    }
                    break;
                case ROUTERS_UTILIZATION_CAPACITY:
                    this.reportName = "Routers utilization and capacity";
                    routersUtilCap = (ArrayList<RoutersUtilizationCapacity>) rd.getRoutersUtilizationCapacityData(
                            startDate, finishDate, pageNumber * recordsPerPage, pageNumber);
                    rows = rows = new String[orders.size() + 1]; // +1 for column headers

                    /* Column headers */
                    rows[0] = "Router name" + COLUMN_SEPARATOR + "Utilization" +
                            COLUMN_SEPARATOR + "Capacity";

                    /* Data */
                    for (int i = 1; i < rows.length; i++) {
                        rows[i] = routersUtilCap.get(i).getDeviceName() +
                                COLUMN_SEPARATOR + routersUtilCap.get(i).getUtilization() +
                                COLUMN_SEPARATOR + routersUtilCap.get(i).getCapacity();
                    }
                    break;
                case PROFITABILITY_BY_MONTH:
                    this.reportName = "Profitability by month";
                    profit = (ArrayList<ProfitInMonth>) rd.getProfitByMonth(startDate,
                            finishDate);
                    rows = rows = new String[orders.size() + 1];

                    /* Column headers */
                    rows[0] = "Month" + COLUMN_SEPARATOR + "Profit";

                    /* Data */
                    for (int i = 1; i < rows.length; i++) {
                        rows[i] = profit.get(i).getMonth() +
                                COLUMN_SEPARATOR + profit.get(i).getProfit();
                    }
                    break;

            }
        } finally {
            dbManager.close();
        }
        return rows;
    }
}
