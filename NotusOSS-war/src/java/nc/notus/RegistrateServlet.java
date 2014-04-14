package nc.notus;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nc.notus.dao.impl.OSSUserDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.OSSUser;


public class RegistrateServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String login = (String) request.getParameter("login");
		String password = (String) request.getParameter("password");
		String email = (String) request.getParameter("email");
		String firstName = (String) request.getParameter("firstName");
		String lastName = (String) request.getParameter("lastName");

		
		DBManager dbManager = new DBManager();
		
		OSSUserDAOImpl userDAO = new OSSUserDAOImpl(dbManager);
		userDAO.add(new OSSUser(firstName, lastName, email, login, password, 0, 5));
		dbManager.commit();
		dbManager.close();
		
	
/*
		String query = "INSERT INTO OSSUSER(firstname, lastname, email, login, password, blocked, roleid) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
		Statement statement = dbManager.prepareStatement(query);
		statement.setString(1, firstName);
		statement.setString(2, lastName);
		statement.setString(3, email);
		statement.setString(4, login);
		statement.setString(5, DigestUtils.sha256Hex(password));
		statement.setInt(6, 0);
		statement.setInt(7, 5);
		statement.executeUpdate();
*/
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
