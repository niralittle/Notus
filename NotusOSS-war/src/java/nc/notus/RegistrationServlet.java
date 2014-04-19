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


/**
* Provides registration new user in system and create new scenario workflow 
* with order status="ENTERING"
* @see {@link nc.notus.states.OrderStatus } 
* @param request
* @param response
* @throws ServletException
* @throws IOException                                                           // REVIEW: no author specified
*/
public class RegistrationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request,
                        HttpServletResponse response) throws ServletException, IOException {
                        	
		// get parameters from request scope
		String login = (String) request.getParameter("login");
		String password = (String) request.getParameter("password");
		String email = (String) request.getParameter("email");
		String firstName = (String) request.getParameter("firstName");
		String lastName = (String) request.getParameter("lastName");
		
		DBManager dbManager = new DBManager();
		OSSUserDAO userDAO = new OSSUserDAOImpl(dbManager);
		
		// server-side validation for duplicating login and email
		if (userDAO.isLoginDuplicate(login)) {
			request.setAttribute("duplicateLogin",                  // REVIEW: watch the red line
					"User with specified login already exist. Choose other login.");
			dbManager.close();                                      // REVIEW: try-finally block should be used
			RequestDispatcher view = request.getRequestDispatcher("registration.jsp");
			view.forward(request, response);
		}

		if (userDAO.isEmailDuplicate(email)) {
			request.setAttribute("duplicateLogin",                  // REVIEW: watch the red line
					"User with specified email already exist in system. "
					+ "Try write to administrator for restoring you account.");
			dbManager.close();                                      // REVIEW: try-finally block should be used
			RequestDispatcher view = request.getRequestDispatcher("registration.jsp");
			view.forward(request, response);
		}
		

               // create new user
		OSSUser user = new OSSUser();
	         user.setFirstName(firstName);
	         user.setLastName(lastName);
	         user.setEmail(email);
	         user.setLogin(login);
	         user.setPassword(password);             
	         user.setBlocked(UserState.ACTIVE.toInt());
	         user.setRoleID(UserRole.CUSTOMER_USER.toInt());
	         
		userDAO.add(user);
                
		dbManager.commit();
		dbManager.close();                                              // REVIEW: try-finally block should be used
		
		/*                                                              // REVIEW: service Order creation undone
		//create workflow with Scenario NEW
		ServiceCatalogDAOImpl cat = new ServiceCatalogDAOImpl(dbManager);
                                                                                // REVIEW: watch the red line
		int serviceCatalogID = Integer.parseInt(request.getParameter("serviceCatalogID"));
		String serviceLocation = (String) request.getParameter("serviceLocation");
		
		// create new order with status ENTERING
        ServiceOrder so = new ServiceOrder();
        	so.setServiceOrderStatusID(1); // need add id to states
        	so.setScenarioID(1); // new scenario id
        	so.setServiceCatalogID(serviceCatalogID);
        	so.setServiceLocation(serviceLocation);
        	// set date
        	// set generated user id
        	
        
        Workflow wf = new NewScenarioWorkflow(so);
        wf.proceedOrder();
		
		*/

		
		//redirect to congratulation page                               // REVIEW: watch the red line
		RequestDispatcher view = request.getRequestDispatcher("orderRecieved.jsp");
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
