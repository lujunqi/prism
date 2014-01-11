package com.prism.exception;

public class BMOException extends Exception {
	private static final long serialVersionUID = 1L;
	public BMOException(Exception e){
		super(e);
	}
}
