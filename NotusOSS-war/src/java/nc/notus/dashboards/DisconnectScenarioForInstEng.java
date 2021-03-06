package nc.notus.dashboards;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nc.notus.dao.ServiceInstanceDAO;
import nc.notus.dao.ServiceOrderDAO;
import nc.notus.dao.TaskDAO;
import nc.notus.dao.impl.ServiceInstanceDAOImpl;
import nc.notus.dao.impl.ServiceOrderDAOImpl;
import nc.notus.dao.impl.TaskDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.entity.Cable;
import nc.notus.entity.Port;
import nc.notus.entity.ServiceInstance;
import nc.notus.entity.ServiceOrder;
import nc.notus.entity.Task;
import nc.notus.workflow.DisconnectScenarioWorkflow;

/**
 * Proceed disconnect task for installation engineer.
 * 
 * @author Vladimir Ermolenko
 */
public class DisconnectScenarioForInstEng extends HttpServlet {
    private int offset = 0;
    private int numbOfRecords = 10;
    
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, DBManagerException {
    	
        response.setContentType("text/html;charset=UTF-8");
        
        DBManager dbManager = new DBManager();
        int taskID = 0;
        int soID = 0;
        int userID = 0;
        String actionStatus;
        int cableID = 0;
        int portID = 0;
        
        try {
            if (request.getParameter("serviceorderid") != null){
                soID  = Integer.parseInt(request.getParameter("serviceorderid"));
            }
            if (request.getParameter("taskid") != null){
                taskID  = Integer.parseInt(request.getParameter("taskid"));
            }
            if (request.getParameter("cableid") != null){
                cableID  = Integer.parseInt(request.getParameter("cableid"));
            }
            if (request.getParameter("portid") != null){
                portID  = Integer.parseInt(request.getParameter("portid"));
            }
            ServiceOrderDAO soDAO = new ServiceOrderDAOImpl(dbManager);
            ServiceOrder so = soDAO.find(soID);
            
            ServiceInstanceDAO siDAO = new ServiceInstanceDAOImpl(dbManager);
            ServiceInstance si = siDAO.find(so.getServiceInstanceID());
            
            DisconnectScenarioWorkflow dwf = new 
            			DisconnectScenarioWorkflow(so,dbManager);

            //Action "Disconnect Cable from Port" and redirect to personal tasks page
            if (request.getParameter("action") != null 
            		&& "Disconnect Cable from Port".equals(request.getParameter("action"))){
                try {
                    dwf.unplugCableFromPort(taskID, cableID, portID, si.getId());
                    actionStatus = "Cable was disconnected from port";
                    dbManager.commit();
                } catch (DBManagerException ex) {
                    dbManager.rollback();
                    actionStatus = "Cable was not disconnected from port";
                }
                TaskDAO taskDAO = new TaskDAOImpl(dbManager);
                
                boolean personal = true;
                List<Task> tasks = taskDAO.getTasksByID(offset, numbOfRecords, userID);
                request.setAttribute("userid", userID);
                request.setAttribute("tasks", tasks);
                request.setAttribute("type", personal);
                response.sendRedirect("TasksAssignment?type=personal&actionStatus="+actionStatus);
                return;
            }
		} catch (DBManagerException wfExc) {
			request.setAttribute("errorMessage", "Service unavailable."); 
			request.getRequestDispatcher("disconnectScenarioForInstEng.jsp").forward(request, response);
		} finally {
			dbManager.close();
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
        try {
            processRequest(request, response);
        } catch (DBManagerException ex) {
            Logger.getLogger(DisconnectScenarioForInstEng.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(DisconnectScenarioForInstEng.class.getName()).log(Level.SEVERE, null, ex);
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
