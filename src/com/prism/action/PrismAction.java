package com.prism.action;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.prism.service.Service;

public class PrismAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String[] xmls = { "config/base/baseConf.xml" };
	private static ApplicationContext context;

	public void init() throws ServletException {
		System.out.println("httpServlet正在加载....");
		context = new ClassPathXmlApplicationContext(xmls);
	}

	public void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		try {
			req.setCharacterEncoding("UTF-8");
			res.setContentType("text/html;charset=UTF-8");
			String action = getAction(req);
			Service vm = (Service) context.getBean(action);
			
			Map<String, Object> reqMap = new HashMap<String, Object>();

			Enumeration<String> en = req.getParameterNames();
			while (en.hasMoreElements()) {
				String name = (String) en.nextElement();
				if (!isNull(req.getParameter(name))) {
					reqMap.put(name, req.getParameter(name));
				}
			}
			reqMap.put("_action", action);
			req.setAttribute("reqMap", reqMap);
			req.setAttribute("context", context);
			req.setAttribute("DBConnection", context.getBean("DBConnection"));
			vm.setRequest(req);
			vm.setResponse(res);
			vm.service();
		} catch (Exception e) {
			e.printStackTrace();
			req.getRequestDispatcher("error.jsp")
			.forward(req, res);
		}
	}

	private String getAction(HttpServletRequest req) {
		try {
			String relativeuri = req.getRequestURI().replaceFirst(
					req.getContextPath(), "");
			
			relativeuri = "/" + relativeuri.replaceAll("/", "");
			int len = relativeuri.length();
			StringBuffer sb = new StringBuffer(relativeuri);
			return sb.substring(1, len - 3);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	public void destroy() {
		super.destroy();
	}

	private boolean isNull(String param) {
		if (param == null) {
			return true;
		} else if ("".equals(param)) {
			return true;
		} else {
			return false;
		}
	}
}
