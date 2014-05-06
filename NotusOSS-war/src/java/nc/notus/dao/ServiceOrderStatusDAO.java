package nc.notus.dao;

import nc.notus.dbmanager.DBManagerException;
import nc.notus.entity.ServiceOrderStatus;
import nc.notus.states.OrderStatus;

/**
 * Interface of DAO for entity ServiceOrderStatus
 * @author Vladimir Ermolenko
 */
public interface ServiceOrderStatusDAO extends GenericDAO<ServiceOrderStatus>{

    /**
     * Method returns id of status of ServiceOrder                              
     * @param ServiceOrderStatusName
     * @return id of our ServiceOrder's status
     * @throws DAOException if id was not found
     */
    public int getServiceOrderStatusID(OrderStatus status) throws DBManagerException;

}
