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

import nc.notus.dao.ScenarioDAO;
import nc.notus.dao.TaskDAO;
import nc.notus.dao.impl.ScenarioDAOImpl;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dao.impl.TaskDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.entity.Scenario;
import nc.notus.entity.ServiceOrder;
import nc.notus.entity.Task;
import nc.notus.states.WorkflowScenario;
import nc.notus.workflow.DisconnectScenarioWorkflow;

/**
 * Implements part of Provisioning Engineer dashboard
 * 
 * @author Vladimir Ermolenko & Panchenko Dmytro
 */
public class ProvisionEngineerTasksServlet extends HttpServlet {

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException, DBManagerException {
		response.setContentType("text/html;charset=UTF-8");

		if (request.getParameter("taskid") != null) {
			
			int taskID = Integer.parseInt(request.getParameter("taskID"));
			DBManager dbManager = null;
			try {
				dbManager = new DBManager();
				TaskDAO taskDAO = new TaskDAOImpl(dbManager);

				Task task = taskDAO.find(taskID);

				String wfScenario = getTaskScenario(task, dbManager);
				request.setAttribute("wfScenario", wfScenario);
				request.setAttribute("task", task);
				
				request.getRequestDispatcher("provisioningEngineerWorkflow.jsp").forward(request, response);
				
			} finally {
				dbManager.close();
			}
		}

	}

	private String getTaskScenario(Task task, DBManager dbManager) throws DBManagerException {

		String wfScenario = null;

		ServiceOrderDAOImpl soDAO = new ServiceOrderDAOImpl(dbManager);
		ScenarioDAO scenarioDAO = new ScenarioDAOImpl(dbManager);

		ServiceOrder order = soDAO.find(task.getServiceOrderID());

		int scenarioID = order.getScenarioID();
		Scenario scenario = scenarioDAO.find(scenarioID);

		if (scenario.getScenario().equalsIgnoreCase(WorkflowScenario.NEW.toString())) {
			wfScenario = "NEW";
		} else if ((scenario.getScenario().equalsIgnoreCase(WorkflowScenario.DISCONNECT.toString()))) {
			wfScenario = "DISCONNECT";
		}

		return wfScenario;
	}

	// <editor-fold defaultstate="collapsed"
	// desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (DBManagerException ex) {
            Logger.getLogger(ProvisionEngineerTasksServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		if ("Delete Circuit from SI".equals(request.getParameter("action"))) {

			DBManager dbManager = null;
			try {
				dbManager = new DBManager();
				ServiceOrderDAOImpl orderDAO = new ServiceOrderDAOImpl(dbManager);
				
				DisconnectScenarioWorkflow wf = null;
				if (request.getParameter("d_orderid") == null
						| request.getParameter("d_taskid") == null) {
					request.setAttribute("success", "Parameters not passes");
				} else {
					// getting ServiceOrder id and Task id
					int orderID = Integer.parseInt(request.getParameter("d_orderid"));
					int taskID = Integer.parseInt(request.getParameter("d_taskid"));

					ServiceOrder order = orderDAO.find(orderID);
					wf = new DisconnectScenarioWorkflow(order);
					wf.removeCurcuitFromSI(taskID);

					request.setAttribute("success","Circuit successfully deleted!");
					request.getRequestDispatcher("provisioningEngineerWorkflow.jsp").forward(request, response);
				}
			} catch (DBManagerException ex) {
                Logger.getLogger(ProvisionEngineerTasksServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
				dbManager.close();
			}
		}
	}

	/**
	 * Returns a short description of the servlet.
	 * 
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}
