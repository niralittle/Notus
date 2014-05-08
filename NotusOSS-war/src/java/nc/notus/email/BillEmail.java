package nc.notus.email;

/**
 * Instances of this class represent billing email messages to be sent to
 * a user by a customer support engineer.
 *  Example of use: <br> <code>
 *      Email bill =
 *          new BillEmail(firstName, serviceName, price);<br>
 *      EmailSender emailSender = new EmailSender();<br>
 *      emailSender.sendEmail(userID, bill); </code>
 *
 * @author Katya Atamanchuk <nira@niralittle.name>
 */
public class BillEmail extends Email {

    String firstName;
    String serviceName;
    String price;

    /**
     *
     * @param firstName name of the user
     * @param serviceName name of the service (e.i. Golden Internet)
     * @param price price of the service
     */
    public BillEmail(String firstName,
            String serviceName, String price) {

        super("/bill.html");
        this.firstName = firstName;
        this.serviceName = serviceName;
        this.price = price;
        subject = "Your Internet bill -- NOTUS Internet Provider";
        message = String.format(message, firstName, serviceName, price);
    }
}
