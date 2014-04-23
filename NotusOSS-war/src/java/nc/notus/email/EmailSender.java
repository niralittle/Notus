package nc.notus.email;

import java.util.List;
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
import nc.notus.dao.OSSUserDAO;
import nc.notus.dao.impl.OSSUserDAOImpl;
import nc.notus.dbmanager.DBManager;
import nc.notus.entity.OSSUser;
import nc.notus.states.UserRole;

/**
 * Class sends emails using method sendEmail().
 * Depends on parameters send to one user - sendEmail(int userID, EMail mail);
 * or group of users - sendEmail(UserRole role, EMail mail);
 * Need SMTP properties from email host and login/password of email author
 * @author Roman Martynuyk
 */
public class EmailSender {

    private final String username = "notus.noreply@gmail.com";
    private final String password = "notusnotus";
    private static Properties props;

    /*SMTP parameters*/
    static {
        props = new Properties();
        /*Parameters for Gmail (Shoud be changed)*/
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
    }

    /**
     * Method send email to 1 user by userID
     * @param userID - ID of the recipient
     * @param mail - text of the letter
     */
    public void sendEmail(int userID, Email mail) {

        DBManager dbManager = new DBManager();
        String userEmail;
        try {
            OSSUserDAO userDAO = new OSSUserDAOImpl(dbManager);
            OSSUser user = userDAO.find(userID);
            userEmail = user.getEmail();
        } finally {
            dbManager.close();
        }

        /*Get the address of user*/
        Address address = null;
        try {
            address = new InternetAddress(userEmail);
        } catch (AddressException ex) {
            Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*Authentication to mail service */
        Session session = Session.getInstance(props, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        /*Send mail*/
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipient(Message.RecipientType.BCC, address);
            message.setSubject(mail.getSubject());
            message.setContent(mail.getMessage(), "text/html");
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendEmail(UserRole role, Email mail) {

        DBManager dbManager = new DBManager();
        List<String> addressList;
        try {
            OSSUserDAO userDAO = new OSSUserDAOImpl(dbManager);
            addressList = userDAO.getGroupEmails(role);
        } finally {
            dbManager.close();
        }
        Address[] address = new Address[addressList.size()];

        /*Authentication to mail service */
        Session session = Session.getInstance(props, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        /*get the array of users addresses*/
        for (int i = 0; i < addressList.size(); i++) {
            try {
                address[i] = new InternetAddress(addressList.get(i));
            } catch (AddressException ex) {
                Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        }

        /*Send mail*/
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.BCC, address);
            message.setSubject(mail.getSubject());
            message.setContent(mail.getMessage(), "text/html");
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
