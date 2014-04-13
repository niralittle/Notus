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

    /**
     * Gets a list of service orders in system with specific service status.
     * @param serviceStatus service status used to return specific list.
     * @return List of ServiceOrder objects.
     */
    public List<ServiceOrder> getServiceOrders(String serviceStatus) {

        List<ServiceOrder> deviceList = new ArrayList<ServiceOrder>();
        String queryString = "SELECT ServiceOrder.id, serviceOrderDate, serviceOrderStatusID, " +
                                    "scenarioID, userID, serviceCatalogID, " +
                                    "serviceInstanceID, serviceLocation " +
                             "FROM ServiceOrder " +
                             "INNER JOIN ServiceOrderStatus ON " +
                                    "serviceOrderStatusID = ServiceOrderStatus.id" +
                             "WHERE status = '?'";
        
        Statement statement = dbManager.prepareStatement(queryString);
        statement.setString(1, serviceStatus);
        ResultIterator ri = statement.executeQuery();

        if (!ri.next()) {
            throw new DAOException("No service orders were found in system");
        }
        do {
            ServiceOrder so = new ServiceOrder();
            so.setId(ri.getInt("id"));
            so.setServiceOrderDate(ri.getString("serviceOrderDate"));
            so.setServiceOrderStatusID(ri.getInt("serviceOrderStatusID"));
            so.setScenario(ri.getInt("scenario"));
            so.setUserID(ri.getInt("userID"));
            so.setServiceCatalogID(ri.getInt("catalogID"));
            so.setServiceInstanceID(ri.getInt("serviceInstanceID"));
            so.setServiceLocation(ri.getString("serviceLocation"));
            deviceList.add(so);
        } while (ri.next());
        return deviceList;
    }
}