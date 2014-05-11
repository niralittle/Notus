package nc.notus.dao;

import java.io.Writer;
import java.sql.Date;
import java.util.List;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.entity.MostProfitableRouterReportData;
import nc.notus.entity.ProfitInMonth;
import nc.notus.entity.RoutersUtilizationCapacity;
import nc.notus.entity.ServiceOrderReportData;

/**
 * Interface of DAO for our reports
 * @author Vladimir Ermolenko & Andrey Ilin
 */
public interface ReportDAO {

    /**
     * Method that returns most profitable router in system
     * @return data about device which is most profitable per period
     * @throws DBManagerException
     */
    public MostProfitableRouterReportData getMostProfitableRouter() throws DBManagerException;

    /**
     * Method that returns list of data about new ServiceOrders per period
     * @param startDate - start of period
     * @param finishDate - end of period
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return list of data about new ServiceOrders per period
     * @throws DBManagerException 
     */
    public List<ServiceOrderReportData> getNewServiceOrders(Date startDate,
            Date finishDate, int offset, int numberOfRecords) throws DBManagerException;

    /**
     * Method that returns list of data about disconnected ServiceOrders per period
     * @param startDate - start of period
     * @param finishDate - end of period
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return list of disconnected list of data about disconnected ServiceOrder per period
     * @throws DBManagerException
     */
    public List<ServiceOrderReportData> getDisconnectServiceOrders(Date startDate,
            Date finishDate, int offset, int numberOfRecords) throws DBManagerException;

    /**
     * Method that returns list of data about routers utilization and capacity 
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return list of data about routers utilization and capacity 
     * @throws DBManagerException
     */
    public List<RoutersUtilizationCapacity> getRoutersUtilizationCapacityData(
            int offset, int numberOfRecords) throws DBManagerException;

    /**
     * Method that returns list of data about profitability by current month
     * @return list of data about profitability by current month
     * @throws DBManagerException 
     */
    public List<ProfitInMonth> getProfitByMonth() throws DBManagerException;

    /**
     * Writes data about most profitable router to Writer as string that
     * represent row. Row separated with columnSeparator to determine data columns.
     * @param storage - Writer object where data will be stored
     * @param columnSeparator separates columns in a string row
     * @throws DBManagerException
     */
    public void getMostProfitableRouter(Writer storage, String columnSeparator)
            throws DBManagerException;

    /**
     * Writes data about new service orders to Writer as strings that represent
     * rows. Rows separated with columnSeparator to determine data columns.
     * @param storage - Writer object where data will be stored
     * @param columnSeparator separates columns in a string row
     * @param startDate - start of period
     * @param finishDate - end of period
     * @throws DBManagerException
     */
    public void getNewServiceOrders(Writer storage, String columnSeparator,
            Date startDate, Date finishDate) throws DBManagerException;

    /**
     * Writes data about disconnect service orders to Writer as strings that represent
     * rows. Rows separated with columnSeparator to determine data columns.
     * @param storage - Writer object where data will be stored
     * @param columnSeparator separates columns in a string row
     * @param startDate - start of period
     * @param finishDate - end of period
     * @throws DBManagerException
     */
    public void getDisconnectServiceOrders(Writer storage, String columnSeparator,
            Date startDate, Date finishDate) throws DBManagerException;

    /**
     * Writes data about routers utilization and capacity to Writer as strings that
     * represent rows. Rows separated with columnSeparator to determine data columns.
     * @param storage - Writer object where data will be stored
     * @param columnSeparator separates columns in a string row
     * @throws DBManagerException
     */
    public void getRoutersUtilizationCapacityData(Writer storage, String columnSeparator)
            throws DBManagerException;

    /**
     * Writes data about profitability by month to Writer as strings that
     * represent rows. Rows separated with columnSeparator to determine data columns.
     * @param storage - Writer object where data will be stored
     * @param columnSeparator separates columns in a string row
     * @throws DBManagerException 
     */
    public void getProfitByMonth(Writer storage, String columnSeparator)
            throws DBManagerException;
}
