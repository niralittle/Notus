package nc.notus;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nc.notus.dao.DAOException;
import nc.notus.dao.OSSUserDAO;
import nc.notus.dao.impl.OSSUserDAOImpl;
import nc.notus.entity.OSSUser;

import nc.notus.reports.RoutersUtilizationCapacityReport;


/**
 * Temporary servlet to test DAO functionality
 * @author Igor Litvinenko
 */
public class TestDAO extends HttpServlet {

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
        PrintWriter out = response.getWriter();

        try {

            //CableDAO dao = new CableDAOImpl();
            //out.println(dao.countAll(null));
            //dao.delete(3);
            //facepalm: don't try to write PK manually, we are using DB logic for it!
            //Cable cable = new Cable(69, "New cable"); // TODO: solve problem with PK overwrite
            //dao.add(cable);
            //out.println(dao.countAll(null));
            //cable = dao.find(68);
            //out.println("Found: " + cable.getCable());
            //cable.setCable("Very new Cable");
            //dao.update(cable);
            //cable = dao.find(68);
            //out.println("Found: " + cable.getCable());
            //dao.close();

//            RoutersUtilizationCapacityReport rucr = new RoutersUtilizationCapacityReport();
//            rucr.getReportData();
//            String[] str = rucr.getRows();
//            for (String st : str) {
//                out.println(st);
//            }
//        } catch (DAOException e) {
//            out.println(e.getMessage());
//=======
//            OSSUserDAO userDAO = new OSSUserDAOImpl();
//            OSSUser user = new OSSUser();
//            user.setFirstName("John");
//            user.setBlocked(0);
//            user.setEmail("email");
//            user.setLastName("Smith");
//            user.setLogin("john");
//            user.setPassword("11111");
//            user.setRoleID(1);
//            userDAO.add(user);
//            user = userDAO.find(7);  // not real id actually
//            userDAO.blockUser(user);
//            user.setPassword("22222");
//            userDAO.update(user);
//            userDAO.delete(7);       // not real id actually
//            userDAO.close();
//       } catch (DAOException exc) {
//            out.println("Error: " + exc.getMessage());
//>>>>>>> 4a234727d941258932d65fe487b653b8d058a724
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
