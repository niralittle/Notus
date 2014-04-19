package nc.notus.reports;
                                                                                // REVIEW: class should be deleted
import java.io.OutputStream;

/**
 * Interface provide methods for system reports.
 * All specific system reports should implement this interface.
 * @author Andrey Ilin
 */
public interface Report {                                          // REVIEW: make this interface and change name to Report

    /**
     * Generates report and writes it to output stream.
     * @param os output stream to be written
     */
    void generateReport(OutputStream os);
}
