package nc.notus.dao;

import java.sql.Date;
import java.util.List;
import nc.notus.entity.Device;
import nc.notus.entity.ProfitInMonth;
import nc.notus.entity.RoutersUtilizationCapacity;
import nc.notus.entity.ServiceInstance;
import nc.notus.entity.ServiceOrder;

/**
 * Interface of DAO for our reports
 * @author Vladimir Ermolenko
 */
public interface ReportDAO {

    /**
     * Method that returns most profitable router in system
     * @param startDate - start of period
     * @param finishDate - finish of period
     * @return device which is most profitable per period
     */
    public Device getMostProfitableRouter(Date startDate, Date finishDate);

    /**
     * Method that returns list of new ServiceOrders per period
     * @param startDate - start of period
     * @param finishDate - finish of period
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return list of new ServiceOrders per period
     */                                                                         
    public List<ServiceOrder> getNewServiceOrders(Date startDate,
                            Date finishDate, int offset, int numberOfRecords);

    /**
     * Method that returns list of disconnected ServiceInstances per period
     * @param startDate - start of period
     * @param finishDate - finish of period
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return list of disconnected ServiceInstances per period
     */                                                                         
    public List<ServiceInstance> getDisconnectedServiceInstances(Date startDate,
                                Date finishDate, int offset, int numberOfRecords);

    /**
     * Method that returns list of objects for routers utilization and capacity report
     * @param startDate - start of period
     * @param finishDate - finish of period
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return list of objects for routers utilization and capacity report
     */                                                                         
    public List<RoutersUtilizationCapacity> getRoutersUtilizationCapacityData(
              Date startDate, Date finishDate, int offset, int numberOfRecords);

    /**
     * Method that returns list of objects for profitability by month report
     * @param startDate - start of period
     * @param finishDate - finish of period
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return list of objects for profitability by month report
     */                                                                         
    public List<ProfitInMonth> getProfitByMonth(Date startDate,
            Date finishDate, int offset, int numberOfRecords);

}
