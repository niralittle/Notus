package nc.notus.dbmanager;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Wraps ResultSet class to handle SQL exceptions
 * @author Igor Litvinenko
 */
public class ResultIterator {
    
    private ResultSet rs;
    
    public ResultIterator(ResultSet rs) {
        this.rs = rs;
    }

    public boolean next() {
        try {
            return rs.next();
        } catch (SQLException exc) {
            throw new DBManagerException("SQL exception", exc);
        }
    }

    public Object getObject(String columnName) {
        try {
            return rs.getObject(columnName);
        } catch (SQLException exc) {
            throw new DBManagerException("SQL exception", exc);
        }
    }

    public Object getObject(int columnNumber) {
        try {
            return rs.getObject(columnNumber);
        } catch (SQLException exc) {
            throw new DBManagerException("SQL exception", exc);
        }
    }

    public int getInt(String columnName) {
        try {
            return rs.getInt(columnName);
        } catch (SQLException exc) {
            throw new DBManagerException("SQL exception", exc);
        }
    }

    public int getInt(int columnNumber) {
        try {
            return rs.getInt(columnNumber);
        } catch (SQLException exc) {
            throw new DBManagerException("SQL exception", exc);
        }
    }

    public String getString(String columnName) {
        try {
            return rs.getString(columnName);
        } catch (SQLException exc) {
            throw new DBManagerException("SQL exception", exc);
        }
    }

    public String getString(int columnNumber) {
        try {
            return rs.getString(columnNumber);
        } catch (SQLException exc) {
            throw new DBManagerException("SQL exception", exc);
        }
    }

    public boolean getBoolean(String columnName) {
        try {
            return rs.getBoolean(columnName);
        } catch (SQLException exc) {
            throw new DBManagerException("SQL exception", exc);
        }
    }

    public boolean getBoolean(int columnNumber) {
        try {
            return rs.getBoolean(columnNumber);
        } catch (SQLException exc) {
            throw new DBManagerException("SQL exception", exc);
        }
    }

    public long getLong(String columnName) {
        try {
            return rs.getLong(columnName);
        } catch (SQLException exc) {
            throw new DBManagerException("SQL exception", exc);
        }
    }

    public long getLong(int columnNumber) {
        try {
            return rs.getLong(columnNumber);
        } catch (SQLException exc) {
            throw new DBManagerException("SQL exception", exc);
        }
    }
}
