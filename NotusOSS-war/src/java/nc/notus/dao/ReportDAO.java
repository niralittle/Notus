/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.dao;

import java.sql.Date;
import java.util.List;
import nc.notus.entity.Device;
import nc.notus.entity.ServiceInstance;
import nc.notus.entity.ServiceOrder;

/**
 * Interface of DAO for our reports
 * @author Vladimir Ermolenko
 */
public interface ReportDAO {

    /**
     * Method that return most profitable router in system
     * @param startDate - start of period
     * @param finishDate - finish of period
     * @return device which is most profitable per period
     */
    public Device getMostProfitableRouter(Date startDate, Date finishDate);

    /**
     * Method that return list of new ServiceOrders per period
     * @param startDate - start of period
     * @param finishDate - finish of period
     * @return list of new ServiceOrders per period
     */
    public List<ServiceOrder> getNewServiceOrders(Date startDate, Date finishDate);

    /**
     * Method that return list of disconnected ServiceInstances per period
     * @param startDate - start of period
     * @param finishDate - finish of period
     * @return list list of disconnected ServiceInstances per period
     */
    public List<ServiceInstance> getDisconnectedServiceInstances(Date startDate, Date finishDate);
}
