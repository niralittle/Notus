package nc.notus;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import nc.notus.dao.CableDAO;
import nc.notus.dao.DAOException;
import nc.notus.dao.GenericDAO;
import nc.notus.dao.impl.CableDAOImpl;
import nc.notus.dao.impl.GenericDAOImpl;
import nc.notus.entity.Cable;
import java.util.*;
//import nc.notus.dao.impl.CableDAOImpl;
import nc.notus.dao.CableDAO;
import nc.notus.dao.impl.RoleDAOImpl;
import nc.notus.entity.Role;
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
            try {
                CableDAO dao = new CableDAOImpl(); // TODO: add this to context on server startup
                out.println(dao.countAll(null));
                //dao.delete(3);
                Cable cable = new Cable(69, "New cable"); // TODO: solve problem with PK overwrite
                dao.add(cable);
                out.println(dao.countAll(null));
                cable = dao.find(68);
                out.println("Found: " + cable.getCable());
                cable.setCable("Very new Cable");
                dao.update(cable);
                cable = dao.find(68);
                out.println("Found: " + cable.getCable());

                /*RoleDAOImpl roleDAO = new RoleDAOImpl();
                Role role = roleDAO.read(1);
                //CableDAO dao = new CableDAOImpl(); // TODO: add this to context on server startup
                //out.println(dao.getCableName(1));
                out.println("Role " + role.getId() + " " + role.getRole());*/
            } catch (DAOException exc) {
                exc.printStackTrace();
                out.println("Error: " + exc);
            }
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
