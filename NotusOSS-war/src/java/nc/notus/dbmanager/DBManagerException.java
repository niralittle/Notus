/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.dbmanager;

/**
 *
 * @author Igor Litvinenko
 */
public class DBManagerException extends RuntimeException {

    /**
     * Creates a new instance of <code>DBManagerException</code> without detail message.
     */
    public DBManagerException() {
    }


    /**
     * Constructs an instance of <code>DBManagerException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public DBManagerException(String msg) {
        super(msg);
    }

    /**
     * Constructs an instance of <code>DBManagerException</code> with the specified detail message
     * and inner exception
     * @param msg the detail message.
     * @param innerException inner exception
     */
    public DBManagerException(String msg, Exception innerException) {
        super(msg, innerException);
    }
}
