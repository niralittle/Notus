package nc.notus;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nc.notus.dao.ProviderLocationDAO;
import nc.notus.dao.ServiceCatalogDAO;
import nc.notus.dao.ServiceTypeDAO;
import nc.notus.dao.impl.ProviderLocationDAOImpl;
import nc.notus.dao.impl.ServiceCatalogDAOImpl;
import nc.notus.dao.impl.ServiceTypeDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.DBManagerException;
import nc.notus.entity.ProviderLocation;
import nc.notus.entity.ServiceCatalog;
import nc.notus.entity.ServiceType;

/**
 * Servlet for getting all service catalogs
 * Gets request from javaScript,
 * gets all service catalogs via DAO
 * and forms responseXML to javaScript
 * @author Alina Vorobiova
 */
public class ServiceCatalogServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws DBManagerException
     */
    private final int OFFSET = 0;
    private final int NUMBER_OF_RECORDS = 20;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, DBManagerException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        DBManager dbManager = new DBManager();
        StringBuffer sb = new StringBuffer();
        try {
            ProviderLocationDAO locationsDAO = new ProviderLocationDAOImpl(dbManager);
            List<ProviderLocation> locations = locationsDAO.getProviderLocations(OFFSET, NUMBER_OF_RECORDS);
            for(ProviderLocation location : locations){
                sb.append("<location>");
                sb.append("<name>"+location.getName()+"</name>");
                sb.append("<address>"+location.getLocation()+"</address>");
                sb.append("<catalogs>");
                ServiceCatalogDAO catalogDAO = new ServiceCatalogDAOImpl(dbManager);
                List<ServiceCatalog> serviceCatalogs =
                        catalogDAO.getServiceCatalogByProviderLocationID(location.getId(), OFFSET, NUMBER_OF_RECORDS);
                for(ServiceCatalog serviceCatalog : serviceCatalogs){
                    sb.append("<catalog>");
                    ServiceTypeDAO type = new ServiceTypeDAOImpl(dbManager);
                    ServiceType serviceType =type.find(serviceCatalog.getServiceTypeID());
                    sb.append("<name>" + serviceType.getService() + "</name>");
                    sb.append("<price>"+serviceCatalog.getPrice()+"</price>");
                    sb.append("</catalog>");
                }
                sb.append("</catalogs>");
                sb.append("</location>");
            }
            request.setAttribute("xml", "<locations>" + sb.toString() + "</locations>");
            
        } finally { 
            out.close();
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
            Logger.getLogger(ServiceCatalogServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ServiceCatalogServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
