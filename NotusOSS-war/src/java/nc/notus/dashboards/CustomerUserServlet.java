package nc.notus.dashboards;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nc.notus.dao.OSSUserDAO;
import nc.notus.dao.ScenarioDAO;
import nc.notus.dao.ServiceCatalogDAO;
import nc.notus.dao.ServiceOrderDAO;
import nc.notus.dao.ServiceTypeDAO;
import nc.notus.dao.impl.OSSUserDAOImpl;
import nc.notus.dao.impl.ScenarioDAOImpl;
import nc.notus.dao.impl.ServiceCatalogDAOImpl;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dao.impl.ServiceTypeDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.OSSUser;
import nc.notus.entity.Scenario;
import nc.notus.entity.ServiceCatalog;
import nc.notus.entity.ServiceOrder;
import nc.notus.entity.ServiceType;
import nc.notus.states.OrderStatus;

/**
 * Servlet prepares data to display on customer user dashboard.
 * 
 * @author Katya Atamanchuk <nira@niralittle.name>
 */
public class CustomerUserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private DBManager dbManager = null;

    /**
     * Puts data into request and forwards it to user.jsp
     *
     * @param request
     *          servlet request
     * @param response
     *          sevlet responce
     * @throws ServletException when something bad happens
     * @throws IOException  when something else, but also bad, happens
     */
    @Override
    protected void doGet(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int startpage = 1;
        int numbOfRecords = 100;
        dbManager = new DBManager();
        try {
            int userID = getUserID(request);
            request.setAttribute("activeInstances",
                    getActiveInstancesList(userID, startpage, numbOfRecords));
            request.setAttribute("processingOrders",
                    getProcessingOrdersList(userID, startpage, numbOfRecords));
            RequestDispatcher view = request.getRequestDispatcher("user.jsp");
            view.forward(request, response);
        } finally {
            dbManager.close();
        }
    }
    //<editor-fold>
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request
     *            servlet request
     * @param response
     *            servlet response
     * @throws ServletException
     *             if a servlet-specific error occurs
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request,
                    HttpServletResponse response) throws ServletException, IOException {
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
            return "Creates data to display on customer user dashboard";
    }// </editor-fold>

    /*
     * Get userID from the session; if there is none - get one from DB,
     * put it in the session and return the value
     */
    private int getUserID(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("userID") == null) {
            OSSUserDAO userDAO = new OSSUserDAOImpl(dbManager);
            String login = request.getUserPrincipal().getName();
            OSSUser user = userDAO.getUserByLogin(login);
            session.setAttribute("userID", user.getId());
        }
        return (Integer) session.getAttribute("userID");
    }

    /**
     * Getting active instances data to display on customer user dashboard
     *
     * @param userID
     *      id of the user whose info we are getting
     * @param startpage
     *      number of the page (used in paging)
     * @param numbOfRecords
     *      amount of records to get (used in paging)
     * @return List<Map<String, String>>
     *      list of maps, where each maprepresents data for a row in a table;
     * map has element name String associated with data String
     * 
     */
    public List<Map<String, String>> getActiveInstancesList(int userID,
            int startpage, int numbOfRecords) {
        ServiceCatalogDAO catalogDAO = new ServiceCatalogDAOImpl(dbManager);
        ServiceTypeDAO typeDAO = new ServiceTypeDAOImpl(dbManager);
        ServiceOrderDAO orderDAO = new ServiceOrderDAOImpl(dbManager);
        List<Map<String, String>> activeInstances =
               new ArrayList<Map<String, String>>();
        List<ServiceOrder> completeOrders = orderDAO.getSOByStatus(userID,
                            OrderStatus.COMPLETED.ordinal() + 1,
                            startpage, numbOfRecords);
        for (ServiceOrder o: completeOrders) {
            Map<String,String> row = new HashMap<String, String>();
            int catalogID = o.getServiceCatalogID();
            ServiceCatalog sc = catalogDAO.find(catalogID);
            ServiceType st = typeDAO.find(sc.getServiceTypeID());
            row.put("serviceLocation", o.getServiceLocation());
            row.put("serviceDescription", st.getService());
            row.put("orderDate", o.getServiceOrderDate().toString());
            row.put("price", Integer.toString(sc.getPrice()));
            row.put("instanceID", Integer.toString(o.getServiceInstanceID()));
            activeInstances.add(row);
        }
        
        return activeInstances;
    }

    /**
     * Getting processing orders data to display on customer user dashboard
     *
     * @param userID
     *      id of the user whose info we are getting
     * @param startpage
     *      number of the page (used in paging)
     * @param numbOfRecords
     *      amount of records to get (used in paging)
     * @return List<Map<String, String>>
     *      list of maps, where each maprepresents data for a row in a table;
     * map has element name String associated with data String
     *
     */
    public List<Map<String, String>> getProcessingOrdersList(int userID,
            int startpage, int numbOfRecords) {
        ScenarioDAO scenarioDAO = new ScenarioDAOImpl(dbManager);
        ServiceCatalogDAO catalogDAO = new ServiceCatalogDAOImpl(dbManager);
        ServiceTypeDAO typeDAO = new ServiceTypeDAOImpl(dbManager);
        ServiceOrderDAO orderDAO = new ServiceOrderDAOImpl(dbManager);
        List<Map<String, String>> processingOrders =
               new ArrayList<Map<String, String>>();
        List<ServiceOrder> orders = orderDAO.getSOByStatus(userID,
                           OrderStatus.PROCESSING.ordinal() + 1,
                           startpage, numbOfRecords);
        for (ServiceOrder o: orders) {
            Map<String,String> row = new HashMap<String, String>();
            int catalogID = o.getServiceCatalogID();
            ServiceCatalog sc = catalogDAO.find(catalogID);
            ServiceType st = typeDAO.find(sc.getServiceTypeID());
            Scenario s = scenarioDAO.find(o.getScenarioID());
            row.put("scenario", s.getScenario());
            row.put("serviceLocation", o.getServiceLocation());
            row.put("serviceDescription", st.getService());
            row.put("orderDate", o.getServiceOrderDate().toString());
            processingOrders.add(row);
        }
           
        return processingOrders;
    }

}
