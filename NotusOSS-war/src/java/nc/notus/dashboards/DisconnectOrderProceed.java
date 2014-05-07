package nc.notus.dashboards;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Session;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.entity.ServiceOrder;
import nc.notus.states.OrderStatus;
import nc.notus.states.WorkflowScenario;
import nc.notus.workflow.DisconnectScenarioWorkflow;
import nc.notus.workflow.WorkflowException;

/**
 * Disconnect scenario workflow
 * @autor Panchenko Dmytro
 */
public class DisconnectOrderProceed extends HttpServlet {
	
	// page to redirect
	private static final String CUSTOMER_USER_PAGE = "CustomerUserServlet";
	
	void processRequest(HttpServletRequest request,
                        HttpServletResponse response)
			throws ServletException, IOException, DBManagerException {

		response.setContentType("text/html;charset=UTF-8");
		
		int serviceInstanceId = Integer.parseInt(
                        request.getParameter("serviceInstanceID"));
		
		//get order
		ServiceOrder serviceOrder = getServiceOrder(serviceInstanceId);
		serviceOrder.setScenarioID(WorkflowScenario.DISCONNECT.toInt());
		serviceOrder.setServiceOrderStatusID(OrderStatus.ENTERING.toInt());
		
		DisconnectScenarioWorkflow disconnectWF = null;
		DBManager dbManager = null;
		try{
			dbManager = new DBManager();
			disconnectWF = new DisconnectScenarioWorkflow(serviceOrder,dbManager);
			disconnectWF.proceedOrder();
			dbManager.commit();
	
			/*
			request.getSession().setAttribute("success", "Your request to disconnect service on location " 
					+ serviceOrder.getServiceLocation() + " accepted!");
			*/
		} catch(WorkflowException wfExc) {
			
		} finally {
			dbManager.close();
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

	
	private ServiceOrder getServiceOrder(int serviceInstanceId) throws DBManagerException {
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
        try {
            processRequest(request, response);
        } catch (DBManagerException ex) {
            Logger.getLogger(DisconnectOrderProceed.class.getName()).log(Level.SEVERE, null, ex);
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
			HttpServletResponse response)
                        throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (DBManagerException ex) {
            Logger.getLogger(DisconnectOrderProceed.class.getName()).log(Level.SEVERE, null, ex);
        }
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
