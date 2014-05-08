package nc.notus.dao;

import java.util.List;
import nc.notus.dbmanager.DBManagerException;
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
                                        int offset, int numberOfRecords) throws DBManagerException;

    /**
     * Method that returns list of ServiceOrders with selected status
     * @param userID
     * @param serviceOrderStatus for searching
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return list ServiceOrders with selected status
     */
    List<ServiceOrder> getSOByStatus(int userID, int serviceOrderStatus,
                                     int offset, int numberOfRecords) throws DBManagerException;

    /**
     * Method that returns number of ServiceOrders with specified status and userID
     * @param userID
     * @param serviceOrderStatus for searching
     * @return number of ServiceOrders with specified status and userID
     */
    int countAllSOByStatus(int userID, int serviceOrderStatus) throws DBManagerException;

    /**
     * Method that returns list of ServiceOrders with selected scenario
     * @param scenario for searching
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return list ServiceOrders with selected scenario
     */
    public List<ServiceOrder> getServiceOrdersByScenario(String scenario,
                                        int offset, int numberOfRecords) throws DBManagerException;
                                        
   /**
    * Return service order by specified SI id.
    * 
    * @param serviceInstanceId - passes SI id
    * @return instance of SO or null if not found
    * 
    * @author Panchenko Dmytro
    */
    public ServiceOrder getServiceOrderBySIId(int serviceInstanceId) throws DBManagerException;



}
