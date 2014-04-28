package nc.notus.dashboards;

import java.io.IOException;
import java.util.List;

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
import nc.notus.dao.ServiceInstanceDAO;
import nc.notus.dao.ServiceTypeDAO;
import nc.notus.dao.impl.OSSUserDAOImpl;
import nc.notus.dao.impl.ScenarioDAOImpl;
import nc.notus.dao.impl.ServiceCatalogDAOImpl;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dao.impl.ServiceInstanceDAOImpl;
import nc.notus.dao.impl.ServiceTypeDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.OSSUser;
import nc.notus.entity.Scenario;
import nc.notus.entity.ServiceCatalog;
import nc.notus.entity.ServiceOrder;
import nc.notus.entity.ServiceInstance;
import nc.notus.entity.ServiceType;
import nc.notus.states.OrderStatus;

/**
 *
 * @author Katya Atamanchuk <nira@niralittle.name>
 */
public class CustomerUserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private DBManager dbManager = null;
    private HttpServletRequest request = null;
    private List<ServiceOrder> completeOrders;
    private List<ServiceOrder> processingOrders;

    void processRequest(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int startpage = 1;
        int numbOfRecords = 100;
        dbManager = new DBManager();
        this.request = request;
        try {
            int userID = getUserID();
            
            ServiceOrderDAO orderDAO = new ServiceOrderDAOImpl(dbManager); 
            completeOrders = (List<ServiceOrder>) orderDAO.getSOByStatus(userID,
            OrderStatus.COMPLETED.ordinal() + 1, startpage, numbOfRecords);
            processingOrders = (List<ServiceOrder>) orderDAO.getSOByStatus(userID,
            OrderStatus.PROCESSING.ordinal() + 1, startpage, numbOfRecords);
        
            fillRequestWithHtml();

            RequestDispatcher view = request.getRequestDispatcher("user.jsp");
            view.forward(this.request, response);

        } finally {
            dbManager.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

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

    private int getUserID() {

        HttpSession session = request.getSession();
        Integer userIDString = (Integer) session.getAttribute("userID");

        if (userIDString == null) {
            String login = request.getUserPrincipal().getName();
            OSSUserDAO userDAO = new OSSUserDAOImpl(dbManager);
            OSSUser user = userDAO.getUserByLogin(login);
            if (user == null) return -1;
            session.setAttribute("userID", user.getId());
            return user.getId();
        } else return userIDString;
    }

  

    private void fillRequestWithHtml() {
       StringBuilder sb = new StringBuilder();
       
       ServiceCatalogDAO catalogDAO = new ServiceCatalogDAOImpl(dbManager);
       ServiceTypeDAO typeDAO = new ServiceTypeDAOImpl(dbManager);
       ScenarioDAO scenarioDAO = new ScenarioDAOImpl(dbManager); 
           
       if (completeOrders.size() > 0) {
            int catalogID;
            ServiceCatalog sc;
            ServiceType st;

            sb.append("<table class='completeOrders'>");
                 sb.append("<thead>");
                    sb.append("<tr>");
                        sb.append("<td>Location</td>");
                        sb.append("<td>Type of Service</td>");
                        sb.append("<td>Since</td>");
                        sb.append("<td>Price</td>");
                        sb.append("<td>Options</td>");
                    sb.append("</tr>");
                 sb.append("</thead>");

             for (ServiceOrder o: completeOrders) {
                catalogID = o.getServiceCatalogID();
                sc = catalogDAO.find(catalogID);
                st = typeDAO.find(sc.getServiceTypeID());
                    sb.append("<tr>");
                        sb.append("<td>").append(o.getServiceLocation()).append("</td>");
                        sb.append("<td>").append(st.getService()).append("</td>");
                        sb.append("<td>").append(o.getServiceOrderDate()).append("</td>");
                        sb.append("<td>").append(sc.getPrice()).append("</td>");
                        sb.append("<td>");
                            sb.append("<button>Modify</button>");
                            sb.append("<button>Disconnect</button>");
                        sb.append("</td>");
                    sb.append("</tr>");
                 }
        sb.append("</table>");
             }

             if (processingOrders.size() > 0) {
                int catalogID;
                ServiceCatalog sc;
                ServiceType st;
                Scenario s; 

        sb.append("<h2>Orders being processed:</h2>");
        sb.append("<table class='processingOrders'>");
             sb.append("<thead>");
                sb.append("<tr>");
                    sb.append("<td>Scenario</td>");
                    sb.append("<td>Location</td>");
                    sb.append("<td>Type of Service</td>");
                    sb.append("<td>Order Date</td>");
                sb.append("</tr>");
             sb.append("</thead>");

                  for (ServiceOrder o: processingOrders) {
                    catalogID = o.getServiceCatalogID();
                    sc = catalogDAO.find(catalogID);
                    st = typeDAO.find(sc.getServiceTypeID());
                    s = scenarioDAO.find(o.getScenarioID());
              
                sb.append("<tr>");
                    sb.append("<td>").append(s.getScenario()).append("</td>");
                    sb.append("<td>").append(o.getServiceLocation()).append("</td>");
                    sb.append("<td>").append(st.getService()).append("</td>");
                    sb.append("<td>").append(o.getServiceOrderDate()).append("</td>");
                sb.append("</tr>");
                } 
            sb.append("</table>");
           } 
       request.setAttribute("userDashboardHtml", sb.toString());
    }
}
