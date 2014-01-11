/**
 * Excel语法文件
 */

package com.prism.common.bean;

import java.util.ArrayList;
import java.util.List;

import jxl.format.CellFormat;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableSheet;

import com.prism.common.Arith;

public class VMExcel {
	private WritableSheet ws;

	public VMExcel(WritableSheet ws) {
		this.ws = ws;
	}

	public void add(int c, int r, Object value) {
		add(c, r, value, c, r, "String");
	}

	public void add(int c, int r, Object value, String type) {
		add(c, r, value, c, r, type);
	}

	public void add(int c, int r, Object value, int mc, int mr) {
		add(c, r, value, mc, mr, "String");
	}

	public void add(int c, int r, Object value, int mc, int mr, String type) {
		try {
			WritableCell cell = ws.getWritableCell(mc, mr);
			CellFormat format = cell.getCellFormat();
			if (format == null) {
				ws.addCell(setCell(c,r,value,format,type));
			} else {
				ws.addCell(setCell(c,r,value,format,type));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private WritableCell setCell(int c, int r, Object value,CellFormat format,
			String type) {
		WritableCell result = ws.getWritableCell(c, r);
		//字符串
		if ("String".equals(type)) {
			Label label = new Label(c, r, value + "", format);
			result = label;
		}
		//数字
		if ("Number".equals(type)) {
			jxl.write.Number n = new jxl.write.Number(c, r,
					Arith.getDouble(value), format);
			result = n;
		}
		//函数
		if ("Formula".equals(type)) {
			jxl.write.Formula  n = new jxl.write.Formula (c, r,
					value+"", format);
			result = n;
		}
		//下拉框
		if ("List".equals(type)) {
			Label label = new Label(c, r, "请选择", format);
			WritableCellFeatures wcf = new WritableCellFeatures();  
		    List<String> angerlist = new ArrayList<String>();
		    String[] values = (value+"").split(",");
		    for(int i=0;i<values.length;i++){
		    	angerlist.add(values[i]);	
		    }
		    wcf.setDataValidationList(angerlist);  
		    label.setCellFeatures(wcf);
		}
		return result;
	}
}
