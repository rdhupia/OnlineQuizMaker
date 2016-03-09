package onlineQuiz.connection;

import java.sql.*;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ConnectionPool {

	private static ConnectionPool pool = null;
	private static DataSource dataSource = null;

	/* mysql */
	private ConnectionPool(String db ) {     
		try {
			InitialContext ic = new InitialContext();
			if ("mysql".equalsIgnoreCase(db))
				dataSource = (DataSource) ic
						.lookup("java:/comp/env/jdbc/dps904_161a04");
			else
				dataSource = (DataSource) ic
						.lookup("java:/comp/env/jdbc/dps904_161a04");
		} catch (NamingException e) {
			System.out.println(e);
		}
	}

	/* mysql or oracle */
	public static synchronized ConnectionPool getInstance(String db ) {
		if (pool == null) {
			pool = new ConnectionPool(db);
		}
		return pool;
	}

	public Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

	public void freeConnection(Connection c) {
		try {
			c.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
}