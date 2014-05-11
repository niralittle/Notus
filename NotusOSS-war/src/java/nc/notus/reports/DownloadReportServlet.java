package nc.notus.reports;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nc.notus.dbmanager.DBManagerException;

/**
 * Handles report download request
 * @author Andrey Ilin
 */
public class DownloadReportServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String reportGenId = request.getParameter("objectId");
        Object a = request.getSession().getAttribute(reportGenId.toString());
        ReportGenerator rg = (ReportGenerator) a;
        if ("xls".equals(request.getParameter("type"))) {

            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" +
                    rg.getReportName() + ".xls");
        }
        if ("csv".equals(request.getParameter("type"))) {

            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=" +
                    rg.getReportName() + ".csv");
        }
        try {
            rg.getReportCSV(response.getWriter());
        } catch (DBManagerException exc) {
            request.getSession().setAttribute("ErrorString", exc.getMessage());
            response.sendRedirect("errorPage.jsp");
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
        return "Handles system report download requests";
    }// </editor-fold>
}
