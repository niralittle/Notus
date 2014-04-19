package nc.notus.reports;
                                                                                // REVIEW: class should be deleted
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import nc.notus.dao.DeviceDAO;
import nc.notus.dao.PortDAO;
import nc.notus.dao.ServiceCatalogDAO;
import nc.notus.dao.ServiceInstanceDAO;
import nc.notus.dao.ServiceOrderDAO;
import nc.notus.dao.impl.DeviceDAOImpl;
import nc.notus.dao.impl.PortDAOImpl;
import nc.notus.dao.impl.ServiceCatalogDAOImpl;
import nc.notus.dao.impl.ServiceInstanceDAOImpl;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.Device;
import nc.notus.entity.Port;
import nc.notus.entity.ServiceCatalog;
import nc.notus.entity.ServiceInstance;
import nc.notus.entity.ServiceOrder;

/**
 * Represents a system report. Generates report about most profitable router.
 * @author Andrey Ilin
 */
public class MostProfitableRouterReport implements Report {

    /*
     * Number of strings to store in reportData array
     * 1. Device ID
     * 2. Device name
     * 3. Profit $
     */
    private final int STRING_NUMBER = 3;

    /*
     * Data for reports stored here
     * Each string is representation of single report parameter
     */
    private String[] reportData = new String[STRING_NUMBER]; //report data stored here

    /**
     * Gets a data for report from the database and stores data to reportData
     * class field, that is represented as String array.
     */
    private void retrieveReportData() {

        /* DBManager and DAOImpl instances creation */
        DBManager dbManager = new DBManager();
        ServiceOrderDAO sodi = new ServiceOrderDAOImpl(dbManager);
        ServiceCatalogDAO scdi = new ServiceCatalogDAOImpl(dbManager);
        ServiceInstanceDAO sidi = new ServiceInstanceDAOImpl(dbManager);
        DeviceDAO ddi = new DeviceDAOImpl(dbManager);
        PortDAO pdi = new PortDAOImpl(dbManager);

        /* List of service orders with status "Completed" */
        ArrayList<ServiceOrder> serviceOrderList =
                (ArrayList<ServiceOrder>) sodi.getServiceOrdersByStatus("Completed", 1, 1000);

        /* Getting ports associated with prices */
        Port[] ports = new Port[serviceOrderList.size()];
        int[] prices = new int[serviceOrderList.size()];
        int arrayIndexer = 0;
        for (ServiceOrder so : serviceOrderList) {
            ServiceInstance si = sidi.find(so.getServiceInstanceID());
            ports[arrayIndexer] = pdi.find(si.getPortID());
            ServiceCatalog sc = scdi.find(so.getServiceCatalogID());
            prices[arrayIndexer] = sc.getPrice();
        }

        /* Getting devices associated with prices */
        Map<Device, Integer> devicePriceMap = new HashMap<Device, Integer>();
        for (int i = 0; i < serviceOrderList.size(); i++) {
            Device device = ddi.find(ports[i].getDeviceID());
            if (devicePriceMap.containsKey(device)) {
                devicePriceMap.put(device, devicePriceMap.get(device) + prices[i]);
            } else {
                devicePriceMap.put(device, prices[i]);
            }
        }

        /* Getting most profitable device and storing it to entry */
        Iterator it = devicePriceMap.entrySet().iterator();
        int maxPrice = 0;
        Map.Entry<Device, Integer> routerProfitEntry = null;
        while (it.hasNext()) {
            Map.Entry<Device, Integer> entry = (Map.Entry<Device, Integer>) it.next();
            if (entry.getValue() > maxPrice) {
                routerProfitEntry = entry;
                maxPrice = entry.getValue();
            }
        }

        /* Saving data from enrty to String array */
        arrayIndexer = 0;
        reportData[arrayIndexer] = Integer.toString(routerProfitEntry.getKey().getId());
        arrayIndexer++;
        reportData[arrayIndexer] = routerProfitEntry.getKey().getName();
        arrayIndexer++;
        reportData[arrayIndexer] = Float.toString(maxPrice);
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
