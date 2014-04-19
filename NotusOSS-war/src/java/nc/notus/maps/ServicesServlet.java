package nc.notus.maps;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nc.notus.entity.ServiceCatalog;
import nc.notus.entity.ServiceType;

/**Servlet for getting service catalogs for the nearest provider location
 * Gets request from javaScript with providerLocationID,
 * gets all service catalogs for this provider location
 * and forms responseXML to javaScript
 * @author Alina
 */
public class ServicesServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        StringBuffer sb = new StringBuffer();
        try {
            int providerLocationID = Integer.valueOf(request.getParameter("providerLocationID"));
            GetServiceCatalogs gsc = new GetServiceCatalogs(providerLocationID);
            List<ServiceCatalog> serviceCatalogs = gsc.getServiceCatalogs();
            if (serviceCatalogs.size() > 0) {
                //forms the responseXML
                for (ServiceCatalog serviceCatalog : serviceCatalogs) {
                    sb.append("<service>");
                    ServiceType serviceType = gsc.getServiceType(serviceCatalog);
                    sb.append("<id>" + serviceCatalog.getId() + "</id>");
                    sb.append("<name>" + serviceType.getService() + "</name>");
                    sb.append("<price>" + serviceCatalog.getPrice() + "</price>");
                    sb.append("</service>");
                }
            }
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().write("<services>" + sb.toString() + "</services>");
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
