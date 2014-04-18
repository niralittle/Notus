package nc.notus.dao;

import nc.notus.entity.ServiceOrderStatus;
import nc.notus.states.OrderStatus;

/**
 * Interface of DAO for entity ServiceOrderStatus
 * @author Vladimir Ermolenko
 */
public interface ServiceOrderStatusDAO extends GenericDAO<ServiceOrderStatus>{

    /**
     * Method return id of status of ServiceOrder                               // REVIEW: fix & update documentation
     * @param ServiceOrderStatusName
     * @return id of our ServiceOrder's ststus
     */
    public int getServiceOrderStatusID(OrderStatus status);

}
