package nc.notus;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
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
 * @author Alina Vorobiova & Katya Atamanchuk
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
        DBManager dbManager = new DBManager();
        try {
            ProviderLocationDAO locationsDAO = new ProviderLocationDAOImpl(dbManager);
            List<ProviderLocation> locations = locationsDAO.getProviderLocations(
                    OFFSET, NUMBER_OF_RECORDS);
            Map<String, Map<String, Integer>> map = new TreeMap<String, Map<String, Integer>>();
            ServiceTypeDAO type = new ServiceTypeDAOImpl(dbManager);
            Map<Integer, String> types = new TreeMap<Integer, String>();
            for(ProviderLocation location : locations){
                ServiceCatalogDAO catalogDAO = new ServiceCatalogDAOImpl(dbManager);
                List<ServiceCatalog> serviceCatalogs =
                        catalogDAO.getServiceCatalogByProviderLocationID(
                        location.getId(), OFFSET, NUMBER_OF_RECORDS);
                Map<String, Integer> prices = new TreeMap<String, Integer>();
                for (ServiceCatalog catalog : serviceCatalogs) {
                    ServiceType serviceType = type.find(catalog.getServiceTypeID());
                    types.put(catalog.getServiceTypeID(), serviceType.getService());
                    prices.put(serviceType.getService(), catalog.getPrice());
                }
                map.put(location.getLocation(), prices);
            }
            request.setAttribute("serviceTypes", types.values());
            request.setAttribute("providerLocations", map);
            RequestDispatcher view = request.getRequestDispatcher("serviceCatalog.jsp");
            view.forward(request, response);
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
