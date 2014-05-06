package nc.notus.dashboards;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nc.notus.dao.OSSUserDAO;
import nc.notus.dao.impl.OSSUserDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.entity.OSSUser;
import nc.notus.states.UserRole;

/**
 * Get users information for specified last name, email or login that uses by
 * Support Engineer to changing user password.
 * 
 * @author Panchenko Dmytro
 */
public class GettingUsersInfo extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String CHANGE_PASSWORD_PAGE = "passwordChanging.jsp";
	private static int RECORDS_PER_PAGE = 10;

	private List<OSSUser> users = null;
	private String login;
	private String email;
	private String lastName;
	
	private int offset;
	private int page;
	

	void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, DBManagerException {
		response.setContentType("text/html;charset=UTF-8");
		
		DBManager dbManager = null;
		OSSUserDAO userDAO = null;
		
		readInputtedData(request);
		if (!isValidParams(request)) {
			redirect(request, response, CHANGE_PASSWORD_PAGE);
		}
	
		try {
			dbManager = new DBManager();
			userDAO = new OSSUserDAOImpl(dbManager);

			if (request.getParameter("page") == null) {
				page = 0;
			} else {
				page = Integer.parseInt(request.getParameter("page"));
				
				if (page == 1) {
					page = 0;
				} else {
					page = page - 1;
				}
			}
			offset = page + RECORDS_PER_PAGE;
			
			request.setAttribute("noOfPages", getPageCount(userDAO));
			request.setAttribute("page", page);

			// search user for one criteria only:
			if (!lastName.isEmpty()) {
				users = userDAO.getUsersByLastName(lastName, offset, page);
			} else if (!login.isEmpty()) {
				users = userDAO.getUsersByLogin(login, offset, page);
			} else {
				users = userDAO.getUsersByEmail(email, offset, page);
			}

			request.setAttribute("findedUsers", users);

		} finally {
			dbManager.close();
		}

		redirect(request, response, CHANGE_PASSWORD_PAGE);
	}

	private void readInputtedData(HttpServletRequest request) {
		email = request.getParameter("email");
		login = request.getParameter("login");
		lastName = request.getParameter("lastName");
	}

	private boolean isValidParams(HttpServletRequest request) {
		if (login.isEmpty() & email.isEmpty() & lastName.isEmpty()) {
			request.setAttribute("errMessage",
					"Specify at least one parameter to search user!");
			return false;
		}
		if (!login.isEmpty() & !email.isEmpty() & !lastName.isEmpty()) {
			request.setAttribute("errMessage",
					"Specify only one parameter to search user!");
			return false;
		}
		
		//check combination
		if (!login.isEmpty() & !email.isEmpty()) {
			request.setAttribute("errMessage",
					"Specify only one parameter to search user!");
			return false;
		}
		if (!login.isEmpty() & !lastName.isEmpty()) {
			request.setAttribute("errMessage",
					"Specify only one parameter to search user!");
			return false;
		}
		if (!email.isEmpty() & !lastName.isEmpty()) {
			request.setAttribute("errMessage",
					"Specify only one parameter to search user!");
			return false;
		}
		
		return true;
	}

	private long getPageCount(OSSUserDAO userDAO) throws DBManagerException {
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (!lastName.isEmpty()) {
			params.put("lastname", "%" + lastName + "%");
		} else if (!login.isEmpty()) {
			params.put("login", "%" + login + "%");
		} else {
			params.put("email", "%" + email + "%");
		}
		params.put("roleid", UserRole.CUSTOMER_USER.toInt());
		
		long quantityOfRecords = userDAO.countAllWithLikeCause(params);
		long quantityOfPages = (long) Math.ceil(quantityOfRecords * 1.0/ RECORDS_PER_PAGE);
		return quantityOfPages;
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
        try {
            processRequest(request, response);
        } catch (DBManagerException ex) {
            Logger.getLogger(GettingUsersInfo.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            processRequest(request, response);
        } catch (DBManagerException ex) {
            Logger.getLogger(GettingUsersInfo.class.getName()).log(Level.SEVERE, null, ex);
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
