package nc.notus;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nc.notus.dao.OSSUserDAO;
import nc.notus.dao.impl.OSSUserDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.OSSUser;
import nc.notus.states.UserRole;
import nc.notus.states.UserState;


public class RegistrationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request,
                        HttpServletResponse response) throws ServletException, IOException {
		String login = (String) request.getParameter("login");
		String password = (String) request.getParameter("password");
		String email = (String) request.getParameter("email");
		String firstName = (String) request.getParameter("firstName");
		String lastName = (String) request.getParameter("lastName");
		
		DBManager dbManager = new DBManager();
		
		OSSUserDAO userDAO = new OSSUserDAOImpl(dbManager);
                OSSUser user = new OSSUser();
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setLogin(login);
                user.setPassword(password);             // TODO: add cipher here
                user.setBlocked(UserState.ACTIVE.toInt());
                user.setRoleID(UserRole.CUSTOMER_USER.toInt());
		userDAO.add(user);
                
		dbManager.commit();
		dbManager.close();
	
		RequestDispatcher view = request.getRequestDispatcher("index.jsp");
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
		return "Short description";
	}// </editor-fold>

}
