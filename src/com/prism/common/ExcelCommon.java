/**
 * EXCEL通用处理
 */
package com.prism.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.CellType;
import jxl.LabelCell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class ExcelCommon {
	public static void main(String[] args) throws Exception {
	}

	private VelocityContext vc = new VelocityContext();

	/**
	 * List转Excel
	 * 
	 * @param param
	 *            入参
	 * @param modelPath
	 *            模板文件
	 * @param rule
	 *            规则
	 * @param os
	 *            输出流
	 */
	public void List2Excel(Map<String, Object> param, String modelPath,
			String rule, OutputStream os) {
		try {
			vc = new VelocityContext();
			Iterator<Map.Entry<String, Object>> it = param.entrySet()
					.iterator();
			while (it.hasNext()) {
				Map.Entry<String, Object> e = (Map.Entry<String, Object>) it
						.next();
				vc.put(e.getKey(), e.getValue());
			}

			HSSFWorkbook workbook = new HSSFWorkbook();

			// Workbook wb = Workbook.getWorkbook(new File(modelPath));
			// WritableWorkbook wwb = Workbook.createWorkbook(os, wb);
			// WritableSheet ws = wwb.getSheet(0);
			// VMExcel excel = new VMExcel(ws);
			// vc.put("excel", excel);
			// getResultfromContent(rule);
			// wwb.write();
			// wwb.close();
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			// } catch (RowsExceededException e) {
			// e.printStackTrace();
			// } catch (WriteException e) {
			// e.printStackTrace();
			// } catch (JXLException e) {
			// e.printStackTrace();
		}
	}

	/**
	 * EXCEL结果转换成LIST
	 * 
	 * @param is
	 *            输入流
	 * @param rule
	 *            规则
	 */
	public List<Map<String, String>> Excel2List(InputStream is, String rule) {
		List<Map<String, String>> contents = new ArrayList<Map<String, String>>();
		try {
			vc = new VelocityContext();
			getResultfromContent(rule);
			String sheetName = (String) vc.get("sheetName");
			@SuppressWarnings("unchecked")
			List<String> colsName = (List<String>) vc.get("colsName");
			@SuppressWarnings("unchecked")
			List<Integer> origin = (List<Integer>) vc.get("origin");
			int cols = colsName.size();
			int x = origin.get(0).intValue();
			int y = origin.get(1).intValue();
			Workbook rwb = Workbook.getWorkbook(is);
			Sheet st = rwb.getSheet(sheetName);
			int rows = st.getRows();

			for (int i = x; i < rows; i++) {
				Map<String, String> map = new HashMap<String, String>();
				for (int j = y; j < cols + y; j++) {
					Cell c00 = st.getCell(j, i);
					String strc00 = c00.getContents();
					if (c00.getType() == CellType.LABEL) {
						LabelCell labelc00 = (LabelCell) c00;
						strc00 = labelc00.getString();
					}
					String key = colsName.get(j - y);
					String value = strc00;
					map.put(key, value);
				}
				contents.add(map);
			}
			rwb.close();
			return contents;
		} catch (Exception e) {
			e.printStackTrace();
			return contents;
		}
	}

	private void getResultfromContent(String rule) {
		try {
			StringWriter stringwriter;
			Velocity.init();
			stringwriter = new StringWriter();
			Velocity.evaluate(vc, stringwriter, "mystring", rule);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
