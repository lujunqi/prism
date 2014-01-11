package com.prism.dbutil.bean;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SQLBuilder {
	private List<Object> cells = new ArrayList<Object>();
	private List<String> tables = new ArrayList<String>();
	private String restrict = "";
	private boolean istype = true;
	public void setTable(String tablename){
		tables.add(tablename);
	}
	public void isType(boolean istype){
		this.istype = istype;
	}
	public void setCell(String cellname,String celltype){
		Object[] cell = new Object[2];
		cell[0] = cellname;
		cell[1] = celltype;
		cells.add(cell);
	}
	public void setCell(String cellname){
		Object[] cell = new Object[2];
		cell[0] = cellname;
		cells.add(cell);
	}
	public void setRestrict(String restrict){
		this.restrict = restrict;
	}
	public String select(){
		String sql = "SELECT "+getCell()+" FROM "+getTable()+" "+restrict;
		cells.clear();
		tables.clear();
		return sql;
	}
	public String insert(){
		String sql = "INSERT INTO " + getTable() + " ("+getCell()+") VALUES ("+getCellValue()+")";
		cells.clear();
		tables.clear();
		return sql;
	}
	public String update(){
		String sql = "UPDATE "+getTable()+" SET "+getCell2()+" "+restrict;;
		cells.clear();
		tables.clear();
		return sql;
	}
	public String delete(){
		String sql = "DELETE FROM "+getTable()+" "+restrict;
		cells.clear();
		tables.clear();
		return sql;
	}
	private String getCell(){
		String reval=null;
		Iterator<Object> it = cells.iterator();
		while(it.hasNext()){
			Object[] vals = (Object[])it.next();
			String val = (String)vals[0];
			if(reval==null){
				reval = val;
			}else{
				reval = reval+","+val;
			}
		}
		return reval;
	}
	private String getCell2(){
		String reval=null;
		Iterator<Object> it = cells.iterator();
		while(it.hasNext()){
			Object[] vals = (Object[])it.next();
			String val = (String)vals[0];
			String type = (String)vals[1];
			if(reval==null){
				if(istype && type!=null){
					reval = val+"=${"+val+"<"+type+">}";
				}else{
					reval = val+"=${"+val+"}";
				}
			}else{
				if(istype && type!=null){
					reval = reval+","+val+"=${"+val+"<"+type+">}";
				}else{
					reval = reval+","+val+"=${"+val+"}";
				}
			}
		}
		return reval;
	}
	private String getCellValue(){
		String reval=null;
		Iterator<Object> it = cells.iterator();
		while(it.hasNext()){
			Object[] vals = (Object[])it.next();
			String val = (String)vals[0];
			String type = (String)vals[1];
			if(reval==null){
				if(istype && type!=null){
					reval = "${"+val+"<"+type+">}";
				}else{
					reval = "${"+val+"}";
				}
			}else{
				if(istype){
					reval = reval+","+"${"+val+"<"+type+">}";
				}else{
					reval = reval+","+"${"+val+"}";
				}
			}
		}
		return reval;
	}
	private String getTable(){
		String reval = null;
		Iterator<String> it = tables.iterator();
		while(it.hasNext()){
			String val = (String)it.next();
			if(reval==null){
				reval = val;
			}else{
				reval = reval+","+val;
			}
		}
		return reval;
	}
	public static void main(String[] args)throws Exception{
		SQLBuilder sb = new SQLBuilder();
		sb.setTable("tbl");
		sb.setCell("col1");
		sb.setCell("col2");
		//sb.isType(false);
		sb.setRestrict("WHERE col3>${col3}");
		System.out.println(sb.update());
	}
}
