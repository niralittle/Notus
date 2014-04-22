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
import nc.notus.states.UserRole;

/**
 * Class sends emails using method sendEmail().
 * Depends on parameters send to one user - sendEmail(int userID, EMail mail);
 * or group of users - sendEmail(UserRole role, EMail mail);
 * Need SMTP properties from email host and login/password of email author
 * @author Roman Martynuyk
 */
public class Mail {

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
    public void sendEmail(int userID, EMail mail) {

        DBManager dbManager = new DBManager();
        OSSUserDAO userDAO = new OSSUserDAOImpl(dbManager);

        /*Get the address of user*/
        Address address = null;
        try {
            address = new InternetAddress(userDAO.getUserEmail(userID));
        } catch (AddressException ex) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
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
            message.setSubject(mail.getTitle());
            message.setContent(mail.getBody(), "text/html");
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        dbManager.close();

    }

    public void sendEmail(UserRole role, EMail mail) {

        DBManager dbManager = new DBManager();
        OSSUserDAO userDAO = new OSSUserDAOImpl(dbManager);
        List<String> addressList = getGroupEmails(role);
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
                Logger.getLogger(MailServlet.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        }

        /*Send mail*/
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.BCC, address);
            message.setSubject(mail.getTitle());
            message.setContent(mail.getBody(), "text/html");
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        dbManager.close();
    }
}
