package nc.notus.dao.impl;

import java.util.ArrayList;
import java.util.List;
import nc.notus.dao.DAOException;
import nc.notus.dao.ServiceOrderDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.ResultIterator;
import nc.notus.dbmanager.Statement;
import nc.notus.entity.ServiceOrder;
import nc.notus.states.OrderStatus;

/**
 * Implementation of DAO for entity ServiceOrder
 * @author Andrey Ilin
 */
public class ServiceOrderDAOImpl extends GenericDAOImpl<ServiceOrder>
        implements ServiceOrderDAO {

    public ServiceOrderDAOImpl(DBManager dbManager) {
        super(dbManager);
    }

    public List<ServiceOrder> getServiceOrdersByScenario(String scenario) {
        List<ServiceOrder> deviceList = new ArrayList<ServiceOrder>();
        String queryString = "SELECT ServiceOrder.id, serviceOrderDate, " +
                "serviceOrderStatusID, scenarioID, userID, serviceCatalogID, " +
                "serviceInstanceID, serviceLocation " +
                "FROM ServiceOrder " +
                "INNER JOIN Scenario ON " +
                "ScenarioID = Scenario.id " +
                "WHERE scenario = '?'";
        Statement statement = dbManager.prepareStatement(queryString);
        statement.setString(1, scenario);
        ResultIterator ri = statement.executeQuery();
        if (!ri.next()) {
            throw new DAOException("No service orders with specified " +
                    "scenario were found in system");
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

    /**
     * Gets a list of service orders in system with specific service status.
     * @param serviceStatus service status used to return specific list.
     * @return List of ServiceOrder objects.
     */
    @Override
    public List<ServiceOrder> getServiceOrdersByStatus(String serviceStatus) {

        List<ServiceOrder> deviceList = new ArrayList<ServiceOrder>();
        String queryString = "SELECT ServiceOrder.id, serviceOrderDate," +
                " serviceOrderStatusID, scenarioID, userID, serviceCatalogID, " +
                "serviceInstanceID, serviceLocation " +
                "FROM ServiceOrder " +
                "INNER JOIN ServiceOrderStatus ON " +
                "serviceOrderStatusID = ServiceOrderStatus.id" +
                "WHERE status = '?'";

        Statement statement = dbManager.prepareStatement(queryString);
        statement.setString(1, serviceStatus);
        ResultIterator ri = statement.executeQuery();

        if (!ri.next()) {
            throw new DAOException("No service orders with specified " +
                    "service status were found in system");
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

    /**
     * Method return id of status of ServiceOrder
     * @param ServiceOrderStatusName
     * @return id of our ServiceOrder's ststus
     */
    @Override
    public int getServiceOrderStatusID(OrderStatus status) {
        String serviceOrderStatusName = status.toString();
        if (serviceOrderStatusName == null) {
            throw new NullPointerException("Null reference invoked!");
    	}
    	String queryString = "SELECT sos.id, sos.status FROM serviceorderstatus sos WHERE sos.status = ?";
	Statement statement = dbManager.prepareStatement(queryString);
	statement.setString(1, serviceOrderStatusName);
	ResultIterator ri = statement.executeQuery();
        if (ri.next()){
            return ri.getInt("id");
        }
        return 0;
    }
}
