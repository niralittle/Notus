package nc.notus.dashboards;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;
import nc.notus.dao.OSSUserDAO;
import nc.notus.dao.ServiceOrderDAO;
import nc.notus.dao.ServiceInstanceDAO;
import nc.notus.dao.impl.OSSUserDAOImpl;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dao.impl.ServiceInstanceDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.OSSUser;
import nc.notus.entity.ServiceOrder;
import nc.notus.entity.ServiceInstance;
import nc.notus.states.OrderStatus;

/**
 *
 * @author Katya Atamanchuk <nira@niralittle.name>
 */
public class CustomerUserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private DBManager dbManager = null;
    private HttpServletRequest request = null;

    void processRequest(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int startpage = 1;
        int numbOfRecords = 100;
        dbManager = new DBManager();
        this.request = request;
        try {
            if (request.getParameter("startpage") != null)
                startpage = Integer.parseInt(request.getParameter("startpage"));

            if (request.getParameter("numbOfRecords") != null)
                numbOfRecords = Integer.parseInt(request
                                .getParameter("numbOfRecords"));

            int userID = getUserID();

            fillRequestWithOrders(userID, OrderStatus.COMPLETED.ordinal() + 1,
                    "completedOrders", startpage, numbOfRecords);
            fillRequestWithOrders(userID, OrderStatus.ENTERING.ordinal() + 1,
                    "enteringOrders", startpage, numbOfRecords);
            fillRequestWithOrders(userID, OrderStatus.PROCESSING.ordinal() + 1,
                    "processingOrders", startpage, numbOfRecords);

            RequestDispatcher view = request.getRequestDispatcher("user.jsp");
            view.forward(request, response);

        } finally {
            dbManager.close();
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
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
            return "Creates data to display on customer user dashboard";
    }// </editor-fold>

    private int getUserID() {

        HttpSession session = request.getSession();
        Integer userIDString = (Integer) session.getAttribute("userID");

        if (userIDString == null) {
            String login = request.getUserPrincipal().getName();
            OSSUserDAO userDAO = new OSSUserDAOImpl(dbManager);
            OSSUser user = userDAO.getUserByLogin(login);
            if (user == null) return -1;
            session.setAttribute("userID", user.getId());
            return user.getId();
        } else return userIDString;
    }

    private void fillRequestWithOrders(int userID,
            int orderStatus, String attributeName,
            int startpage, int numbOfRecords) {

        ServiceOrderDAO orderDAO = new ServiceOrderDAOImpl(dbManager);

        List<ServiceOrder> orders = (List<ServiceOrder>)
            orderDAO.getSOByStatus( userID,
            orderStatus, startpage, numbOfRecords);

        request.setAttribute(attributeName, orders);
        
    }
}
