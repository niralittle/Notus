package nc.notus.dao;

import java.util.Map;

/**
 * This interface provides general DAO functions for all entities
 * @author Igor Litvinenko
 */
public interface GenericDAO<T> {
    /**
     * Method that returns the number of entries from a table that meet some
     * criteria (where clause params)
     *
     * @param params sql parameters
     * @return the number of records meeting the criteria
     */
    long countAll(Map<String, Object> params) throws DAOException;

    void add(T t) throws DAOException;

    void delete(Object id) throws DAOException;

    T find(Object id) throws DAOException;

    void update(T t) throws DAOException;
}
