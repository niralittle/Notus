package nc.notus.reports;

import java.io.IOException;
import java.io.Writer;
import nc.notus.dbmanager.DBManagerException;
import org.apache.log4j.Logger;

/**
 * This class contains operations to manipulate with system report object.
 * @author Andrey Ilin
 */
public class ReportGenerator {

    private static Logger logger = Logger.getLogger(ReportGenerator.class.getName());
    /* Separates columns in reportData row strings */
    private final String COLUMN_SEPARATOR = "#";

    /*
     * Report data stored here                                                  
     * Data is stored as strings that represents table rows
     * Columns in this rows are separated with COLUMN_SEPARATOR
     * First element is a row of column names
     */
    private AbstractReport reportRef = null;

    /**
     * Creates a new instance of ReportGenerator for specific report 
     * @param report that would be generated
     */
    public ReportGenerator(AbstractReport report) {
        this.reportRef = report;
    }

    /**
     * Returns report associated with a report generator
     * @return report object inherited from AbstractReport
     */
    public AbstractReport getReport() {
        return this.reportRef;
    }

    /**
     * Returns name of the report to be generated
     * @return name of the report to be generated
     */
    public String getReportName() {
        return reportRef.getReportName();
    }

    /**
     * Returns a report as html format string
     * @return html format string
     * @throws DBManagerException 
     */
    public String getReportHTML() throws DBManagerException {
        StringBuilder HTMLReportBuilder = new StringBuilder();
        HTMLReportBuilder.append("<table class='table table-bordered table-striped table-hover'");
        if (reportRef.getReportData() != null) {
            String[] rows = reportRef.getReportData();
            String[] columns = rows[0].split(COLUMN_SEPARATOR); //headers
            HTMLReportBuilder.append("<tr>");
            for (int i = 0; i < columns.length; i++) {
                HTMLReportBuilder.append("<th>");
                HTMLReportBuilder.append(columns[i]);
                HTMLReportBuilder.append("</th>");
            }
            HTMLReportBuilder.append("</tr>");
            for (int i = 1; i < rows.length; i++) {
                columns = rows[i].split(COLUMN_SEPARATOR);
                HTMLReportBuilder.append("<tr>");
                for (int j = 0; j < columns.length; j++) {
                    HTMLReportBuilder.append("<td>");
                    HTMLReportBuilder.append(columns[j]);
                    HTMLReportBuilder.append("</td>");
                }
                HTMLReportBuilder.append("</tr>");
            }
            HTMLReportBuilder.append("</table>");
        }
        return HTMLReportBuilder.toString();
    }

    /**
     * Writes report data to the Writer object
     * @param writer
     * @throws DBManagerException
     */
    public void getReportCSV(Writer writer) throws DBManagerException {
        try {
            StringBuilder CSVReportHeaderBuilder = new StringBuilder();
            CSVReportHeaderBuilder.append("sep=");
            CSVReportHeaderBuilder.append(COLUMN_SEPARATOR);
            CSVReportHeaderBuilder.append("\n");
            CSVReportHeaderBuilder.append(reportRef.getReportName());
            CSVReportHeaderBuilder.append("\n");
            CSVReportHeaderBuilder.append(reportRef.getReportData()[0]);
            CSVReportHeaderBuilder.append("\n");
            writer.write(CSVReportHeaderBuilder.toString());
            reportRef.getFileData(writer, COLUMN_SEPARATOR);
        } catch (IOException exc) {
            logger.error(exc.getMessage(), exc);
        }
    }
}
