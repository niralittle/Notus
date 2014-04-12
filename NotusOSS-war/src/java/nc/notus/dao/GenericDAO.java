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
     * criteria (WHERE clause params)
     * @param params sql parameters (name-value) that are criteria for SELECT
     * @return the number of records meeting the criteria
     */
    long countAll(Map<String, Object> params);

    /**
     * Method creates new instance of entity in DB
     * @param t entity to add to DB
     */
    void add(T t);

    /**
     * Method deletes instance of entity from DB by given primary key
     * @param id id of entity instance
     */
    void delete(Object id);

    /**
     * Method finds instance of entity in DB by given primary key.
     * It invocates entity's setters to fill instance of entity with data
     * retrieved from DB. It is assumed that types of fields in DB are
     * equal to that in class. So are the names of setters.
     * @param id id of entity instance
     * @return instance of entity found
     */
    T find(Object id);

    /**
     * Method substitutes instance of entity in DB with one given.
     * It invocates <code>getId()</code> method of instance to get primary key
     * @param t instance of entity to update in DB
     */
    void update(T t);
}
