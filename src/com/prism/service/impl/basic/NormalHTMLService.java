package com.prism.service.impl.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;

import com.prism.common.JsonUtil;

public class NormalHTMLService extends BaseService
{
	public void service() throws ServletException, IOException
	{

		super.service();
		PrintWriter out = getResponse().getWriter();
		try
		{
			Iterator<Entry<String, Object>> it = sourceMap.entrySet().iterator();
			while (it.hasNext())
			{
				Map.Entry<java.lang.String, java.lang.Object> entry = (Map.Entry<java.lang.String, java.lang.Object>) it
						.next();
				getRequest().setAttribute(entry.getKey(), entry.getValue());

			}
			if (sourceMap.containsKey("TEMPLATE"))
			{
				getRequest().getRequestDispatcher((String) sourceMap.get("TEMPLATE")).forward(getRequest(), getResponse());
			}
		} catch (Exception e)
		{
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("code", -1);
			m.put("info", e.getMessage());
			JsonUtil ju = new JsonUtil();
			out.println(ju.toJson(m));
			e.printStackTrace();
		}
	}
}
