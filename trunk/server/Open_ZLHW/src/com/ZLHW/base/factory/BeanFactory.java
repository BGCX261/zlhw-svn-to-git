package com.ZLHW.base.factory;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


import flex.messaging.FlexContext;


public class BeanFactory implements ApplicationContextAware{
//	public static ApplicationContext context = 
//		WebApplicationContextUtils.getWebApplicationContext(FlexContext.getServletContext());
//	public static ApplicationContext context = 
//		new ClassPathXmlApplicationContext("classpath:conf/spring/applicationContext.xml");
	public static ApplicationContext context ;
	/**
	 * 用于velocity的调用
	 * @param beanName
	 * @return
	 */
	public static Object LookUp(String beanName){
		return context.getBean(beanName);
	}
	public static <T> T LookUp(Class<T> beanClass){
		return context.getBean(beanClass);
	}
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		context=arg0;
		
	}
}
