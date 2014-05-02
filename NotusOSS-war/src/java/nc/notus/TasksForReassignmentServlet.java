/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nc.notus.dao.TaskDAO;
import nc.notus.dao.impl.TaskDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.Task;

/**
 * Servlet for getting all assigned tasks
 * and forwards it to jsp
 * @author Alina Vorobiova
 */
public class TasksForReassignmentServlet extends HttpServlet {
    private final int OFFSET = 1;
    private final int NUMBER_OF_RECORDS = 100;
   
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
        PrintWriter out = response.getWriter();
        DBManager dbManager = new DBManager();
        TaskDAO taskDAO = new TaskDAOImpl(dbManager);
        try {
            String taskID = request.getParameter("taskID");
            String engineerID = request.getParameter("engineerID");
            if(taskID != null && engineerID != null){
                Task task = taskDAO.find(Integer.parseInt(taskID));
                task.setEmployeeID(Integer.parseInt(engineerID));
                taskDAO.update(task);
                dbManager.commit();
            }
            List<Task> tasks = taskDAO.getAssignedTasks(OFFSET, NUMBER_OF_RECORDS);
            request.setAttribute("listOfTasks", tasks);
            request.getRequestDispatcher("tasksForReasignment.jsp").forward(request, response);
        } finally { 
            out.close();
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