package nc.notus;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validates data input by customer user during registration.
 * Performs only regex-checking of specified fields.
 * 
 * @author Panchenko Dmytro
 *
 */
public class ValidationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	//no spaces allowed. Minimum lenght - 3 chars, maximum - 40
	private final String LOGIN_PATTERN = "^[A-Za-z0-9_-]{3,40}$";
	
	//no spaces allowed and special signs. Minimum lenght - 6 chars, maximum - 40
	private final String PASSWORD_PATTERN = "^[A-Za-z0-9!@#$%^&*()_]{6,40}$";
	
	/**
	 * Validates data input by user
	 * Forward to {@link registration.jsp} if at least one field is incorrect.
	 * 
	 * @param request
	 * 	must contain attributes with names:<br/>
	 * 	login, password, password2, email, firstName, lastName
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String login = (String) request.getParameter("login");
		String password = (String) request.getParameter("password");
		String password2 = (String) request.getParameter("password2");
		String email = (String) request.getParameter("email");
		String firstName = (String) request.getParameter("firstName");
		String lastName = (String) request.getParameter("lastName");
		
		//writes all errors to one string
		StringBuilder errors = new StringBuilder();
		
		Pattern pattern;
		Matcher matcher;
		boolean isValid = true;

		pattern = Pattern.compile(LOGIN_PATTERN);
		matcher = pattern.matcher(login);

		if (!matcher.matches()) {
			isValid = false;                                       
			errors.append("- Provide correct login. " +
                                "Spaces not allowed. Minimum length - " +
                                "3 chars, maximum - 40.\n");
							
		}

		matcher = pattern.matcher(lastName);
		if (!matcher.matches()) {
			isValid = false;                                        
			errors.append("- Provide correct last name. " +
                                "Spaces not allowed\n");
				
		}

		matcher = pattern.matcher(firstName);
		if (!matcher.matches()) {
			isValid = false;                                        
			errors.append("- Provide correct first name. " +
                                "Spaces not allowed\n");
		}

		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email);
		if (!matcher.matches()) {
			isValid = false;
			errors.append("- Provide correct email.\n");
		}

		pattern = Pattern.compile(PASSWORD_PATTERN);
		matcher = pattern.matcher(password);
		if (!matcher.matches()) {
			isValid = false;
			errors.append("- Provide correct password. " +
                                "Minimum length - 6 chars.\n");
		}

		if (!password.equals(password2)) {
			isValid = false;
			errors.append("- Password don't matches.\n");
		}
		
		if (isValid) {
			RequestDispatcher view = request.getRequestDispatcher("/Register");
			view.forward(request, response);
		} else {
			request.setAttribute("errors", errors.toString());
			RequestDispatcher view = request.getRequestDispatcher("registration.jsp");
			view.forward(request, response);
		}			
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
		return "Validates input data in the registration form.";
	}// </editor-fold>

}
