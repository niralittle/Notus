/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.dashboards;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nc.notus.dao.TaskDAO;
import nc.notus.dao.impl.TaskDAOImpl;
import nc.notus.dbmanager.DBManager;
import java.util.List;
import nc.notus.dao.OSSUserDAO;
import nc.notus.dao.ServiceOrderDAO;
import nc.notus.dao.impl.OSSUserDAOImpl;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.entity.OSSUser;
import nc.notus.entity.ServiceOrder;
import nc.notus.entity.Task;
import nc.notus.states.UserRole;
import nc.notus.workflow.NewScenarioWorkflow;

/**
 * Implements part of Installation Engineer dashboard
 * @author Vladimir Ermolenko
 */
public class InstallationEngineerTasks extends HttpServlet {

   
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
        int taskID;
        int soID;
        DBManager dbManager = new DBManager();
        try {
            if (request.getParameter("startpage") != null) {
                startpage = Integer.parseInt(request.getParameter("startpage"));
            }
            if (request.getParameter("numbOfRecords") != null) {
                numbOfRecords = Integer.parseInt(request.getParameter("numbOfRecords"));
            }

            TaskDAO taskDAO = new TaskDAOImpl(dbManager);
            List<Task> tasksEng = taskDAO.getEngTasks(startpage, numbOfRecords, UserRole.INSTALLATION_ENGINEER.toInt());
            if (request.getParameter("taskid") != null){
                taskID  = Integer.parseInt(request.getParameter("taskid"));
            }
            else {
                taskID = 1;
            }
            if (request.getParameter("serviceorderid") != null){
                soID  = Integer.parseInt(request.getParameter("serviceorderid"));
            }
            else {
                soID = 1;
            }

            ServiceOrderDAO soDAO = new ServiceOrderDAOImpl(dbManager);
            ServiceOrder so = soDAO.find(soID);
            NewScenarioWorkflow nwf = new NewScenarioWorkflow(so);

            //String login = request.getUserPrincipal().getName();
            OSSUserDAO userDAO = new OSSUserDAOImpl(dbManager);
            OSSUser user = userDAO.find(4); //just stub till
                                            //writing getUserByLogin(request.getUserPrincipal().getName())

            //Action "Assign Task"
            if (request.getParameter("action").equals("Assign")){
                nwf.assignTask(taskID, user.getId());
                taskDAO = new TaskDAOImpl(dbManager);
                tasksEng = taskDAO.getEngTasks(startpage, numbOfRecords, UserRole.INSTALLATION_ENGINEER.toInt());
                request.setAttribute("tasksEng", tasksEng);
                request.getRequestDispatcher("installationEngineer.jsp").forward(request, response);
                return;
                }


            //Action "Submit Task"
            if (request.getParameter("action").equals("Submit")){
                    //request.setAttribute("taskid", taskID);
                    //request.setAttribute("soid", soID);
                    response.sendRedirect("SubmitTask");
                    //request.getRequestDispatcher("installationEngineerWorkflow.jsp").forward(request, response);
                    return;
                }


//            request.setAttribute("taskid", taskID);
//            request.setAttribute("soid", soID);
//            request.getRequestDispatcher("installationEngineer.jsp").forward(request, response);


            taskDAO = new TaskDAOImpl(dbManager);
            tasksEng = taskDAO.getEngTasks(startpage, numbOfRecords, UserRole.INSTALLATION_ENGINEER.toInt());
            request.setAttribute("tasksEng", tasksEng);
            request.getRequestDispatcher("installationEngineer.jsp").forward(request, response);
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
