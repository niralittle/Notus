/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.dao;

import nc.notus.entity.ServiceOrderStatus;
import nc.notus.states.OrderStatus;

/**
 *
 * @author Vladimir Ermolenko
 */
public interface ServiceOrderStatusDAO extends GenericDAO<ServiceOrderStatus>{

    /**
     * Method return id of status of ServiceOrder
     * @param ServiceOrderStatusName
     * @return id of our ServiceOrder's ststus
     */
    public int getServiceOrderStatusID(OrderStatus status);

}
