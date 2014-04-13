package nc.notus.dbmanager;

import java.io.Closeable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Wraps <code>PreparedStatement</code> class to handle SQL exceptions and
 * hide <code>Connection</code> from user.
 * @author Igor Litvinenko
 */
public class Statement implements Closeable {

    private PreparedStatement prStatement;

    public Statement(PreparedStatement prStatement) {
        this.prStatement = prStatement;
    }

    public void setInt(int parameterIndex, int value) {
        try {
            this.prStatement.setInt(parameterIndex, value);
        } catch (SQLException exc) {
            throw new DBManagerException("SQL Exception", exc);
        }
    }

    public void setString(int parameterIndex, String value) {
        try {
            this.prStatement.setString(parameterIndex, value);
        } catch (SQLException exc) {
            throw new DBManagerException("SQL Exception", exc);
        }
    }

    public void setObject(int parameterIndex, Object value) {
        try {
            this.prStatement.setObject(parameterIndex, value);
        } catch (SQLException exc) {
            throw new DBManagerException("SQL Exception", exc);
        }
    }

    /**
     * Executes query on created statement.
     * @return ResultIterator that represents result of query
     */
    public ResultIterator executeQuery() {
        try {
            ResultSet rs = prStatement.executeQuery();
            ResultIterator ri = new ResultIterator(rs);
            return ri;
        } catch (SQLException exc) {
            throw new DBManagerException("Can't execute query.", exc);
        }
    }

    /**
     * Executes update query on created statement.
     * @return number of affected rows
     */
    public int executeUpdate() {
        try {
            int rowsAffected = prStatement.executeUpdate();
            return rowsAffected;
        } catch (SQLException exc) {
            throw new DBManagerException("Can't execute query.", exc);
        }
    }

    @Override
    public void close() {
        try {
            prStatement.close();
        } catch (SQLException exc) {
            throw new DBManagerException("SQL Exception", exc);
        }
    }
}
