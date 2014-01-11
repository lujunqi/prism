package com.prism.dbutil;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface XStatement {
	/**
	 * @param key
	 *            String�� �����KEYֵ
	 * @param password
	 *            Object�� ����ӳ�����
	 * @return Object�� ͬmap��put����
	 */
	public Object put(String key, Object obj);

	/**
	 * @param key
	 *            String�� �����KEYֵ
	 * @param password
	 *            Object�� ����ӳ�����
	 * @return Object�� ͬmap��put����
	 */
	public Object put(String key, int obj);

	/**
	 * @param key
	 *            String�� �����KEYֵ
	 * @param password
	 *            long�� ����ӳ�����
	 * @return Object�� ͬmap��put����
	 */
	public Object put(String key, long obj);

	/**
	 * �����������޸ġ�ɾ������
	 * 
	 * @param sql
	 *            String�� �����ģ��SQL���
	 * @return Object�� Ӱ���¼��
	 */
	public Object executeUpdate(String sql) throws SQLException;

	/**
	 * ���ڴ洢���̲���
	 * 
	 * @param sql
	 *            String�� �����ģ��SQL���
	 * @return MAP�� ���ش洢������OUT������
	 */
	
	public Map<String,Object> executeCall(String sql) throws SQLException;

	/**
	 * ���ڿɲ�ѯ�����¼��ʱ�Ĳ�ѯ������ͨ��maxinum����
	 * 
	 * @param sql
	 *            String�� �����ģ��SQL���
	 * @param maxinum
	 *            int�� �������Ʒ�������¼��
	 * @return ArrayList�� ����ArrayList���͵ļ�¼�������м�¼ΪMAP��ʾ keyΪ�ֶ�������д��
	 */
	public List<Map<String,Object>> getListColValue(String sql) throws SQLException;

	/**
	 * ���ڿɲ�ѯ��С��¼��ʱ�Ĳ�ѯ����
	 * 
	 * @param sql
	 *            String�� �����ģ��SQL���
	 * @return ArrayList�� ����ArrayList���͵ļ�¼�������м�¼ΪMAP��ʾ keyΪ�ֶ�������д��
	 */
	public List<Map<String,Object>> getListColValue(String sql, int maximum) throws SQLException;

}
