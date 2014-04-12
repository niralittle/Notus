/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus.dbmanager;

import java.io.Closeable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
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

    public ResultIterator executeQuery() {
        try {
            ResultSet rs = prStatement.executeQuery();
            ResultIterator ri = new ResultIterator(rs);
            return ri;
        } catch (SQLException exc) {
            throw new DBManagerException("Can't execute query.", exc);
        }
    }

    public int executeUpdate() {
        try {
            int rowsAffected = prStatement.executeUpdate();
            prStatement.getConnection().commit();
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
