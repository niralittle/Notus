package nc.notus.reports;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import nc.notus.dao.impl.DeviceDAOImpl;
import nc.notus.dao.impl.PortDAOImpl;
import nc.notus.entity.Device;

/**
 * Represents report utilization and capacity of the system routers.
 * @author Andrey Ilin
 */
public class RoutersUtilizationCapacityReport extends AbstractReport {

    private final String FIELD_SEPARATOR = "#";
    private String[] rows = null;

    /**
     * Gets a data for report from the database and handles it.
     */
    public void getReportData() {
        String[] fields = {"port_status", "device_id"};
        int fieldsIndex = 0;
        int portStatusValue = 0; //port status TODO: discuss constants
        int index = 0; //index for rows array
        PortDAOImpl pdi = new PortDAOImpl();
        DeviceDAOImpl ddi = new DeviceDAOImpl();
        ArrayList<Device> devices = (ArrayList<Device>) ddi.getDevices();
        rows = new String[devices.size()];
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(fields[fieldsIndex], portStatusValue); //put first parameter to map
        fieldsIndex++; //index second paramerter
        for (Device device : devices) {
            params.put(fields[fieldsIndex], device.getId()); //put next parameter to map
            rows[index] = device.getName() + FIELD_SEPARATOR +
                    device.getPortQuantity() + FIELD_SEPARATOR +
                    ((float) pdi.countAll(params) / device.getPortQuantity() * 100); // Value in percents
            index++; //increase array index
        }
        pdi.close();
        ddi.close();
    }

    /**
     * Generates report and writes it to output stream.
     * @param os output stream to be written
     */
    protected void generateReport(OutputStream os) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
