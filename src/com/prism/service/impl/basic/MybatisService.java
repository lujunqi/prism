/**
 * MyBatis操作数据库
 */
package com.prism.service.impl.basic;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.prism.exception.BMOException;
import com.prism.service.Service;

public class MybatisService implements Service {
	protected Map<String, Object> reqMap = new HashMap<String, Object>();
	private SqlSession sqlSession;
	private HttpServletRequest req;
	private HttpServletResponse res;
	protected VelocityContext vc = new VelocityContext();

	@SuppressWarnings("unchecked")
	public void service() throws ServletException, IOException {
		reqMap = (Map<String, Object>) req.getAttribute("reqMap");
		sqlSession = (SqlSession) req.getAttribute("sqlSession");
	}

	// SELECT
	protected List<Object> selectResult(String key) throws BMOException {

		String sqlKey = (String) sourceMap.get(key);
		try {
			SqlSession sqlSession = getSession();
//			System.out.println(reqMap);
			if (reqMap.containsKey("@minnum") && reqMap.containsKey("@maxnum")) {
				int minnum = Integer.parseInt(reqMap.get("@minnum") + "");
				int maxnum = Integer.parseInt(reqMap.get("@maxnum") + "");
				List<Object> l = sqlSession.selectList(sqlKey, reqMap,
						new RowBounds(minnum, maxnum - minnum));
				sqlSession.close();
				return l;
			} else {
				List<Object> l = sqlSession.selectList(sqlKey, reqMap);
				sqlSession.close();
				return l;
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BMOException(e);
		}
	}

	// UPDATE
	protected int updateResult(String key) throws BMOException {
		try {
			SqlSession sqlSession = getSession();
			String sqlKey = (String) sourceMap.get(key);
			int result = sqlSession.update(sqlKey, reqMap);
			sqlSession.commit();
			sqlSession.close();
			return result;

		} catch (IOException e) {
			e.printStackTrace();
			throw new BMOException(e);
		}
	}

	// INSERT
	protected int insertResult(String key) throws BMOException {
		try {
			String sqlKey = (String) sourceMap.get(key);
			SqlSession sqlSession = getSession();
			int r = sqlSession.insert(sqlKey, reqMap);
			sqlSession.commit();
			sqlSession.close();
			return r;
		} catch (IOException e) {
			e.printStackTrace();
			throw new BMOException(e);
		}
	}

	// DELETE
	protected int deleteResult(String key) throws BMOException {
		try {
			SqlSession sqlSession = getSession();
			String sqlKey = (String) sourceMap.get(key);
			int result = sqlSession.delete(sqlKey, reqMap);
			sqlSession.commit();
			sqlSession.close();
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			throw new BMOException(e);
		}
	}

	// PROCEDURE
	protected Map<String, Object> callResult(String key) throws BMOException {
		try {
			String sqlKey = (String) sourceMap.get(key);
			Map<String, Object> map = new HashMap<String, Object>();
			map.putAll(reqMap);
			SqlSession sqlSession = getSession();
			sqlSession.selectOne(sqlKey, map);
			sqlSession.commit();
			sqlSession.close();
			return map;
		} catch (IOException e) {
			e.printStackTrace();
			throw new BMOException(e);
		}
	}

	protected String getResultfromContent(String s) throws Exception {
		s = (String) sourceMap.get(s);
		StringWriter stringwriter;
		Velocity.init();
		stringwriter = new StringWriter();
		Velocity.evaluate(vc, stringwriter, "mystring", s);
		return stringwriter.toString();
	}

	public static void main(String[] args) {
		MybatisService ms = new MybatisService();
		try {
			SqlSession sqlSession = ms.getSession();
			Object obj = sqlSession.selectList("sm_priSpec");
			System.out.println(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected SqlSession getSession() throws IOException {
		String resource = "SqlMapConfig.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		SqlSessionFactory factory = builder.build(reader);
		if (sqlSession != null) {
			return sqlSession;
		}
		SqlSession session = factory.openSession(true);
		return session;
	}

	protected Map<String, Object> sourceMap = new HashMap<String, Object>();

	public void setSourceMap(Map<String, Object> sourceMap) {
		this.sourceMap = sourceMap;
	}

	public Map<String, Object> getSourceMap() {
		return sourceMap;
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
