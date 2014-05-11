/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.dashboards;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nc.notus.dao.CableDAO;
import nc.notus.dao.OSSUserDAO;
import nc.notus.dao.PortDAO;
import nc.notus.dao.ScenarioDAO;
import nc.notus.dao.ServiceCatalogDAO;
import nc.notus.dao.ServiceInstanceDAO;
import nc.notus.dao.ServiceOrderDAO;
import nc.notus.dao.ServiceTypeDAO;
import nc.notus.dao.TaskDAO;
import nc.notus.dao.impl.CableDAOImpl;
import nc.notus.dao.impl.OSSUserDAOImpl;
import nc.notus.dao.impl.PortDAOImpl;
import nc.notus.dao.impl.ScenarioDAOImpl;
import nc.notus.dao.impl.ServiceCatalogDAOImpl;
import nc.notus.dao.impl.ServiceInstanceDAOImpl;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dao.impl.ServiceTypeDAOImpl;
import nc.notus.dao.impl.TaskDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.entity.Cable;
import nc.notus.entity.OSSUser;
import nc.notus.entity.Port;
import nc.notus.entity.Scenario;
import nc.notus.entity.ServiceCatalog;
import nc.notus.entity.ServiceInstance;
import nc.notus.entity.ServiceOrder;
import nc.notus.entity.ServiceType;
import nc.notus.entity.Task;
import nc.notus.states.UserRole;
import nc.notus.states.WorkflowScenario;

/**
 * Implements tasks assignment from role tasks to personal task for
 * all types of engineers
 * 
 * @author Vladimir Ermolenko
 */
public class TasksAssignment extends HttpServlet {
   
	private static final int RECORDS_PER_PAGE = 5;

