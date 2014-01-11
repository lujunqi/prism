/**
 * 通用修改、删除、新增操作
 * 
 */
package com.prism.service.impl.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.prism.common.JsonUtil;
import com.prism.common.VMResponse;

public class NormalUptService extends BaseService {
	private VelocityContext vc = new VelocityContext();

	public void service()
			throws ServletException, IOException {
		super.service();
		PrintWriter out = getResponse().getWriter();
		try {
			Map<String,Object> obj = new HashMap<String,Object>();
			if (sourceMap.containsKey("DSQL")) {
				convertSql("DSQL", "NSQL");
				obj.put("code",1);
				obj.put("result",updateResult("NSQL"));
			} else if (sourceMap.containsKey("SQL")) {
				obj.put("code",1);
				obj.put("result",updateResult("SQL"));
			}
			if (sourceMap.containsKey("ALIAS")) {
				String alias = (String)sourceMap.get("ALIAS");
				getRequest().setAttribute(alias, obj);
				getRequest().setAttribute("this", obj);
			}
			String action = (String) reqMap.get("_action");
			if (sourceMap.containsKey("VIEW")) {
				vc = new VelocityContext();
				vc.put(action, obj);
				reqMap.put(action, obj);
				reqMap.put("this", obj);
				getRequest().setAttribute("this", obj);
				VMResponse  vm = new VMResponse();
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
				getRequest().getRequestDispatcher((String) sourceMap.get("FORWARD"))
						.forward(getRequest(),getResponse());
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

	private String getResultfromContent(String s) throws Exception {
		s = (String) sourceMap.get(s);
		StringWriter stringwriter;
		Velocity.init();
		stringwriter = new StringWriter();
		Velocity.evaluate(vc, stringwriter, "mystring", s);
		return stringwriter.toString();
	}
}
