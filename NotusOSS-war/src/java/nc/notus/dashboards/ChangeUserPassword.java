package nc.notus.dashboards;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nc.notus.dao.OSSUserDAO;
import nc.notus.dao.impl.OSSUserDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.OSSUser;
import nc.notus.states.UserState;

/**
 * Change password for specified user.
 * 
 * @author Panchenko Dmytro
 */
public class ChangeUserPassword extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	//page to redirect
	private static final String CHANGE_PASSWORD_PAGE = "passwordChanging.jsp";

	void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		//read necessary parameters from request scope
		String newPassword = request.getParameter("newPassword");
		int userId = Integer.parseInt(request.getParameter("userId"));
		
		//create DAO
		OSSUserDAOImpl userDAO = null;
		DBManager dbManager = null;
		try {
			dbManager = new DBManager();
			userDAO = new OSSUserDAOImpl(dbManager);
			
			//get user by id and set him new password
			OSSUser user = userDAO.find(userId);
			user.setPassword(newPassword);
			
			//update password in DB
			userDAO.update(user);
			
			//commit changes
			dbManager.commit();
			
			//say all ok
			request.setAttribute("success", "Password changed successfully!");
		} finally {
			dbManager.close();
		}
		
		redirect(request, response, CHANGE_PASSWORD_PAGE);
	}
	/**
	 * Redirect to passes page.
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @throws ServletException
	 * @throws IOException
	 */
	private void redirect(HttpServletRequest request,
			HttpServletResponse response, String page) throws ServletException,
			IOException {
		RequestDispatcher view = request.getRequestDispatcher(page);
		view.forward(request, response);
		return;
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	
	private void blockUser(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

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

			redirect(request, response, CHANGE_PASSWORD_PAGE);
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
		if(request.isUserInRole("ADMINISTRATOR")) {
			blockUser(request, response);
		} else {
			processRequest(request, response);
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