	private int offset;
	private int page;
	private HttpSession session;
	
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, DBManagerException {
        response.setContentType("text/html;charset=UTF-8");
        
        DBManager dbManager = new DBManager();
        String login = "";
        OSSUser user = null;
        Task task = null;
        TaskDAO taskDAO = null;
        int taskID;
        List<Task> tasksEng;
        List<Map<String, String>> tasksEngFull;
        Cable cable = null;
        Port port = null;
        boolean personal = false;
        String actionStatus = null;
        String taskName = null;
        String serviceLocation = null;
        String serviceDescription = null;
        int price = 0;

        try {
            taskDAO = new TaskDAOImpl(dbManager);
            OSSUserDAO userDAO = new OSSUserDAOImpl(dbManager);

            if (request.getUserPrincipal() != null) {
                login = request.getUserPrincipal().getName();
            }
            else {
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }

            if (request.getParameter("type") != null && "personal".equals(request.getParameter("type"))){
                personal = true;
            }

            if (userDAO.getUserByLogin(login) != null){
                user = userDAO.getUserByLogin(login);
            }
            if (request.getParameter("actionStatus") != null ){
                actionStatus = (String) request.getParameter("actionStatus");
            }

            if (request.getParameter("taskName") != null){
                taskName  = (String) request.getParameter("taskName");
            }
            if (request.getParameter("serviceLocation") != null){
                serviceLocation  = (String) request.getParameter("serviceLocation");
            }
            if (request.getParameter("serviceDescription") != null){
                serviceDescription  = (String) request.getParameter("serviceDescription");
            }
            if (request.getParameter("price") != null){
                price  = Integer.parseInt(request.getParameter("price"));
            }

            //Action "Assign to myself" tasks from group to personal or choose task from personal to execute it
            if (request.getParameter("action") != null && !"Back to Tasks".equals(request.getParameter("action"))){
                if (request.getParameter("taskid") != null){
                    taskID  = Integer.parseInt(request.getParameter("taskid"));
                    task = taskDAO.find(taskID);
                }
                if (!personal) {
                    task.setEmployeeID(user.getId());
                    try {
                        taskDAO.update(task);
                        dbManager.commit();
                    } catch (DBManagerException ex) {
                        dbManager.rollback();
                        actionStatus = "Task was was not assigned";
                    }
                } else {
                	int roleID = user.getRoleID();
                	
                        if (user.getRoleID() == UserRole.INSTALLATION_ENGINEER.toInt()) {
                            ServiceOrderDAO soDAO = new ServiceOrderDAOImpl(dbManager);
                            ServiceOrder so = soDAO.find(task.getServiceOrderID());
                            PortDAO portDAO = new PortDAOImpl(dbManager);
                            ServiceInstanceDAO siDAO = new ServiceInstanceDAOImpl(dbManager);
                            CableDAO cableDAO = new CableDAOImpl(dbManager);
                            if (so.getScenarioID() == WorkflowScenario.NEW.toInt()){
                                port = portDAO.getFreePort();
                                cable = cableDAO.getFreeCable();
                            }
                            if (so.getScenarioID() == WorkflowScenario.DISCONNECT.toInt()){
                                ServiceInstance si = siDAO.find(so.getServiceInstanceID());
                                port = portDAO.find(si.getPortID());
                                cable = cableDAO.find(port.getCableID());
                            }


                            request.setAttribute("taskName", taskName);
                            request.setAttribute("serviceLocation", serviceLocation);
                            request.setAttribute("serviceDescription", serviceDescription);
                            request.setAttribute("price", price);
                            request.setAttribute("port", port);
                            request.setAttribute("cable", cable);
                            request.setAttribute("taskid", task.getId());
                            request.setAttribute("soid", task.getServiceOrderID());
                            request.setAttribute("userid", user.getId());
                            String wfScenario = getTaskScenario(task, dbManager);
                            session = request.getSession();
                            session.setAttribute("taskid", task.getId());
                            session.setAttribute("serviceorderid", task.getServiceOrderID());
                            session.setAttribute("port", port);
                            session.setAttribute("cable", cable);
                            session.setAttribute("userid", user.getId());
                            if (wfScenario.equalsIgnoreCase(WorkflowScenario.NEW.toString())) {
                                request.getRequestDispatcher("installationEngineerWorkflow.jsp").forward(request, response);
                                return;
                            }
                            if (wfScenario.equalsIgnoreCase(WorkflowScenario.DISCONNECT.toString())) {
                                request.getRequestDispatcher("disconnectScenarioForInstEng.jsp").forward(request, response);
                                return;
                            }
                        }
                        if (roleID  == UserRole.PROVISION_ENGINEER.toInt()) {
                            prepareTask(task, request, dbManager, roleID );
                            request.getRequestDispatcher("provisioningEngineerWorkflow.jsp").forward(request, response);
                            return;
                        }
                        if (roleID  == UserRole.SUPPORT_ENGINEER.toInt()) {
                            prepareTask(task, request, dbManager, roleID );
                            request.getRequestDispatcher("supportEngineer.jsp").forward(request, response);
                            return;
                        }
                    }
            }
            
            
            if (request.getParameter("page") == null) {
                page = 1;
            } else {
                try {
                    //if page number is integer
                    page = Integer.parseInt(request.getParameter("page"));
                } catch (NumberFormatException ex) {
                    //if page number is incorrect
                    page = 1;
                }
            }
            offset = (page-1) * RECORDS_PER_PAGE ;

            request.setAttribute("noOfPages", getPageCount(taskDAO, user, personal));
            request.setAttribute("page", page);
			
            if (!personal) {
                tasksEng = taskDAO.getEngTasks(offset, RECORDS_PER_PAGE, user.getRoleID());
            } else {
                tasksEng = taskDAO.getTasksByID(offset, RECORDS_PER_PAGE, user.getId());
            }
                tasksEngFull = new ArrayList<Map<String, String>>();
                for (Task t: tasksEng) {
                    Map<String,String> row = new HashMap<String, String>();
                    ServiceOrderDAOImpl soDAO = new ServiceOrderDAOImpl(dbManager);
                    ServiceCatalogDAO catalogDAO = new ServiceCatalogDAOImpl(dbManager);
                    ServiceTypeDAO typeDAO = new ServiceTypeDAOImpl(dbManager);
                    ServiceOrder order = soDAO.find(t.getServiceOrderID());
                    ServiceCatalog sc  = catalogDAO.find(order.getServiceCatalogID());
                    ServiceType st = typeDAO.find(sc.getServiceTypeID());
                    row.put("taskID", Integer.toString(t.getId()));
                    row.put("taskName", t.getName());
                    row.put("serviceLocation", order.getServiceLocation());
                    row.put("serviceDescription", st.getService());
                    row.put("price", Integer.toString(sc.getPrice()));
                    tasksEngFull.add(row);
                }
            request.setAttribute("tasksEngFull", tasksEngFull);
            request.setAttribute("type", personal);
            request.setAttribute("user", user);
            request.setAttribute("actionStatus", actionStatus);
            request.getRequestDispatcher("tasksAssignment.jsp").forward(request, response);
        } catch(DBManagerException ex) {
        	request.setAttribute("errMessage", "Service unavailable.");
            request.getRequestDispatcher("tasksAssignment.jsp").forward(request, response);
        } finally {
            dbManager.close();
        }

    } 
    
