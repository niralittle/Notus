/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
import nc.notus.entity.ProviderLocation;
import nc.notus.entity.ServiceCatalog;
import nc.notus.entity.ServiceType;

/**
 * Servlet for getting all service catalogs
 * Gets request from javaScript,
 * gets all service catalogs via DAO
 * and forms responseXML to javaScript
 * @author Alina
 */
public class ServiceCatalogServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final int START = 1;
    private final int NUMBER_OF_RECORDS = 20;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        DBManager dbManager = new DBManager();
        StringBuffer sb = new StringBuffer();
        try {
            ProviderLocationDAO locationsDAO = new ProviderLocationDAOImpl(dbManager);
            List<ProviderLocation> locations = locationsDAO.getProviderLocations(START, NUMBER_OF_RECORDS);
            for(ProviderLocation location : locations){
                sb.append("<location>");
                sb.append("<name>"+location.getName()+"</name>");
                sb.append("<address>"+location.getLocation()+"</address>");
                sb.append("<catalogs>");
                ServiceCatalogDAO catalogDAO = new ServiceCatalogDAOImpl(dbManager);
                List<ServiceCatalog> serviceCatalogs =
                        catalogDAO.getServiceCatalogByProviderLocationID(location.getId(), START, NUMBER_OF_RECORDS);
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
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().write("<locations>" + sb.toString() + "</locations>");
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