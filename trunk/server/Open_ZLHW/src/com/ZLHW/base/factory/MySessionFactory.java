package com.ZLHW.base.factory;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

import com.ZLHW.base.table.BaseTable;
import com.ZLHW.util.propertiesOperator;

public class MySessionFactory extends LocalSessionFactoryBean {

	@Override
	protected void postProcessConfiguration(Configuration config)
			throws HibernateException {
		super.postProcessConfiguration(config);
	}

	@Override
	protected void postProcessMappings(Configuration config)
			throws HibernateException {
		Document document = DocumentHelper.createDocument();
		document.addDocType("hibernate-mapping ",
				"-//Hibernate/Hibernate Mapping DTD 3.0//EN",
				"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd");
		Element mapping = document.addElement("hibernate-mapping");
		propertiesOperator rc = new propertiesOperator("conf/beans.properties");// 相对路径
		Set<Entry<Object, Object>> set = rc.getAll();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Entry<String, String> e=(Entry<String, String>) iterator.next();
			try {
				Class clazz=Class.forName(e.getKey());
				BaseTable tb=(BaseTable)clazz.newInstance();
				Element element=tb.getElement();
				mapping.add(element);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		config.addXML(document.asXML());
	}

}
