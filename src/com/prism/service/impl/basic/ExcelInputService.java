/**
 * EXCEL处理
 */
package com.prism.service.impl.basic;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.prism.common.ExcelCommon;
import com.prism.common.VMRequest;

public class ExcelInputService extends BaseService {
	private VelocityContext vc = new VelocityContext();

	public void service()
			throws ServletException, IOException {
		super.service();
		PrintWriter out = getResponse().getWriter();
		try {
			String path = "/";
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setRepository(new File(path));
			factory.setSizeThreshold(1024 * 1024);
			ServletFileUpload upload = new ServletFileUpload(factory);
			@SuppressWarnings("unchecked")
			List<FileItem> list = upload.parseRequest(getRequest());
			String action = (String) reqMap.get("_action");
			for (FileItem item : list) {
				if (item.isFormField()) {
					String name = item.getFieldName();// input name
					String value = item.getName();// input content
					getRequest().setAttribute(name, value);
				} else {
					String value = item.getName();// input content
					value = value.substring(value.lastIndexOf("\\") + 1,
							value.length());
					String rule = (String) sourceMap.get("RULE");
					InputStream fileInputStream = item.getInputStream();
					ExcelCommon excel = new ExcelCommon();
					List<Map<String, String>> excels = excel.Excel2List(
							fileInputStream, rule);
					fileInputStream.close();
					vc.put(action, excels);
					reqMap.put(action, excels);
				}
			}
			if (sourceMap.containsKey("VIEW")) {
				vc = new VelocityContext();
				VMRequest vm = new VMRequest();
				vm.setReqMap(reqMap);
				vc.put("v", vm);
				String content = getResultfromContent("VIEW");
				out.print(content);
			}
			if (sourceMap.containsKey("FORWARD")) {
				getRequest().getRequestDispatcher((String) sourceMap.get("FORWARD"))
						.forward(getRequest(), getResponse());
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.print(e.getMessage());
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
