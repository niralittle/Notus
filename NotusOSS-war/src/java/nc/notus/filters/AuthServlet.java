/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.filters;

import java.io.IOException;

import nc.notus.dbmanager.DBManagerException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nc.notus.dao.OSSUserDAO;
import nc.notus.dao.impl.OSSUserDAOImpl;
import nc.notus.dbmanager.DBManager;

/**
 * Check if user blocked. If yes - then redirect to error-page.             
 *                 
 * @author Pacnhenko Dmytro
 *
 */
public class AuthServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	
	private static Logger logger=Logger.getLogger(AuthServlet.class.getName());
	private static final String LOGIN_ERROR_PAGE = "loginError.jsp";
	
	protected void processRequest(HttpServletRequest request,
                HttpServletResponse response)
                throws ServletException, IOException, DBManagerException {
		
		logger.log(Level.INFO, "Test loger");
		String username = request.getParameter("j_username");
		String password = request.getParameter("j_password");
		
		OSSUserDAO userDAO = null;
		DBManager dbManager = null;
		StringBuilder sb = new StringBuilder();
		try {
			dbManager = new DBManager();
			userDAO = new OSSUserDAOImpl(dbManager);
			if(userDAO.isBlocked(username)) {
				sb.append("User with specified login blocked!");
				request.setAttribute("errMessage", sb.toString());
				redirect(request, response, LOGIN_ERROR_PAGE);
			} 
		} finally {
			dbManager.close();
		}
		
		response.sendRedirect("j_security_check?j_username=" 
				+ username + "&j_password="+password);
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
			HttpServletResponse response)
                        throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (DBManagerException ex) {
            java.util.logging.Logger.getLogger(AuthServlet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
	}

	
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
                        throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (DBManagerException ex) {
            java.util.logging.Logger.getLogger(AuthServlet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
	}

	
	@Override
	public String getServletInfo() {
		return "LogoutServlet invalidates current session " +
                        "and logs the user out.";
	}

}
