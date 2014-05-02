package nc.notus.dao;

import java.io.IOException;
import java.io.Writer;
import java.sql.Date;
import java.util.List;
import nc.notus.entity.MostProfitableRouterReportData;
import nc.notus.entity.ProfitInMonth;
import nc.notus.entity.RoutersUtilizationCapacity;
import nc.notus.entity.ServiceOrderReportData;

/**
 * Interface of DAO for our reports
 * @author Vladimir Ermolenko
 */
public interface ReportDAO {

    /**
     * Method that returns most profitable router in system
     * @return data about device which is most profitable per period
     */
    public MostProfitableRouterReportData getMostProfitableRouter();

    /**
     * Writes data about most profitable router to Writer as string that
     * represent row. Row separated with columnSeparator to determine data columns.
     * @param storage - Writer data will be stored at
     * @param columnSeparator separates columns in a string row
     */
    public void getMostProfitableRouter(Writer storage, String columnSeparator)
            throws IOException;

    /**
     * Method that returns list of new ServiceOrders per period
     * @param startDate - start of period
     * @param finishDate - end of period
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return list of new ServiceOrders per period
     */
    public List<ServiceOrderReportData> getNewServiceOrders(Date startDate,
            Date finishDate, int offset, int numberOfRecords);

    /**
     * Writes data about new service orders to Writer as strings that represent
     * rows. Rows separated with columnSeparator to determine data columns.
     * @param storage - Writer data will be stored at
     * @param columnSeparator separates columns in a string row
     * @param startDate - start of period
     * @param finishDate - end of period
     * @throws IOException in case of writer errors
     */
    public void getNewServiceOrders(Writer storage, String columnSeparator,
            Date startDate, Date finishDate) throws IOException;

    /**
     * Method that returns list of disconnected ServiceInstances per period
     * @param startDate - start of period
     * @param finishDate - end of period
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return list of disconnected ServiceInstances per period
     */
    public List<ServiceOrderReportData> getDisconnectServiceOrders(Date startDate,
            Date finishDate, int offset, int numberOfRecords);

    /**
     * Writes data about disconnect service orders to Writer as strings that represent
     * rows. Rows separated with columnSeparator to determine data columns.
     * @param storage - Writer data will be stored at
     * @param columnSeparator separates columns in a string row
     * @param startDate - start of period
     * @param finishDate - end of period
     * @throws IOException in case of writer errors
     */
    public void getDisconnectServiceOrders(Writer storage, String columnSeparator,
            Date startDate, Date finishDate) throws IOException;

    /**
     * Method that returns list of objects for routers utilization and capacity report
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return list of objects for routers utilization and capacity report
     */
    public List<RoutersUtilizationCapacity> getRoutersUtilizationCapacityData(
            int offset, int numberOfRecords);

    /**
     * Writes data about routers utilization and capacity to Writer as strings that
     * represent rows. Rows separated with columnSeparator to determine data columns.
     * @param storage - Writer data will be stored at
     * @param columnSeparator separates columns in a string row
     */
    public void getRoutersUtilizationCapacityData(Writer storage, String columnSeparator)
            throws IOException;

    /**
     * Method that returns list of objects for profitability by month report
     * @param startDate - start of period
     * @param finishDate - finish of period
     * @return list of objects for profitability by month report
     */
    public List<ProfitInMonth> getProfitByMonth(Date month);

    /**
     * Writes data about profitability by month to Writer as strings that
     * represent rows. Rows separated with columnSeparator to determine data columns.
     * @param storage - Writer data will be stored at
     * @param columnSeparator separates columns in a string row
     */
    public void getProfitByMonth(Writer storage, String columnSeparator,
            Date startDate, Date finishDate) throws IOException;
}
