package nc.notus.dashboards;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nc.notus.dao.impl.OSSUserDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.OSSUser;

/**
 * Get users information for specified last name or email or login 
 * that uses by Support Engineer to changing user password.
 * 
 * @author Panchenko Dmytro
 */
public class GettingUsersInfo extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	// page to redirect
	private static final String CHANGE_PASSWORD_PAGE = "passwordChanging.jsp";
	
	private List<OSSUser> users = null;
	private String login;
	private String email;
	private String lastName;

	void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		int startPage = 1;
		int numberOfRecords = 30;
		
		email = request.getParameter("email");
		login = request.getParameter("login");
		lastName = request.getParameter("lastName");
		
		//if all fields empty we can't search any user
		if (login.isEmpty() & email.isEmpty() & lastName.isEmpty()) {
			redirect(request, response, CHANGE_PASSWORD_PAGE);
		}
		if (!login.isEmpty() & !email.isEmpty() & !lastName.isEmpty()) {
			redirect(request, response, CHANGE_PASSWORD_PAGE);
		}
		
		// create necessary DAO
		DBManager dbManager = null;
		OSSUserDAOImpl userDAO = null;
		
		try {
			dbManager = new DBManager();
			userDAO = new OSSUserDAOImpl(dbManager);

			// search user for one criteria only:
			if(!lastName.isEmpty()) {
				users = userDAO.getUsersByLastName(lastName, startPage, numberOfRecords);
			} else if (!login.isEmpty()) {
				users = userDAO.getUsersByLogin(login, startPage, numberOfRecords);	
			} else {
				users = userDAO.getUsersByEmail(email, startPage, numberOfRecords);
			}
			
			request.setAttribute("findedUsers", users);

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
