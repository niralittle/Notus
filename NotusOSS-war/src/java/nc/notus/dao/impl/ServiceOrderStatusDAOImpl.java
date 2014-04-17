/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.dao.impl;

import nc.notus.dao.DAOException;
import nc.notus.dao.ServiceOrderStatusDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.ResultIterator;
import nc.notus.dbmanager.Statement;
import nc.notus.entity.ServiceOrderStatus;
import nc.notus.states.OrderStatus;

/**
 * Implementation of DAO for entity ServiceOrderStatus
 * @author Vladimir Ermolenko
 */
public class ServiceOrderStatusDAOImpl extends GenericDAOImpl<ServiceOrderStatus> implements ServiceOrderStatusDAO {

    public ServiceOrderStatusDAOImpl(DBManager dbManager) {
        super(dbManager);
    }

     /**
     * Method return id of status of ServiceOrder
     * @param ServiceOrderStatusName
     * @return id of our ServiceOrder's ststus
     */
    @Override
    public int getServiceOrderStatusID(OrderStatus status) {
        String serviceOrderStatusName = status.toString();
    	String queryString = "SELECT sos.id, sos.status FROM serviceorderstatus sos WHERE sos.status = ?";
	Statement statement = dbManager.prepareStatement(queryString);
	statement.setString(1, serviceOrderStatusName);
	ResultIterator ri = statement.executeQuery();
        if (ri.next()){
            return ri.getInt("id");
        } else {
            throw new DAOException("Given Order Status was not found in DB: " +
                    status.toString());
        }
    }
}