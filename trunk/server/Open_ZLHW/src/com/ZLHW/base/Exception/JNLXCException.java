package com.ZLHW.base.Exception;

public class JNLXCException extends Exception {
	private String viewName;
	private String errorMessage;
	public String getViewName() {
		return viewName;
	}
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public JNLXCException(Throwable throwable) {
		super(throwable);
	}
	public JNLXCException( String errorMessage,String viewName) {
		super(errorMessage);
		this.errorMessage = errorMessage;
		this.viewName = viewName;
	}
	
}
