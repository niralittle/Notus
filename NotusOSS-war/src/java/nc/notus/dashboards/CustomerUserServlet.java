package nc.notus.dashboards;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nc.notus.controllers.CustomerUserController;
import nc.notus.dao.OSSUserDAO;
import nc.notus.dao.ScenarioDAO;
import nc.notus.dao.ServiceCatalogDAO;
import nc.notus.dao.ServiceInstanceDAO;
import nc.notus.dao.ServiceOrderDAO;
import nc.notus.dao.ServiceTypeDAO;
import nc.notus.dao.impl.OSSUserDAOImpl;
import nc.notus.dao.impl.ScenarioDAOImpl;
import nc.notus.dao.impl.ServiceCatalogDAOImpl;
import nc.notus.dao.impl.ServiceInstanceDAOImpl;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dao.impl.ServiceTypeDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.entity.OSSUser;
import nc.notus.entity.Scenario;
import nc.notus.entity.ServiceCatalog;
import nc.notus.entity.ServiceInstance;
import nc.notus.entity.ServiceOrder;
import nc.notus.entity.ServiceType;
import nc.notus.states.InstanceStatus;
import nc.notus.states.OrderStatus;
import nc.notus.states.WorkflowScenario;
import nc.notus.workflow.NewScenarioWorkflow;
import nc.notus.workflow.Workflow;

/**
 * Servlet prepares data to display on customer user dashboard.
 * 
 * @author Katya Atamanchuk <nira@niralittle.name>
 */
public class CustomerUserServlet extends HttpServlet {

    private DBManager dbManager = null;
    private static final int NUMBER_OF_RECORDS = 10;

