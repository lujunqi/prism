package com.prism.dbutil.bean;

import java.sql.Types;

public class TypeMapping{
	public static Object t(Object v, int type){
		Object r = v;
		if (v == null){
			return v;
		}
		switch(type){
		case Types.VARCHAR:
			r = v.toString();
			break;
		case Types.INTEGER:
			r = Integer.parseInt(v.toString());
			break;
		case Types.NUMERIC:
			r = Long.getLong(v.toString());
			break;
			
		}
		return r;
	}

}
