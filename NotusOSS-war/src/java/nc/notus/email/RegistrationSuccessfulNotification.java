package nc.notus.email;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Katya Atamanchuk <nira@niralittle.name>
 */
public class RegistrationSuccessfulNotification {

    String firstName;
    String connectionDate;
    String login;
    String password;
    String message;


    public RegistrationSuccessfulNotification(String firstName,
            String connectionDate, String login, String password)
            throws FileNotFoundException, IOException {
        this.firstName = firstName;
        this.connectionDate = connectionDate;
        this.login = login;
        this.password = password;
        buildTemplate();
        message = String.format(message, firstName, connectionDate, login, password);

        System.out.println(message);
    }

    private void buildTemplate() throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream("/registrationSuccess.html")));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            int c = 0;
            while((c = br.read()) != -1) {
                    sb.append((char) c);
            }
            message = sb.toString();
        } finally {
            br.close();
        }
    }

   public static void main(String... args) {
        try {
            RegistrationSuccessfulNotification r = new RegistrationSuccessfulNotification("Katya", "12.02.2014", "nira", "123");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RegistrationSuccessfulNotification.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RegistrationSuccessfulNotification.class.getName()).log(Level.SEVERE, null, ex);
        }

   }
}