package com.prism.dbutil;

import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * @author Lost.Fly 优点：SQL模板采取的是velocity模板库规则，可以建立复杂模板 缺点：放弃了预编译功能，数据库操作的共享性不强
 */
public class VMStatement implements XStatement {
	private VelocityContext vc;
	private Connection conn = null;

	public VMStatement(Connection conn) {
		vc = new VelocityContext();
		this.conn = conn;
	}

	public Object put(String key, Object obj) {
		return vc.put(key, obj);
	}

	public Object put(String key, int obj) {
		return vc.put(key, Integer.valueOf(obj + ""));
	}

	public Object put(String key, long obj) {
		return vc.put(key, Long.valueOf(obj + ""));
	}

	public Object executeUpdate(String sql) throws SQLException {
		PreparedStatement cmd = conn
				.prepareStatement(getResultfromContent(sql));
		Integer reval = new Integer(cmd.executeUpdate());
		cmd.close();
		return reval;
	}

	/**
	 * JDBC的Statement方法不提供存储过程处理方法固该方法不作复杂处理
	 */
	public Map<String,Object> executeCall(String sql) throws SQLException {
		CallableStatement cmd = conn.prepareCall(getResultfromContent(sql));
		cmd.close();
		return null;
	}

	public List<Map<String,Object>> getListColValue(String sql) throws SQLException {
		PreparedStatement cmd = conn
				.prepareStatement(getResultfromContent(sql));
		ResultSet rs = cmd.executeQuery();
		List<Map<String,Object>> relist = getListFormRs(rs, -1);
		rs.close();
		cmd.close();
		return relist;
	}

	public List<Map<String,Object>> getListColValue(String sql, int maximum) throws SQLException {
		PreparedStatement cmd = conn
				.prepareStatement(getResultfromContent(sql));
		ResultSet rs = cmd.executeQuery();
		List<Map<String,Object>> relist = getListFormRs(rs, maximum);
		rs.close();
		cmd.close();
		return relist;
	}

	private List<Map<String,Object>> getListFormRs(ResultSet rs, int maxinum)
			throws SQLException {
		List<Map<String,Object>> relist = new ArrayList<Map<String,Object>>();
		ResultSetMetaData rsmd = rs.getMetaData();
		int step = 0;
		while (rs.next()) {
			step++;
			Map<String,Object> remap = new HashMap<String,Object>();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				String name = rsmd.getColumnName(i);
				Object value = rs.getObject(i);
				remap.put(name.toUpperCase(), value);
			}
			relist.add(remap);
			if (step > maxinum && maxinum != -1) {
				break;
			}
		}
		return relist;
	}

	private String getResultfromContent(String s) {
		try {
			StringWriter stringwriter;
			Velocity.init();
			stringwriter = new StringWriter();
			Velocity.evaluate(vc, stringwriter, "mystring", s);
			return stringwriter.toString();
		} catch (Exception e) {
			System.out.println("VelTemplate.getVelstr=" + e);
			return null;
		}
	}
}
