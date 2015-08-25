package com.ZLHW.base.Exception;
/**
 * 向前台返回的错误信息
 * @author admin
 *
 */
public class BaseErrorModel extends  RuntimeException{
	
	public BaseErrorModel(String errorMessage,String errorView) {
		this.errorMessage = errorMessage;
		this.errorView = errorView;
	}
	private String errorView;
	private String errorMessage;
	public String getErrorView() {
		return errorView;
	}
	public void setErrorView(String errorView) {
		this.errorView = errorView;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	
}
