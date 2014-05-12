package nc.notus.dashboards;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nc.notus.dao.OSSUserDAO;
import nc.notus.dao.RoleDAO;
import nc.notus.dao.ServiceOrderDAO;
import nc.notus.dao.TaskDAO;
import nc.notus.dao.impl.OSSUserDAOImpl;
import nc.notus.dao.impl.RoleDAOImpl;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dao.impl.TaskDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.entity.OSSUser;
import nc.notus.entity.Role;
import nc.notus.entity.ServiceOrder;
import nc.notus.entity.Task;

/**
 * Servlet for getting all assigned tasks
 * and forwards it to jsp-page.
 * @author Alina Vorobiova
 */
public class TasksForReassignmentServlet extends HttpServlet {
	
    private final int RECORDS_PER_PAGE  = 10;
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws DBManagerException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, DBManagerException {
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
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("taskStatusID", "1");
           
            long countAll = taskDAO.countAllAssigned();
            int numberOfPages = (int) Math.ceil(countAll * 1.0/ RECORDS_PER_PAGE);
            request.setAttribute("pages", numberOfPages);
            
            int page = 1;
            if(request.getParameter("page") != null){
            	try{
            		page = Integer.parseInt(request.getParameter("page"));
            	} catch(NumberFormatException e) {
            		page = 1;
            	}
            }
            request.setAttribute("page", page);
            int offset = (page-1) * RECORDS_PER_PAGE;
            List<Task> tasks = taskDAO.getAssignedTasks(offset, RECORDS_PER_PAGE);
            List<Map<String,String>> tasksInfo = new ArrayList<Map<String, String>>();
            ServiceOrderDAO soDAO = new ServiceOrderDAOImpl(dbManager);
            OSSUserDAO userDAO = new OSSUserDAOImpl(dbManager);
            RoleDAO roleDAO = new RoleDAOImpl(dbManager);
            for (Task task : tasks) {
                Map<String,String> taskInfo = new HashMap<String, String>();
                taskInfo.put("taskID", String.valueOf(task.getId()));
                ServiceOrder so = soDAO.find(task.getServiceOrderID());
                taskInfo.put("soAdr", so.getServiceLocation());
                taskInfo.put("taskName", task.getName());
                taskInfo.put("empID", String.valueOf(task.getEmployeeID()));
                OSSUser user = userDAO.find(task.getEmployeeID());
                taskInfo.put("name", user.getFirstName());
                taskInfo.put("surname", user.getLastName());
                Role role = roleDAO.find(task.getRoleID());
                taskInfo.put("role", role.getRole());


                tasksInfo.add(taskInfo);
            }
            request.setAttribute("listOfTasks", tasksInfo);
            request.getRequestDispatcher("tasksForReasignment.jsp").forward(request, response);
        } catch(DBManagerException ex) {
        	request.setAttribute("errMessage", "Service unavailable.");
        	request.getRequestDispatcher("tasksForReasignment.jsp").forward(request, response);
        } finally { 
            out.close();
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
            Logger.getLogger(TasksForReassignmentServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(TasksForReassignmentServlet.class.getName()).log(Level.SEVERE, null, ex);
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
