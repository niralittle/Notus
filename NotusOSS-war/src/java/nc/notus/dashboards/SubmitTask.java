/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.dashboards;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nc.notus.dao.PortDAO;
import nc.notus.dao.ServiceOrderDAO;
import nc.notus.dao.TaskDAO;
import nc.notus.dao.impl.PortDAOImpl;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dao.impl.TaskDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.Cable;
import nc.notus.entity.Port;
import nc.notus.entity.ServiceOrder;
import nc.notus.entity.Task;
import nc.notus.states.UserRole;
import nc.notus.workflow.NewScenarioWorkflow;

/**
 * Implements part of Installation Engineer dashboard
 * @author Vladimir Ermolenko
 */
public class SubmitTask extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        DBManager dbManager = new DBManager();
        int portQuantity = 60;
        Cable cable;
        int taskID;
        int soID;
        try {
            if (request.getParameter("taskid") != null){
                taskID  = Integer.parseInt(request.getParameter("taskid"));
            }
            else {
                taskID = 0;
            }
            if (request.getParameter("serviceorderid") != null){
                soID  = Integer.parseInt(request.getParameter("serviceorderid"));
            }
            else {
                soID = 0;
            }
            if (request.getParameter("cable") != null){
                cable  = (Cable) (request.getAttribute("serviceorderid"));
            }
            else {
                cable = new Cable();
            }
            ServiceOrderDAO soDAO = new ServiceOrderDAOImpl(dbManager);
            ServiceOrder so = soDAO.find(soID);
            PortDAO portDAO = new PortDAOImpl(dbManager);
            Port port = portDAO.getFreePort();
            NewScenarioWorkflow nwf = new NewScenarioWorkflow(so);

            //Action "Create Router"
            if (request.getParameter("action").equals("Create Router")){
                if (port == null){
                nwf.createRouter(taskID, portQuantity);
                }
            }

            //Action "Create Cable"
            if (request.getParameter("action").equals("Create Cable")){
                nwf.createCable(taskID, "UTP Cable");
            }

            //Action "Connect Cable to Port"
            if (request.getParameter("action").equals("Connect Cable to Port")){
                nwf.plugCableToPort(taskID, 1, port.getId());
                TaskDAO taskDAO = new TaskDAOImpl(dbManager);
                int startpage = 1;
                int numbOfRecords = 10;
                List<Task> tasksEng = taskDAO.getEngTasks(startpage, numbOfRecords, UserRole.INSTALLATION_ENGINEER.toInt());
                dbManager.commit();
                request.setAttribute("tasksEng", tasksEng);
                request.getRequestDispatcher("installationEngineer.jsp").forward(request, response);
            }

            request.setAttribute("port", port);
            request.setAttribute("cable", cable);
            request.setAttribute("taskid", taskID);
            request.setAttribute("soid", soID);
            request.getRequestDispatcher("installationEngineerWorkflow.jsp").forward(request, response);
        } finally {
                dbManager.close();
        }
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
        processRequest(request, response);
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
        processRequest(request, response);
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
