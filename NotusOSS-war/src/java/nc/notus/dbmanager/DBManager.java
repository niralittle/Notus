package nc.notus.dbmanager;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * This class provides functionality of managing connection to DB.
 * It incapsulates <code>DataSource</code> and is responsible for obtaining and
 * releasing DB connection.
 * @author Igor Litvinenko
 */
public class DBManager implements Closeable {

    private DataSource dataSource; // DataSource respresening DB
    private Connection conn;       // DB connection

    /**
     * Creates new instance of <code>DBManager</code>.
     * Uses JNDI lookup to obtain DataSource.
     */
    public DBManager() {
        try {
            InitialContext initContext = new InitialContext();
            dataSource = (DataSource) initContext.lookup("jdbc/_NOTUS");
        } catch (NamingException exc) {
            throw new DBManagerException("Cannot retrieve jdbc/_NOTUS", exc);
        }
        conn = this.getConnection();
    }

    /**
     * Creates new <code>Statement</code> from given SQL query.
     * @param query SQL query to form statement from
     * @return Statement of given query
     */
    public Statement prepareStatement(String query) {
        try {
            PreparedStatement prStatement = conn.prepareStatement(query);
            return new Statement(prStatement);
        } catch (SQLException exc) {
            throw new DBManagerException("SQL Exception", exc);
        }
    }

    /**
     * Gets connection to DataSource
     * @return Connection
     */
    private Connection getConnection() {
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            return conn;
        } catch (SQLException exc) {
            throw new DBManagerException("Cannot obtain connection", exc);
        }
    }

    /**
     * Releases obtained connection
     */
    private void releaseConnection() {
        try {
            conn.close();
        } catch (SQLException exc) {
            throw new DBManagerException("Cannot close connection", exc);
        }
    }

    /**
     * Releases resources
     */
    public void close() {
        this.releaseConnection();
    }
}