    /**
     * Prepare some data before redirecting to engineer page.
     * 
     */
    private void prepareTask(Task task, HttpServletRequest request, DBManager dbManager, 
    		int roleID) throws DBManagerException {
    		
		if (roleID == UserRole.INSTALLATION_ENGINEER.toInt()) {
			return;
		}
		if (roleID == UserRole.PROVISION_ENGINEER.toInt()) {
			String serviceType = request.getParameter("serviceDescription");
			request.setAttribute("serviceType", serviceType);
			
			String wfScenario = getTaskScenario(task, dbManager);
			request.setAttribute("wfScenario", wfScenario);
		}
		if(roleID  == UserRole.SUPPORT_ENGINEER.toInt()){
			String serviceType = request.getParameter("serviceDescription");
			String price = request.getParameter("price");
					
			request.setAttribute("price", price);
			request.setAttribute("serviceType", serviceType);
		}
		request.setAttribute("task", task);
	}
    
    /**
     * Get workflow scenario for given task.
     */
    private String getTaskScenario(Task task, DBManager dbManager) throws DBManagerException {
		ServiceOrderDAOImpl soDAO = new ServiceOrderDAOImpl(dbManager);
		ScenarioDAO scenarioDAO = new ScenarioDAOImpl(dbManager);

		ServiceOrder order = soDAO.find(task.getServiceOrderID());

		int scenarioID = order.getScenarioID();
		Scenario scenario = scenarioDAO.find(scenarioID);

		
		if (scenario.getScenario().equalsIgnoreCase(WorkflowScenario.NEW.toString())) {
			return "NEW";
		} else if ((scenario.getScenario().equalsIgnoreCase(WorkflowScenario.DISCONNECT.toString()))) {
			return "DISCONNECT";
		}
		return null;
	}
    
    /**
     * Get count of pages with tasks in DB.
     */
    private long getPageCount(TaskDAO taskDAO, OSSUser user, boolean personal) throws DBManagerException {
		Map<String, Object> params = new HashMap<String, Object>();
		
	    if (personal) {
	    	 params.put("employeeid", user.getId());
	    } else {
	    	 params.put("roleid", user.getRoleID());
	    	 params.put("employeeid", null);
	    }
	    params.put("TASKSTATUSID", 1);
		
		long quantityOfRecords = taskDAO.countAll(params);
		long quantityOfPages = (long) Math.ceil(quantityOfRecords * 1.0/ RECORDS_PER_PAGE);
		return quantityOfPages;
	}

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
        try {
            processRequest(request, response);
        } catch (DBManagerException ex) {
            Logger.getLogger(TasksAssignment.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (DBManagerException ex) {
            Logger.getLogger(TasksAssignment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
