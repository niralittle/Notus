package nc.notus.email;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Katya Atamanchuk <nira@niralittle.name>
 */
public class RegistrationSuccessfulNotification { //implements Email {

    String message;
    String subject;

    public RegistrationSuccessfulNotification(String firstName,
            String connectionDate, String login, String password)
            throws FileNotFoundException, IOException {
        buildTemplate();
        
        message = String.format(message, firstName, connectionDate, login, password);
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

    public String getMessage() {
        return message;
    }


    public String getSubject() {
        return subject;
    }
}