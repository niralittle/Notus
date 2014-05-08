package nc.notus.email;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.log4j.Logger;

/**
 *
 * @author Katya Atamanchuk
 */
public abstract class Email {

    protected String subject;
    protected String message;
    private static Logger logger = Logger.getLogger(Email.class.getName());

    public Email(String template) {
        BufferedReader br = null;
        try {
            new BufferedReader(new InputStreamReader(getClass().
                    getResourceAsStream(template), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            int c = 0;
            while ((c = br.read()) != -1) {
                sb.append((char) c);
            }
            message = sb.toString();
            br.close();
        } catch (IOException exc) {
            logger.error(exc.getMessage(), exc);
        }
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }
}
