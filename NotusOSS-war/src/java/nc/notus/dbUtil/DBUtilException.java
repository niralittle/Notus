package nc.notus.dbUtil;

public class DBUtilException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Creates a new instance of <code>DBUtilException</code> without detail message.
     */
    public DBUtilException() {
    }


    /**
     * Constructs an instance of <code>DBUtilException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public DBUtilException(String msg) {
        super(msg);
    }

    /**
     * Constructs an instance of <code>DBUtilException</code> with the specified detail message
     * and inner exception
     * @param msg the detail message.
     * @param innerException inner exception
     */
    public DBUtilException(String msg, Exception innerException) {
        super(msg, innerException);
    }

}
