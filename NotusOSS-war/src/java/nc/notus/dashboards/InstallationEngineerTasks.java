/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.dashboards;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nc.notus.dao.TaskDAO;
import nc.notus.dao.impl.TaskDAOImpl;
import nc.notus.dbmanager.DBManager;
import java.util.List;
import nc.notus.dao.OSSUserDAO;
import nc.notus.dao.impl.OSSUserDAOImpl;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.entity.Task;

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
    throws ServletException, IOException, DBManagerException {
        response.setContentType("text/html;charset=UTF-8");
        int page = 1;
        int numbOfRecords = 10;
        DBManager dbManager = new DBManager();
        String login = "";
        int userID = 0;
        try {
            if (request.getParameter("startpage") != null) {
                page = Integer.parseInt(request.getParameter("startpage"));
            }
            if (request.getParameter("numbOfRecords") != null) {
                numbOfRecords = Integer.parseInt(request.getParameter("numbOfRecords"));
            }
            if (request.getParameter("login") != null) {
                login = request.getUserPrincipal().getName();
            }
            OSSUserDAO userDAO = new OSSUserDAOImpl(dbManager);
            if (userDAO.getUserByLogin(login) != null){
                userID = userDAO.getUserByLogin(login).getId();
            }
            TaskDAO taskDAO = new TaskDAOImpl(dbManager);
            List<Task> tasks = taskDAO.getTasksByID((page - 1) * numbOfRecords, numbOfRecords, userID);
            request.setAttribute("tasks", tasks);
            request.setAttribute("userid", userID);
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
        try {
            processRequest(request, response);
        } catch (DBManagerException ex) {
            Logger.getLogger(InstallationEngineerTasks.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(InstallationEngineerTasks.class.getName()).log(Level.SEVERE, null, ex);
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
