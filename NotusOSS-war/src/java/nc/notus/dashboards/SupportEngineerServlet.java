package nc.notus.dashboards;

import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nc.notus.dao.TaskDAO;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dao.impl.TaskDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.entity.ServiceOrder;
import nc.notus.entity.Task;
import nc.notus.workflow.NewScenarioWorkflow;

public class SupportEngineerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String SUPPORT_PAGE = "supportEngineer.jsp";

	void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

	

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
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

	if ("Send bill".equalsIgnoreCase(request.getParameter("action"))) {
		if (request.getParameter("taskid") != null) {
			int taskID = Integer.parseInt(request.getParameter("taskid"));
			
			DBManager dbManager = null;
			NewScenarioWorkflow wf = null;
			ServiceOrderDAOImpl orderDAO = null;
			TaskDAO taskDAO = null;
			try {
				dbManager = new DBManager();

				taskDAO = new TaskDAOImpl(dbManager);
				Task task = taskDAO.find(taskID);
				int orderID = task.getServiceOrderID();

				orderDAO = new ServiceOrderDAOImpl(dbManager);
				ServiceOrder order = orderDAO.find(orderID);

				wf = new NewScenarioWorkflow(order, dbManager);
				wf.approveBill(taskID);
				dbManager.commit();
				request.setAttribute("success", "Bill was sent!");
			} catch (DBManagerException ex) {
                    Logger.getLogger(SupportEngineerServlet.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
				dbManager.close();
			}

			RequestDispatcher view = request.getRequestDispatcher(SUPPORT_PAGE);
			view.forward(request, response);
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
		return "Registers user in the system, creates a new order "
				+ "and executes it ('New' scenario workflow).";
	}// </editor-fold>

}
