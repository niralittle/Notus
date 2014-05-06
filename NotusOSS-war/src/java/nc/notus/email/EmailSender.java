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
import nc.notus.dbmanager.DBManagerException;
import nc.notus.entity.OSSUser;
import nc.notus.states.UserRole;

/**
 * Class sends emails using method sendEmail().
 * Depends on parameters send to one user - sendEmail(int userID, EMail mail);
 * or group of users - sendEmail(UserRole role, EMail mail);
 * Need SMTP properties from email host and login/password of email author
 *
 * @author Roman Martynuyk & Katya Atamanchuk
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
     * Method sends email to 1 user by userID
     * @param userID ID of the recipient
     * @param mail the letter
     */
    public void sendEmail(int userID, Email mail) {
        Thread sender = new SenderThread(userID, mail);
        sender.start();
    }

    /**
     * Method sends emails to a group of users
     * @param role user group role
     * @param mail the letter
     */
    public void sendEmail(UserRole role, Email mail) {
        Thread sender = new SenderThread(role, mail);
        sender.start();
    }

    private class SenderThread extends Thread {

        private final Email mail;
        private UserRole role;
        private int userID;

        SenderThread(UserRole role, Email mail) {
            this.mail = mail;
            this.role = role;

        }

        SenderThread(int userID, Email mail) {
            this.mail = mail;
            this.userID = userID;
        }

        @Override
        public void run() {

        /*Send mail*/
            try {
                DBManager dbManager = new DBManager();
                Address[] address = null;
                try {
                    OSSUserDAO userDAO = new OSSUserDAOImpl(dbManager);
                    if (role == null) {
                        address = new Address[1];
                        OSSUser user = userDAO.find(userID);
                        address[0] = new InternetAddress(user.getEmail());
                    } else {
                        List<String> userEmails = userDAO.getGroupEmails(role);
                        address = new Address[userEmails.size()];
                        for (int i = 0; i < userEmails.size(); i++) {
                            address[i] = new InternetAddress(userEmails.get(i));
                        }
                    }
                } catch (AddressException ex) {
                    Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    dbManager.close();
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
                    message.setRecipients(Message.RecipientType.BCC, address);
                    message.setSubject(mail.getSubject());
                    message.setContent(mail.getMessage(), "text/html");
                    Transport.send(message);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            } catch (DBManagerException ex) {
                Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
