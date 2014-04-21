package nc.notus;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nc.notus.dao.ServiceOrderDAO;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.ServiceOrder;
import nc.notus.workflow.DisconnectScenarioWorkflow;
import nc.notus.workflow.ModifyScenarioWorkflow;
import nc.notus.workflow.NewScenarioWorkflow;

/**
 *
 * @author Igor Litvinenko
 */
public class WorkflowTestServlet extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        try {
            ServiceOrder so;
            DBManager dbManager = new DBManager();
            try {
                ServiceOrderDAO soDAO = new ServiceOrderDAOImpl(dbManager);
                so = soDAO.find(6);
            } finally {
                dbManager.close();
            }

            //NewScenarioWorkflow wf = new NewScenarioWorkflow(so);
            //wf.proceedOrder();
            //wf.assignTask(6, 4);
            //wf.createRouter(6, 2);
            //wf.createCable(6);
            //wf.plugCableToPort(6, 2, 61);
            //wf.assignTask(7, 3);
            //wf.assignPortToServiceInstance(7, 61);
            //wf.approveBill(8);
            //ModifyScenarioWorkflow wf = new ModifyScenarioWorkflow(so);
            //wf.proceedOrder();
            //wf.approveBill(9);
            //DisconnectScenarioWorkflow wf = new DisconnectScenarioWorkflow(so);
            //wf.proceedOrder();
            //wf.unassignPort(11, 61, 52);
            //wf.unplugCableFromPort(12, 2, 61);
        } finally {
            out.close();
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
