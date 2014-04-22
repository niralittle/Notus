/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.email;

/**
 *
 * @author Igor Litvinenko
 */
public class ExampleEmail implements Email {

    private String name;
    private String surname;

    public ExampleEmail(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getMessage() {
        return "Hello you, " + name + ", " + surname;
    }

    public String getTitle() {
        return "Greeting email";
    }

}
