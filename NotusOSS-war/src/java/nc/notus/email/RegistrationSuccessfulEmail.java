package nc.notus.email;

/**
 *
 * @author Katya Atamanchuk <nira@niralittle.name>
 */
public class RegistrationSuccessfulEmail extends Email {

    String firstName;
    String connectionDate;
    String login;
    String password;

    public RegistrationSuccessfulEmail(String firstName,
            String connectionDate, String login, String password) {
        super("/registrationSuccess.html");

        this.firstName = firstName;
        this.connectionDate = connectionDate;
        this.login = login;
        this.password = password;
        subject = String.format("Congratulation, %s, you are registred!", firstName);
        message = String.format(message, firstName, connectionDate, login, password);
    }
}