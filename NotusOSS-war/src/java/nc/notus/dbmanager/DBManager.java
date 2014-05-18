package nc.notus.dbmanager;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
 * This class provides functionality of managing connection to DB.
 * It encapsulates <code>DataSource</code> and is responsible for obtaining and
 * releasing DB connection.
 * @author Igor Litvinenko & Panchenko Dmytro
 */
public class DBManager implements Closeable {

    private static Logger logger = Logger.getLogger(DBManager.class.getName());
    private DataSource dataSource; // DataSource respresening DB
    private Connection conn;       // DB connection

    /**
     * Creates new instance of <code>DBManager</code>.
     * Uses JNDI lookup to obtain DataSource.
     * @throws DBManagerException
     */
    public DBManager() throws DBManagerException {
        try {
            InitialContext initContext = new InitialContext();
            dataSource = (DataSource) initContext.lookup("jdbc/_NOTUS");
        } catch (NamingException exc) {
            logger.error(exc.getMessage(), exc);
            throw new DBManagerException("Cannot retrieve jdbc/_NOTUS", exc);
        }
        conn = this.getConnection();
    }

    /**
     * Creates new <code>Statement</code> from given SQL query.
     * @param query SQL query to form statement from
     * @return Statement of given query
     * @throws DBManagerException 
     */
    public Statement prepareStatement(String query) throws DBManagerException {
        try {
            String generatedColumns[] = {"ID"}; // primary key column for Statement.getGeneratedPrimaryKey()
            PreparedStatement prStatement = conn.prepareStatement(query, generatedColumns);
            return new Statement(prStatement);
        } catch (SQLException exc) {
            logger.error(exc.getMessage(), exc);
            throw new DBManagerException("SQL Exception", exc);
        }
    }
    // REVIEW: documentation expected

    public void commit() throws DBManagerException {
        try {
            conn.commit();
        } catch (SQLException exc) {
            logger.error(exc.getMessage(), exc);
            throw new DBManagerException("Cannot execute commit", exc);
        }
    }
    // REVIEW: documentation expected

    public void rollback() throws DBManagerException {
        try {
            conn.rollback();
        } catch (SQLException exc) {
            logger.error(exc.getMessage(), exc);
            throw new DBManagerException("Cannot execute rollback", exc);
        }
    }

    /**
     * Gets connection to DataSource
     * @return Connection
     */
    private Connection getConnection() throws DBManagerException {
        try {
            conn = dataSource.getConnection();
            
            if(conn == null) {
                throw new DBManagerException("Cannot obtain connection", exc);
            }
            
            conn.setAutoCommit(false);
            return conn;
        } catch (SQLException exc) {
            logger.error(exc.getMessage(), exc);
            throw new DBManagerException("Cannot obtain connection", exc);
        }
    }

    /**
     * Releases obtained connection
     */
    private void releaseConnection() throws DBManagerException {
        try {
            if(conn != null) {
                conn.close();
            }
        } catch (SQLException exc) {
            logger.error(exc.getMessage(), exc);
            throw new DBManagerException("Cannot close connection", exc);
        }
    }

    /**
     * Releases resources
     */
    public void close() {
        try {
            this.releaseConnection();
        } catch (DBManagerException exc) {
        }
    }
}
