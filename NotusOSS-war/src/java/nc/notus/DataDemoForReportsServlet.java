/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.notus;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nc.notus.dao.OSSUserDAO;
import nc.notus.dao.PortDAO;
import nc.notus.dao.ServiceInstanceDAO;
import nc.notus.dao.ServiceOrderDAO;
import nc.notus.dao.impl.OSSUserDAOImpl;
import nc.notus.dao.impl.PortDAOImpl;
import nc.notus.dao.impl.ServiceInstanceDAOImpl;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.OSSUser;
import nc.notus.entity.Scenario;
import nc.notus.entity.ServiceCatalog;
import nc.notus.entity.ServiceInstance;
import nc.notus.entity.ServiceInstanceStatus;
import nc.notus.entity.ServiceOrder;
import nc.notus.entity.ServiceOrderStatus;
import nc.notus.states.InstanceStatus;
import nc.notus.states.OrderStatus;
import nc.notus.states.UserRole;
import nc.notus.states.UserState;
import nc.notus.states.WorkflowScenario;
import nc.notus.workflow.NewScenarioWorkflow;

/**
 * Test fill of database.
 * @author Andrey Ilin
 */
public class DataDemoForReportsServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = response.getWriter();
        DBManager dbManager = new DBManager();
        try {
            try {
                int taskId = 1;
                int employeeId = 4;
                int userId = 6;
                int servCatId = 1;
                int serviceOrderId = 1;
                int serviceInstanceId = 1;
                int cabelId = 2;
                int portId = 0;
                PortDAO portDAO = new PortDAOImpl(dbManager);
                OSSUserDAO userDAO = new OSSUserDAOImpl(dbManager);
                ServiceOrderDAO soDAO = new ServiceOrderDAOImpl(dbManager);

                userDAO.add(new OSSUser("Ivan", "Drago", "ivan@mail.ru", "ivdrago",
                        "1010", UserState.ACTIVE.toInt(), UserRole.CUSTOMER_USER.toInt()));
                userDAO.add(new OSSUser("Bender", "Rodriguez", "bender@gmail.com", "thebender",
                        "0101", UserState.ACTIVE.toInt(), UserRole.CUSTOMER_USER.toInt()));
                userDAO.add(new OSSUser("Antony", "Soprano", "soprano@i.ua", "fattony",
                        "1100", UserState.ACTIVE.toInt(), UserRole.CUSTOMER_USER.toInt()));

                dbManager.commit();
                pw.write("USERS ADDED");
                int counter = 0;
                while (counter < 3) {
                    employeeId = 4;
                    soDAO.add(new ServiceOrder(serviceOrderId, Date.valueOf("2014-01-01"), 1, 1,
                            userId, servCatId, serviceInstanceId, "location" + Integer.toString(userId)));
                    dbManager.commit();
                    pw.write("ORDER CREATED");
                    NewScenarioWorkflow nsw = new NewScenarioWorkflow(soDAO.find(serviceOrderId));
                    nsw.proceedOrder();
                    nsw.assignTask(taskId, employeeId);
                    if (portDAO.getFreePort() == null) {
                        nsw.createRouter(taskId, 2);
                    }
                    nsw.createCable(taskId, "type" + servCatId);
                    portId = portDAO.getFreePort().getId();
                    nsw.plugCableToPort(taskId, cabelId, portId);
                    taskId++;
                    employeeId--;
                    nsw.assignTask(taskId, employeeId);
                    nsw.createCircuit(portId, "config" + employeeId);
                    taskId++;
                    employeeId--;
                    nsw.assignTask(taskId, employeeId);
                    nsw.approveBill(taskId);
                    taskId++;
                    userId++;
                    servCatId++;
                    serviceOrderId++;
                    serviceInstanceId++;
                    cabelId++;
                    counter++;
                }
                dbManager.commit();
                pw.write("COMPLETED");


            } finally {
                dbManager.close();
            }
        } finally {
            pw.close();
        }



    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
