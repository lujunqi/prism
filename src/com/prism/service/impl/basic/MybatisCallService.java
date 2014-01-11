/**
 * 修改
 */
package com.prism.service.impl.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.velocity.VelocityContext;

import com.prism.common.JsonUtil;
import com.prism.common.VMResponse;

public class MybatisCallService extends MybatisService {
	public void service() throws ServletException, IOException {
		super.service();
		PrintWriter out = getResponse().getWriter();
		Map<String, Object> obj = new HashMap<String, Object>();

		try {
			if (sourceMap.containsKey("SQL")) {
				obj = callResult("SQL");
			}
			String action = (String) reqMap.get("_action");
			if (sourceMap.containsKey("VIEW")) {
				vc = new VelocityContext();
				vc.put(action, obj);
				reqMap.put(action, obj);
				reqMap.put("this", obj);
				getRequest().setAttribute("this", obj);
				VMResponse vm = new VMResponse();
				vm.setReqMap(reqMap);
				vc.put("v", vm);
				String content = getResultfromContent("VIEW");
				out.print(content);
			}
			if (sourceMap.containsKey("FORWARD")) {
				reqMap.put("this", obj);
				getRequest().setAttribute("this", obj);
				reqMap.put(action, obj);
				getRequest().setAttribute(action, obj);
				getRequest().getRequestDispatcher(
						(String) sourceMap.get("FORWARD")).forward(
						getRequest(), getResponse());
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
