package nc.notus.dao;

import java.util.List;
import nc.notus.entity.ServiceOrder;

/**
 * Interface of DAO for entity ServiceOrder
 * @author Vladimir Ermolenko
 */
public interface ServiceOrderDAO extends GenericDAO<ServiceOrder> {

    /**
     * Method that returns list of ServiceOrders with selected status
     * @param serviceOrderStatus for serching
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return list ServiceOrders with selected status
     */
    List<ServiceOrder> getServiceOrdersByStatus(String serviceOrderStatus,
                                        int offset, int numberOfRecords);

    /**
     * Method that returns list of ServiceOrders with selected status
     * not not connected to any Service Instance
     * @param userID
     * @param serviceOrderStatus for searching
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return list ServiceOrders with selected status
     * not not connected to any Service Instance
     */
    List<ServiceOrder> getSOByStatusWoSI(int userID, String serviceOrderStatus,
                                        int offset, int numberOfRecords);

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
