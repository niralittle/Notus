package nc.notus.reports;
                                                                                // REVIEW: class should be deleted
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import nc.notus.dao.DeviceDAO;
import nc.notus.dao.PortDAO;
import nc.notus.dao.impl.DeviceDAOImpl;
import nc.notus.dao.impl.PortDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.Device;

/**
 * Represents utilization and capacity of the system routers report.
 * @author Andrey Ilin
 */
public class RoutersUtilizationCapacityReport implements Report {

    /* Separates data coluFIELD_SEPARATORmns in srings stored in reportData array */
    private final String COLUMN_SEPARATOR = "#";
    /*
     * Data for reports stored here 
     * Each string is a representation of a table column that stores report data
     * Columns are separated with COLUMN_SEPARATOR
     * String example: router name#router port quantity#router's utilization in %
     */
    private String[] reportData = null;

    /**
     * Gets a data for report from the database and stores data to reportData
     * class field, that is represented as string array.
     */
    private void retrieveReportData() {

        /* DBManager and DAOImpl instances creation */
        DBManager dbManager = new DBManager();
        PortDAO pdi = new PortDAOImpl(dbManager);
        DeviceDAO ddi = new DeviceDAOImpl(dbManager);

        ArrayList<Device> devices = (ArrayList<Device>) ddi.getAllDevices();
        int portStatusValue = 0; // 0 - port is free
        Map<String, Object> params = new HashMap<String, Object>(); //params for <code>find()</code> method
        params.put("portStatus", portStatusValue);
        this.reportData = new String[devices.size()]; //init class variable, that stores a result
        int index = 0; //index for reportData array
        for (Device device : devices) {
            params.put("deviceID", device.getId());
            this.reportData[index] = device.getName() + COLUMN_SEPARATOR +
                    device.getPortQuantity() + COLUMN_SEPARATOR +
                    ((float) pdi.countAll(params) / device.getPortQuantity() * 100); // Value in percents
            index++; //increase array index
        }
        dbManager.close();
    }

    /**
     * Generates report and writes it to output stream.
     * @param os output stream to be written
     */
    public void generateReport(OutputStream os) {
        /* NEW EXCEL DOCUMENT CREATION CODE WILL BE HERE */
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
