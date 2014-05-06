package nc.notus.filters;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Redirect engineers to personal tasks page.         
 *                 
 * @author Pacnhenko Dmytro
 *
 */
public class RedirectingServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	
	protected void processRequest(HttpServletRequest request,
                HttpServletResponse response)
                throws ServletException, IOException {
		
		if(isAuthorized(request) == false) {
			redirect(request, response, "index.jsp");
		} else if (isEngineer(request)) {
			redirect(request, response, "TasksAssignment?type=personal");
		} else if(isAdministator(request)) {
			redirect(request, response, "adminDashboard.jsp");
		} else if(isCustomerUser(request)) {
			redirect(request, response, "index.jsp");
		}
    }
	
	private boolean isEngineer(HttpServletRequest request) {
		if(request.isUserInRole("SUPPORT_ENGINEER") 
				|| request.isUserInRole("PROVISION_ENGINEER")
				|| request.isUserInRole("INSTALLATION_ENGINEER")) {
			return true;
		}
		return false;
	}
	
	private boolean isCustomerUser(HttpServletRequest request) {
		if(request.isUserInRole("CUSTOMER_USER")) {
			return true;
		}
		return false;
	}
	private boolean isAuthorized(HttpServletRequest request) {
		if(request.getUserPrincipal() != null) {
			return true;
		}
		return false;
	}
	
	private boolean isAdministator(HttpServletRequest request) {
		if(request.isUserInRole("ADMINISTRATOR")) {
			return true;
		}
		return false;
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
		processRequest(request, response);
	}

	
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
                        throws ServletException, IOException {
		processRequest(request, response);
	}

	
	@Override
	public String getServletInfo() {
		return "LogoutServlet invalidates current session " +
                        "and logs the user out.";
	}

}
