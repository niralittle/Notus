/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.notus.dbmanager;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Igor Litvinenko
 */
public class DBManager implements Closeable {

    private DataSource dataSource;
    private Connection conn;

    public DBManager() {
        try {
            InitialContext initContext = new InitialContext();
            dataSource = (DataSource) initContext.lookup("jdbc/_NOTUS");
        } catch (NamingException exc) {
            throw new DBManagerException("Cannot retrieve jdbc/_NOTUS", exc);
        }
        conn = this.getConnection();
    }

    public Statement prepareStatement(String query) {
        try {
            PreparedStatement prStatement = conn.prepareStatement(query);
            return new Statement(prStatement);
        } catch (SQLException exc) {
            throw new DBManagerException("SQL Exception", exc);
        }
    }

    private Connection getConnection() {
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            return conn;
        } catch (SQLException exc) {
            throw new DBManagerException("Cannot obtain connection", exc);
        }
    }

    private void releaseConnection() {
        try {
            conn.close();
        } catch (SQLException exc) {
            throw new DBManagerException("Cannot close connection", exc);
        }
    }

    public void close() {
        this.releaseConnection();
    }
}
