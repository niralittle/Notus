package nc.notus.dao.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import nc.notus.dao.GenericDAO;
import nc.notus.dao.DAOException;
import nc.notus.dbmanager.DBManager;
import nc.notus.dbmanager.ResultIterator;
import nc.notus.dbmanager.Statement;

/**
 * Implementation of generic DAO interface. Only CRUD operations are provided by
 * this class. Use <code>close()</code> method after usage of this class to
 * properly release resources.
 *
 * @author Igor Litvinenko
 */
public abstract class GenericDAOImpl<T> implements GenericDAO<T> {

    private Class<T> type;          // here all type information about the given type is stored
    protected DBManager dbManager;  // instance of DBManager that incapsulates connection to DB

    public GenericDAOImpl() {
        dbManager = new DBManager();
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }

    /**
     * Method that returns the number of entries from a table that meet some
     * criteria (where clause params)
     *
     * @param params sql parameters(could be <code>null</code>)
     * @return the number of records meeting the criteria
     */
    @Override
    public long countAll(Map<String, Object> params) {
        String queryString = "SELECT COUNT(*) FROM " + type.getSimpleName();
        String queryClauses = getQueryClauses(params, "AND");
        if (!queryClauses.isEmpty()) {
            queryString += " WHERE " + queryClauses;
        }
        Statement statement = dbManager.prepareStatement(queryString);
        ResultIterator ri = statement.executeQuery();
        ri.next();
        return ri.getLong(1);
    }

    /**
     * Method creates new instance of entity in DB
     * @param t entity to add to DB
     */
    @Override
    public void add(T t) {
        String queryString = "INSERT INTO " + type.getSimpleName() + "(";
        Map<String, Object> fields = getFieldsList(t);
        for (String fieldName : fields.keySet()) {
            queryString += fieldName + ", ";
        }
        queryString = queryString.substring(0, queryString.length() - 2); // trim last ", "
        queryString += ") VALUES (";
        for (Object value : fields.values()) {
            if (value instanceof Number) {
                queryString += value;
            } else if (value == null) {
                queryString += "NULL";
            } else {
                // string
                queryString += "'" + value + "'";
            }
            queryString += ", ";
        }
        queryString = queryString.substring(0, queryString.length() - 2); // trim last ", "
        queryString += ")";
        Statement statement = dbManager.prepareStatement(queryString);
        statement.executeUpdate();
    }

    /**
     * Method deletes instance of entity from DB by given primary key
     * @param id id of entity instance
     */
    @Override
    public void delete(Object id) {
        if (id instanceof Number) {
            String queryString =
                    "DELETE FROM " + type.getSimpleName() + " WHERE id = ?";
            Statement statement = dbManager.prepareStatement(queryString);
            statement.setObject(1, id);
            statement.executeUpdate();
        } else {
            throw new DAOException("Wrong primary key type");
        }
    }

    /**
     * Method finds instance of entity in DB by given primary key.
     * It invocates entity's setters to fill instance of entity with data
     * retrieved from DB. It is assumed that types of fields in DB are
     * equal to that in class. So are the names of setters.
     * @param id id of entity instance
     * @return instance of entity found
     */
    public T find(Object id) {
        if (!(id instanceof Number)) {
            throw new DAOException("Wrong primary key type");
        }

        String queryString =
                "SELECT * FROM " + type.getSimpleName() + "  WHERE id = ?";
        Statement statement = dbManager.prepareStatement(queryString);
        statement.setObject(1, id);
        ResultIterator ri = statement.executeQuery();
        if (!ri.next()) {
            throw new DAOException("No record found for given primary key");
        }

        try {
            T t = type.newInstance();
            Map<String, Object> fields = getFieldsList(t);
            for (String fieldName : fields.keySet()) {
                String methodName = "set" +
                        fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                Field field = type.getDeclaredField(fieldName);
                Method method = type.getDeclaredMethod(methodName, field.getType());
                if (field.getType() == int.class) {
                    method.invoke(t, ri.getInt(fieldName));
                } else if (field.getType() == long.class) {
                    method.invoke(t, ri.getLong(fieldName));
                } else if (field.getType() == boolean.class) {
                    method.invoke(t, ri.getBoolean(fieldName));
                } else if (field.getType() == String.class) {
                    method.invoke(t, ri.getString(fieldName));
                } else {
                    throw new DAOException("Incompatible field type");
                }
            }
            return t;
        } catch (InstantiationException exc) {
            throw new DAOException("Cannot create entity instance", exc);
        } catch (Exception exc) {
            throw new DAOException("Cannot invoke setter", exc);
        }
    }

    /**
     * Method substitutes instance of entity in DB with one given.
     * It invocates <code>getId()</code> method of instance to get primary key
     * @param t instance of entity to update in DB
     */
    @Override
    public void update(T t) {
        Object id;
        try {
            id = type.getMethod("getId").invoke(t);
        } catch (Exception exc) {
            throw new DAOException("Cannot invoke getId() method", exc);
        }

        if (!(id instanceof Number)) {
            throw new DAOException("Wrong primary key type");
        }
        String queryString = "UPDATE " + type.getSimpleName() + " SET ";

        Map<String, Object> fieldsList = getFieldsList(t);
        String queryClauses = getQueryClauses(fieldsList, ",");
        queryString += queryClauses;

        queryString += " WHERE id = ?";

        Statement statement = dbManager.prepareStatement(queryString);
        statement.setObject(1, id);
        statement.executeUpdate();
    }

    /**
     * This method gets list of fields and their values of given entity.
     * It invocates getters of entity in order to retrieve values.
     * It is assumed that names of getters in class are corresponding to
     * the names of fields of that class.
     * @param t instance of entity to get fields from
     * @return map of field names and their corresponding values
     */
    private Map<String, Object> getFieldsList(T t) {
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] fields = type.getDeclaredFields();
        for (Field f : fields) {
            try {
                String fieldName = f.getName();
                String methodName = "get" +
                        fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                map.put(fieldName, type.getMethod(methodName).invoke(t));
            } catch (Exception exc) {
                throw new DAOException("Cannot obtain fields of entity", exc);
            }
        }
        return map;
    }

    /**
     * Mathod forms query clauses by putting proper equality operators and separate
     * those with given delimenter.
     * Output example: <code>id = 4, name = 'John', working is TRUE</code>
     * @param params map of param names and their values
     * @param delim delimiter that separates clauses
     * @return query clauses
     */
    private String getQueryClauses(final Map<String, Object> params, String delim) {
        final StringBuffer queryString = new StringBuffer();
        if ((params != null) && !params.isEmpty()) {
            for (final Iterator<Map.Entry<String, Object>> it = params.entrySet().iterator(); it.hasNext();) {
                final Map.Entry<String, Object> entry = it.next();
                if (entry.getValue() instanceof Boolean) {
                    queryString.append(entry.getKey()).append(" is ").append(entry.getValue()).append(" ");
                } else {
                    if (entry.getValue() instanceof Number) {
                        queryString.append(entry.getKey()).append(" = ").append(entry.getValue());
                    } else {
                        // string equality
                        queryString.append(entry.getKey()).append(" = '").append(entry.getValue()).append("'");
                    }
                }
                if (it.hasNext()) {
                    queryString.append(" " + delim + " ");
                }
            }
        }

        return queryString.toString();
    }

    /**
     * This method releases resources
     */
    @Override
    public void close() {
        dbManager.close();
    }
}




