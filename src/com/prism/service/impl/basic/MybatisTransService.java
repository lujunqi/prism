/**
 * mybatis 事务处理
 */
package com.prism.service.impl.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import com.prism.common.VMResponse;

public class MybatisTransService extends MybatisService {

	private Map<String, Object> result = new HashMap<String, Object>();

	public void service() throws ServletException, IOException {
		super.service();
		// PrintWriter out = getResponse().getWriter();
		SqlSession sqlSession = getSession();
		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		Transaction tran = null;
		PrintWriter out = getResponse().getWriter();
		try {
			sqlSession.getConnection().setAutoCommit(false);
			tran = transactionFactory
					.newTransaction(sqlSession.getConnection());
			vc.put("dao", sqlSession);
			vc.put("req", reqMap);
			result = new HashMap<String, Object>();
			String action = (String) reqMap.get("_action");
			if (sourceMap.containsKey("SQL")) {
				Object sql = sourceMap.get("SQL");
				if (sql instanceof List) {
					tranList(sql, sqlSession);
					reqMap.put(action, result);
					reqMap.put("this", result);
				} else if (sql instanceof String) {
					System.out.println(getResultfromContent("SQL"));
				}
				
				vc.put("this", reqMap);
				vc.put(action, reqMap);

			}
			// 视图模板
			if (sourceMap.containsKey("VIEW")) {
				VMResponse vm = new VMResponse();
				vm.setReqMap(reqMap);
				vc.put("v", vm);
				String content = getResultfromContent("VIEW");
				out.print(content);
			}

			// FORWARD 页面跳转
			if (sourceMap.containsKey("FORWARD")) {
				getRequest().getRequestDispatcher(
						(String) sourceMap.get("FORWARD")).forward(
						getRequest(), getResponse());
			}
			// REDIRECT 页面重定向
			if (sourceMap.containsKey("REDIRECT")) {
				getResponse().sendRedirect((String) sourceMap.get("REDIRECT"));
			}
			tran.commit();
			tran.close();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				tran.rollback();
				tran.close();
			} catch (SQLException e1) {
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				tran.rollback();
				tran.close();
			} catch (SQLException e1) {
			}
		}
	}

	private void tranList(Object sql, SqlSession sqlSession)
			throws SQLException {
		@SuppressWarnings("unchecked")
		List<String> list = (List<String>) sql;
		for (String s : list) {
			String[] keys = s.split(":");
			if (keys.length == 2) {
				String key1 = keys[1].replaceAll("\\.", "_");
				if ("UPDATE".equalsIgnoreCase(keys[0])) {
					Object obj = sqlSession.update(keys[1], reqMap);
					reqMap.put(key1, obj);
					result.put(key1, obj);
				} else if ("DELETE".equalsIgnoreCase(keys[0])) {
					Object obj = sqlSession.delete(keys[1], reqMap);
					reqMap.put(key1, obj);
					result.put(key1, obj);
				} else if ("INSERT".equalsIgnoreCase(keys[0])) {
					Object obj = sqlSession.insert(keys[1], reqMap);
					reqMap.put(key1, obj);
					result.put(key1, obj);
				} else if ("SELECT".equalsIgnoreCase(keys[0])) {
					Object obj = sqlSession.selectList(keys[1], reqMap);
					reqMap.put(key1, obj);
					result.put(key1, obj);
				} else if ("SELECTONE".equalsIgnoreCase(keys[0])) {
					Object obj = sqlSession.selectOne(keys[1], reqMap);
					reqMap.put(key1, obj);
					result.put(key1, obj);
				}
			} else {
				String key1 = s.replaceAll("\\.", "_");
				Object obj = sqlSession.update(keys[1], reqMap);
				reqMap.put(key1, obj);
				result.put(key1, obj);
			}
		}
	}
}
