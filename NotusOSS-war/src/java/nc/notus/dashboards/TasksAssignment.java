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
import nc.notus.dao.CableDAO;
import nc.notus.dao.OSSUserDAO;
import nc.notus.dao.PortDAO;
import nc.notus.dao.ServiceOrderDAO;
import nc.notus.dao.TaskDAO;
import nc.notus.dao.impl.CableDAOImpl;
import nc.notus.dao.impl.OSSUserDAOImpl;
import nc.notus.dao.impl.PortDAOImpl;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dao.impl.TaskDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.Cable;
import nc.notus.entity.OSSUser;
import nc.notus.entity.Port;
import nc.notus.entity.ServiceOrder;
import nc.notus.entity.Task;
import nc.notus.states.UserRole;

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
        int taskID;
        List<Task> tasksEng;
        boolean personal = false;
        try {
            taskDAO = new TaskDAOImpl(dbManager);
            OSSUserDAO userDAO = new OSSUserDAOImpl(dbManager);

            if (request.getUserPrincipal() != null) {
                login = request.getUserPrincipal().getName();
            }
            else {
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
//            if (request.getParameter("taskid") != null){
//                taskID  = Integer.parseInt(request.getParameter("taskid"));
//                task = taskDAO.find(taskID);
//            }

            if (request.getParameter("type") != null && request.getParameter("type").equals("personal")){
                personal = true;
            }

            if (userDAO.getUserByLogin(login) != null){
                user = userDAO.getUserByLogin(login);
            }

            //Action "Assign" tasks from group to personal or choose task from personal to execute it
            if (request.getParameter("action") != null && request.getParameter("action").equals("Submit")){
                if (request.getParameter("taskid") != null){
                    taskID  = Integer.parseInt(request.getParameter("taskid"));
                    task = taskDAO.find(taskID);
                }
                if (!personal) {
                    task.setEmployeeID(user.getId());
                    taskDAO.update(task);
                    dbManager.commit();
                }
                else {
                    request.setAttribute("taskid", task.getId());
                    request.setAttribute("user", user);
                    if (user.getRoleID() == UserRole.INSTALLATION_ENGINEER.toInt()) {
                        ServiceOrderDAO soDAO = new ServiceOrderDAOImpl(dbManager);
                        ServiceOrder so = soDAO.find(task.getServiceOrderID());
                        PortDAO portDAO = new PortDAOImpl(dbManager);
                        Port port = portDAO.getFreePort();
                        CableDAO cableDAO = new CableDAOImpl(dbManager);
                        Cable cable = cableDAO.getFreeCable();
                        request.setAttribute("port", port);
                        request.setAttribute("cable", cable);
                        request.setAttribute("taskid", task.getId());
                        request.setAttribute("soid", task.getServiceOrderID());
                        request.setAttribute("userid", user.getId());
                        request.getRequestDispatcher("installationEngineerWorkflow.jsp").forward(request, response);
                    }   
                    if (user.getRoleID() == UserRole.PROVISION_ENGINEER.toInt()) {
                        request.getRequestDispatcher("provisioningEngineerWorkflow.jsp").forward(request, response);
                    }
                    if (user.getRoleID() == UserRole.SUPPORT_ENGINEER.toInt()) {
                        request.getRequestDispatcher("supportEngineer.jsp").forward(request, response);
                    }
                }
            }

            if (!personal) {
                tasksEng = taskDAO.getEngTasks(startpage, numbOfRecords, user.getRoleID());
            }
            else {
                tasksEng = taskDAO.getTasksByID(startpage, numbOfRecords, user.getId());
            }
            request.setAttribute("tasksEng", tasksEng);
            request.setAttribute("type", personal);
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
