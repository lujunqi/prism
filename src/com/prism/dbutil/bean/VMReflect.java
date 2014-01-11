//方法名映射参数
package com.prism.dbutil.bean;

import java.lang.reflect.Method;

public class VMReflect {
	public Object put(String param, Object obj) {
		Object val = null;
		try {
			ReflectEx ref = new ReflectEx(param);
			String methods = ref.getMethods();
			Object[] params = ref.getParams();
			
			String fields = ref.getFields();
			int t = ref.getType();
			Class<? extends Object> cls = obj.getClass();
			Method[] meths = cls.getMethods();
			if (methods == null && fields == null) {
				val = obj;
			} else if (methods == null && fields != null) {
				val = cls.getField(fields).get(obj);
			} else if (methods != null && fields == null) {
				for (int i = 0; i < meths.length; i++) {
					Method meth = meths[i];
					if (methods.equals(meth.getName())
							&& meth.getParameterTypes().length == params.length) {
						for(int j = 0; j < meth.getParameterTypes().length; j++) {
							Class<?> cl = meth.getParameterTypes()[j];
							String type = cl.toString();
							if("int".equals(type)){
								params[j] = Integer.parseInt(String.valueOf(params[j]));
							}
						}
						val = meth.invoke(obj,params);
					}
				}
			}
			val = TypeMapping.t(val, t);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return val;
	}
}
