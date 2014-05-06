package nc.notus.dao.impl;

import java.io.IOException;
import java.io.Writer;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import nc.notus.dao.ReportDAO;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.dbmanager.ResultIterator;
import nc.notus.dbmanager.Statement;
import nc.notus.entity.MostProfitableRouterReportData;
import nc.notus.entity.ProfitInMonth;
import nc.notus.entity.RoutersUtilizationCapacity;
import nc.notus.entity.ServiceOrderReportData;

/**
 * Implementation of DAO for our reports
 * @author Vladimir Ermolenko, Andrey Ilin
 */
public class ReportDAOImpl implements ReportDAO {

	private static Logger logger = Logger.getLogger(ReportDAOImpl.class.getName());
	
    private DBManager dbManager;

    public ReportDAOImpl(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * Method that returns most profitable router in system
     * @param startDate - start of period
     * @param finishDate - finish of period
     * @return device which is most profitable per period
     */
    @Override
    public MostProfitableRouterReportData getMostProfitableRouter() {
        
    	MostProfitableRouterReportData  data = null;
    	Statement statement = null;
    	ResultIterator ri = null;
    	
    	String query = "SELECT d.id, d.name, d.portquantity, sum(sc.price) total " +
                "FROM serviceorder so " +
                "LEFT JOIN servicecatalog sc ON so.servicecatalogid = sc.id " +
                "LEFT JOIN serviceinstance si ON so.serviceinstanceid = si.id " +
                "LEFT JOIN serviceinstancestatus sis ON si.serviceinstancestatusid = sis.id " +
                "LEFT JOIN port p ON si.portid = p.id " +
                "LEFT JOIN device d ON p.deviceid = d.id " +
                "WHERE sis.status = 'Active' " +
                "GROUP BY d.id, d.name, d.portquantity " +
                "ORDER BY total DESC";
		try {
			statement = dbManager.prepareStatement(query);
			ri = statement.executeQuery();
			if (ri.next()) {
				data = new MostProfitableRouterReportData();
				data.setId(ri.getInt("id"));
				data.setName(ri.getString("name"));
				data.setPortQuantity(ri.getInt("portquantity"));
				data.setProfit(ri.getInt("total"));
			}
		} catch (DBManagerException exc) {
			logger.error(exc.getMessage(), exc);
		} finally {
			statement.close();
		}
		return data;
    }

    /**
     * Method that returns list of new ServiceOrders per period
     * @param startDate - start of period
     * @param finishDate - finish of period
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return list of new ServiceOrders per period
     */
    @Override
    public List<ServiceOrderReportData> getNewServiceOrders(Date startDate,
            Date finishDate, int offset, int numberOfRecords) {
        
    	Statement statement = null;
    	ResultIterator ri = null;
    	List<ServiceOrderReportData> orders = null;
    	
    	String query = "SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM ( " +
                "SELECT so.id, so.serviceorderdate, so.servicelocation, " +
                "st.service, sc.price, pl.name, pl.location " +
                "FROM serviceorder so " +
                "LEFT JOIN scenario s ON so.scenarioid = s.id " +
                "LEFT JOIN servicecatalog sc ON so.servicecatalogid = sc.id " +
                "LEFT JOIN servicetype st ON sc.servicetypeid = st.id " +
                "LEFT JOIN providerlocation pl ON sc.providerlocationid = pl.id " +
                "WHERE so.serviceorderdate BETWEEN ? AND ? " +
                "AND s.scenario = 'New' " +
                "ORDER BY so.serviceorderdate ASC " +
                ") a where ROWNUM <= ? ) " +
                "WHERE rnum  > ?";
		try {
			statement = dbManager.prepareStatement(query);
			statement.setDate(1, startDate);
			statement.setDate(2, finishDate);
			statement.setInt(3, offset + numberOfRecords);
			statement.setInt(4, offset);
			
			ri = statement.executeQuery();
			orders = new ArrayList<ServiceOrderReportData>();
			
			while (ri.next()) {
				ServiceOrderReportData serviceOrderData = new ServiceOrderReportData();
				serviceOrderData.setId(ri.getInt("id"));
				serviceOrderData.setDate(ri.getDate("serviceorderdate").toString());
				serviceOrderData.setServiceLocation(ri.getString("servicelocation"));
				serviceOrderData.setServiceName(ri.getString("service"));
				serviceOrderData.setPrice(ri.getInt("price"));
				serviceOrderData.setProviderLocationName(ri.getString("name"));
				serviceOrderData.setProviderLocation(ri.getString("location"));
				orders.add(serviceOrderData);
			}
		} catch (DBManagerException exc) {
			logger.error(exc.getMessage(), exc);
		} finally {
			statement.close();
		}
        return orders;
    }

    /**
     * Method that returns list of disconnected ServiceInstances per period
     * @param startDate - start of period
     * @param finishDate - finish of period
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return list of disconnected ServiceInstances per period
     */
    @Override
    public List<ServiceOrderReportData> getDisconnectServiceOrders(Date startDate,
            Date finishDate, int offset, int numberOfRecords) {
    	
    	Statement statement = null;
    	ResultIterator ri = null;
    	List<ServiceOrderReportData> orders = null;
    	
    	String query = "SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM ( " +
                "SELECT so.id, so.serviceorderdate, so.servicelocation, " +
                "st.service, sc.price, pl.name, pl.location " +
                "FROM serviceorder so " +
                "LEFT JOIN scenario s ON so.scenarioid = s.id " +
                "LEFT JOIN servicecatalog sc ON so.servicecatalogid = sc.id " +
                "LEFT JOIN servicetype st ON sc.servicetypeid = st.id " +
                "LEFT JOIN providerlocation pl ON sc.providerlocationid = pl.id " +
                "WHERE so.serviceorderdate BETWEEN ? AND ? " +
                "AND s.scenario = 'Disconnect' " +
                "ORDER BY so.serviceorderdate ASC " +
                ") a where ROWNUM <= ? ) " +
                "WHERE rnum  > ?";
        try{
			statement = dbManager.prepareStatement(query);
			statement.setDate(1, startDate);
			statement.setDate(2, finishDate);
			statement.setInt(3, offset + numberOfRecords);
			statement.setInt(4, offset);

			ri = statement.executeQuery();
			orders = new ArrayList<ServiceOrderReportData>();
			while (ri.next()) {
				ServiceOrderReportData serviceOrderData = new ServiceOrderReportData();
				serviceOrderData.setId(ri.getInt("id"));
				serviceOrderData.setDate(ri.getDate("serviceorderdate").toString());
				serviceOrderData.setServiceLocation(ri.getString("servicelocation"));
				serviceOrderData.setServiceName(ri.getString("service"));
				serviceOrderData.setPrice(ri.getInt("price"));
				serviceOrderData.setProviderLocationName(ri.getString("name"));
				serviceOrderData.setProviderLocation(ri.getString("location"));
				orders.add(serviceOrderData);
			}
		} catch (DBManagerException exc) {
			logger.error(exc.getMessage(), exc);
		} finally {
			statement.close();
		}
        return orders;
    }

    /**
     * Method that returns list of objects for routers utilization and capacity report
     * @param startDate - start of period
     * @param finishDate - finish of period
     * @param offset - offset from start position in paging
     * @param numberOfRecords - quantity of records to fetch
     * @return list of objects for routers utilization and capacity report
     */
    @Override
    public List<RoutersUtilizationCapacity> getRoutersUtilizationCapacityData(
            int offset, int numberOfRecords) {

    	Statement statement = null;
    	ResultIterator ri = null;
    	List<RoutersUtilizationCapacity> routersUtilizationCapacity = null;
    	
        String query = "SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM ( " +
                "SELECT d.id, d.name, d.portquantity," +
                "COUNT(p.portnumber) / d.portquantity * 100 AS utilization " +
                "FROM device d " +
                "LEFT JOIN port p ON p.deviceid = d.id " +
                "WHERE p.portstatus = '1' GROUP BY d.id, d.name, d.portquantity" +
                ") a where ROWNUM <= ? ) " +
                "WHERE rnum  > ?";
		try {
			statement = dbManager.prepareStatement(query);
			statement.setInt(1, offset + numberOfRecords);
			statement.setInt(2, offset);
			ri = statement.executeQuery();
			routersUtilizationCapacity = new ArrayList<RoutersUtilizationCapacity>();
			while (ri.next()) {
				RoutersUtilizationCapacity routUtCap = new RoutersUtilizationCapacity();
				routUtCap.setDeviceId(ri.getInt("id"));
				routUtCap.setDeviceName(ri.getString("name"));
				routUtCap.setCapacity(ri.getInt("portquantity"));
				routUtCap.setUtilization(ri.getFloat("utilization"));
				routersUtilizationCapacity.add(routUtCap);
			}
		} catch (DBManagerException exc) {
			logger.error(exc.getMessage(), exc);
		} finally {
			statement.close();
		}
        return routersUtilizationCapacity;
    }

    ////////////////////////////////////////MODIFY\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
   
    /**
     * Method that returns list of objects for profitability by month report
     * @param startDate - start of period
     * @param finishDate - finish of period
     * @return list of objects for profitability by month report
     */
    @Override
    public List<ProfitInMonth> getProfitByMonth(Date month) {
        Statement statement = null;
        ResultIterator ri = null;
        List<ProfitInMonth> profitByMonths = null;
        
        String query = "";
        
		try {
			statement = dbManager.prepareStatement(query);
			ri = statement.executeQuery();
			profitByMonths = new ArrayList<ProfitInMonth>();
			while (ri.next()) {
				ProfitInMonth profInMonth = new ProfitInMonth();
				profInMonth.setProfit(ri.getInt("profit"));
				profInMonth.setMonth(ri.getString("months"));
				profitByMonths.add(profInMonth);
			}
		} catch (DBManagerException exc) {
			logger.error(exc.getMessage(), exc);
		} finally {
			statement.close();
		}
        return profitByMonths;
    }

    /**
     * Writes data about profitability by month to Writer as strings that
     * represent rows. Rows separated with columnSeparator to determine data columns.
     * @param storage - Writer data will be stored at
     * @param columnSeparator separates columns in a string row
     */
    @Override
    public void getProfitByMonth(Writer storage, String columnSeparator,
            Date startDate, Date finishDate) throws IOException {
    }
    ////////////////////////////////////////MODIFY\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    /**
     * Writes data about new service orders to Writer as strings that represent
     * rows. Rows separated with columnSeparator to determine data columns.
     * @param storage - Writer data will be stored at
     * @param columnSeparator separates columns in a string row
     * @param startDate - start of period
     * @param finishDate - end of period
     * @throws IOException in case of writer errors
     */
    @Override
    public void getNewServiceOrders(Writer storage, String columnSeparator,
            Date startDate, Date finishDate) throws IOException {
       
    	Statement statement = null;
        ResultIterator ri = null;
        List<ProfitInMonth> orders = null;
         
    	String query = "SELECT so.id, so.serviceorderdate, so.servicelocation, " +
                "st.service, sc.price, pl.name, pl.location " +
                "FROM serviceorder so " +
                "LEFT JOIN scenario s ON so.scenarioid = s.id " +
                "LEFT JOIN servicecatalog sc ON so.servicecatalogid = sc.id " +
                "LEFT JOIN servicetype st ON sc.servicetypeid = st.id " +
                "LEFT JOIN providerlocation pl ON sc.providerlocationid = pl.id " +
                "WHERE so.serviceorderdate BETWEEN ? AND ? " +
                "AND s.scenario = 'New' " +
                "ORDER BY so.serviceorderdate ASC ";
		try {
			statement = dbManager.prepareStatement(query);
			statement.setDate(1, startDate);
			statement.setDate(2, finishDate);
			ri = statement.executeQuery();
			while (ri.next()) {
				storage.write(String.valueOf(ri.getInt("id")));
				storage.write(columnSeparator);
				storage.write(ri.getDate("serviceorderdate").toString());
				storage.write(columnSeparator);
				storage.write(ri.getString("servicelocation"));
				storage.write(columnSeparator);
				storage.write(ri.getString("service"));
				storage.write(columnSeparator);
				storage.write(String.valueOf(ri.getInt("price")));
				storage.write(columnSeparator);
				storage.write(ri.getString("name"));
				storage.write(columnSeparator);
				storage.write(ri.getString("location"));
				storage.write("\n");
			}
		} catch (DBManagerException exc) {
			logger.error(exc.getMessage(), exc);
		} finally {
			statement.close();
		}
    	//return orders;
    }

    /**
     * Writes data about disconnect service orders to Writer as strings that represent
     * rows. Rows separated with columnSeparator to determine data columns.
     * @param storage - Writer data will be stored at
     * @param columnSeparator separates columns in a string row
     * @param startDate - start of period
     * @param finishDate - end of period
     * @throws IOException in case of writer errors
     */
    @Override
    public void getDisconnectServiceOrders(Writer storage, String columnSeparator,
            Date startDate, Date finishDate) throws IOException, DBManagerException {
        String query = "SELECT so.id, so.serviceorderdate, so.servicelocation, " +
                "st.service, sc.price, pl.name, pl.location " +
                "FROM serviceorder so " +
                "LEFT JOIN scenario s ON so.scenarioid = s.id " +
                "LEFT JOIN servicecatalog sc ON so.servicecatalogid = sc.id " +
                "LEFT JOIN servicetype st ON sc.servicetypeid = st.id " +
                "LEFT JOIN providerlocation pl ON sc.providerlocationid = pl.id " +
                "WHERE so.serviceorderdate BETWEEN ? AND ? " +
                "AND s.scenario = 'Disconnect' " +
                "ORDER BY so.serviceorderdate ASC ";
        Statement statement = dbManager.prepareStatement(query);
        statement.setDate(1, startDate);
        statement.setDate(2, finishDate);
        ResultIterator ri = statement.executeQuery();
        while (ri.next()) {
            storage.write(String.valueOf(ri.getInt("id")));
            storage.write(columnSeparator);
            storage.write(ri.getDate("serviceorderdate").toString());
            storage.write(columnSeparator);
            storage.write(ri.getString("servicelocation"));
            storage.write(columnSeparator);
            storage.write(ri.getString("service"));
            storage.write(columnSeparator);
            storage.write(String.valueOf(ri.getInt("price")));
            storage.write(columnSeparator);
            storage.write(ri.getString("name"));
            storage.write(columnSeparator);
            storage.write(ri.getString("location"));
            storage.write("\n");
        }
    }

    /**
     * Writes data about routers utilization and capacity to Writer as strings that
     * represent rows. Rows separated with columnSeparator to determine data columns.
     * @param storage - Writer data will be stored at
     * @param columnSeparator separates columns in a string row
     */
    @Override
    public void getRoutersUtilizationCapacityData(Writer storage, String columnSeparator)
            throws IOException, DBManagerException {
        String query = "SELECT d.id, d.name, d.portquantity," +
                "COUNT(p.portnumber) / d.portquantity * 100 AS utilization " +
                "FROM device d " +
                "LEFT JOIN port p ON p.deviceid = d.id " +
                "WHERE p.portstatus = '1' GROUP BY d.id, d.name, d.portquantity";
        Statement statement = dbManager.prepareStatement(query);
        ResultIterator ri = statement.executeQuery();
        while (ri.next()) {
            storage.write(String.valueOf(ri.getInt("id")));
            storage.write(columnSeparator);
            storage.write(ri.getString("name"));
            storage.write(columnSeparator);
            storage.write(String.valueOf(ri.getInt("portquantity")));
            storage.write(columnSeparator);
            storage.write(String.valueOf(ri.getFloat("utilization")));
            storage.write("\n");
        }
    }

    /**
     * Writes data about most profitable router to Writer as string that
     * represent row. Row separated with columnSeparator to determine data columns.
     * @param storage - Writer data will be stored at
     * @param columnSeparator separates columns in a string row
     */
    @Override
    public void getMostProfitableRouter(Writer storage, String columnSeparator)
            throws IOException, DBManagerException {
        String query = "SELECT d.id, d.name, d.portquantity, sum(sc.price) total " +
                "FROM serviceorder so " +
                "LEFT JOIN servicecatalog sc ON so.servicecatalogid = sc.id " +
                "LEFT JOIN serviceinstance si ON so.serviceinstanceid = si.id " +
                "LEFT JOIN serviceinstancestatus sis ON si.serviceinstancestatusid = sis.id " +
                "LEFT JOIN port p ON si.portid = p.id " +
                "LEFT JOIN device d ON p.deviceid = d.id " +
                "WHERE sis.status = 'Active' " +
                "GROUP BY d.id, d.name, d.portquantity " +
                "ORDER BY total DESC";
        Statement statement = dbManager.prepareStatement(query);
        ResultIterator ri = statement.executeQuery();
        ri.next();
        storage.write(String.valueOf(ri.getInt("id")));
        storage.write(columnSeparator);
        storage.write(ri.getString("name"));
        storage.write(columnSeparator);
        storage.write(String.valueOf(ri.getInt("portquantity")));
        storage.write(columnSeparator);
        storage.write(String.valueOf(ri.getFloat("total")));
        storage.write("\n");

    }
}
