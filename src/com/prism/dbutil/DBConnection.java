package com.prism.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.prism.exception.DAOException;

public class DBConnection {
	private Connection CONN = null;
	private String JNDI = null;
	private Map<String, String> JDBC = new HashMap<String, String>();

	@SuppressWarnings("finally")
	public Connection getConnection() {
		try {
			if (JNDI != null) {
				Context ctx = new InitialContext();
				DataSource ds = (DataSource) ctx.lookup(JNDI);
				CONN = ds.getConnection();
			} else {
				System.out.println(JDBC);
				if (!JDBC.isEmpty()) {
					Class.forName((String) JDBC.get("driverClassName"));
					CONN = DriverManager.getConnection(
							(String) JDBC.get("url"),
							(String) JDBC.get("username"),
							(String) JDBC.get("password"));
				}
			}
		} catch (NamingException e) {
			e.printStackTrace();
			throw new DAOException(e);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		} finally {
			return CONN;
		}
	}

	public String getJNDI() {
		return JNDI;
	}

	public void setJNDI(String jNDI) {
		JNDI = jNDI;
	}
	public Map<String,String> getJDBC() {
		return JDBC;
	}

	public void setJDBC(Map<String,String> JDBC) {
		this.JDBC = JDBC;
	}

	public void closeConnection() {
		try {
			CONN.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
