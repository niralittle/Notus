/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.dashboards;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nc.notus.dao.CableDAO;
import nc.notus.dao.PortDAO;
import nc.notus.dao.ServiceOrderDAO;
import nc.notus.dao.TaskDAO;
import nc.notus.dao.impl.CableDAOImpl;
import nc.notus.dao.impl.PortDAOImpl;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dao.impl.TaskDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.entity.Cable;
import nc.notus.entity.Port;
import nc.notus.entity.ServiceOrder;
import nc.notus.entity.Task;
import nc.notus.workflow.NewScenarioWorkflow;

/**
 * Implements part of Installation Engineer dashboard
 * @author Vladimir Ermolenko
 */
public class SubmitTask extends HttpServlet {

    private String actionStatus;
    
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
        int portQuantity = 60;
        Cable cable;
        Port port;
        int taskID = 0;
        int soID = 0;
        int userID = 0;
        String taskName = "";
        String serviceLocation = "";
        String serviceDescription = "";
        int price = 0;
        try {
            if (request.getParameter("taskid") != null){
                taskID  = Integer.parseInt(request.getParameter("taskid"));
            }
            if (request.getParameter("userid") != null){
                userID  = Integer.parseInt(request.getParameter("userid"));
            }
            if (request.getParameter("serviceorderid") != null){
                soID  = Integer.parseInt(request.getParameter("serviceorderid"));
            }
            if (request.getParameter("cable") != null){
                cable  = (Cable) (request.getAttribute("cable"));
            }
            if (request.getParameter("port") != null){
                port  = (Port) (request.getAttribute("port"));
            }
            if (request.getParameter("taskName") != null){
                taskName  = (String) request.getParameter("taskName");
            }
            if (request.getParameter("serviceLocation") != null){
                serviceLocation  = (String) request.getParameter("serviceLocation");
            }
            if (request.getParameter("serviceDescription") != null){
                serviceDescription  = (String) request.getParameter("serviceDescription");
            }
            if (request.getParameter("price") != null){
                price  = Integer.parseInt(request.getParameter("price"));
            }
            ServiceOrderDAO soDAO = new ServiceOrderDAOImpl(dbManager);
            ServiceOrder so = soDAO.find(soID);
            PortDAO portDAO = new PortDAOImpl(dbManager);
            port = portDAO.getFreePort();
            CableDAO cableDAO = new CableDAOImpl(dbManager);
            cable = cableDAO.getFreeCable();
            NewScenarioWorkflow nwf = new NewScenarioWorkflow(so, dbManager);

            //Action "Create Router"
            if (request.getParameter("action") != null && "Create Router".equals(request.getParameter("action"))){
                try {
                    nwf.createRouter(taskID, portQuantity);
                    actionStatus = "Router was created";
                    dbManager.commit();
                } catch (DBManagerException ex) {
                    dbManager.rollback();
                    actionStatus = "Error! Router was not created";
                }
                port = portDAO.getFreePort();
            }

            //Action "Create Cable"
            if (request.getParameter("action") != null && "Create Cable".equals(request.getParameter("action"))){
                try {
                    nwf.createCable(taskID, "UTP Cable");
                    actionStatus = "Cable was created, task completed";
                    dbManager.commit();
                } catch (DBManagerException ex) {
                    dbManager.rollback();
                    actionStatus = "Cable was not created";
                }
                cable = cableDAO.getFreeCable();
            }

            //Action "Connect Cable to Port" and redirect to personal tasks page
            if (request.getParameter("action") != null && "Connect Cable to Port".equals(request.getParameter("action"))){
                 try {
                     nwf.plugCableToPort(taskID, cable.getId(), port.getId());
                     actionStatus = "Cable connection was created";
                     dbManager.commit();
                 } catch (DBManagerException ex) {
                    dbManager.rollback();
                    actionStatus = "Cable connection was not created";
                }
                TaskDAO taskDAO = new TaskDAOImpl(dbManager);
                int offset = 0;
                int numbOfRecords = 10;
                boolean personal = true;
                List<Task> tasks = taskDAO.getTasksByID(offset, numbOfRecords, userID);
                request.setAttribute("userid", userID);
                request.setAttribute("tasks", tasks);
                request.setAttribute("type", personal);
                response.sendRedirect("TasksAssignment?type=personal&actionStatus="+actionStatus);
                return;
            }
            request.setAttribute("taskName", taskName);
            request.setAttribute("serviceLocation", serviceLocation);
            request.setAttribute("serviceDescription", serviceDescription);
            request.setAttribute("price", price);
            request.setAttribute("cable", cable);
            request.setAttribute("port", port);
            request.setAttribute("taskid", taskID);
            request.setAttribute("soid", soID);
            request.setAttribute("userid", userID);
            request.setAttribute("actionStatus", actionStatus);
            request.getRequestDispatcher("installationEngineerWorkflow.jsp").forward(request, response);
        } catch (DBManagerException e) {
			request.setAttribute("errMessage", "Error was occured. ");
			request.getRequestDispatcher("installationEngineerWorkflow.jsp").forward(request, response);
		} finally {
                dbManager.close();
        }
    } 

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
            Logger.getLogger(SubmitTask.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(SubmitTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
