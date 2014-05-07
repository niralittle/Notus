package nc.notus.dashboards;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.AdministratorController;
import controllers.SupportEngineerController;
import nc.notus.dbmanager.DBManagerException;



/**
 * Change password for specified user.
 * 
 * @author Panchenko Dmytro
 */
public class SupportEngineerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String SUPPORT_PAGE = "supportEngineer.jsp";
	// page to redirect
	private static final String CHANGE_PASSWORD_PAGE = "passwordChanging.jsp";


	/**
	 * Redirect to passes page.
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @throws ServletException
	 * @throws IOException
	 */
	private void redirectTo(String page, HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			IOException {
		RequestDispatcher view = request.getRequestDispatcher(page);
		view.forward(request, response);
		return;
	}
	
	private void blockUser(HttpServletRequest request) {
		AdministratorController adminControl = null;
		
		if (request.isUserInRole("ADMINISTRATOR")) {
			
			if (request.getParameter("userId") != null) {
				try { // try block user
					int userID = Integer.parseInt(request.getParameter("userId"));

					adminControl = new AdministratorController();
					adminControl.blockUser(userID);
					request.setAttribute("success", adminControl.getActionSuccess());
				} catch (DBManagerException exc) {
					request.setAttribute("errMessage", exc.getMessage());
				}
				
			} else {
				request.setAttribute("errMessage", "TaskID not passed!");
			}
			
		} else {
			request.setAttribute("errMessage", "To perfrom this action you must be"
					+ " logged in as Administrator! ");
		}
	}
	
	private void sendBill(HttpServletRequest request) {
		
		SupportEngineerController supportControl = null;
		if (request.getParameter("taskid") != null) {
			int taskID = Integer.parseInt(request.getParameter("taskid"));
			
			try { 		//try send bill
				supportControl = new SupportEngineerController();
				supportControl.sendBillToCustomer(taskID);
				request.setAttribute("success",
						supportControl.getActionStatus());
			} catch (DBManagerException exc) {
				request.setAttribute("errMessage", exc.getMessage());
			}
		} else {
			request.setAttribute("success", "TaskID not passed!");
		}
	}
	
	private void changePassword(HttpServletRequest request) {
		
		SupportEngineerController supportControl = null;
		// read necessary parameters from request scope
		if (request.getParameter("userId") != null) {
			
			String newPassword = request.getParameter("newPassword");
			int userID = Integer.parseInt(request.getParameter("userId"));
			
			try {		//try change password
				supportControl = new SupportEngineerController();
				supportControl.changeCustomerPassword(userID, newPassword);
				request.setAttribute("success",
						supportControl.getActionStatus());
			} catch (DBManagerException exc) {
				request.setAttribute("errMessage", exc.getMessage());
			}
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

		if ("Send bill".equalsIgnoreCase(request.getParameter("action"))) {
			sendBill(request);
			redirectTo(SUPPORT_PAGE, request, response);
			
		} else if ("Change password".equals(request.getParameter("action"))) {
			changePassword(request);
			redirectTo(CHANGE_PASSWORD_PAGE, request, response);
			
		} else if ("Block user".equals(request.getParameter("action"))) {
			blockUser(request);
			redirectTo(CHANGE_PASSWORD_PAGE, request, response);
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
