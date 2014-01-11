/**
 * 修改
 */
package com.prism.service.impl.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import com.prism.common.JsonUtil;
import com.prism.common.VMResponse;

public class MybatisDelService extends MybatisService {
	public void service() throws ServletException, IOException {
		super.service();
		PrintWriter out = getResponse().getWriter();
		Map<String, Object> obj = new HashMap<String, Object>();
		try {
			if (sourceMap.containsKey("SQL")) {
				obj.put("code", 1);
				obj.put("result", deleteResult("SQL"));
			}
			String action = (String) reqMap.get("_action");
			reqMap.put(action, obj);
			reqMap.put("this", obj);
			getRequest().setAttribute("this", obj);
			getRequest().setAttribute(action, obj);
			vc.put(action, obj);
			vc.put("this", obj);

			// 视图模板
			if (sourceMap.containsKey("VIEW")) {
				VMResponse vm = new VMResponse();
				vm.setReqMap(reqMap);
				vc.put("v", vm);
				String content = getResultfromContent("VIEW");
				out.print(content);
			}

			// FORWARD 页面跳转
			if (sourceMap.containsKey("FORWARD")) {

				getRequest()
						.setAttribute("TEMPLATE", sourceMap.get("TEMPLATE"));
				getRequest().getRequestDispatcher(
						(String) sourceMap.get("FORWARD")).forward(
						getRequest(), getResponse());
			}
			// REDIRECT 页面重定向
			if (sourceMap.containsKey("REDIRECT")) {
				getResponse().sendRedirect((String) sourceMap.get("REDIRECT"));
			}
		} catch (Exception e) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("code", -1);
			m.put("info", e.getMessage());
			JsonUtil ju = new JsonUtil();
			out.println(ju.toJson(m));
			e.printStackTrace();
		}
	}

}
