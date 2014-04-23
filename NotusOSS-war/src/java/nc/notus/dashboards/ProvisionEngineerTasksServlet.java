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
import nc.notus.dao.TaskDAO;
import nc.notus.dao.impl.TaskDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.Task;
import nc.notus.states.UserRole;

/**
 * Implements part of Installation Engineer dashboard
 * @author Vladimir Ermolenko
 */
public class ProvisionEngineerTasksServlet extends HttpServlet {
   
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
        try {
            if (request.getParameter("startpage") != null) {
                startpage = Integer.parseInt(request.getParameter("startpage"));
            }
            if (request.getParameter("numbOfRecords") != null) {
                numbOfRecords = Integer.parseInt(request.getParameter("numbOfRecords"));
            }
            TaskDAO taskDAO = new TaskDAOImpl(dbManager);
            List<Task> tasksEng = taskDAO.getEngTasks(startpage, numbOfRecords, UserRole.PROVISION_ENGINEER.toInt());
            request.setAttribute("tasksEng", tasksEng);
            request.getRequestDispatcher("provisioningEngineer.jsp").forward(request, response);
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
