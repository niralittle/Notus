package nc.notus.email;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author Igor Litvinenko
 */
public abstract class Email {

    protected String subject;
    protected String message;

    public Email(String template) {
        message = "";
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(getClass().getResourceAsStream(template)));
            String line = br.readLine();
            while(line != null) {
                message += line;
                line = br.readLine();
            }
            br.close();
        } catch (Exception exc) {
            throw new RuntimeException("Template build failed");
        }
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }
}
