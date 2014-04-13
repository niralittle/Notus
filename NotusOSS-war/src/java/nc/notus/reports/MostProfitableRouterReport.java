package nc.notus.reports;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javanet.staxutils.BaseXMLEventReader;
import nc.notus.dao.impl.DeviceDAOImpl;
import nc.notus.dao.impl.PortDAOImpl;
import nc.notus.dao.impl.ServiceCatalogDAOImpl;
import nc.notus.dao.impl.ServiceInstanceDAOImpl;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.entity.Device;
import nc.notus.entity.Port;
import nc.notus.entity.ServiceCatalog;
import nc.notus.entity.ServiceInstance;
import nc.notus.entity.ServiceOrder;

/**
 * Represents a system report. Generates report about most profitable router.
 * @author Andrey Ilin
 */
public class MostProfitableRouterReport extends AbstractReport {

    /**
     * Gets a data for report from the database and handles it.
     */
    public void getReportData() {
        int serviceStatusId = 4; //completed TODO: discuss emun constants
        ServiceOrderDAOImpl sodi = new ServiceOrderDAOImpl();
        ServiceCatalogDAOImpl scdi = new ServiceCatalogDAOImpl();
        ServiceInstanceDAOImpl sidi = new ServiceInstanceDAOImpl();
        DeviceDAOImpl ddi = new DeviceDAOImpl();
        PortDAOImpl pdi = new PortDAOImpl();
        ArrayList<ServiceOrder> serviceOrderList = (ArrayList<ServiceOrder>) sodi.getServiceOrders(serviceStatusId);
        ServiceCatalog sc = null;
        ServiceInstance si = null;
        int arrayIndexer = 0;
        Port[] ports = new Port[serviceOrderList.size()];
        int[] prices = new int[serviceOrderList.size()];
        for (ServiceOrder so : serviceOrderList) {
            si = sidi.find(so.getServiceInctanceID());
            ports[arrayIndexer] = pdi.find(si.getPortID());
            sc = scdi.find(so.getServiceCatalogID());
            prices[arrayIndexer] = sc.getPrice();
        }
        //We got ports associated prices
        //Now must get device profit
        //Do not modify this keys in, because of hashCode()
        Map<Device, Integer> devicePriceMap = new HashMap<Device, Integer>();
        for (int i = 0; i < serviceOrderList.size(); i++) {
            Device device = ddi.find(ports[i].getDeviceID());
            if (devicePriceMap.containsKey(device)) {
                devicePriceMap.put(device, devicePriceMap.get(device) + prices[i]);
            } else {
                devicePriceMap.put(device, prices[i]);
            }
        }
        Iterator it = devicePriceMap.entrySet().iterator();
        int maxPrice = 0;
        Map.Entry<Device, Integer> routerProfitEntry = null;
        while (it.hasNext()) {
            Map.Entry<Device, Integer> entry = (Map.Entry<Device, Integer>) it.next();
            if (entry.getValue() > maxPrice) {
                routerProfitEntry = entry;
            }
        }
    }

    /**
     * Generates report and writes it to output stream.
     * @param os output stream to be written
     */
    public void generateReport(OutputStream os) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
