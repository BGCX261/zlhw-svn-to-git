package com.ZLHW.base.service;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.hibernate.criterion.Criterion;
import org.jbpm.api.Execution;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.IdentityService;
import org.jbpm.api.ManagementService;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.TaskService;
import org.jbpm.api.model.ActivityCoordinates;
import org.jbpm.api.task.Task;
import org.jbpm.examples.task.assignee.Order;
import org.jbpm.pvm.internal.env.EnvironmentFactory;
import org.jbpm.pvm.internal.env.EnvironmentImpl;
import org.jbpm.pvm.internal.history.model.HistoryActivityInstanceImpl;
import org.jbpm.pvm.internal.history.model.HistoryTaskInstanceImpl;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.model.TransitionImpl;
import org.jbpm.pvm.internal.task.ParticipationImpl;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.springframework.transaction.annotation.Transactional;

import sun.rmi.runtime.Log;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.Form.Page;
import com.ZLHW.base.Trigger.Trigger;
import com.ZLHW.base.dao.IDAO;
import com.ZLHW.base.factory.BeanFactory;
import com.ZLHW.common.JBPM.FlowStateImage;
import com.ZLHW.common.JBPM.HistoryTask;
import com.ZLHW.common.JBPM.ImgLocate;
import com.ZLHW.common.JBPM.MyTask;
import com.ZLHW.common.model.Admin;
import com.ZLHW.common.service.AdminService;
import com.danga.MemCached.MemCachedClient;

import flex.messaging.FlexContext;
import flex.messaging.FlexSession;

@Transactional
public class BaseService<BEAN, PRIMARY> implements IService<BEAN, PRIMARY> {
	protected Logger log ;
	protected MemCachedClient mcc = new MemCachedClient();
	
	private String serverUrl;
	private String serverPort;
	
	protected Class<BEAN> beanClazz;
	protected Class<PRIMARY> primaryClazz;

	@SuppressWarnings("unchecked")
	public BaseService() {
		try{
		log = Logger.getLogger(this.getClass());
		//相当于this.clazz=T.getClass();通过反射获得真实的类
		this.beanClazz = (Class<BEAN>) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		this.primaryClazz = (Class<PRIMARY>) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[1];
		}catch(Exception e){
			
		}
	}
	
	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	
	private IDAO<BEAN, PRIMARY> dao;

	public IDAO<BEAN, PRIMARY> getDao() {
		if(dao==null){
			String beanClazzSimpleName = beanClazz.getSimpleName();
			for(Field field : this.getClass().getDeclaredFields()){
				if(field.getType().getSimpleName().equals(beanClazzSimpleName+"Dao")
						|| field.getType().getSimpleName().equals(beanClazzSimpleName+"DAO")){
					field.setAccessible(true);
					try {
						Object value = field.get(this);
						if(value!=null){
							this.setDao((IDAO<BEAN, PRIMARY>) value);
							break;
						}
					} catch (Exception e) {
						log.info(this.getClass().getSimpleName()+"无法自动注入对应的dao");
					}
				}
				
			}
		}
		return dao;
	}
	private String lowerCaseFirstWord(String str){
		return str.substring(0, 1).toLowerCase()+str.substring(1);
	}

	public void setDao(IDAO<BEAN, PRIMARY> dao) {
		this.dao = dao;
	}

	public BEAN create(BEAN entity) throws BaseErrorModel  {
		return getDao().create(entity);
	}
	
	public BEAN loadById(PRIMARY id) {
		return getDao().loadById(id);
	}

	public void delete(BEAN entity) throws BaseErrorModel {
		try{
			getDao().delete(entity);	
		}catch (Exception e) {
			log.error("删除"+entity+"异常", e);
			throw new BaseErrorModel("无法正常删除，请先删除关联数据","");
		}
		
	}

	public void deleteById(PRIMARY id) throws BaseErrorModel {
		getDao().deleteById(id);
	}
	
	public void deleteById(PRIMARY[] ids) throws BaseErrorModel {
		for(PRIMARY id:ids) {
			getDao().deleteById(id);
		}
	}
	
	
	public BEAN refresh(BEAN entity) throws BaseErrorModel{
		getDao().refresh(entity);
		return entity;
	}

	public List<BEAN> findAll() {
		return getDao().findAll();
	}
	
	public List<BEAN> findAllAsc() {
		return getDao().findAllAsc();
	}

