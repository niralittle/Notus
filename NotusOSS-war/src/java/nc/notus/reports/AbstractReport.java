package nc.notus.reports;

import java.io.IOException;
import java.io.Writer;

/**
 * Abstract representation of a system report.
 * All system reports should extend this class
 * @author Andrey Ilin
 */
public abstract class AbstractReport {

    /**
     * Returns report data as string array with specific strings.
     * This strings are representation of report row.
     * They are separetad with COLUMN_SEPARATOR, that is specified for
     * each report and report generator.
     * @return report data as string array
     */
    public abstract String[] getReportData();

    /**
     * Gets report name.
     * @return report name
     */
    public abstract String getReportName();

    /**
     * Writes all emount of report data to character stream.
     * Then data can be written to file.
     * @param writer Writer object
     * @param fileSeparator data column separator
     */
    public abstract void getFileData(Writer writer, String fileSeparator)
            throws IOException;

    /*
     * This methods should be overridden if report has paging functionality.
     * Return value of methods below specified for reports that don't have
     * paging functionality.
     *
     */
    protected boolean checkNextPage() {
        return false;
    }

    protected boolean getNextDataPage() {
        return false;
    }

    protected boolean getPreviousDataPage() {
        return false;
    }

    protected int getCurrentPageIndex() {
        return 0;
    }

    protected void setCurrentPageIndex(int pageIndex) {
    }
}
