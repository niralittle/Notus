package nc.notus.dbmanager;

import java.io.Closeable;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * Wraps <code>PreparedStatement</code> class to handle SQL exceptions and
 * hide <code>Connection</code> from user.
 * @author Igor Litvinenko & Panchenko Dmytro
 */
public class Statement implements Closeable {

    private static Logger logger = Logger.getLogger(DBManager.class.getName());
    private PreparedStatement prStatement;

    public Statement(PreparedStatement prStatement) {
        this.prStatement = prStatement;
    }

    public void setInt(int parameterIndex, int value) throws DBManagerException {
        try {
            this.prStatement.setInt(parameterIndex, value);
        } catch (SQLException exc) {
            logger.error(exc.getMessage(), exc);
            throw new DBManagerException("SQL Exception", exc);
        }
    }

    public void setString(int parameterIndex, String value) throws DBManagerException {
        try {
            this.prStatement.setString(parameterIndex, value);
        } catch (SQLException exc) {
            logger.error(exc.getMessage(), exc);
            throw new DBManagerException("SQL Exception", exc);
        }
    }

    public void setDate(int parameterIndex, Date value) throws DBManagerException {
        try {
            this.prStatement.setDate(parameterIndex, value);
        } catch (SQLException exc) {
            logger.error(exc.getMessage(), exc);
            throw new DBManagerException("SQL Exception", exc);
        }
    }

    public void setObject(int parameterIndex, Object value) throws DBManagerException {
        try {
            this.prStatement.setObject(parameterIndex, value);
        } catch (SQLException exc) {
            logger.error(exc.getMessage(), exc);
            throw new DBManagerException("SQL Exception", exc);
        }
    }

    /**
     * Executes query on created statement.
     * @return ResultIterator that represents result of query
     * @throws DBManagerException 
     */
    public ResultIterator executeQuery() throws DBManagerException {
        try {
            ResultSet rs = prStatement.executeQuery();
            ResultIterator ri = new ResultIterator(rs);
            return ri;
        } catch (SQLException exc) {
            logger.error(exc.getMessage(), exc);
            throw new DBManagerException("Can't execute query.", exc);
        }
    }

    /**
     * Executes update query on created statement.
     * @return number of affected rows
     * @throws DBManagerException
     */
    public int executeUpdate() throws DBManagerException {
        try {
            int rowsAffected = prStatement.executeUpdate();
            return rowsAffected;
        } catch (SQLException exc) {
            logger.error(exc.getMessage(), exc);
            throw new DBManagerException("Can't execute query.", exc);
        }
    }

    /**
     * Method returns primary key that was generated in Statement execution
     * process.
     * @return generated primary key
     * @throws DBManagerException
     */
    public Object getGeneratedPrimaryKey() throws DBManagerException {
        try {
            ResultSet generatedKeys = prStatement.getGeneratedKeys();
            if (generatedKeys != null && generatedKeys.next()) {
                int primaryKey = generatedKeys.getInt(1);
                return primaryKey;
            } else {
                logger.error("Primary key not generated!");
                throw new DBManagerException("Primary key not generated!");
            }
        } catch (SQLException exc) {
            logger.error(exc.getMessage(), exc);
            throw new DBManagerException("Cannot get generated PK", exc);
        }
    }

    @Override
    public void close() {
        try {
            prStatement.close();
        } catch (SQLException exc) {
            logger.error(exc.getMessage(), exc);
        }
    }

    public void setLong(int parameterIndex, long value) throws DBManagerException {
        try {
            this.prStatement.setLong(parameterIndex, value);
        } catch (SQLException exc) {
            logger.error(exc.getMessage(), exc);
            throw new DBManagerException("SQL Exception", exc);
        }
    }
}
