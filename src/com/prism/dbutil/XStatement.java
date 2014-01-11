package com.prism.dbutil;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface XStatement {
	/**
	 * @param key
	 *            String型 输入的KEY值
	 * @param password
	 *            Object型 输入映射对象
	 * @return Object型 同map的put方法
	 */
	public Object put(String key, Object obj);

	/**
	 * @param key
	 *            String型 输入的KEY值
	 * @param password
	 *            Object型 输入映射对象
	 * @return Object型 同map的put方法
	 */
	public Object put(String key, int obj);

	/**
	 * @param key
	 *            String型 输入的KEY值
	 * @param password
	 *            long型 输入映射对象
	 * @return Object型 同map的put方法
	 */
	public Object put(String key, long obj);

	/**
	 * 用于新增、修改、删除操作
	 * 
	 * @param sql
	 *            String型 输入的模板SQL语句
	 * @return Object型 影响记录数
	 */
	public Object executeUpdate(String sql) throws SQLException;

	/**
	 * 用于存储过程操作
	 * 
	 * @param sql
	 *            String型 输入的模板SQL语句
	 * @return MAP型 返回存储过程中OUT的数据
	 */
	
	public Map<String,Object> executeCall(String sql) throws SQLException;

	/**
	 * 用于可查询出大记录集时的查询操作，通过maxinum限制
	 * 
	 * @param sql
	 *            String型 输入的模板SQL语句
	 * @param maxinum
	 *            int型 输入限制返回最大记录数
	 * @return ArrayList型 返回ArrayList类型的记录集，单行记录为MAP表示 key为字段名（大写）
	 */
	public List<Map<String,Object>> getListColValue(String sql) throws SQLException;

	/**
	 * 用于可查询出小记录集时的查询操作
	 * 
	 * @param sql
	 *            String型 输入的模板SQL语句
	 * @return ArrayList型 返回ArrayList类型的记录集，单行记录为MAP表示 key为字段名（大写）
	 */
	public List<Map<String,Object>> getListColValue(String sql, int maximum) throws SQLException;

}
