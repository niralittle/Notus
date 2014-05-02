/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.dashboards;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nc.notus.dao.ServiceOrderDAO;
import nc.notus.dao.TaskDAO;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dao.impl.TaskDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.ServiceOrder;
import nc.notus.entity.Task;
import nc.notus.states.UserRole;
import nc.notus.workflow.NewScenarioWorkflow;

/**
 * Implements part of Provisioning Engineer dashboard
 * @author Vladimir Ermolenko
 */
public class CreateCircuit extends HttpServlet {
   
	
	private static final String PERSONAL_TASKS_PAGE = "TasksAssignment?type=personal";
	private static final String PROVISIONING_WORKFLOW_PAGE = "provisioningEngineerWorkflow.jsp";
	
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
        DBManager dbManager = new DBManager();
        int taskID;
        int soID;
        String circuitConf;
        try {
            if (request.getParameter("taskid") != null){
                taskID  = Integer.parseInt(request.getParameter("taskid"));
            }
            else {
                taskID = 0;
            }
            if (request.getParameter("serviceorderid") != null){
                soID  = Integer.parseInt(request.getParameter("serviceorderid"));
            }
            else {
                soID = 0;
            }
            if (request.getParameter("circuit") != null){
                circuitConf  = request.getParameter("circuit");
            }
            else {
                circuitConf = "";
            }
            ServiceOrderDAO soDAO = new ServiceOrderDAOImpl(dbManager);
            ServiceOrder so = soDAO.find(soID);
            NewScenarioWorkflow nwf = new NewScenarioWorkflow(so);

            //Action "Create Circuit"
            if (request.getParameter("action").equals("Create Circuit")){
                nwf.createCircuit(taskID, circuitConf);
                TaskDAO taskDAO = new TaskDAOImpl(dbManager);
                int startpage = 1;
                int numbOfRecords = 10;
                List<Task> tasksEng = taskDAO.getEngTasks(startpage, numbOfRecords, UserRole.PROVISION_ENGINEER.toInt());
                redirect(request, response, PERSONAL_TASKS_PAGE);
            }
            request.setAttribute("taskid", taskID);
            request.setAttribute("soid", soID);
            request.setAttribute("circuit", circuitConf);
            redirect(request, response, PROVISIONING_WORKFLOW_PAGE);

        } finally {
                dbManager.close();
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
	private void redirect(HttpServletRequest request,
			HttpServletResponse response, String page) throws ServletException,
			IOException {
		RequestDispatcher view = request.getRequestDispatcher(page);
		view.forward(request, response);
		return;
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
