package nc.notus.dashboards;

import nc.notus.dao.ServiceOrderDAO;

import nc.notus.dao.impl.ServiceOrderDAOImpl;

import nc.notus.dbmanager.DBManager;
import nc.notus.entity.ServiceOrder;
import nc.notus.workflow.NewScenarioWorkflow;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Provide assigning given Port to given Service Instance. It also creates Circuit and
 * links it with SI as logical entity of provided service. It also sets status
 * of given task to "Completed". After execution it automatically creates task
 * to Customer Support Engineer group.
 * 
 * @author Dmytro Panchenko
 */
public class ProvisionEngeenierServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private int taskID;
	private int portID;
	private int serviceInstanceID;
	private int serviceOrderId;

	void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// read paramaters from request scope
		parseParams(request);

		ServiceOrder so;
		DBManager dbManager = null;

		// get service order to creating workflow instance
		try {
			dbManager = new DBManager();
			ServiceOrderDAO soDAO = new ServiceOrderDAOImpl(dbManager);
			so = soDAO.find(serviceOrderId);
		} finally {
			dbManager.close();
		}

		// ==== or add default constructor to NewScenarioWorkflow implementation

		// create workflow instance and assign received port to SI
		NewScenarioWorkflow wf = new NewScenarioWorkflow(so);
		wf.assignPort(taskID, portID, serviceInstanceID);

		request.setAttribute("success", "Port successfully assigned!");
		
		RequestDispatcher view = request.getRequestDispatcher("provisionEngeenier.jsp");
		view.forward(request, response);
	}

	private void parseParams(HttpServletRequest request) {
		serviceOrderId = Integer.parseInt(request
				.getParameter("serviceOrderID"));
		portID = Integer.parseInt(request.getParameter("portID"));
		serviceInstanceID = Integer.parseInt(request
				.getParameter("serviceInstanceID"));
		taskID = Integer.parseInt(request.getParameter("taskID"));
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
