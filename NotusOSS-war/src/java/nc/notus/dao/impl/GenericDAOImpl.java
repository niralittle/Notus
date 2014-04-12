package nc.notus.dao.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.sql.DataSource;
import nc.notus.dao.GenericDAO;
import javax.naming.InitialContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import nc.notus.dao.DAOException;

/**
 * Implementation of generic DAO interface
 * @author Igor Litvinenko
 */
public abstract class GenericDAOImpl<T> implements GenericDAO<T> {

    private DataSource dataSource;
    private Class<T> type;

    public GenericDAOImpl() throws DAOException {
        try {
            InitialContext initContext = new InitialContext();
            dataSource = (DataSource) initContext.lookup("jdbc/_NOTUS");
        } catch (NamingException exc) {
            throw new DAOException(
                    "Cannot retrieve jdbc/_NOTUS", exc);
        }

        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }

    @Override
    public long countAll(Map<String, Object> params) throws DAOException {
        Connection conn = null;
        try {
            conn = getConnection();

            String queryString = "SELECT COUNT(*) FROM " + type.getSimpleName();
            String queryClauses = getQueryClauses(params, "AND");
            if(!queryClauses.isEmpty()) {
                queryString += " WHERE " + queryClauses;
            }
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(queryString);
            rs.next();
            return rs.getLong(1);
        } catch (SQLException exc) {
            throw new DAOException("SQL Exception.", exc);
        } finally {
            if (conn != null) {
                releaseConnection(conn);
            }
        }
    }

    @Override
    public void add(T t) throws DAOException {
        Connection conn = null;
        try {
            conn = getConnection();

            String queryString = "INSERT INTO " + type.getSimpleName() + "(";
            Map<String, Object> fields = getFieldsList(t);
            for(String fieldName : fields.keySet()) {
                queryString += fieldName + ", ";
            }
            queryString = queryString.substring(0, queryString.length() - 2); // trim last ", "
            queryString += ") VALUES (";
            for(Object value : fields.values()) {
                if(value instanceof Number) {
                    queryString += value;
                } else if(value == null) {
                    queryString += "NULL";
                } else {
                    // string
                    queryString += "'" + value + "'";
                }
                queryString += ", ";
            }
            queryString = queryString.substring(0, queryString.length() - 2); // trim last ", "
            queryString += ")";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(queryString);
            conn.commit();
        } catch (SQLException exc) {
            throw new DAOException("SQL Exception.", exc);
        } finally {
            if (conn != null) {
                releaseConnection(conn);
            }
        }
    }

    @Override
    public void delete(Object id) throws DAOException {
        Connection conn = null;
        try {
            conn = getConnection();

            if(id instanceof Number) {
                String queryString =
                    "DELETE FROM " + type.getSimpleName() + " WHERE id = " + id;
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(queryString);
                conn.commit();
            } else {
                throw new DAOException("Wrong primary key type");
            }
        } catch (SQLException exc) {
            throw new DAOException("SQL Exception.", exc);
        } finally {
            if (conn != null) {
                releaseConnection(conn);
            }
        }
    }

    public T find(Object id) throws DAOException {
        Connection conn = null;
        try {
            conn = getConnection();

            if(!(id instanceof Number)) {
                throw new DAOException("Wrong primary key type");
            }
            String queryString =
                "SELECT * FROM " + type.getSimpleName() + " WHERE id = " + id;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(queryString);
            if(!rs.next()) {
                throw new DAOException("No record found for given primary key");
            }

            T t = type.newInstance();
            Map<String, Object> fields = getFieldsList(t);
            for(String fieldName : fields.keySet()) {
                String methodName = "set" +
                    fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                Field field = type.getDeclaredField(fieldName);
                Method method = type.getDeclaredMethod(methodName, field.getType());
                if(field.getType() == int.class) {
                    method.invoke(t, rs.getInt(fieldName));
                } else if(field.getType() == long.class){
                    method.invoke(t, rs.getLong(fieldName));
                } else if(field.getType() == boolean.class){
                    method.invoke(t, rs.getBoolean(fieldName));
                } else if(field.getType() == String.class) {
                    method.invoke(t, rs.getString(fieldName));
                } else {
                    throw new DAOException("Incompatible field type");
                }
            }

            return t;
        } catch (SQLException exc) {
            throw new DAOException("SQL Exception.", exc);
        } catch(DAOException exc) {
            throw exc;
        } catch (Exception exc) {
            throw new DAOException("Exception.", exc);
        } finally {
            if (conn != null) {
                releaseConnection(conn);
            }
        }
    }

    @Override
    public void update(T t) throws DAOException {
        Connection conn = null;
        try {
            conn = getConnection();

            Object id = type.getMethod("getId").invoke(t);
            if(!(id instanceof Number)) {
                throw new DAOException("Wrong primary key type");
            }
            String queryString = "UPDATE " + type.getSimpleName() + " SET ";

            Map<String, Object> fieldsList = getFieldsList(t);
            String queryClauses = getQueryClauses(fieldsList, ",");
            queryString += queryClauses;

            queryString += " WHERE id = " + id;

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(queryString);
            conn.commit();
        } catch (SQLException exc) {
            throw new DAOException("SQL Exception.", exc);
        } catch (Exception exc) {
            throw new DAOException("Exception.", exc);
        } finally {
            if (conn != null) {
                releaseConnection(conn);
            }
        }
    }

    private Map<String, Object> getFieldsList(T t) throws DAOException {
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] fields = type.getDeclaredFields();
        for(Field f : fields) {
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




