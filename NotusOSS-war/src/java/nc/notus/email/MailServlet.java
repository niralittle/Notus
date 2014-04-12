/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.email;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

                                                                                // REVIEW: wrong order in documenting. See CodingConventions in Google Disk
/**
 *
 * @author Roman Martynuyk
 * last changes 04.08.2014
 * MailServlet class is responsible for sending mail to user
 * or group of users
 *
 */
public class MailServlet extends HttpServlet {

    private String username = "notus.noreply@gmail.com";                        // REVIEW: static final should be used
    private String password = "notusnotus";
    private Properties props;

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
    throws ServletException, IOException {                                      // REVIEW: wrong formatting
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String toEmail = request.getParameter("address");
        String subject = request.getParameter("subject");
        String text = request.getParameter("text");

        try {
            send(toEmail, subject, text);
        } finally {                                                             // REVIEW: why is finally used
            out.close();
        }
    }

     public void send(String toEmail, String subject, String text){             // REVIEW: documenting expected
        props = new Properties();

        /*Parameters for Gmail (Shoud be changed)*/
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        /*Authentication to mail service */
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        /*Splitting address*/
        String[] s = toEmail.split(";");
        Address[] address = new Address[s.length];

        for(int i = 0; i<s.length;i++){
            try {
                address[i] = new InternetAddress(s[i].trim());
            } catch (AddressException ex) {
                Logger.getLogger(MailServlet.class.getName()).log(Level.SEVERE, // REVIEW: exeption should be thrown
                        null, ex);
            }
        }

        /*Sending mail*/
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.BCC, address);
            message.setSubject(subject);
            message.setContent(text,"text/html");
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
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
