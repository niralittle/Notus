package nc.notus.dashboards;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.SupportEngineerController;
import nc.notus.dao.OSSUserDAO;
import nc.notus.dao.impl.OSSUserDAOImpl;

import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.entity.OSSUser;

import nc.notus.states.UserState;


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

	private void blockUser(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			DBManagerException {

		int userID = Integer.parseInt(request.getParameter("userId"));
		DBManager dbManager = null;
		try {
			dbManager = new DBManager();

			OSSUserDAO userDAO = new OSSUserDAOImpl(dbManager);
			OSSUser user = userDAO.find(userID);

			user.setBlocked(UserState.BLOCKED.toInt());
			userDAO.update(user);
			dbManager.commit();

			request.setAttribute("success", "User, " + user.getLogin()
					+ ", successfully blocked!");

			redirectTo(CHANGE_PASSWORD_PAGE, request, response);
		} finally {
			dbManager.close();
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

		SupportEngineerController supportControl = null;
		
		if ("Send bill".equalsIgnoreCase(request.getParameter("action"))) {
			if (request.getParameter("taskid") != null) {
				int taskID = Integer.parseInt(request.getParameter("taskid"));

				supportControl = new SupportEngineerController();
				supportControl.sendBillToCustomer(taskID);
				request.setAttribute("success", supportControl.getActionStatus());
				redirectTo(SUPPORT_PAGE, request, response);
			}

		} else if ("Change password".equals(request.getParameter("action"))) {
			// read necessary parameters from request scope
			String newPassword = request.getParameter("newPassword");
			int userID = Integer.parseInt(request.getParameter("userId"));

			supportControl = new SupportEngineerController();
			supportControl.changeCustomerPassword(userID, newPassword);
			request.setAttribute("success", supportControl.getActionStatus());

			redirectTo(SUPPORT_PAGE, request, response);
		} else if ("Block user".equals(request.getParameter("action"))) {

			if (request.isUserInRole("ADMINISTRATOR")) {
				try {
					blockUser(request, response);
				} catch (DBManagerException ex) {
				}
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
