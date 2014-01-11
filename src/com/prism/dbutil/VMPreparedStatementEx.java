package com.prism.dbutil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.prism.dbutil.bean.ExMatcher;
import com.prism.dbutil.bean.Reflect;
import com.prism.dbutil.bean.ReflectEx;
import com.prism.dbutil.bean.VMReflect;

/**
 * @author Lost.Fly
 * ģ��SQL����������� :{classname.method<dbtype>} 
 *                        ֧���࣬
 *                        ��ķ����Լ��������ķ��� 
 *                        DBTYPE Ϊ����ӳ����������java.sql.Types�ĳ�������Ӧ
 *            ������� ^{mapname<dbtype>}
 *                        mapnameΪMAP��keyֵ
 *                        DBTYPE Ϊ����ӳ����������java.sql.Types�ĳ�������Ӧ
 * δʵ�ֵģ�1��date���Ͳ�������oracle�汾�ģ�jdbc���е�����
 *         2��ģ�������ʱ��ʵ������/���������
 *         3��ģ���й��ڸ��ӵ��ദ��ʵ�� �磺class.method.method
 */
public class VMPreparedStatementEx implements XStatement{
	private Connection conn = null;
	private Map<String,Object> map = new HashMap<String,Object>();
	private PreparedStatement cmd=null;
	private List<String> colsname = new ArrayList<String>();
	public VMPreparedStatementEx(Connection conn){
		this.conn = conn;
	}
	/**
	 * @param key String�� 
	 * @param password Object�� ����ӳ�����
	 * @return Object�� ͬmap��put����
	 */
	public Object put(String key, Object obj){
		return map.put(key,obj);
	}
	/**
	 * @param key String��
	 * @param password int�� ����ӳ�����
	 * @return Object�� ͬmap��put����
	 */
	public Object put(String key, int obj){
		return map.put(key,Integer.valueOf(obj+""));
	}
	
	/**
	 * @param key String��
	 * @param password long�� ����ӳ�����
	 * @return Object�� ͬmap��put����
	 */
	public Object put(String key,long obj){
		return map.put(key,Long.valueOf(obj+""));
	}
	/**
	 * @param key Map�� 
	 */
	public void putAll(Map<String,Object> map){
		this.map.putAll(map);
	}
	
