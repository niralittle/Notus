package nc.notus.reports;

import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class contains operations to manipulate with system report object.
 * @author Andrey Ilin
 */
public class ReportGenerator {

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
     * @param type report's type
     * @param startDateString  date report term starts with
     * @param finishDateString date report term ends with
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
     */
    public String getReportHTML() {
        StringBuilder HTMLReportBuilder = new StringBuilder();
        HTMLReportBuilder.append("<table border='1' width='90%' cellpadding='10'>");
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
     * Returns a string that represents CSV report with separator specified
     * in COLUMN_SEPARATOR field
     * @return string that represents CSV report
     */
    public void getReportCSV(Writer writer) {
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
        } catch (IOException ex) {
            Logger.getLogger(ReportGenerator.class.getName()).log(Level.SEVERE,
                    "Work with writer in getReportCSV method went wrong",
                    ex);
        }
    }
}
