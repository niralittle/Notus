package nc.notus.dashboards;

import java.io.IOException;

import javax.mail.Session;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.ServiceOrder;
import nc.notus.states.OrderStatus;
import nc.notus.states.WorkflowScenario;
import nc.notus.workflow.DisconnectScenarioWorkflow;

/**
 * Disconnect scenario workflow
 * @autor Panchenko Dmytro
 */
public class DisconnectOrderProceed extends HttpServlet {
	
	// page to redirect
	private static final String CUSTOMER_USER_PAGE = "CustomerUserServlet";
	
	void processRequest(HttpServletRequest request,
                        HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		
		int serviceInstanceId = Integer.parseInt(
                        request.getParameter("serviceInstanceID"));
		
		//get order
		ServiceOrder serviceOrder = getServiceOrder(serviceInstanceId);
			serviceOrder.setScenarioID(WorkflowScenario.DISCONNECT.toInt());
			serviceOrder.setServiceOrderStatusID(OrderStatus.ENTERING.toInt());
		DisconnectScenarioWorkflow disconnectWF = null;
		try{
			disconnectWF = new DisconnectScenarioWorkflow(serviceOrder);
			disconnectWF.proceedOrder();
			
			/*
			request.getSession().setAttribute("success", "Your request to disconnect service on location " 
					+ serviceOrder.getServiceLocation() + " accepted!");
			*/
		} finally {
			
		}
		response.sendRedirect(CUSTOMER_USER_PAGE);
		//redirect(request, response, CUSTOMER_USER_PAGE);
	}

	/**
	 * Redirect to passed page.
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @throws ServletException
	 * @throws IOException
	 */
	private void redirect(HttpServletRequest request,
			HttpServletResponse response, String page)
                        throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher(page);
		view.forward(request, response);
	}

	
	private ServiceOrder getServiceOrder(int serviceInstanceId) {
		DBManager dbManager = null;
                ServiceOrder serviceOrder;
		try {
			dbManager = new DBManager();
			ServiceOrderDAOImpl soDAO =
                                new ServiceOrderDAOImpl(dbManager);
                        serviceOrder = soDAO
                                .getServiceOrderBySIId(serviceInstanceId);
		} finally {
			dbManager.close();
		}
		return serviceOrder;
	}
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
                        throws ServletException, IOException {
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
			HttpServletResponse response)
                        throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 * 
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "";
	}// </editor-fold>

}
