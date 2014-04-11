package nc.notus.dao.impl;

import java.sql.SQLException;
import javax.naming.NamingException;
import javax.sql.DataSource;
import nc.notus.dao.GenericDAO;
import javax.naming.InitialContext;
import java.sql.Connection;
import nc.notus.dao.DAOException;

/**
 * Implementation of generic DAO interface
 * @author Igor Litvinenko
 */
public abstract class GenericDAOImpl implements GenericDAO {

    private DataSource dataSource;

    public GenericDAOImpl() throws DAOException {
        try {
            InitialContext initContext = new InitialContext();
            dataSource = (DataSource) initContext.lookup("jdbc/_NOTUS");
        } catch (NamingException exc) {
            throw new DAOException(
                    "Cannot retrieve jdbc/_NOTUS", exc);
        }
    }

    protected Connection getConnection() throws DAOException {
        try {
            Connection conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            return conn;
        } catch (SQLException exc) {
            throw new DAOException(
                    "Cannot obtain connection", exc);
        }
    }

    protected void releaseConnection(Connection conn) throws DAOException {
        try {
            conn.close();
        } catch (SQLException exc) {
            throw new DAOException(
                    "Cannot release connection", exc);
        }
    }
}




