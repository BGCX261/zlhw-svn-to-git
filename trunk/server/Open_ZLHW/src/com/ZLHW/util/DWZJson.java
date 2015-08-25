package com.ZLHW.util;


/**
 * 与DWZ交互的JOSN对象数据
 * @author Administrator
 *
 */
public class DWZJson {
	public static String CLOSE_CURRENT = "closeCurrent";
	public static String FORWARD="forward";
	
	private String statusCode = "200";//状态吗
	private String message = "success";//提示框的内容
	private String callbackType = "";//返回类型CLOSE_CURRENT与FORWARD
	private String navTabId = "";//返回的tab的rel名，必须的
	private String forwardUrl = "";//返回加载地址，默认为空
	
	
	public DWZJson() {
		statusCode="200";
		message="success";
		callbackType = "";
		navTabId = "";
		forwardUrl = "";
	}
	
	public static DWZJson getInstance(String tab, String msg) {
		DWZJson dwz = new DWZJson();
		dwz.setNavTabId(tab);//返回所显示的tab
		dwz.setMessage(msg);//返回操作提示
		return dwz;
	}
	
	public DWZJson(String statusCode,String message,String callbackType,String navTabId,String forwardUrl) {
		this.statusCode=statusCode;
		this.message=message;
		this.callbackType=callbackType;
		this.navTabId=navTabId;
		this.forwardUrl=forwardUrl;
	}

	public String toString() {
		StringBuffer JSON = new StringBuffer();
		JSON.append("{");
		JSON.append("\"statusCode\"").append(":").append(
				"\"" + statusCode + "\",");
		JSON.append("\"message\"").append(":").append("\"" + message + "\",");
		JSON.append("\"navTabId\"").append(":").append("\"" + navTabId + "\",");
		JSON.append("\"callbackType\"").append(":").append(
				"\"" + callbackType + "\",");
		JSON.append("\"forwardUrl\"").append(":").append(
				"\"" + forwardUrl + "\",");
		JSON.append("}");
		return JSON.toString();
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCallbackType() {
		return callbackType;
	}

	public void setCallbackType(String callbackType) {
		this.callbackType = callbackType;
	}

	public String getNavTabId() {
		return navTabId;
	}

	public void setNavTabId(String navTabId) {
		this.navTabId = navTabId;
	}

	public String getForwardUrl() {
		return forwardUrl;
	}

	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}

}
