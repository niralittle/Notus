package nc.notus.reports;

import java.io.OutputStream;

/**
 * Abstract representation of system report. All speciifc system reports have to
 * inherit this class.
 * @author Andrey Ilin
 */
public abstract class AbstractReport {                                          // REVIEW: make this interface and change name to Report

    /**
     * Gets a data for report from the database and handles it.
     */
    protected abstract void getReportData();                                    // REVIEW: why protected? public!

    /**
     * Generates report and writes it to output stream.
     * @param os output stream to be written
     */
    protected abstract void generateReport(OutputStream os);
}
