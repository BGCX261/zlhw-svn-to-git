package com.ZLHW.template;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.junit.Test;

import com.ZLHW.base.Field.BaseField;
import com.ZLHW.base.HTable.BasicModel;
import com.ZLHW.base.table.BaseTable;
import com.ZLHW.common.model.Author;
import com.ZLHW.common.model.AuthorDetail;
import com.ZLHW.common.model.DataDict;
import com.ZLHW.common.model.DataDictCatalog;
import com.ZLHW.common.model.ExcutionExt;
import com.ZLHW.common.model.Job;
import com.ZLHW.common.model.Job_Role;
import com.ZLHW.common.model.Organ;
import com.ZLHW.common.model.Organ_Job;
import com.ZLHW.common.model.Role;
import com.ZLHW.common.model.Role_AuthorDetail;
import com.ZLHW.common.model.TaskExt;
import com.ZLHW.util.ConfigEdit;
import com.ZLHW.util.propertiesOperator;

/**
 * 
 *<p>
 * 采用自定义初始化设置，解析指定位置模版, 输出到文件
 * </p>
 * 
 * Create on 2006-4-3 11:04:52
 * 
 * @author Jonathan Q. Bo
 * @version 1.0 valocity study
 */

public class TemplateGenerate {
	String templatePath;// 模板文件
	File export;// 输出文件
	VelocityContext context;// 匹配内容
	FileWriter writer;
	BufferedWriter bw;

	/**
	 * 默认构造函数，velocity.properties位于./WebRoot/WEB-INF/velocity.properties
	 */
	public TemplateGenerate(String templatePath, File export) {
		this("./WebRoot/WEB-INF/velocity.properties", templatePath, export);
	}

	public TemplateGenerate(String templatePath, String export) {
		this("./WebRoot/WEB-INF/velocity.properties", templatePath, new File(
				export));
	}

	public TemplateGenerate(HashMap<String, Object> source,
			String templatePath, String export) {
		this("./WebRoot/WEB-INF/velocity.properties", templatePath, new File(
				export));
		setReplaceContent(source);
	}

