package nc.notus;

import java.sql.Date;
import java.util.Calendar;
import nc.notus.dao.OSSUserDAO;
import nc.notus.dao.ScenarioDAO;
import nc.notus.dao.ServiceOrderDAO;
import nc.notus.dao.ServiceOrderStatusDAO;
import nc.notus.dao.impl.OSSUserDAOImpl;
import nc.notus.dao.impl.ScenarioDAOImpl;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dao.impl.ServiceOrderStatusDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.OSSUser;
import nc.notus.entity.ServiceOrder;
import nc.notus.states.OrderStatus;
import nc.notus.states.UserRole;
import nc.notus.states.UserState;
import nc.notus.states.WorkflowScenario;
import nc.notus.workflow.NewScenarioWorkflow;
import nc.notus.workflow.Workflow;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import nc.notus.email.EmailSender;
import nc.notus.email.RegistrationSuccessfulEmail;

/**
 * Provides registration in system for new user and creates new scenario workflow
 * for order with status "Entering"
 * @author Dmytro Panchenko & Igor Litvinenko
 */
public class RegistrationServlet extends HttpServlet {

    private String login;
    private String password;
    private String passwordConf;
    private String email;
    private String firstName;
    private String lastName;
    private int catalogID;
    private String serviceLocation;

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        parseParams(request);
        StringBuilder errMessage = new StringBuilder();


        DBManager dbManager = new DBManager();
        ServiceOrder newOrder = null;
        Integer userID = null;
        boolean paramsValid = false;
        try {
            paramsValid = validateParams(dbManager, errMessage);
            if (paramsValid) {
                userID = createUser(dbManager);
                newOrder = createOrder(dbManager, userID);
                dbManager.commit();
            }
        } finally {
            dbManager.close();
        }

        if (paramsValid) {
            // send email to user
            RegistrationSuccessfulEmail notificationEmail =
                    new RegistrationSuccessfulEmail(firstName, "24.04.2014", login, password);
            EmailSender emailSender = new EmailSender();
            emailSender.sendEmail(userID, notificationEmail);

            // proceed Order
            Workflow wf = new NewScenarioWorkflow(newOrder);
            wf.proceedOrder();

            // redirect to congratulation page
            RequestDispatcher view = request.getRequestDispatcher("orderRecieved.jsp");
            view.forward(request, response);
        } else {
            request.setAttribute("errMessage", errMessage.toString());
            RequestDispatcher view = request.getRequestDispatcher("registration.jsp");
            view.forward(request, response);
        }
    }

    private boolean validateParams(DBManager dbManager, StringBuilder errMessage) {
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        // no spaces allowed. Minimum lenght - 3 chars, maximum - 40
        final String LOGIN_PATTERN = "^[A-Za-z0-9_-]{3,40}$";
        // no spaces allowed and special signs. Minimum lenght - 6 chars, maximum - 40
        final String PASSWORD_PATTERN = "^[A-Za-z0-9!@#$%^&*()_]{6,40}$";

        Pattern pattern;
        Matcher matcher;

        boolean isValid = true;

        pattern = Pattern.compile(LOGIN_PATTERN);
        matcher = pattern.matcher(login);
        if (!matcher.matches()) {
            isValid = false;
            errMessage.append("- Provide correct login. Spaces not allowed. " +
                    "Minimum length - 3 chars, maximum - 40.<br />");
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

        OSSUserDAO userDAO = new OSSUserDAOImpl(dbManager);
        if (userDAO.isLoginDuplicate(login)) {
            errMessage.append("- User with specified login already exist. " +
                    "Choose other login.<br />");
            isValid = false;
        }

        if (userDAO.isEmailDuplicate(email)) {
            errMessage.append("- User with specified email already exist " +
                    "in system. Try write to administrator for " +
                    "restoring you account.<br />");
            isValid = false;
        }

        try {
            serviceLocation = java.net.URLDecoder.decode(serviceLocation, "UTF-8");
        } catch (Exception exc) {
            isValid = false;
            errMessage.append("- Wrong location specified.<br />");
        }

        return isValid;
    }

    private void parseParams(HttpServletRequest request) {
        login = request.getParameter("login");
        password = request.getParameter("password");
        passwordConf = request.getParameter("passwordConf");
        email = request.getParameter("email");
        firstName = request.getParameter("firstName");
        lastName = request.getParameter("lastName");
        catalogID = Integer.parseInt(request.getParameter("serviceCatalogID"));
        serviceLocation = request.getParameter("serviceLocationID");
    }

    private int createUser(DBManager dbManager) {
        OSSUserDAO userDAO = new OSSUserDAOImpl(dbManager);
        OSSUser user = new OSSUser();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setLogin(login);
        user.setPassword(password);
        user.setBlocked(UserState.ACTIVE.toInt());
        user.setRoleID(UserRole.CUSTOMER_USER.toInt());
        int userID = (Integer) userDAO.add(user);
        return userID;
    }

    private ServiceOrder createOrder(DBManager dbManager, int userID) {

        ServiceOrderStatusDAO statusDAO = new ServiceOrderStatusDAOImpl(dbManager);
        ScenarioDAO scenarioDAO = new ScenarioDAOImpl(dbManager);
        ServiceOrderDAO orderDAO = new ServiceOrderDAOImpl(dbManager);

        // create new order with status ENTERING
        ServiceOrder so = new ServiceOrder();

        int orderStatusID = statusDAO.getServiceOrderStatusID(OrderStatus.ENTERING);
        so.setServiceOrderStatusID(orderStatusID);

        so.setScenarioID(scenarioDAO.getScenarioID(WorkflowScenario.NEW));
        so.setServiceCatalogID(catalogID);
        so.setServiceInstanceID(null);
        so.setServiceLocation(serviceLocation);
        so.setUserID(userID);

        Calendar cal = java.util.Calendar.getInstance();
        Date date = new Date(cal.getTimeInMillis());
        so.setServiceOrderDate(date);

        int orderID = (Integer) orderDAO.add(so);
        so.setId(orderID);
        return so;
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
        return "Registers user in the system, creates a new order " +
                "and executes it ('New' scenario workflow).";
    }// </editor-fold>
}
