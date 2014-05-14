package nc.notus.dashboards;

import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nc.notus.controllers.AdministratorController;
import nc.notus.controllers.SupportEngineerController;
import nc.notus.dbmanager.DBManagerException;

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
    
    //pattern to server-side password validation
    private static final String PASSWORD_PATTERN = "^[A-Za-z0-9!@#$%^&*()_]{6,40}$";

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
    
    /**
     * Block user by ADMINISTRATOR.
     * 
     * @param request servlet request
     */
    private void blockUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AdministratorController adminControl = null;

        if (request.isUserInRole("ADMINISTRATOR")) {

            if (request.getParameter("userId") != null) {
                try { // try block user
                	int userID = Integer.parseInt(request.getParameter("userId"));
                	
                    adminControl = new AdministratorController();
                    adminControl.blockUser(userID);
                    request.setAttribute("success", "User was successfully blocked!");
                } catch (DBManagerException exc) {
                    request.setAttribute("errMessage", exc.getMessage());
                    redirectTo(CHANGE_PASSWORD_PAGE, request, response);
                } 
            } else {
                request.setAttribute("errMessage", "TaskID not passed!");
            }

        } else {
            request.setAttribute("errMessage", "To perfrom this action you must be" + " logged in as Administrator! ");
        }
    }
    
    /**
     * Send bill to customer user by SUPPORT_ENGINEER
     */
    private void sendBill(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SupportEngineerController supportControl = null;
        if (request.getParameter("taskid") != null) {
            int taskID = Integer.parseInt(request.getParameter("taskid"));

			try { // try send bill
				supportControl = new SupportEngineerController();
				supportControl.sendBillToCustomer(taskID);

				request.setAttribute("success",
						"Bill was successfully sent!");
			} catch (DBManagerException exc) {
				request.setAttribute("errMessage", exc.getMessage());
				redirectTo(SUPPORT_PAGE, request, response);
			}
		} else {
			request.setAttribute("success", "TaskID not passed!");
		}
    }

    /**
     * Change password by SUPPORT_ENGINEER.
     */
    private void changePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SupportEngineerController supportControl = null;
        // read necessary parameters from request scope
        if (request.getParameter("userId") != null) {
        	String newPassword = request.getParameter("newPassword");	
            if(!isPasswordValid(newPassword)) {
            	request.setAttribute("errMessage", 
            				"Password not valid:" 
							+ "<br>- minimum length 6 char;" 
							+ "<br>- only letters and numbers are acceptable.");
                redirectTo(CHANGE_PASSWORD_PAGE, request, response);
			} else {

				try { // try change password
					int userID = 
							Integer.parseInt(request.getParameter("userId"));
					
					supportControl = new SupportEngineerController();
					supportControl.changeCustomerPassword(userID, newPassword);

					request.setAttribute("success",
							"Password was successfully changed!");
				} catch (DBManagerException exc) {
					request.setAttribute("errMessage", exc.getMessage());
					redirectTo(CHANGE_PASSWORD_PAGE, request, response);
				} catch (NumberFormatException numbExc) {
					request.setAttribute("errMessage", "Passed paramater not valid!");
					redirectTo(CHANGE_PASSWORD_PAGE, request, response);
				}
			}
		}

    }
    
	private boolean isPasswordValid(String password) {
		boolean isValid = true;
		Pattern pattern;
		Matcher matcher;
		
		pattern = Pattern.compile(PASSWORD_PATTERN);
		matcher = pattern.matcher(password);
		
		//check if valid
		if (!matcher.matches()) {
			isValid = false;
		}
		return isValid;
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

        if ("Send bill".equalsIgnoreCase(request.getParameter("action"))) {
            sendBill(request, response);
            redirectTo(SUPPORT_PAGE, request, response);

        } else if ("Apply".equals(request.getParameter("action"))) {
            changePassword(request, response);
            redirectTo(CHANGE_PASSWORD_PAGE, request, response);

        } else if ("Block".equals(request.getParameter("action"))) {
            blockUser(request, response);
            redirectTo(CHANGE_PASSWORD_PAGE, request, response);
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Registers user in the system, creates a new order " + "and executes it ('New' scenario workflow).";
    }
}
