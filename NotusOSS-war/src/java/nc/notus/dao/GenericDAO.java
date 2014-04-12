package nc.notus.dao;

import java.io.Closeable;
import java.util.Map;

/**
 * This interface provides general DAO functions for all entities
 * @author Igor Litvinenko
 */
public interface GenericDAO<T> extends Closeable {
    /**
     * Method that returns the number of entries from a table that meet some
     * criteria (where clause params)
     *
     * @param params sql parameters
     * @return the number of records meeting the criteria
     */
    long countAll(Map<String, Object> params);

    void add(T t);

    void delete(Object id);

    T find(Object id);

    void update(T t);
}
