/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.notus.email;

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

/**
 * Class sends emails using method send()
 * Need SMTP properties from email host and login/password of email author
 * @author Roman
 */
public class Mail {

    private final String username = "notus.noreply@gmail.com";
    private final String password = "notusnotus";
    private Properties props;

    /**
     * @param toEmail - email address of receiver
     * @param subject - subject of Email
     * @param text - the contents of the letter
     */
    public void send(String toEmail, String subject, String text) {
        props = new Properties();

        /*Parameters for Gmail (Shoud be changed)*/
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        /*Authentication to mail service */
        Session session = Session.getInstance(props, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        /*Splitting address*/
        String[] s = toEmail.split(";");
        Address[] address = new Address[s.length];

        for (int i = 0; i < s.length; i++) {
            try {
                address[i] = new InternetAddress(s[i].trim());
            } catch (AddressException ex) {
                Logger.getLogger(MailServlet.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        }

        /*Sending mail*/
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.BCC, address);
            message.setSubject(subject);
            message.setContent(text, "text/html");
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
