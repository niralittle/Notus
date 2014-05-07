 package nc.notus.email;

import java.io.IOException;

/**
 * Instances of this class represent email messages to be sent to
 * a customer user after his request to change a password has been satisfied.
 * Example of use: <br> <code>
 *      Email newPasssordEmail =
 *          new NewPasswordEmail(firstName, password);<br>
 *      EmailSender emailSender = new EmailSender();<br>
 *      emailSender.sendEmail(userID, newPasssordEmail); </code>
 *
 * @author Katya Atamanchuk <nira@niralittle.name>
 */
public class NewPasswordEmail extends Email {

    String firstName;
    String password;

    /**
     *
     * @param firstName user's name
     * @param password user's new password
     * @throws IOException
     */
    public NewPasswordEmail(String firstName, String password)
            throws IOException {

        super("/changePasswordSuccess.html");
        this.firstName = firstName;
        this.password = password;
        subject = "Your password has been changed -- NOTUS Internet Provider";
        message = String.format(message, firstName, password);
    }

}
