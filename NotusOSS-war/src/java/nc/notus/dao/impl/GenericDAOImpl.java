package nc.notus.dao.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.HashMap;

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

    public GenericDAOImpl(DBManager dbManager) {
        this.dbManager = dbManager;
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }

    /**
     * Method that returns the number of entries from a table that meet some
     * criteria (WHERE clause params)
     * @param params sql parameters (name-value) that are criteria for SELECT
     * @return the number of records meeting the criteria
     */
    @Override
    public long countAll(Map<String, Object> params) {
        // form SQL query
        String queryString = "SELECT COUNT(*) FROM " + type.getSimpleName();
        if (params != null && !params.isEmpty()) {
            queryString += " WHERE ";
            for (Object value : params.values()) {
                if (value != null) {
                    queryString += "? = ?";
                } else {
                    queryString += "? IS NULL";
                }
                queryString += " AND ";
            }
            queryString = queryString.substring(0, queryString.length() - 5); // delete last " AND "
        }

        Statement statement = dbManager.prepareStatement(queryString);
        // fill in statement
        int paramIndex = 1;
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            statement.setString(paramIndex++, entry.getKey());
            if (entry.getValue() != null) {
                statement.setObject(paramIndex++, entry.getValue());
            }
        }

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
            if (value != null) {
                queryString += "?";
            } else {
                queryString += "NULL";
            }
            queryString += ", ";
        }
        queryString = queryString.substring(0, queryString.length() - 2); // trim last ", "
        queryString += ")";

        Statement statement = dbManager.prepareStatement(queryString);
//        throw new DAOException(queryString);
        // fill in statement
        int paramIndex = 1;
        for (Object value : fields.values()) {
            if (value != null) {
                statement.setObject(paramIndex++, value);
            }
        }
        statement.executeUpdate();
        statement.close();
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
            statement.close();
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
                "SELECT * FROM " + type.getSimpleName() + " WHERE id = ?";
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

        // form SQL query
        String queryString = "UPDATE " + type.getSimpleName() + " SET ";
        Map<String, Object> fieldsList = getFieldsList(t);
        for (Object value : fieldsList.values()) {
            if (value != null) {
                queryString += "? = ?";
            } else {
                queryString += "? = NULL";
            }
            queryString += ", ";
        }
        queryString = queryString.substring(0, queryString.length() - 2); // delete last ", "
        queryString += " WHERE id = ?";

        Statement statement = dbManager.prepareStatement(queryString);
        // fill in statement
        int paramIndex = 1;
        for (Map.Entry<String, Object> entry : fieldsList.entrySet()) {
            statement.setString(paramIndex++, entry.getKey());
            if(entry.getValue() != null) {
                statement.setObject(paramIndex++, entry.getValue());
            }
        }
        statement.setObject(paramIndex, id);
        statement.executeUpdate();
        statement.close();
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
}




