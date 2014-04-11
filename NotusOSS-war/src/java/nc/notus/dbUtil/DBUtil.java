package nc.notus.dbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBUtil {

	private DataSource dataSource;
	private Connection con;
	private Statement stmt;
	private PreparedStatement pstmt;

	public DBUtil() {
		try {
			InitialContext initContext = new InitialContext();
			dataSource = (DataSource) initContext.lookup("jdbc/_NOTUS");
		} catch (NamingException exc) {
			throw new DBUtilException("Cannot retrieve jdbc/_NOTUS", exc);
		}
	}

	public void execute(String query) {
		if (query == null) {
			throw new NullPointerException("Null reference query.");
		}
		try {
			getConnection();
			prepareStatement();
			stmt.execute(query);
		} catch (SQLException exc) {
			throw new DBUtilException("Can't execute the query.", exc);
		} finally {
			try {
				closeResources();
			} catch (SQLException e) {
				throw new DBUtilException("Can't close resources.", e);
			}
		}
	}
	
	public ResultSet executeQuery(String query) {
		if (query == null) {
			throw new NullPointerException("Null reference query.");
		}
		try {
			getConnection();
			preparePreparedStatement(query);
			
			return pstmt.executeQuery();
		} catch (SQLException exc) {
			throw new DBUtilException("Can't execute the query.", exc);
		} finally {
			try {
				closeResources();
			} catch (SQLException e) {
				throw new DBUtilException("Can't close resources.", e);
			}
		}
	}

	private PreparedStatement preparePreparedStatement(String query) {
		try {
			pstmt = con.prepareStatement(query,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			return pstmt;
		} catch (SQLException e) {
			throw new DBUtilException();
		}
	}

	private Statement prepareStatement() throws SQLException {
		try {
			stmt = con.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			return stmt;
		} catch (SQLException e) {
			throw new SQLException(e);
		}
	}

	private Connection getConnection() throws SQLException {
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			return con;
		} catch (SQLException exc) {
			throw new SQLException("Cannot obtain connection", exc);
		}
	}

	private void closeResources() throws SQLException {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
			stmt.close();
			con.close();
		} catch (SQLException e) {
			throw new SQLException(e);
		}
	}

}
