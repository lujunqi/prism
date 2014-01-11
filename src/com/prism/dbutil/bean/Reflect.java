package com.prism.dbutil.bean;

//获取类名 方法名 参数

import java.sql.Types;
import java.util.List;

public class Reflect {
	private String param = null; // 参数

	public Reflect(String param) {
		this.param = param;
	}

	public int getDirect() {// 参数方向
		if (param.startsWith("$")) {
			return 0;
		}
		if (param.startsWith("#")) { // 存储过程输入
			return 1;
		}
		return 0;
	}

	public int getType() {// 数据类型
		ExMatcher ex = new ExMatcher();
		String type = ex.regex(param, "[<](.*)[>]", 1);
		Types types = null;
		try {
			if ("int".equalsIgnoreCase(type)) {
				type = "INTEGER";
			}
			if ("long".equalsIgnoreCase(type)) {
				type = "NUMERIC";
			}
			// java.sql.Types.NUMERIC
			if ("string".equalsIgnoreCase(type)) {
				type = "VARCHAR";
			}
			int sqltype = 0;
			if ("cursor".equalsIgnoreCase(type)) {
//				sqltype = oracle.jdbc.OracleTypes.CURSOR;
			} else {
				sqltype = Class.forName("java.sql.Types")
						.getField(type.toUpperCase()).getInt(types);
			}
			return sqltype;
		} catch (Exception e) {
			return Types.OTHER;
		}

	}

	public String getClassName() {// 类名
		ExMatcher ex = new ExMatcher();
		return ex.regex(param, "[\\{](\\w*|\\d*)", 1);
	}

	public String getMethods() {// 方法名
		ExMatcher ex = new ExMatcher();
		return ex.regex(param, "[.](\\w*|\\d*)[\\(]", 1);
	}

	public String getFields() { // 属性
		ExMatcher ex = new ExMatcher();
		return ex.regex(param, "[.](\\w*|\\d*)[\\}]", 1);
	}

	public Object[] getParams() {// 参数值
		String regex = "['](\\w*|\\d*)[']";
		ExMatcher ex = new ExMatcher();
		List<String> list = ex.regexs(param, regex, 1);
		if (list == null) {
			return null;
		} else {
			return list.toArray();
		}
	}

	public static void main(String[] as) throws Exception {
		String param = "${AREA_ID}_${AREA_NAME}";
		Reflect r = new Reflect(param);
		Object[] objs = r.getParams();
		System.out.println(r.getClassName());
		for (int i = 0; i < objs.length; i++) {
			System.out.println(objs[i] + "==");
		}
		// String regex = "['](\\w*|\\d*)[']";
		// ExMatcher ex = new ExMatcher();
		// System.out.println(ex.regex(param,regex,0)+"--");
		//
		// Reflect ref = new Reflect("${name.get()<int>}");
		//
		// System.out.println(ref.getType()+"==="+Types.OTHER);
		// Object[] objs = ref.getParams();
		// for(int i=0;i<objs.length;i++){
		// System.out.println(objs[i]+"==");
		// }
		//

	}

}
