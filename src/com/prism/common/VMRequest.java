package com.prism.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VMRequest {
	private Map<String, Object> reqMap = new HashMap<String, Object>();
	
	public String set(String name, String type) {
		String result = "${" + name + ",<" + type + ">}";
		return result;
	}
	
	public String set(String name) {
		String result = "${" + name + "}";
		return result;
	}
	public String sets(String name){
		String result = "";
		if(reqMap.get(name) instanceof List){
			List<?> list = (List<?>)reqMap.get(name);
			for(int i=0;i<list.size();i++){
				if("".equals(result)){
					result += "${"+name+".get('"+i+"')}";
				}else{
					result += ",${"+name+".get('"+i+"')}";	
				}
			}
			set(name);
		}
		return result;
	}
	public String sets(String name, String type) {
		String result = "";
		if(reqMap.get(name) instanceof List){
			List<?> list = (List<?>)reqMap.get(name);
			for(int i=0;i<list.size();i++){
				if("".equals(result)){
					result += "${"+name+".get('"+i+"')}";
				}else{
					result += ",${"+name+".get('"+i+"')}";	
				}
			}
			set(name,type);
		}
		return result;
	}
	public boolean isEmpty(String name) {
		if (reqMap.get(name) == null ) {
			return true;
		}else if( "".equals(reqMap.get(name))){
			return true;
		} else {
			return false;
		}
	}
	public boolean isNull(Object obj){
		if(obj==null){
			return true;
		}else if("".equals(obj)){
			return true;
		}else if("null".equals(obj)){
			return true;
		}else{
			return false;
		}
	}
	public boolean equals(String name, String value) {
		if (reqMap.get(name) == null && value == null) {
			return false;
		}
		if (value.equals(reqMap.get(name))) {
			return true;
		} else {
			return false;
		}
	}

	public Map<String, Object> getReqMap() {
		return reqMap;
	}

	public void setReqMap(Map<String, Object> reqMap) {
		this.reqMap = reqMap;
	}
}
