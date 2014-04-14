package nc.notus.reports;

import java.util.ArrayList;
import java.util.List;
import nc.notus.dao.ServiceOrderDAO;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.ServiceOrder;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Represents report about disconnect order in system by specified period.
 * Period is an interval between two dates in which disconnect orders where created.
 * @author Andrey Ilin
 */
public class DisconnectOrdersPerPeriodReport {

    /* Date strings passed into the object must match this pattern */
    private final String DATE_PATTERN = "dd.MM.yyyy";

    /* Separates data columns in srings stored in reportData array */
    private final String COLUMN_SEPARATOR = "#";

    /*
     * Data for reports stored here
     * Each string is a representation of a table column that stores report data
     * Columns are separated with COLUMN_SEPARATOR
     * String example: service order date#service order id
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
     * Constructor that creates a DisconnectOrdersPerPeriodReport object with specified
     * time period presented as dates it starts and ends with.
     * @param fromDate date from which period starts
     * @param toDate date in which priod ends
     */
    public DisconnectOrdersPerPeriodReport(String toDate, String fromDate) {
        dateTimeFormatter = DateTimeFormat.forPattern(DATE_PATTERN);
        this.fromDate = dateTimeFormatter.parseDateTime(fromDate);
        this.toDate = dateTimeFormatter.parseDateTime(toDate);
        this.reportData = null;
    }

    private void retrieveReportData() {

        /* DBManager and DAOImpl instances creation */
        DBManager dbManager = new DBManager();
        ServiceOrderDAO sodi = new ServiceOrderDAOImpl(dbManager);

        List<ServiceOrder> serviceOrderList = new ArrayList<ServiceOrder>();
        List<ServiceOrder> filteredServiceOrderList = new ArrayList<ServiceOrder>();
        serviceOrderList = sodi.getServiceOrdersByScenario("Disconnect");
        DateTime serviceOrderDate = null;

        /* Filtering service order list by date: include fromDate, exclude toDate */
        for (ServiceOrder order : serviceOrderList) {
            serviceOrderDate = this.dateTimeFormatter.parseDateTime(order.getServiceOrderDate());
            if ((serviceOrderDate.isAfter(this.fromDate) || serviceOrderDate.isEqual(fromDate)) &&
                    serviceOrderDate.isBefore(this.toDate)) {
                filteredServiceOrderList.add(order);
            }
        }

        /* Saving data to reportData array */
        this.reportData = new String[filteredServiceOrderList.size()];
        int arrayIndexer = 0;
        for (ServiceOrder so : filteredServiceOrderList) {
            reportData[arrayIndexer] = so.getServiceOrderDate() +
                    COLUMN_SEPARATOR + so.getId();
            arrayIndexer++;
        }
    }
}

