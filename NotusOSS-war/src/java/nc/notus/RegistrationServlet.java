package nc.notus;

import nc.notus.controllers.AdministratorController;
import nc.notus.controllers.CustomerUserController;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides registration in system for new user and creates new scenario
 * workflow for order with status "Entering"
 * 
 * @author Dmytro Panchenko & Igor Litvinenko
 */
public class RegistrationServlet extends HttpServlet {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String LOGIN_PATTERN = "^[A-Za-z0-9_-]{3,40}$";
    private static final String PASSWORD_PATTERN = "^[A-Za-z0-9!@#$%^&*()_]{6,40}$";
    private static Logger logger = Logger.getLogger(RegistrationServlet.class.getName());
    // pages to redirect
    private static final String CUSTOMER_REGISTRATION_PAGE = "registration.jsp";
    private static final String ENGINEER_REGISTRATION_PAGE = "registerEngineer.jsp";
    private static final String CONGRATULATION_PAGE = "orderRecieved.jsp";
    //User credentials
    private String login;
    private String password;
    private String passwordConf;
    private String email;
    private String firstName;
    private String lastName;
    //for register new engineer in ADMIN dashboard
    private int groupID;
    //captcha
    private String inputtedCaptcha;
    private String generatedCaptcha;
    //chosen service and location
    private int catalogID;
    private String serviceLocation;
    //keep true if ADMIN register new user
    private boolean isAdmin;

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        //local variable derclaration
        boolean isParamsValid = false;
        StringBuilder errMessage = new StringBuilder();

        // logic actions
        isAdmin = request.isUserInRole("ADMINISTRATOR");
        readParamaters(request);

        isParamsValid = validateParams(errMessage);

        if (!isParamsValid && isAdmin) {
            redirectTo(ENGINEER_REGISTRATION_PAGE, request, response);
        }
        if (!isParamsValid && !isAdmin) {
            redirectTo(CUSTOMER_REGISTRATION_PAGE, request, response);
        }

        try {
            // if user is ADMINISTRATOR we only register new engineer
            // NC.KYIV.2014.WIND.REG.3
            if (isAdmin) {
                AdministratorController adminControl = null;
                adminControl = new AdministratorController();
                adminControl.registerNewEngineer(login, password, email,
                        firstName, lastName, groupID);
                request.setAttribute("success", adminControl.getActionStatus());
                redirectTo(ENGINEER_REGISTRATION_PAGE, request, response);
            } else {
                CustomerUserController userControl = null;
                userControl = new CustomerUserController();
                userControl.register(login, password, email, firstName,
                        lastName, catalogID, serviceLocation);
                redirectTo(CONGRATULATION_PAGE, request, response);
            }
        } catch (DBManagerException exc) {
            request.setAttribute("errMessage", exc.getMessage());
            if (isAdmin) {
                redirectTo(ENGINEER_REGISTRATION_PAGE, request, response);
            }
            redirectTo(CUSTOMER_REGISTRATION_PAGE, request, response);
        }
    }

    /**
     * Read inputted params from request scope.
     * @param request
     */
    private void readParamaters(HttpServletRequest request) {

        login = request.getParameter("login");
        password = request.getParameter("password");
        passwordConf = request.getParameter("passwordConf");
        email = request.getParameter("email");
        firstName = request.getParameter("firstName");
        lastName = request.getParameter("lastName");

        // read captcha
        inputtedCaptcha = request.getParameter("code");
        generatedCaptcha = (String) request.getSession().getAttribute("captcha");

        if (isAdmin) {
            groupID = Integer.parseInt(request.getParameter("employeeGroup"));
        } else {
            catalogID = Integer.parseInt(request.getParameter("serviceCatalogID"));
            serviceLocation = request.getParameter("serviceLocationID");
        }
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
    private void redirectTo(String page, HttpServletRequest request,
            HttpServletResponse response) throws ServletException,
            IOException {
        RequestDispatcher view = request.getRequestDispatcher(page);
        view.forward(request, response);
        return;
    }

    private boolean validateParams(StringBuilder errMessage) {

        Pattern pattern;
        Matcher matcher;

        boolean isValid = true;
        if (!generatedCaptcha.equals(inputtedCaptcha)) {
            isValid = false;
            errMessage.append(" - Code don't matches. <br />");
        }
        pattern = Pattern.compile(LOGIN_PATTERN);
        matcher = pattern.matcher(login);
        if (!matcher.matches()) {
            isValid = false;
            errMessage.append("- Provide correct login. Spaces not allowed. " + "Minimum length - 3 chars, maximum - 40.<br />");
        }
        matcher = pattern.matcher(lastName);
        if (!matcher.matches()) {
            isValid = false;
            errMessage.append("- Provide correct last name. Spaces are not allowed.<br />");

        }
        matcher = pattern.matcher(firstName);
        if (!matcher.matches()) {
            isValid = false;
            errMessage.append("- Provide correct first name. Spaces not allowed.<br />");
        }
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            isValid = false;
            errMessage.append("- Provide correct email.<br />");
        }
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            isValid = false;
            errMessage.append("- Provide correct password. Minimum length - 6 chars.<br />");
        }
        if (!password.equals(passwordConf)) {
            isValid = false;
            errMessage.append("- Password doesn't match confirmation.<br />");
        }
        if (!isAdmin) {
            try {
                serviceLocation = java.net.URLDecoder.decode(serviceLocation,
                        "UTF-8");
            } catch (UnsupportedEncodingException exc) {
                //isValid = false;
                //errMessage.append("- Wrong location specified.<br />");
                //
            } catch (IllegalArgumentException exc) {
                //isValid = false;
                //errMessage.append("- Wrong location specified.<br />");
            }
        }
        return isValid;
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);

    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Registers user in the system, creates a new order " + "and executes it ('New' scenario workflow).";
    }// </editor-fold>
}
