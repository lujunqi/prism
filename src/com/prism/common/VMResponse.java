package com.prism.common;

import java.util.HashMap;
import java.util.Map;

public class VMResponse{
	private Map<String, Object> reqMap = new HashMap<String, Object>();
	public Map<String, Object> getReqMap() {
		return reqMap;
	}
	public void setReqMap(Map<String, Object> reqMap) {
		this.reqMap = reqMap;
	}
	public String toJson(String key){
		JsonUtil ju = new JsonUtil();
		return ju.toJson(reqMap.get(key));
	}
	public String toJson(){
		return toJson("this");
	}
}
