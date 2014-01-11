package com.prism.common;

import java.util.Map;

/*
 * 控件生成器
 */
public class VMControl
{
	public static String formControl(Map<String, String> m)
	{
		String type = (String)m.get("0");
		return type;
	}
}