	public List findHql(String HQL, Object... args) {
		return getDao().findByHQL(HQL, args);
	}
	
	public List findHql(String hql, int fromIdx, int fetchCount, Object... args) {
		return getDao().findByHQL(hql, fromIdx, fetchCount, args);
	}

	public List<BEAN> findList(int pageNo, int pageSize) {
		return getDao().findList(pageNo, pageSize);
	}

	public BEAN getById(PRIMARY id) {
		return (BEAN) getDao().getById(id);
	}

	public List<BEAN> getByColumn(String columnName,Object arg) {
		return  getDao().getByColumn(columnName, arg);
	}
	
	public List<BEAN> getByColumnAsc(String columnName,Object arg) {
		return  getDao().getByColumnAsc(columnName, arg);
	}
	
	public int getCountOfAll() {
		return getDao().getCountOfAll();
	}

	public int getCountOfAll(String HQL, Object... args) {
		return getDao().getCountOfAll(HQL, args);
	}

	public void update(BEAN entity) throws BaseErrorModel {
		getDao().update(entity);
	}

	public List findHqlCached(String hql, int time, int fromIdx, int fetchCount,
			Object... args) {
		StringBuffer cacheKey = new StringBuffer(this.getClass().getName())
				.append(".findHql:").append(fromIdx).append(fetchCount);
		for(Object o:args) {
			cacheKey.append(args.toString());
		}
		List l = (List) mcc.get(cacheKey.toString());
		if (l == null) {
			l = this.findAll();
			mcc.set(cacheKey.toString(), l, time);
		}
		return l;
	}

	public List<BEAN> findAllCached(int time) {
		StringBuffer cacheKey = new StringBuffer(this.getClass().getName())
				.append(".findAll");
		List l = (List) mcc.get(cacheKey.toString());
		if (l == null) {
			l = this.findAll();
			mcc.set(cacheKey.toString(), l, time);
		}
		return l;
	}

	public List<BEAN> findListCached(int time, int pageNo, int pageSize) {
		StringBuffer cacheKey = new StringBuffer(this.getClass().getName())
				.append(".findListCached");
		cacheKey.append(pageNo).append(pageSize);
		List l = (List) mcc.get(cacheKey.toString());
		if (l == null) {
			l = this.findList(pageNo, pageSize);
			mcc.set(cacheKey.toString(), l, time);
		}
		return l;
	}

	public BEAN getByIdCached(int time, PRIMARY id) {
		StringBuffer cacheKey = new StringBuffer(this.getClass().getName())
				.append(".getByIdCached:").append(id);
		BEAN bean = (BEAN) mcc.get(cacheKey.toString());
		if (bean == null) {
			bean = this.getById(id);
			mcc.set(cacheKey.toString(), bean, time);
		}
		return bean;
	}

	public int getCountOfAllCached(int time) {
		StringBuffer cacheKey = new StringBuffer(this.getClass().getName())
				.append(".getCountOfAllCached");
		Integer total = (Integer) mcc.get(cacheKey.toString());
		if (total == null) {
			total = this.getCountOfAll();
			mcc.set(cacheKey.toString(), total, time);
		}
		return total;
	}

	public int getCountOfAllCached(String HQL, int time, String... args) {
		StringBuffer cacheKey = new StringBuffer(this.getClass().getName())
				.append(".getCountOfAllCached:");
		cacheKey.append(HQL);
		for (String str : args) {
			cacheKey.append(str);
		}
		Integer total = (Integer) mcc.get(cacheKey.toString());
		if (total == null) {
			total = this.getCountOfAll(HQL, args);
			mcc.set(cacheKey.toString(), total, time);
		}
		return total;
	}
	/**
	 * 获取当前用户的session
	 * @return
	 */
	public FlexSession getFlexSession() {
		return flex.messaging.FlexContext.getFlexSession();
	}
	/**
	 * 获取当前用户
	 */
	public Admin getAdmin(){
		return (Admin) getFlexSession().getAttribute("Admin");
	}
	/**
	 * 获取测试用的springbean
	 * @return
	 */
	public BaseService getTestService(){
		return (BaseService)BeanFactory.LookUp(this.getClass().getSimpleName());
	}
	

	public Page findByPageWithHQL(Page page,String HQL, Object...args){
		return this.getDao().findByPageWithHQL(page, HQL, args);
	}

	public Page findAllByPage(Page page) {
		return this.getDao().findAllByPage(page);
	}
}
