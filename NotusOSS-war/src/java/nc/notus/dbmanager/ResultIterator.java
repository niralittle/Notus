package nc.notus.dbmanager;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * Wraps ResultSet class to handle SQL exceptions
 * @author Igor Litvinenko & Pancnhenko Dmytro
 */
public class ResultIterator {                                                   

    private ResultSet rs;
    private static Logger logger = Logger.getLogger(DBManager.class.getName());

    public ResultIterator(ResultSet rs) {
        this.rs = rs;
    }

    public boolean next() throws DBManagerException {
        try {
            return rs.next();
        } catch (SQLException exc) {
        	logger.error(exc.getMessage(), exc);
            throw new DBManagerException("SQL exception", exc);
        }
    }

    public Object getObject(String columnName) throws DBManagerException {
        try {
            return rs.getObject(columnName);
        } catch (SQLException exc) {
        	logger.error(exc.getMessage(), exc);
            throw new DBManagerException("SQL exception", exc);
        }
    }

    public Object getObject(int columnNumber) throws DBManagerException {
        try {
            return rs.getObject(columnNumber);
        } catch (SQLException exc) {
        	logger.error(exc.getMessage(), exc);
            throw new DBManagerException("SQL exception", exc);
        }
    }

    public Date getDate(String columnName) throws DBManagerException {
        try {
            return rs.getDate(columnName);
        } catch (SQLException exc) {
        	logger.error(exc.getMessage(), exc);
            throw new DBManagerException("SQL exception", exc);
        }
    }

    public Date getDate(int columnNumber) throws DBManagerException {
        try {
            return rs.getDate(columnNumber);
        } catch (SQLException exc) {
        	logger.error(exc.getMessage(), exc);
            throw new DBManagerException("SQL exception", exc);
        }
    }

    public float getFloat(String columnName) throws DBManagerException {
        try {
            return rs.getFloat(columnName);
        } catch (SQLException exc) {
        	logger.error(exc.getMessage(), exc);
            throw new DBManagerException("SQL exception", exc);
        }
    }

    public float getFloat(int columnNumber) throws DBManagerException {
        try {
            return rs.getFloat(columnNumber);
        } catch (SQLException exc) {
        	logger.error(exc.getMessage(), exc);
            throw new DBManagerException("SQL exception", exc);
        }
    }

    public int getInt(String columnName) throws DBManagerException {
        try {
            return rs.getInt(columnName);
        } catch (SQLException exc) {
        	logger.error(exc.getMessage(), exc);
            throw new DBManagerException("SQL exception", exc);
        }
    }

    public int getInt(int columnNumber) throws DBManagerException {
        try {
            return rs.getInt(columnNumber);
        } catch (SQLException exc) {
        	logger.error(exc.getMessage(), exc);
            throw new DBManagerException("SQL exception", exc);
        }
    }

    public String getString(String columnName) throws DBManagerException {
        try {
            return rs.getString(columnName);
        } catch (SQLException exc) {
        	logger.error(exc.getMessage(), exc);
            throw new DBManagerException("SQL exception", exc);
        }
    }

    public String getString(int columnNumber) throws DBManagerException {
        try {
            return rs.getString(columnNumber);
        } catch (SQLException exc) {
        	logger.error(exc.getMessage(), exc);
            throw new DBManagerException("SQL exception", exc);
        }
    }

    public boolean getBoolean(String columnName) throws DBManagerException {
        try {
            return rs.getBoolean(columnName);
        } catch (SQLException exc) {
        	logger.error(exc.getMessage(), exc);
            throw new DBManagerException("SQL exception", exc);
        }
    }

    public boolean getBoolean(int columnNumber) throws DBManagerException {
        try {
            return rs.getBoolean(columnNumber);
        } catch (SQLException exc) {
        	logger.error(exc.getMessage(), exc);
            throw new DBManagerException("SQL exception", exc);
        }
    }

    public long getLong(String columnName) throws DBManagerException {
        try {
            return rs.getLong(columnName);
        } catch (SQLException exc) {
        	logger.error(exc.getMessage(), exc);
            throw new DBManagerException("SQL exception", exc);
        }
    }

    public long getLong(int columnNumber) throws DBManagerException {
        try {
            return rs.getLong(columnNumber);
        } catch (SQLException exc) {
        	logger.error(exc.getMessage(), exc);
            throw new DBManagerException("SQL exception", exc);
        }
    }
}
