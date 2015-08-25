package com.ZLHW.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.ZLHW.base.table.BaseTable;

public class ConfigEdit {
	private final static String SRC_SPRING_CONFIG = "src/conf/applicationContext.xml";
	private final static String FLEX_CONFIG="WebRoot/WEB-INF/flex/remoting-config.xml";

	
	public static void addToFlex(String name){
		SAXReader reader = new SAXReader();
		Document document;
		try {
			document = reader.read(new File(FLEX_CONFIG));
			Element flex = document.getRootElement();
			// Flex-------------------------------------------------------------
			Element destination = flex.addElement("destination");
			destination.addAttribute("id", name);
			Element properties=destination.addElement("properties");
			Element factory=properties.addElement("factory");
			Element source=properties.addElement("source");
			factory.addText("springFactory");
			source.addText(Tools.firstLowcase(name));
			

			// 更新保存----------------------------------------------------
			XMLWriter writer = new XMLWriter(new FileWriter(FLEX_CONFIG));
			writer.write(document);
			writer.close();

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public static void addToSRCSpring(BaseTable tmp,String daopath,String servicepath,String actionpath) {
		SAXReader reader = new SAXReader();
		String name=tmp.getClass().getSimpleName();
		Document document;
		try {
			document = reader.read(new File(SRC_SPRING_CONFIG));
			Element beans = document.getRootElement();
			beans.addComment("===================以下是"+name+tmp.getComment()+"相关的beans====================");
			// DAO-------------------------------------------------------------
			if(!daopath.equals("")){
			Element dao = beans.addElement("bean");
			dao.addAttribute("id", Tools.firstLowcase(name + "DAO"));
			dao.addAttribute("class", daopath);
			dao.addAttribute("parent", "DAO");
			}
			// Server----------------------------------------------------------
			if(!servicepath.equals("")){
			Element service = beans.addElement("bean");
			service.addAttribute("id", Tools.firstLowcase(name + "Service"));
			service.addAttribute("class", servicepath);
			service.addAttribute("parent","BaseService");
			Element serProperty = service.addElement("property");
			serProperty.addAttribute("name", "dao");
			serProperty.addAttribute("ref", Tools.firstLowcase(name + "DAO"));
			}
			// action----------------------------------------------------------
			if(!actionpath.equals("")){
			Element action = beans.addElement("bean");
			action.addAttribute("name", name);
			action.addAttribute("class", actionpath);
			Element actionProperty = action.addElement("property");
			actionProperty.addAttribute("name", name.toLowerCase() + "Service");
			actionProperty.addAttribute("ref", name + "Server");
			}
			// action----------------------------------------------------------
//			Element action = beans.addElement("bean");
//			action.addAttribute("name", name);
//			action.addAttribute("class", "com.zb.template." + name + "Action");
//			Element actionProperty = action.addElement("property");
//			actionProperty.addAttribute("name", name.toLowerCase() + "Service");
//			actionProperty.addAttribute("ref", name + "Server");

			// 更新保存----------------------------------------------------
			XMLWriter writer = new XMLWriter(new FileWriter(SRC_SPRING_CONFIG));
			writer.write(document);
			writer.close();

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}