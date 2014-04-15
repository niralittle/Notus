package nc.notus.dao;

import java.util.List;
import nc.notus.entity.ServiceOrder;

/**
 * Interface of DAO for entity ServiceOrder.
 * @author Andrey Ilin & Vladimir Ermolenko
 */
public interface ServiceOrderDAO extends GenericDAO<ServiceOrder> {

    /**
     * Gets a list of service orders in system with specific service status.
     * @param serviceStatus service status used to return specific list.
     * @return List of ServiceOrder objects.
     */
    List<ServiceOrder> getServiceOrdersByStatus(String serviceStatus);
    List<ServiceOrder> getServiceOrdersByScenario(String scenario);

     /**
     * Method return id of status of ServiceOrder
     * @param ServiceOrderStatusName
     * @return id of our ServiceOrder's ststus
     */
    public int getServiceOrderStatusID(String ServiceOrderStatusName);
}
