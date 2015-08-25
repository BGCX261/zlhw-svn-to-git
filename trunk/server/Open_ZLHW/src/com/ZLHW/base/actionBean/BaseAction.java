package com.ZLHW.base.actionBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.danga.MemCached.MemCachedClient;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public class BaseAction extends ActionSupport{
	protected MemCachedClient mcc = new MemCachedClient();
		public HttpServletRequest getRequest(){
			ActionContext ac = ActionContext.getContext();
			return (HttpServletRequest) ac.get(ServletActionContext.HTTP_REQUEST);
		}
		
		public HttpServletResponse getResponse(){
			ActionContext ac = ActionContext.getContext();
			return (HttpServletResponse) ac.get(ServletActionContext.HTTP_RESPONSE);
		} 
}