	/**�����������޸ġ�ɾ������
	 * @param sql String�� �����ģ��SQL���
	 * @return Object�� Ӱ���¼��
	 */
	public Object executeUpdate(String sql)throws SQLException{		
		PreparedStatement cmd = conn.prepareStatement(preparedSql(sql));
		
		List<String> list = getParam(sql);
		for(int i=0;i<list.size();i++){
			String param = (String)list.get(i);
			VMReflect vm = new VMReflect();
			Reflect rf = new Reflect(param);
			int type = rf.getType();
			Object val = vm.put(param,map.get(rf.getClassName()));
			if(type==1111){
				cmd.setObject(i+1,val);
			}else{
				cmd.setObject(i+1,val,type);
			}
		}
		Integer reval = new Integer(cmd.executeUpdate());
		cmd.close();
		return reval;
	}
	/**���ڴ洢���̲���
	 * @param sql String�� �����ģ��SQL���
	 * @return MAP�� ���ش洢������OUT������
	 */
	public Map<String,Object> executeCall(String sql)throws SQLException{
		Map<String,Object> tmap = new HashMap<String,Object>();
		CallableStatement cmd = conn.prepareCall(preparedSql(sql));
		List<String> list = getParam(sql);
		for(int i=0;i<list.size();i++){
			String param = (String)list.get(i);
			VMReflect vm = new VMReflect();
			Reflect rf = new Reflect(param);
			int type = rf.getType();
			int direct = rf.getDirect();
			if(direct==1){
				String classname = rf.getClassName();
				cmd.registerOutParameter(i+1,type);
				tmap.put(classname,(i+1)+"");
			}else if(direct==0){
				Object val = vm.put(param,map.get(rf.getClassName()));
				cmd.setObject(i+1,val,type);
			}
		}
		cmd.execute();
		Iterator<String> it = tmap.keySet().iterator();
		while(it.hasNext()){
			String key = (String)it.next();
			int paramindex = Integer.parseInt((String)tmap.get(key));
			Object val = cmd.getObject(paramindex);
			tmap.put(key,val);
		}
		cmd.close();
		return tmap;
	}
	/**���ڿɲ�ѯ�����¼��ʱ�Ĳ�ѯ������ͨ��maxinum����
	 * @param sql String�� �����ģ��SQL���
	 * @param maxinum int�� �������Ʒ�������¼�� -1Ϊ������
	 * @return ArrayList�� ����ArrayList���͵ļ�¼�������м�¼ΪMAP��ʾ keyΪ�ֶ�������д��
	 */
	public List<Map<String,Object>> getListColValue(String sql,int maxinum)throws SQLException{
		ResultSet rs = getResultSet(sql);
		List<Map<String,Object>> relist = getListFormRs(rs,maxinum);
		rs.close();
		cmd.close();
		return relist;
	}
	public List<String> getColsName(){
		return colsname;
	}
	/**���ڿɲ�ѯ�����¼��ʱ�Ĳ�ѯ����
	 * @param sql String�� �����ģ��SQL���
	 * @return ResultSet�� ����ResultSet���͵ļ�¼����
	 */
	public ResultSet getResultSet(String sql)throws SQLException{
		cmd = conn.prepareStatement(preparedSql(sql));
		List<String> list = getParam(sql);
		for(int i=0;i<list.size();i++){
			String param = (String)list.get(i);
			VMReflect vm = new VMReflect();
			ReflectEx rf = new ReflectEx(param);
			int type = rf.getType();
			Object val = vm.put(param,map.get(rf.getClassName()));
			if(type==1111){
				cmd.setObject(i+1,val);
			}else{
				cmd.setObject(i+1,val,type);
			}
		}
		ResultSet rs = cmd.executeQuery();
		return rs;
	}
	/**���ڿɲ�ѯ��С��¼��ʱ�Ĳ�ѯ����
	 * @param sql String�� �����ģ��SQL���
	 * @return ArrayList�� ����ArrayList���͵ļ�¼�������м�¼ΪMAP��ʾ keyΪ�ֶ�������д��
	 */
	public List<Map<String,Object>> getListColValue(String sql)throws SQLException{
		return getListColValue(sql,-1);
	}
	public List<Map<String,Object>> getListFormRs(ResultSet rs,int maxinum)throws SQLException{
		List<Map<String,Object>> relist = new ArrayList<Map<String,Object>>();
		ResultSetMetaData rsmd=rs.getMetaData(); 
		int step = 0;
		while(rs.next()){
			step++;
			if(step>maxinum && maxinum!=-1){
				break;
			}
			Map<String,Object> remap = new HashMap<String,Object>();
			for(int i=1;i<=rsmd.getColumnCount();i++){
				String name = rsmd.getColumnName(i);
				colsname.add(name);
				Object value = rs.getObject(i);
				remap.put(name.toUpperCase(),value);
			}
			relist.add(remap);
		}
		return relist;
	}
	//SQL Ԥ���� 
	private String preparedSql(String sql){
		ExMatcher ex = new ExMatcher();
		String regex = "s/[\\s(\\:,\\^)]\\{([\\s(\\w,.,\\(,\\),#,<,>)]+)\\}/?/";
		return ex.perl(sql,regex);
	}
	private List<String> getParam(String sql){
		ExMatcher ex = new ExMatcher();
		String regex = "[\\s(\\:,\\^)]\\{([\\s(\\w,.,\\(,\\),#,<,>)]+)\\}";
		return ex.regexs(sql,regex,0);
	}
}
