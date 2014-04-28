/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.dashboards;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nc.notus.dao.OSSUserDAO;
import nc.notus.dao.ScenarioDAO;
import nc.notus.dao.TaskDAO;
import nc.notus.dao.impl.OSSUserDAOImpl;
import nc.notus.dao.impl.ScenarioDAOImpl;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dao.impl.TaskDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.Scenario;
import nc.notus.entity.ServiceOrder;
import nc.notus.entity.Task;
import nc.notus.states.UserRole;
import nc.notus.states.WorkflowScenario;
import nc.notus.workflow.DisconnectScenarioWorkflow;

/**
 * Implements part of Installation Engineer dashboard
 * @author Vladimir Ermolenko & Panchenko Dmytro
 */
public class ProvisionEngineerTasksServlet extends HttpServlet {
   
	private List<Task> newScenarioTasks;
	private List<Task> disconnectScenarioTasks;
	
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
        String login = "";
        int userID = 0;
        DBManager dbManager = new DBManager();
        try {
            if (request.getParameter("startpage") != null) {
                startpage = Integer.parseInt(request.getParameter("startpage"));
            }
            if (request.getParameter("numbOfRecords") != null) {
                numbOfRecords = Integer.parseInt(request.getParameter("numbOfRecords"));
            }
            OSSUserDAO userDAO = new OSSUserDAOImpl(dbManager);
            if (userDAO.getUserByLogin(login) != null){
                userID = userDAO.getUserByLogin(login).getId();
            }
            TaskDAO taskDAO = new TaskDAOImpl(dbManager);
            List<Task> tasks = taskDAO.getEngTasks(startpage, numbOfRecords, 
					UserRole.PROVISION_ENGINEER.toInt());
           // List<Task> tasks = taskDAO.getTasksByID(startpage, numbOfRecords, userID);
            if(tasks.size() > 0) {
            	
            	sortTasksByScenario(tasks, dbManager);
            	
            	if (newScenarioTasks.size() > 0) {
        			request.setAttribute("newScenarioTasks", newScenarioTasks);
        		   }
        		   if (disconnectScenarioTasks.size() > 0) {
        			request.setAttribute("disconnectScenarioTasks",disconnectScenarioTasks);
        		   }

            }
           // request.setAttribute("tasks", tasks);
            request.setAttribute("userid", userID);
            request.getRequestDispatcher("provisioningEngineer.jsp").forward(request, response);
        } finally {
            dbManager.close();
        }
    } 

    private void sortTasksByScenario(List<Task> tasks, DBManager dbManager) {
    	
    	newScenarioTasks = new ArrayList<Task>();
		disconnectScenarioTasks = new ArrayList<Task>();
		
		ServiceOrderDAOImpl soDAO = new ServiceOrderDAOImpl(dbManager);
		ScenarioDAO scenarioDAO = new ScenarioDAOImpl(dbManager);

		for (Task task : tasks) {
			ServiceOrder order = soDAO.find(task.getServiceOrderID());

			int scenarioID = order.getScenarioID();
			Scenario scenario = scenarioDAO.find(scenarioID);

			if (scenario.getScenario().equalsIgnoreCase(WorkflowScenario.NEW.toString())) {
				newScenarioTasks.add(task);
			} else  {
				disconnectScenarioTasks.add(task);
			} 
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
    	if ("Delete Circuit from SI".equals(request.getParameter("action"))) {

			DBManager dbManager = null;
			try {
				dbManager = new DBManager();
				ServiceOrderDAOImpl orderDAO = new ServiceOrderDAOImpl(dbManager);
				DisconnectScenarioWorkflow wf = null;
				if(request.getParameter("d_orderid") == null 
						| request.getParameter("d_taskid") == null) {
					request.setAttribute("success", "Parameters not passes");
				} else {
				// getting ServiceOrder id and Task id
				int orderID = Integer.parseInt(request.getParameter("d_orderid"));
				int taskID = Integer.parseInt(request.getParameter("d_taskid"));

				ServiceOrder order = orderDAO.find(orderID);
				wf = new DisconnectScenarioWorkflow(order);
				wf.removeCurcuitFromSI(taskID);

				request.setAttribute("success", "Curcuit was removed!Order ");
				processRequest(request, response);
				}
			} finally {
				dbManager.close();
			}
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
