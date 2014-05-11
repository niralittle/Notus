package nc.notus.email;

/**
 * Instances of this class represent group email messages to be sent to
 * a group of users by an administrator.
 *  Example of use: <br> <code>
 *      Email news =
 *          new GroupMessageEmail("Check out what's new", newsHtml);<br>
 *      EmailSender emailSender = new EmailSender();<br>
 *      emailSender.sendEmail(UserRole.CUSTOMER_USER, news); </code>
 *
 * @author Katya Atamanchuk <nira@niralittle.name>
 */
public class GroupMessageEmail extends Email {

    String text;  

    /**
     *
     * @param topic subject of the email
     * @param t text of the email (with html tags)
     */
    public GroupMessageEmail(String topic, String t) {

        super("/groupMessage.html");
        this.text = t;
        subject = topic + " -- NOTUS Internet Provider";
        message = String.format(message, topic, text);
    }
}
