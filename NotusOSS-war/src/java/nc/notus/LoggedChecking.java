/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nc.notus.dao.OSSUserDAO;
import nc.notus.dao.ScenarioDAO;
import nc.notus.dao.ServiceOrderDAO;
import nc.notus.dao.ServiceOrderStatusDAO;
import nc.notus.dao.TaskDAO;
import nc.notus.dao.impl.OSSUserDAOImpl;
import nc.notus.dao.impl.ScenarioDAOImpl;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dao.impl.ServiceOrderStatusDAOImpl;
import nc.notus.dao.impl.TaskDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.entity.OSSUser;
import nc.notus.entity.ServiceOrder;
import nc.notus.states.OrderStatus;
import nc.notus.states.WorkflowScenario;
import nc.notus.workflow.NewScenarioWorkflow;
import nc.notus.workflow.Workflow;

/**
 *
 * @author Vladimir Ermolenko
 */
public class LoggedChecking extends HttpServlet {

    String login;
    int catalogID;
    String serviceLocation;
    TaskDAO taskDAO = null;
    OSSUser user = null;
    ServiceOrder newOrder;
    Integer userID = null;

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, DBManagerException {
        response.setContentType("text/html;charset=UTF-8");
        DBManager dbManager = new DBManager();
        
        try {
            taskDAO = new TaskDAOImpl(dbManager);
            OSSUserDAO userDAO = new OSSUserDAOImpl(dbManager);

            
            if (request.getParameter("serviceCatalogID") != null) {
                catalogID = Integer.parseInt(request.getParameter("serviceCatalogID"));
            }
            if (request.getParameter("serviceLocationID") != null) {
                serviceLocation = request.getParameter("serviceLocationID");
                serviceLocation = java.net.URLDecoder.decode(serviceLocation,"UTF-8");
            }
            
            // if user already logged in system
            if (request.getUserPrincipal() != null) {
                login = request.getUserPrincipal().getName();
            }
            else {
                //if user not logged but registered user
                HttpSession session = request.getSession();
                session.setAttribute("serviceCatalogID", catalogID);
                session.setAttribute("serviceLocationID", serviceLocation);
                response.sendRedirect("CustomerUser");
                return;
            }

            if (userDAO.getUserByLogin(login) != null){
                user = userDAO.getUserByLogin(login);
            }
            
            userID = user.getId();
            // create new Service Order
            newOrder = createOrder(dbManager, userID);
            dbManager.commit();
            // proceed Service Order
            Workflow wf = new NewScenarioWorkflow(newOrder);
            wf.proceedOrder();
            response.sendRedirect("CustomerUser");
            return;

        } finally { 
            dbManager.close();
        }
    }

    /**
     * Create new Service Order with status ENTERING
     * @param dbManager
     * @param userID
     * @return so  - new Service Order
     */
    private ServiceOrder createOrder(DBManager dbManager, int userID) throws DBManagerException {

		ServiceOrderStatusDAO statusDAO = new ServiceOrderStatusDAOImpl(dbManager);
		ScenarioDAO scenarioDAO = new ScenarioDAOImpl(dbManager);
		ServiceOrderDAO orderDAO = new ServiceOrderDAOImpl(dbManager);

		// create new order with status ENTERING
		ServiceOrder so = new ServiceOrder();
		int orderStatusID = statusDAO.getServiceOrderStatusID(OrderStatus.ENTERING);
			so.setServiceOrderStatusID(orderStatusID);
			so.setScenarioID(scenarioDAO.getScenarioID(WorkflowScenario.NEW));
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

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (DBManagerException ex) {
            Logger.getLogger(LoggedChecking.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (DBManagerException ex) {
            Logger.getLogger(LoggedChecking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
