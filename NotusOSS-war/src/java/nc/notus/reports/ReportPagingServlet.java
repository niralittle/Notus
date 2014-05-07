package nc.notus.reports;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nc.notus.dbmanager.DBManagerException;

/**
 *                                                                              //REVIEW: documentation here
 * @author Andrey Ilin
 */
public class ReportPagingServlet extends HttpServlet {

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
        String reportGenId = request.getParameter("objectId");
        if (reportGenId != null) {
            Object a = request.getSession().getAttribute(reportGenId.toString());
            ReportGenerator rg = (ReportGenerator) a;
            if (request.getParameter("nextpage") != null) {
                rg.getReport().getNextDataPage();
                if (!rg.getReport().checkNextPage()) {
                    request.setAttribute("nextpage", "disabled");
                } else {
                    request.setAttribute("nextpage", "enabled");
                }
            } else if (request.getParameter("prevpage") != null) {
                if (!rg.getReport().getPreviousDataPage()) {
                    request.setAttribute("prevpage", "disabled");
                } else {
                    request.setAttribute("prevpage", "enabled");
                }
            }

            request.getSession().setAttribute("table", rg.getReportHTML());
            String objectId = UUID.randomUUID().toString();

            request.getSession().setAttribute(objectId, request);
            request.getSession().setAttribute("objectId", objectId);
            request.getSession().setAttribute(objectId, (Object) rg);
            request.getSession().setAttribute("title", rg.getReportName());

            request.getRequestDispatcher("report.jsp").forward(request, response);
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
            Logger.getLogger(ReportPagingServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ReportPagingServlet.class.getName()).log(Level.SEVERE, null, ex);
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
