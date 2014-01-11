/**
 * service基础方法
 */
package com.prism.service.impl.basic;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.prism.common.JsonUtil;
import com.prism.common.VMRequest;
import com.prism.dbutil.DBCommand;
import com.prism.exception.BMOException;
import com.prism.exception.DAOException;
import com.prism.service.Service;

public class BaseService implements Service {
	protected Map<String, Object> reqMap;
	private VelocityContext vc = new VelocityContext();
	protected Object dbConn;
	private HttpServletRequest req;
	private HttpServletResponse res;

	@SuppressWarnings("unchecked")
	public void service() throws ServletException, IOException {
		reqMap = (Map<String, Object>) req.getAttribute("reqMap");
		dbConn = req.getAttribute("DBConnection");
		vc = new VelocityContext();
		// cookie参数
		Cookie[] cookies = req.getCookies();
		Map<String, Object> cookieMap = new HashMap<String, Object>();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				cookieMap.put(cookie.getName(), cookie.getValue());
			}
		}
		reqMap.put("cookie", cookieMap);
		// session参数
		HttpSession session = req.getSession();
		Map<String, Object> sessionMap = new HashMap<String, Object>();
		Enumeration<String> en2 = session.getAttributeNames();
		while (en2.hasMoreElements()) {
			String name = (String) en2.nextElement();
			Object value = session.getAttribute(name);
			sessionMap.put(name, value);
		}
		reqMap.put("session", sessionMap);
		// json参数 单数据为String，多数据为List<Map>
		if (sourceMap.containsKey("JSON")) {
			JsonUtil ju = new JsonUtil();
			Object json = sourceMap.get("JSON");

			if (json instanceof String) {
				if (reqMap.containsKey(json)) {
					Object val = ju.toObject((String) reqMap.get(json));
					vc.put((String) json, val);
					reqMap.put((String) json, val);
				}

			} else if (json instanceof List) {
				if (reqMap.containsKey(json)) {
					List<String> list = (List<String>) json;
					for (int i = 0; i < list.size(); i++) {
						String key = (String) list.get(i);
						vc.put(key, ju.toObject((String) reqMap.get(json)));
					}
				}
			}
		}
	}

	// SELECT
	protected List<Map<String, Object>> selectResult(String key)
			throws BMOException {
		String sql = (String) sourceMap.get(key);
		DBCommand cmd = new DBCommand(dbConn);
		try {
			if (reqMap.containsKey("@minnum") && reqMap.containsKey("@maxnum")) {
				int minnum = Integer.parseInt(reqMap.get("@minnum") + "");
				int maxnum = Integer.parseInt(reqMap.get("@maxnum") + "");

				return cmd.executeSelect(sql, reqMap, minnum, maxnum);
			} else {
				return cmd.executeSelect(sql, reqMap);
			}
		} catch (DAOException e) {
			e.printStackTrace();
			throw new BMOException(e);
		}
	}

	protected List<Map<String, Object>> selectResult(String key, int minnum,
			int maxnum) throws BMOException {
		String sql = (String) sourceMap.get(key);
		DBCommand cmd = new DBCommand(dbConn);
		try {
			return cmd.executeSelect(sql, reqMap, minnum, maxnum);
		} catch (DAOException e) {
			throw new BMOException(e);
		}
	}

	protected Map<String, Object> callResult(String key) throws BMOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.putAll(reqMap);
		String sql = (String) sourceMap.get(key);
		DBCommand cmd = new DBCommand(dbConn);
		try {
			return cmd.executeCall(sql, map);
		} catch (DAOException e) {
			throw new BMOException(e);
		}
	}

	protected Object updateResult(String key) throws BMOException {
		String sql = (String) sourceMap.get(key);
		DBCommand cmd = new DBCommand(dbConn);
		try {
			return cmd.executeUpdate(sql, reqMap);
		} catch (DAOException e) {
			throw new BMOException(e);
		}
	}

	protected String convertSql() {
		return convertSql("SQL", "SQL");
	}

	protected String convertSql(String oldKey, String newKey) {
		try {
			String sql = (String) sourceMap.get(oldKey);
			VMRequest v = new VMRequest();
			v.setReqMap(reqMap);
			vc.put("v", v);
			String newSql = getResultfromContent(sql);
			sourceMap.put(newKey, newSql);
			return newSql;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	protected Map<String, Object> sourceMap = new HashMap<String, Object>();

	public void setSourceMap(Map<String, Object> sourceMap) {
		this.sourceMap = sourceMap;
	}

	public Map<String, Object> getSourceMap() {
		return sourceMap;
	}

	private String getResultfromContent(String s) throws Exception {
		StringWriter stringwriter;
		Velocity.init();
		stringwriter = new StringWriter();
		Velocity.evaluate(vc, stringwriter, "mystring", s);
		return stringwriter.toString();
	}

	@Override
	public HttpServletRequest getRequest() {
		return this.req;
	}

	@Override
	public void setRequest(HttpServletRequest req) {
		this.req = req;
	}

	@Override
	public HttpServletResponse getResponse() {
		return this.res;
	}

	@Override
	public void setResponse(HttpServletResponse res) {
		this.res = res;
	}

}
