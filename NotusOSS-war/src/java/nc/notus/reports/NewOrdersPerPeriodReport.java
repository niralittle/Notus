package nc.notus.reports;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import nc.notus.dao.ServiceOrderDAO;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.ServiceOrder;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Represents report about new order in system by specified period.
 * Period is an interval between two dates in which new orders where created.
 * @author Andrey Ilin
 */
public class NewOrdersPerPeriodReport implements Report {

    /*
     * Date strings passed into the object must match this pattern.
     */
    private final String DATE_PATTERN = "dd.MM.yyyy";

    /* Separates data columns in srings stored in reportData array */
    private final String FIELD_SEPARATOR = "#";

    /*
     * Data for reports stored here
     * Each string is a representation of a table column that stores report data
     * Columns are separated with FIELD_SEPARATOR
     * String example: router name#router port quantity#router's utilization in %
     */
    private String[] reportData;

    /* org.joda.time.DateTime object that represents date from which period starts */
    private DateTime fromDate;

    /* org.joda.time.DateTime object that represents date in which priod ends */
    private DateTime toDate;

    /*
     * org.joda.time.format.DateTimeFormatter
     * object that hadnles String to DateTime parsing operation
     */
    private DateTimeFormatter dateTimeFormatter;

    /**
     * Constructor that creates a NewOrdersPerPeriodReport object with specified
     * time period presented as dates it starts and ends with.
     * @param fromDate date from which period starts
     * @param toDate date in which priod ends
     */
    public NewOrdersPerPeriodReport(String fromDate, String toDate) {
        dateTimeFormatter = DateTimeFormat.forPattern(DATE_PATTERN);
        this.fromDate = dateTimeFormatter.parseDateTime(fromDate);
        this.toDate = dateTimeFormatter.parseDateTime(toDate);
    }

    /**
     * Gets a data for report from the database and stores data to reportData
     * class field, that is represented as TODO: as what?
     */
    private void retrieveReportData() {
        DBManager dbManager = new DBManager();
        ServiceOrderDAO sodi = new ServiceOrderDAOImpl(dbManager);

        List<ServiceOrder> serviceOrderList = new ArrayList<ServiceOrder>();
        List<ServiceOrder> filteredServiceOrderList = new ArrayList<ServiceOrder>();
        serviceOrderList = sodi.getServiceOrdersByScenario("New");
        DateTime serviceOrderDate = null;

        /* Filtering service order list by date: include fromDate, exclude toDate */
        for (ServiceOrder order : serviceOrderList) {
            serviceOrderDate = this.dateTimeFormatter.parseDateTime(order.getServiceOrderDate());
            if ((serviceOrderDate.isAfter(this.fromDate) || serviceOrderDate.isEqual(fromDate)) &&
                    serviceOrderDate.isBefore(this.toDate)) {
                filteredServiceOrderList.add(order);
            }
        }
        


    }

    public void generateReport(OutputStream os) {
        /* NEW EXCEL DOCUMENT CREATION CODE WILL BE HERE */
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
