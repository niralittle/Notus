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
    List<ServiceOrder> getServiceOrdersByStatus(String serviceStatus);

    /**
     * Method that returns list of ServiceOrders with selected scenario
     * @param scenario for searching
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return list ServiceOrders with selected scenario
     */
    public List<ServiceOrder> getServiceOrdersByScenario(String scenario,
                                        int offset, int numberOfRecords);


}
