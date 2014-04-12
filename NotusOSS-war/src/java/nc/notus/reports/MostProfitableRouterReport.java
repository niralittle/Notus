package nc.notus.reports;

import java.io.OutputStream;

/**
 * Represents a system report. Generates report about most profitable router.
 * @author Andrey Ilin
 */
public class MostProfitableRouterReport extends AbstractReport {

    /**
     * Gets a data for report from the database and handles it.
     */
    protected void getReportData() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Generates report and writes it to output stream.
     * @param os output stream to be written
     */
    protected void generateReport(OutputStream os) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
