package nc.notus.dao;

import java.util.List;
import nc.notus.entity.ServiceOrder;

/**
 * Interface of DAO for entity ServiceOrder.
 * @author Andrey Ilin
 */
public interface ServiceOrderDAO extends GenericDAO<ServiceOrder> {

    /**
     * Gets a list of service orders in system with specific service status.
     * @param serviceStatus service status used to return specific list.
     * @return List of ServiceOrder objects.
     */
    List<ServiceOrder> getServiceOrders(String serviceStatus);
}