package nc.notus.email;

import java.io.IOException;

/**
 * Instances of this class represent email messages to be sent to 
 * a newly registered customer user.
 *  Example of use: <br> <code>
 *      Email notificationEmail =
 *          new RegistrationSuccessfulEmail(firstName, login, password);<br>
 *      EmailSender emailSender = new EmailSender();<br>
 *      emailSender.sendEmail(userID, notificationEmail); </code>
 *
 * @author Katya Atamanchuk <nira@niralittle.name>
 */
public class RegistrationSuccessfulEmail extends Email {

    String firstName; 
    String login;
    String password;

    /**
     *
     * @param firstName name of the user
     * @param login user's login
     * @param password user's password
     * @throws IOException
     */
    public RegistrationSuccessfulEmail(String firstName,
            String login, String password) 
            throws IOException {

        super("/registrationSuccess.html");
        this.firstName = firstName;
        this.login = login;
        this.password = password;
        subject = String.format("Congratulation, %s, you are registered!", firstName);
        message = String.format(message, firstName, login, password);
    }
}