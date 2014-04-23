package nc.notus.dashboards;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nc.notus.dao.TaskDAO;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dao.impl.TaskDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.Task;
import nc.notus.states.UserRole;
import nc.notus.workflow.NewScenarioWorkflow;

public class SupportEngineerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private List<Task> tasks;

	void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		int startpage = 1;
		int numbOfRecords = 100;
		DBManager dbManager = new DBManager();
		try {
			if (request.getParameter("startpage") != null) {
				startpage = Integer.parseInt(request.getParameter("startpage"));
			}
			if (request.getParameter("numbOfRecords") != null) {
				numbOfRecords = Integer.parseInt(request
						.getParameter("numbOfRecords"));
			}

			TaskDAO taskDAO = new TaskDAOImpl(dbManager);
			tasks = taskDAO.getEngTasks(startpage, numbOfRecords,
					UserRole.SUPPORT_ENGINEER.toInt());

			if (tasks.size() > 0) {
				request.setAttribute("tasks", tasks);
			} else {
				request.setAttribute("notification",
						"No tasks finded! You are free at this moment!");
			}

			RequestDispatcher view = request.getRequestDispatcher("supportEngineer.jsp");
			view.forward(request, response);

		} finally {
			dbManager.close();
		}

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

		if (tasks != null || tasks.size() > 0) {
			DBManager dbManager = null;
			try {
				dbManager = new DBManager();
				ServiceOrderDAOImpl order = new ServiceOrderDAOImpl(dbManager);
				NewScenarioWorkflow wf = null;

				for (int i = 0; i < tasks.size(); i++) {

					// getting ServiceOrder id and Task id for each task in list
					int orderID = tasks.get(i).getServiceOrderID();
					int taskID = tasks.get(i).getId();

					// create workflow for each task and approveBill
					wf = new NewScenarioWorkflow(order.find(orderID));
					wf.approveBill(taskID);
				}
				request.setAttribute("success", "All bills were sent!");
			} finally {
				dbManager.close();
			}
		} 
		tasks = null;
		request.setAttribute("tasks", null);
		processRequest(request, response);
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
