package nc.notus.email;

import java.io.IOException;

/**
 * Instances of this class represent email messages to be sent to
 * a customer user after his "Disconnect" order has been completed.
 * Example of use: <br> <code>
 *      Email notificationEmail =
 *          new DisconnectSuccessfulEmail(firstName, orderID, location);<br>
 *      EmailSender emailSender = new EmailSender();<br>
 *      emailSender.sendEmail(userID, notificationEmail); </code>
 *
 * @author Katya Atamanchuk <nira@niralittle.name>
 */
public class DisconnectSuccessfulEmail extends Email {

    String firstName;
    int orderId;
    String location;

    /**
     *
     * @param firstName user's name
     * @param orderId ID of the completed order
     * @param location address of the connection
     */
    public DisconnectSuccessfulEmail(String firstName, int orderId,
            String location) {

        super("/disconnectSuccess.html");
        this.firstName = firstName;
        this.orderId = orderId;
        this.location = location;
        subject = "Your disconnect order has been completed ";
        message = String.format(message, firstName, orderId, location);
    }
}
