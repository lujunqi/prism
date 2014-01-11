package com.prism.service;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Service {
	public void service()
	throws ServletException, IOException;
	public void setSourceMap(Map<String,Object> sourceMap);
	public Map<String,Object> getSourceMap();
	public HttpServletRequest getRequest();
	public void setRequest(HttpServletRequest req);
	public HttpServletResponse getResponse();
	public void setResponse(HttpServletResponse res);
}
