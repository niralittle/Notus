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
 * Handles requests for report view                                                                       
 * @author Andrey Ilin
 */
public class ReportViewServlet extends HttpServlet {

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
        int reportTypeValue = Integer.parseInt(request.getParameter("report"));
        //String byMonth = request.getParameter("bymonth");
        String startDate = request.getParameter("fromdate");
        String finishDate = request.getParameter("todate");

        AbstractReport currentReport = null;
        switch (reportTypeValue) {
            case 0:
                currentReport = new MostProfitableRouterReport("Most profitable router");
                break;
            case 1:
                currentReport = new NewOrdersPerPeriodReport("New orders per period",
                        startDate, finishDate);
                break;
            case 2:
                currentReport = new DisconnectOrdersPerPeriodReport("Disconnect orders per period",
                        startDate, finishDate);
                break;
            case 3:
                currentReport = new RoutersUtilizationAndCapacityReport("Routers utilization and capacity");
                break;
//            case 4:
//                currentReport = new ProfitabilityByMonthReport("Profitability by month",
//                        byMonth);
//                break;

        }
        if (currentReport.getReportData().length > 1) {
            ReportGenerator reportGenerator = new ReportGenerator(currentReport);

            request.getSession().setAttribute("table", reportGenerator.getReportHTML());
            String objectId = UUID.randomUUID().toString();
            request.setAttribute("prevpage", "disabled");
            if (currentReport.checkNextPage()) {
                request.setAttribute("nextpage", "enabled");
            } else {
                request.setAttribute("nextpage", "disabled");
            }
            request.getSession().setAttribute("objectId", objectId);
            request.getSession().setAttribute(objectId, (Object) reportGenerator);
            request.getSession().setAttribute("title", currentReport.getReportName());

            request.getRequestDispatcher("report.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("noreport.jsp").forward(request, response);
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
            Logger.getLogger(ReportViewServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ReportViewServlet.class.getName()).log(Level.SEVERE, null, ex);
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
