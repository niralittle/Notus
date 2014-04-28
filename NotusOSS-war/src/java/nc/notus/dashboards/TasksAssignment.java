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
import nc.notus.dao.OSSUserDAO;
import nc.notus.dao.ScenarioDAO;
import nc.notus.dao.ServiceOrderDAO;
import nc.notus.dao.TaskDAO;
import nc.notus.dao.impl.OSSUserDAOImpl;
import nc.notus.dao.impl.ScenarioDAOImpl;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dao.impl.TaskDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.OSSUser;
import nc.notus.entity.Scenario;
import nc.notus.entity.ServiceOrder;
import nc.notus.entity.Task;
import nc.notus.states.WorkflowScenario;

/**
 * Implements tasks assignment from role tasks to personal task for
 * all types of engineers
 * @author Vladimir Ermolenko
 */
public class TasksAssignment extends HttpServlet {
   
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
        int startpage = 1;
        int numbOfRecords = 10;
        DBManager dbManager = new DBManager();
        String login = "";
        OSSUser user = null;
        Task task = null;
        TaskDAO taskDAO = null;
        try {
            login = request.getUserPrincipal().getName();

            //if (request.getParameter("task") != null){
             //   task  = (Task) (request.getAttribute("task"));
            //}
            taskDAO = new TaskDAOImpl(dbManager);
            OSSUserDAO userDAO = new OSSUserDAOImpl(dbManager);
            if (userDAO.getUserByLogin(login) != null){
                user = userDAO.getUserByLogin(login);
            }

            //Action "Assign" tasks
            if (request.getParameter("action") != null && request.getParameter("action").equals("Assign")){
                taskDAO.assignTask(task);
                List<Task> tasksEng = taskDAO.getEngTasks(startpage, numbOfRecords, user.getRoleID());
                request.setAttribute("tasksEng", tasksEng);
                request.setAttribute("user", user);
                request.getRequestDispatcher("tasksAssignment.jsp").forward(request, response);
                return;
            }

            List<Task> tasksEng = taskDAO.getEngTasks(startpage, numbOfRecords, user.getRoleID());
            request.setAttribute("tasksEng", tasksEng);
            request.setAttribute("user", user);
            request.getRequestDispatcher("tasksAssignment.jsp").forward(request, response);
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