    /**
     * Puts data into request and forwards it to user.jsp
     *
     * @param request
     *          servlet request
     * @param response
     *          servlet response
     * @throws ServletException when something bad happens
     * @throws IOException  when something else, but also bad, happens
     */
    @Override
    protected void doGet(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        
        String siPageAttr = request.getParameter("siPage");
        String soPageAttr = request.getParameter("soPage");
        int siPage = (siPageAttr == null) ? 1 : Integer.parseInt(siPageAttr);
        int soPage = (soPageAttr == null) ? 1 : Integer.parseInt(soPageAttr);

        try {
            dbManager = new DBManager();
        } catch (DBManagerException ex) {
            Logger.getLogger(CustomerUserServlet.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

        try {
            int userID = getUserID(request);
            ServiceOrderDAO orderDAO = new ServiceOrderDAOImpl(dbManager);
            int numbOfSORecords = orderDAO.countAllSOByStatus(userID, OrderStatus.PROCESSING.toInt());
            int numbOfSIRecords = orderDAO.countUsersActiveSIs(userID);
            
            Integer numbOfSOPages = (numbOfSORecords - 1) / NUMBER_OF_RECORDS + 1;
            Integer numbOfSIPages = (numbOfSIRecords - 1) / NUMBER_OF_RECORDS + 1;
            
            request.setAttribute("numbOfSOPages", numbOfSOPages);
            request.setAttribute("numbOfSIPages", numbOfSIPages);

            request.setAttribute("activeInstances",
                    getActiveInstancesList(userID, siPage, NUMBER_OF_RECORDS));
            request.setAttribute("processingOrders",
                    getProcessingOrdersList(userID, soPage, NUMBER_OF_RECORDS));
            
            RequestDispatcher view =
                (request.isUserInRole("SUPPORT_ENGINEER")) ? 
                 request.getRequestDispatcher("userSOandSI.jsp") :
                 request.getRequestDispatcher("user.jsp");
            view.forward(request, response);

        } catch (DBManagerException ex) {
            Logger.getLogger(CustomerUserServlet.class.getName())
                    .log(Level.SEVERE, null, ex);
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
        if ("Disconnect".equals(request.getParameter("action"))) {
            if (request.getParameter("serviceInstanceID") != null) {
                int serviceInstanceId;
                CustomerUserController userControl;
                try {
                    serviceInstanceId = Integer.parseInt(
                    		request.getParameter("serviceInstanceID"));
                    
                    userControl = new CustomerUserController();
                    userControl.proceedToDisconnect(serviceInstanceId);
                } catch (DBManagerException wfExc) {
                    Logger.getLogger(CustomerUserServlet.class.getName())
                            .log(Level.SEVERE, null, wfExc);
                    response.sendRedirect("CustomerUser");
                } catch(NumberFormatException numExc) {
                	response.sendRedirect("CustomerUser");
                }
            }
            
            //redirect to user cabinet
            response.sendRedirect("CustomerUser");
            return;
        }
               
        //if need to create new SO
        if (request.getParameter("serviceLocationID") != null
                && request.getParameter("serviceCatalogID") != null) {
            processOrder(request, response);
        } 
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
            return "Creates data to display on customer user dashboard";
    }

    /**
     * Get userID from request; if there is none - get one from DB,
     * put it in there and return the value
     */
    private int getUserID(HttpServletRequest request) throws DBManagerException {
        if (request.getParameter("userID") == null) {
            OSSUserDAO userDAO = new OSSUserDAOImpl(dbManager);
            String login = request.getUserPrincipal().getName();
            OSSUser user = userDAO.getUserByLogin(login);
            request.setAttribute("userID", user.getId());
            return user.getId();
        }
        return Integer.parseInt(request.getParameter("userID"));
    }

    /**
     * Proceed the service order. 
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    private void processOrder(HttpServletRequest request, 
            HttpServletResponse response) 
            throws IOException {
        try {
            dbManager = new DBManager();
            int userID = getUserID(request);
            int catalogID =
                    Integer.parseInt(request.getParameter("serviceCatalogID"));
            String serviceLocation =
                    request.getParameter("serviceLocationID");
            serviceLocation = java.net.URLDecoder.decode(serviceLocation,
                        "UTF-8");
            ServiceOrder newOrder = createOrder(dbManager, userID,
                    serviceLocation, catalogID);
            dbManager.commit();
            
            Workflow wf = new NewScenarioWorkflow(newOrder, dbManager);
            wf.proceedOrder();
            dbManager.commit();
            response.sendRedirect("CustomerUser");
        } catch (DBManagerException ex) {
            Logger.getLogger(CustomerUserServlet.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            dbManager.close();
        }
    }

    /**
     * Creates new Service Order with status ENTERING
     * @param dbManager
     * @param userID
     * @return so - new Service Order
     */
    private ServiceOrder createOrder(DBManager dbManager, int userID,
            String serviceLocation, int catalogID)
            throws DBManagerException {

        ServiceOrderDAO orderDAO = new ServiceOrderDAOImpl(dbManager);

        ServiceOrder so = new ServiceOrder();
        so.setServiceOrderStatusID(OrderStatus.ENTERING.toInt());
        so.setScenarioID(WorkflowScenario.NEW.toInt());
        so.setServiceCatalogID(catalogID);
        so.setServiceInstanceID(null);
        so.setServiceLocation(serviceLocation);
        so.setUserID(userID);

        Calendar cal = java.util.Calendar.getInstance();
        Date date = new Date(cal.getTimeInMillis());
        so.setServiceOrderDate(date);

        int orderID = (Integer) orderDAO.add(so);
        so.setId(orderID);
        
        return so;
    }


    /**
     * Getting active instances data to display on customer user dashboard
     *
     * @param userID
     *      id of the user whose info we are getting
     * @param page
     *      number of the page (used in paging)
     * @param numbOfRecords
     *      amount of records to get (used in paging)
     * @return list of maps, where each map represents data 
     * for a row in a table; map has element name String
     * associated with data String
     * 
     */
    public List<Map<String, String>> getActiveInstancesList(int userID,
            int page, int numbOfRecords) throws DBManagerException {
    	
        ServiceCatalogDAO catalogDAO = new ServiceCatalogDAOImpl(dbManager);
        ServiceTypeDAO typeDAO = new ServiceTypeDAOImpl(dbManager);
        ServiceOrderDAO orderDAO = new ServiceOrderDAOImpl(dbManager);
        
        List<Map<String, String>> activeInstances =
               new ArrayList<Map<String, String>>();
        List<ServiceOrder> completeOrders = orderDAO.getSOByStatus(userID,
                            OrderStatus.COMPLETED.toInt(),
                           (numbOfRecords * (page - 1)), numbOfRecords);
        
        for (ServiceOrder o: completeOrders) {
            Map<String,String> row = new HashMap<String, String>();
            ServiceInstanceDAO instanceDAO = new ServiceInstanceDAOImpl(dbManager);
            
            ServiceInstance instance = instanceDAO.find(o.getServiceInstanceID());
            if (instance.getServiceInstanceStatusID() == InstanceStatus.ACTIVE.toInt()) {
                int catalogID = o.getServiceCatalogID();
                ServiceCatalog sc = catalogDAO.find(catalogID);
                ServiceType st = typeDAO.find(sc.getServiceTypeID());

                row.put("orderID", Integer.toString(o.getId()));
                row.put("serviceLocation", o.getServiceLocation());
                row.put("serviceDescription", st.getService());
                row.put("orderDate", o.getServiceOrderDate().toString());
                row.put("price", Integer.toString(sc.getPrice()));
                row.put("instanceID", Integer.toString(o.getServiceInstanceID()));
                
                activeInstances.add(row);
            }
        } 
        return activeInstances;
    }

    /**
     * Getting processing orders data to display on customer user dashboard
     *
     * @param userID
     *      id of the user whose info we are getting
     * @param page
     *      number of the page (used in paging)
     * @param numbOfRecords
     *      amount of records to get (used in paging)
     * @return list of maps, where each map represents data 
     * for a row in a table; map has element name String
     * associated with data String
     *
     */
    public List<Map<String, String>> getProcessingOrdersList(int userID,
            int page, int numbOfRecords) throws DBManagerException {
    	
        ScenarioDAO scenarioDAO = new ScenarioDAOImpl(dbManager);
        ServiceCatalogDAO catalogDAO = new ServiceCatalogDAOImpl(dbManager);
        ServiceTypeDAO typeDAO = new ServiceTypeDAOImpl(dbManager);
        ServiceOrderDAO orderDAO = new ServiceOrderDAOImpl(dbManager);
        
        List<Map<String, String>> processingOrders =
               new ArrayList<Map<String, String>>();
        List<ServiceOrder> orders = orderDAO.getSOByStatus(userID,
                           OrderStatus.PROCESSING.toInt(),
                           (numbOfRecords * (page - 1)), numbOfRecords);
        for (ServiceOrder o: orders) {
            Map<String,String> row = new HashMap<String, String>();
            int catalogID = o.getServiceCatalogID();
            ServiceCatalog sc = catalogDAO.find(catalogID);
            ServiceType st = typeDAO.find(sc.getServiceTypeID());
            Scenario s = scenarioDAO.find(o.getScenarioID());
            row.put("orderID", Integer.toString(o.getId()));
            row.put("scenario", s.getScenario());
            row.put("serviceLocation", o.getServiceLocation());
            row.put("serviceDescription", st.getService());
            row.put("orderDate", o.getServiceOrderDate().toString());
            
            processingOrders.add(row);
        }
        return processingOrders;
    }

}
