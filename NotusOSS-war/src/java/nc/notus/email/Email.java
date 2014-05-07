package nc.notus.email;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Katya Atamanchuk
 */
public abstract class Email {

    protected String subject;
    protected String message;

    public Email(String template) throws IOException {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream(template),
                "UTF-8"));
        try {
            StringBuilder sb = new StringBuilder();
            int c = 0;
            while((c = br.read()) != -1) {
                    sb.append((char) c);
            }
            message = sb.toString();
        } finally {
            br.close();
        }
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }
}
