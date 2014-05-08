package nc.notus.reports;
import java.io.Writer;
import nc.notus.dbmanager.DBManagerException;

/**
 * Abstract representation of a system report.
 * All system reports should extend this class
 * @author Andrey Ilin
 */
public abstract class AbstractReport {

    /**
     * Returns report data as string array with specific strings.
     * This strings are representation of report row.
     * They are separated with COLUMN_SEPARATOR, that is specified for
     * each report and report generator.
     * @return report data as string array
     * @throws DBManagerException
     */
    public abstract String[] getReportData() throws DBManagerException;

    /**
     * Gets report name.
     * @return report name
     */
    public abstract String getReportName();

    /**
     * Writes all amount of report data to character stream.
     * Then data can be written to file.
     * Strings written at Writer are representation of report row.
     * Report rows are separated to columns with fileSeparator.
     * @param writer Writer object
     * @param fileSeparator data column separator
     * @throws DBManagerException
     */
    public abstract void getFileData(Writer writer, String fileSeparator)
            throws DBManagerException;

    /*
     * This methods should be overridden if report has paging functionality.
     * Return value of methods below specified for reports that don't have
     * paging functionality. Documentation given with implementations.
     *
     */
    protected boolean checkNextPage() throws DBManagerException {
        return false;
    }

    protected boolean getNextDataPage() throws DBManagerException {
        return false;
    }

    protected boolean getPreviousDataPage() throws DBManagerException {
        return false;
    }

    protected int getCurrentPageIndex() {
        return 0;
    }

    protected void setCurrentPageIndex(int pageIndex) throws DBManagerException {
    }
}
