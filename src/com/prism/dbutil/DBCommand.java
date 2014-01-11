package com.prism.dbutil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.prism.exception.DAOException;

public class DBCommand {
	private DBConnection dbconn = new DBConnection();
	public DBCommand(Object dbconn){
		this.dbconn = (DBConnection)dbconn;
	}
	public List<Map<String, Object>> executeSelect(String sql,
			Map<String, Object> map) throws DAOException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			VMPreparedStatement cmd = new VMPreparedStatement(
					dbconn.getConnection());
			cmd.putAll(map);
			list = cmd.getListColValue(sql);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			dbconn.closeConnection();
		}
		return list;
	}

	public List<Map<String, Object>> executeSelect(String sql,
			Map<String, Object> map, int minnum, int maxnum)
			throws DAOException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			VMPreparedStatement cmd = new VMPreparedStatement(
					dbconn.getConnection());
			cmd.putAll(map);
			list = cmd.getListColValue(sql, minnum, maxnum);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			dbconn.closeConnection();
		}
		return list;
	}

	public Object executeUpdate(String sql, Map<String, Object> map)
			throws DAOException {
		Object result = new Object();
		try {
			VMPreparedStatement cmd = new VMPreparedStatement(
					dbconn.getConnection());
			cmd.putAll(map);
			result = cmd.executeUpdate(sql);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			dbconn.closeConnection();
		}
		return result;
	}

	public Map<String, Object> executeCall(String sql, Map<String, Object> map)
			throws DAOException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			VMPreparedStatement cmd = new VMPreparedStatement(
					dbconn.getConnection());
			cmd.putAll(map);
			result = cmd.executeCall(sql);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			dbconn.closeConnection();
		}
		return result;
	}
}
