package nc.notus;

import java.io.IOException;

import java.sql.Date;
import java.util.Calendar;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

/**
 * Provides registration in system for new user and creates new scenario workflow
 * for order with status "Entering"
 * @author Dmytro Panchenko & Igor Litvinenko
 */
public class RegistrationServlet extends HttpServlet {

    private String login;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private int catalogID;
    private String serviceLocation;

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        this.parseParams(request);

        DBManager dbManager = new DBManager();
        ServiceOrder newOrder;
        try {
            OSSUserDAO userDAO = new OSSUserDAOImpl(dbManager);
            if (userDAO.isLoginDuplicate(login)) {
                request.setAttribute("duplicateLogin", "User with specified " +
                        "login already exist. Choose other login.");
                RequestDispatcher view = request.getRequestDispatcher("registration.jsp");
                view.forward(request, response);
                return;
            }

            if (userDAO.isEmailDuplicate(email)) {
                request.setAttribute("duplicateLogin", "User with specified " +
                        "email already exist in system. Try write to " +
                        "administrator for restoring you account.");
                RequestDispatcher view = request.getRequestDispatcher("registration.jsp");
                view.forward(request, response);
                return;
            }

            int userID = createUser(dbManager);
            newOrder = createOrder(dbManager, userID);

            dbManager.commit();
        } finally {
            dbManager.close();
        }

        Workflow wf = new NewScenarioWorkflow(newOrder);
        wf.proceedOrder();

        //redirect to congratulation page
        RequestDispatcher view = request.getRequestDispatcher("orderRecieved.jsp");
        view.forward(request, response);
    }

    private void parseParams(HttpServletRequest request) {
        login = (String) request.getParameter("login");
        password = (String) request.getParameter("password");
        email = (String) request.getParameter("email");
        firstName = (String) request.getParameter("firstName");
        lastName = (String) request.getParameter("lastName");
        catalogID = Integer.parseInt(request.getParameter("serviceCatalogID"));
        serviceLocation = (String) request.getParameter("serviceLocationID");
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

        int orderID = (Integer)orderDAO.add(so);
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
        return "Short description";
    }// </editor-fold>
}
