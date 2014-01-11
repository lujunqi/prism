package com.prism.service.impl.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import com.prism.common.JsonUtil;
import com.prism.common.VMResponse;

public class MybatisSltService extends MybatisService{
	public void service() throws ServletException,
	IOException	{
		super.service();
		PrintWriter out = getResponse().getWriter();
		List<Object> list = new ArrayList<Object>();
		try{
			if(sourceMap.containsKey("SQL")) {
				list = selectResult("SQL");
			}
			
			String action = (String) reqMap.get("_action");
			reqMap.put(action, list);
			reqMap.put("this", list);
			getRequest().setAttribute("this", list);
			getRequest().setAttribute(action, list);
			vc.put(action, list);
			vc.put("this", list);
			
			// 视图模板
			if (sourceMap.containsKey("VIEW")) {
				VMResponse  vm = new VMResponse();
				vm.setReqMap(reqMap);
				vc.put("v", vm);
				String content = getResultfromContent("VIEW");
				out.print(content);
			}
			
			// FORWARD 页面跳转
			if (sourceMap.containsKey("FORWARD")) {
				
				getRequest().setAttribute("TEMPLATE", sourceMap.get("TEMPLATE"));
				getRequest().getRequestDispatcher((String) sourceMap.get("FORWARD"))
						.forward(getRequest(), getResponse());
			}
			// REDIRECT 页面重定向
			if (sourceMap.containsKey("REDIRECT")) {
				getResponse().sendRedirect((String) sourceMap.get("REDIRECT"));
			}
		} catch (Exception e) {
			Map<String,Object> m = new HashMap<String,Object>();
			m.put("code", -1);
			m.put("info", e.getMessage());
			JsonUtil ju = new JsonUtil();
			out.println(ju.toJson(m));
			e.printStackTrace();
		}
	}
}
