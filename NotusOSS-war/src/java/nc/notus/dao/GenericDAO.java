package nc.notus.dao;

import java.sql.Connection;

/**
 * This interface provides CRUD operations on every entity in the DB
 * @author Igor Litvinenko
 */
public interface GenericDAO {

    Connection getConnection() throws DAOException;

    void releaseConnection(Connection conn) throws DAOException;
}
