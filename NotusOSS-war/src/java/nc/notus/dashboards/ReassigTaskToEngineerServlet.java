package nc.notus.dashboards;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nc.notus.dao.OSSUserDAO;
import nc.notus.dao.RoleDAO;
import nc.notus.dao.TaskDAO;
import nc.notus.dao.impl.OSSUserDAOImpl;
import nc.notus.dao.impl.RoleDAOImpl;
import nc.notus.dao.impl.TaskDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.entity.OSSUser;
import nc.notus.entity.Role;
import nc.notus.entity.Task;

/**
 * Servlet for getting all employees by role
 * and forwards it to jsp-page.
 * @author Alina Vorobiova
 */
public class ReassigTaskToEngineerServlet extends HttpServlet {
	
    private final int RECORDS_PER_PAGE = 5;
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
        OSSUserDAO userDAO = new OSSUserDAOImpl(dbManager);
        int taskID;
        Task task;
        try {            
            taskID = Integer.parseInt(request.getParameter("taskID"));
            task = taskDAO.find(taskID);
            int roleID = task.getRoleID();
            RoleDAO roleDAO = new RoleDAOImpl(dbManager);
            Role role = roleDAO.find(task.getRoleID());
            request.setAttribute("role", role.getRole());
            long countAll = userDAO.countAssignedByRoleID(roleID);
            Integer numberOfPages = (int)(countAll - 1) / RECORDS_PER_PAGE + 1;
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
            
            List<OSSUser> engineers = userDAO.getUsersByRoleID(roleID, offset, RECORDS_PER_PAGE);
            request.setAttribute("listOfEngineers", engineers);
            request.setAttribute("taskID", taskID);
            
            request.getRequestDispatcher("reassignTaskEngineer.jsp").forward(request, response);
        } catch (DBManagerException e) {
			request.setAttribute("errMessage", "Error was occured. ");
			request.getRequestDispatcher("reassignTaskEngineer.jsp").forward(request, response);
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
            Logger.getLogger(ReassigTaskToEngineerServlet.class.getName()).log(Level.SEVERE, null, ex);
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
			} catch (DBManagerException e) {
				// redirect to page with error message
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
