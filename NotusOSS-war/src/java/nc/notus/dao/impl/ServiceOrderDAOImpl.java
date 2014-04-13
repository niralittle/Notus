package nc.notus.dao.impl;

import java.util.ArrayList;
import java.util.List;
import nc.notus.dao.DAOException;
import nc.notus.dao.ServiceOrderDAO;
import nc.notus.dbmanager.ResultIterator;
import nc.notus.dbmanager.Statement;
import nc.notus.entity.ServiceOrder;

/**
 * Implementation of DAO for entity ServiceOrder
 * @author Andrey Ilin
 */
public class ServiceOrderDAOImpl extends GenericDAOImpl<ServiceOrder>
        implements ServiceOrderDAO {

    private final int FIRST_COLUMN = 1;

    /**
     * Gets a list of service orders in system with specific service status.
     * @param serviceStatusId the id of service status used to return specific
     * list. If serviceStatusId is null, method returns list of all service orders.
     * @return List of ServiceOrder objects.
     */
    public List<ServiceOrder> getServiceOrders(Integer serviceStatusId) {
        int columnCounter = FIRST_COLUMN; //columns are numbered from 1
        int paramIndex = 1; //placeholder indexes are numbered from 1
        List<ServiceOrder> deviceList = new ArrayList<ServiceOrder>();
        ServiceOrder so = null;
        String queryString = null;
        if (serviceStatusId == null) {
            queryString = "SELECT * FROM service_order";
        } else {
            queryString = "SELECT * FROM service_order WHERE so_status_id = ?";
        }
        Statement statement = dbManager.prepareStatement(queryString);
        statement.setInt(paramIndex, serviceStatusId);
        ResultIterator ri = statement.executeQuery();
        if (!ri.next()) {
            throw new DAOException("No service orders were found in system");
        }
        do {
            so = new ServiceOrder();
            so.setId(ri.getInt(columnCounter));
            columnCounter++;
            so.setServiceOrderDate(ri.getString(columnCounter));
            columnCounter++;
            so.setServiceOrderStatus(ri.getInt(columnCounter));
            columnCounter++;
            so.setScenario(ri.getInt(columnCounter));
            columnCounter++;
            so.setUserID(ri.getInt(columnCounter));
            columnCounter++;
            so.setServiceCatalogID(ri.getInt(columnCounter));
            columnCounter++;
            so.setServiceInctanceID(ri.getInt(columnCounter));
            columnCounter++;
            so.setServiceLocation(ri.getString(columnCounter));
            columnCounter = FIRST_COLUMN;
            deviceList.add(so);
        } while (ri.next());
        return deviceList;
    }
}