	/**
	 * TemplateGenerate构造函数
	 * 
	 * @param path
	 *            velocity.properties路径
	 */
	public TemplateGenerate(String path, String templatePath, File export) {
		try {
			Velocity.init(path);
		} catch (Exception e) {
			System.out.println("## 源文件不存在！");
			e.printStackTrace();
		}
		this.context = new VelocityContext();
		this.templatePath = templatePath;
		this.export = export;
		System.out.println(export.getAbsolutePath());
		try {
			export.createNewFile();
			this.writer = new FileWriter(export, false);
			this.bw = new BufferedWriter(this.writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setReplaceContent(HashMap<String, Object> source) {
		Set<Entry<String, Object>> set = source.entrySet();
		for (Entry<String, Object> e : set) {
			context.put(e.getKey(), e.getValue());
		}
	}

	public void mergeTemplate(String character) {
		try {
			Velocity.mergeTemplate(this.templatePath, character, this.context,
					this.bw);
			bw.flush();
			bw.close();
		} catch (ResourceNotFoundException e1) {
			System.out.println("## 源文件不存在！");
			e1.printStackTrace();
		} catch (ParseErrorException e2) {
			System.out.println("## 解析文件错误！");
			e2.printStackTrace();
		} catch (MethodInvocationException e3) {
			System.out.println("## 方法调用异常!");
			e3.printStackTrace();
		} catch (Exception e4) {
			System.out.println("## 其他错误!");
			e4.printStackTrace();
		}
	}

	/**
	 * 生成某个bean的dao、service、spring、flex配置文件
	 */
	public static void createOne(BaseTable tmp) throws IllegalArgumentException, IllegalAccessException {
		//修改此对象类型即可生成到和service
		
		String name=tmp.getClass().getSimpleName();
		String templatePackage = "src/com/ZLHW/template/";
		String exportPackage = "src/com/ZLHW/common/";
		String packagepath="com.ZLHW.common";
		String daopath=packagepath+".dao."+name+"DAO";
		String servicepath=packagepath+".service."+name+"Service";
		String actionpath=packagepath+".action."+name+"Action";
		HashMap<String, Object> hm = new HashMap();
		
		Field[] fs=tmp.getClass().getFields();
		BaseField[] fields = new BaseField[fs.length];
		for(int a=0;a<fs.length;a++) {
			fields[a]=(BaseField) fs[a].get(tmp);
		}
		hm.put("packagepath", packagepath);
		hm.put("daopath", daopath);
		hm.put("servicepath", servicepath);
		hm.put("actionpath", actionpath);
		hm.put("TableBean", tmp);
		hm.put("fields", fields);
		new TemplateGenerate(hm, templatePackage + "templateDAO.vm",
				exportPackage + "/dao/"+name+"DAO.java").mergeTemplate("gb2312");
		new TemplateGenerate(hm, templatePackage + "templateService.vm",
				exportPackage + "/service/"+name+"Service.java").mergeTemplate("gb2312");
		ConfigEdit.addToSRCSpring(tmp,daopath,servicepath,"");
		ConfigEdit.addToFlex(name+"Service");
//		new TemplateGenerate(hm, templatePackage + "list.vm",
//				exportPackage + "list.vm").mergeTemplate("UTF-8");
//		new TemplateGenerate(hm, templatePackage + "add.vm",
//		exportPackage + "add.vm").mergeTemplate("UTF-8");
//		new TemplateGenerate(hm, templatePackage + "update.vm",
//				exportPackage + "update.vm").mergeTemplate("UTF-8");

//		new TemplateGenerate(hm, templatePackage + "templateForm.vm",
//				exportPackage + name+"Form.java").mergeTemplate("UTF-8");
//		new TemplateGenerate(hm, templatePackage + "templateAction.vm",
//				exportPackage + name+"Action.java").mergeTemplate("UTF-8");
//		ConfigEdit.addToSpring(name);
//		ConfigEdit.addToSRCSpring(name);
//		ConfigEdit.addToStruts(name);
	}
	
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		generateOneFlexBean(new Author());
		generateOneFlexBean(new AuthorDetail());
		generateOneFlexBean(new Role());
		generateOneFlexBean(new DataDict());
		generateOneFlexBean(new DataDictCatalog());
		generateOneFlexBean(new ExcutionExt());
		generateOneFlexBean(new Job_Role());
		generateOneFlexBean(new Job());
		generateOneFlexBean(new Organ_Job());
		generateOneFlexBean(new Organ());
		generateOneFlexBean(new Role_AuthorDetail());
		generateOneFlexBean(new TaskExt());
//		createOne(new ProductRecordDetail());
//		generateOneFlexBean(new Admin());
//		generateOneFlexBean(new ProductRecord());
//		generateOneFlexBean(new ProductType());
//		generateOneFlexBean(new Mould());
	}
	/**
	 * 生成所有的dao、service、spring、flex配置文件
	 */
       public static void createAll() {
		propertiesOperator rc = new propertiesOperator("E:/workplace/new_ssh/WebRoot/WEB-INF/classes/beans.properties");// 相对路径
		Set<Entry<Object, Object>> set = rc.getAll();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Entry<String, String> e=(Entry<String, String>) iterator.next();
			try {
				Class clazz=Class.forName(e.getKey());
				BaseTable tb=(BaseTable)clazz.newInstance();
				createOne(tb);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
       
       /**
   	 * 自动生成生成flexbean
   	 */
   	public void generateFLEXBean(){
   		propertiesOperator rc = new propertiesOperator("E:/workplace/JNLXC/WebRoot/WEB-INF/classes/beans.properties");// 相对路径
   		Set<Entry<Object, Object>> set = rc.getAll();
   		Iterator iterator = set.iterator();
   		while (iterator.hasNext()) {
   			Entry<String, String> e=(Entry<String, String>) iterator.next();
   			try {
   				Class clazz=Class.forName(e.getKey());
   				BaseTable tb=(BaseTable)clazz.newInstance();
   				File f=new File("src/flexModul/"+tb.getClass().getSimpleName()+".as");
   				f.createNewFile();
   				FileWriter fw=new FileWriter(f);
   				fw.write(tb.printFLEXList());
   				fw.close();
   				System.out.println(tb.printFLEXList());
   			} catch (Exception e1) {
   				// TODO Auto-generated catch block
   				e1.printStackTrace();
   			}
   		}
   	}
   	public static void generateOneFlexBean(BasicModel tb) {
   		File f=new File("src/flexModul/"+tb.getClass().getSimpleName()+".as");
			try {
				f.createNewFile();
			FileWriter fw=new FileWriter(f);
			fw.write(tb.printFLEXList());
			fw.close();
			System.out.println(tb.printFLEXList());
			} catch (IOException e) {
				e.printStackTrace();
			}
   	}

}