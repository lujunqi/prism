/**
 * 将内容转换成EXCEL输出
 */
package com.prism.service.impl.basic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import com.prism.common.ExcelCommon;
import com.prism.service.Service;

public class ExcelOutputService extends BaseService {
	public void service()
			throws ServletException, IOException {
		super.service();
		try {
		
			ExcelCommon excel = new ExcelCommon();
			@SuppressWarnings("rawtypes")
			List<?> list = new ArrayList();
			if (sourceMap.containsKey("DATASOURCE")){
				
				Service vm = (Service)sourceMap.get("DATASOURCE");
				vm.setRequest(this.getRequest());
				vm.setResponse(this.getResponse());
				vm.service();
				list = (List<?>)getRequest().getAttribute("this");
			}
//			if (sourceMap.containsKey("DSQL")) {
//				convertSql("DSQL", "NSQL");
//				list = selectResult("NSQL");
//			} else if (sourceMap.containsKey("SQL")) {
//				list = selectResult("SQL");
//			}
			String action = (String) reqMap.get("_action");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put(action, list);
			String modelPath = (String) sourceMap.get("ModelPath");
			String rule = (String) sourceMap.get("RULE");
			if (sourceMap.containsKey("SAVEPATH")) {
				String savePath = (String) sourceMap.get("SAVEPATH");
				Date date = new Date();
				String fileName = date.getTime()+".xls";
				File file = new File(savePath, fileName);
				FileOutputStream os = new FileOutputStream(file);
				excel.List2Excel(param, modelPath, rule, os);
				os.close();
				PrintWriter out = getResponse().getWriter();
				out.print(file.getAbsolutePath());
			} else {
				Date date = new Date();
				String fileName = date.getTime()+".xls";
				getResponse().setHeader("Content-disposition",
						"attachment; filename="+fileName);// 设定输出文件头
				getResponse().setContentType("application/msexcel");// 定义输出类型
				excel.List2Excel(param, modelPath, rule, getResponse().getOutputStream());
			}
		} catch (Exception e) {
			System.out.println("status=error&info=" + e.getMessage());
			e.printStackTrace();
		}
	}
}
