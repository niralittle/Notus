package nc.notus.dashboards;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nc.notus.dao.OSSUserDAO;
import nc.notus.dao.impl.OSSUserDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.entity.OSSUser;
import nc.notus.states.UserRole;

/**
 * Get users information for specified last name, email or login that uses by
 * Support Engineer to changing user password.
 * 
 * @author Panchenko Dmytro
 */
public class GettingUsersInfo extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    //page to changing user password
    private static final String CHANGE_PASSWORD_PAGE = "passwordChanging.jsp";
    private static int RECORDS_PER_PAGE = 5;
    
    //user list to return
    private List<OSSUser> users = null;
    private String login;
    private String email;
    private String lastName;
    
    private int offset;
    private int page;

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        response.setContentType("text/html;charset=UTF-8");

        DBManager dbManager = null;
        OSSUserDAO userDAO = null;
        long noOfPages;

        readInputtedData(request);
        if (!isValidParams(request)) {
            redirect(request, response, CHANGE_PASSWORD_PAGE);
        }
        try {
            dbManager = new DBManager();
            userDAO = new OSSUserDAOImpl(dbManager);
            
            noOfPages = getPageCount(userDAO); 	
            
            if (request.getParameter("page") == null) {
                page = 1;
            } else {
            	try{
            		page = Integer.parseInt(request.getParameter("page"));
            	} catch(NumberFormatException e) {
            		page = 1;
            	}
                if (page < 1 || page > noOfPages) {
                	page = 1;            	
                }
            }
            offset = (page - 1) * RECORDS_PER_PAGE;

            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("page", page);

            // search user for one criteria only:
            if (!lastName.isEmpty()) {
                users = userDAO.getUsersByLastName(lastName, offset, RECORDS_PER_PAGE);
            } else if (!login.isEmpty()) {
                users = userDAO.getUsersByLogin(login, offset, RECORDS_PER_PAGE);
            } else {
                users = userDAO.getUsersByEmail(email, offset, RECORDS_PER_PAGE);
            }

            request.setAttribute("findedUsers", users);
        } catch (DBManagerException exc) {
            request.setAttribute("errMessage", "Service is temporarily unavailable");
        } finally {
            dbManager.close();
        }

        redirect(request, response, CHANGE_PASSWORD_PAGE);
    }

    /**
     * Read inputet data from request scope.
     * 
     * @param request servlet request
     */
    private void readInputtedData(HttpServletRequest request) {
        email = request.getParameter("email");
        login = request.getParameter("login");
        lastName = request.getParameter("lastName");
    }

    /**
     * Check if read data are valid.
     * 
     * @param request servlet request
     */
    private boolean isValidParams(HttpServletRequest request) {
        if (login.isEmpty() & email.isEmpty() & lastName.isEmpty()) {
            request.setAttribute("errMessage",
                    "Specify at least one parameter to search user!");
            return false;
        }
        if (!login.isEmpty() & !email.isEmpty() & !lastName.isEmpty()) {
            request.setAttribute("errMessage",
                    "Specify only one parameter to search user!");
            return false;
        }

        //check combination
        if (!login.isEmpty() & !email.isEmpty()) {
            request.setAttribute("errMessage",
                    "Specify only one parameter to search user!");
            return false;
        }
        if (!login.isEmpty() & !lastName.isEmpty()) {
            request.setAttribute("errMessage",
                    "Specify only one parameter to search user!");
            return false;
        }
        if (!email.isEmpty() & !lastName.isEmpty()) {
            request.setAttribute("errMessage",
                    "Specify only one parameter to search user!");
            return false;
        }

        return true;
    }

    /**
     * Calculate count of pages for specified search parameter.
     */
    private long getPageCount(OSSUserDAO userDAO) throws DBManagerException {
        Map<String, Object> params = new HashMap<String, Object>();

        if (!lastName.isEmpty()) {
            params.put("lastname", "%" + lastName + "%");
        } else if (!login.isEmpty()) {
            params.put("login", "%" + login + "%");
        } else {
            params.put("email", "%" + email + "%");
        }
        params.put("roleid", UserRole.CUSTOMER_USER.toInt());

        long quantityOfRecords = userDAO.countAllWithLikeCause(params);
        long quantityOfPages = (long) Math.ceil(quantityOfRecords * 1.0 / RECORDS_PER_PAGE);
        
        return quantityOfPages;
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

    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
        return "Registers user in the system, creates a new order " + "and executes it ('New' scenario workflow).";
    }// </editor-fold>
}
