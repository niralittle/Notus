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
 * Implementation of generic DAO interface
 * @author Igor Litvinenko
 */
public abstract class GenericDAOImpl<T> implements GenericDAO<T> {

    private Class<T> type;
    protected DBManager dbManager;

    public GenericDAOImpl() {
        dbManager = new DBManager();
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }

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

    @Override
    public void close() {
        dbManager.close();
    }
}




