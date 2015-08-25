package com.ZLHW.base.dao;

/**
 *  ClassName: GenericHibernateDao.java
 *  created on 11:46:07 AM
 *  Copyrights 2008 qjyong All rights reserved.
 *  EMail: qjyong@gmail.com
 */

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.core.IsInstanceOf;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.beans.factory.annotation.Autowired;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.Form.Page;
import com.ZLHW.base.HTable.BasicModel;
import com.ZLHW.base.Trigger.Trigger;
import com.ZLHW.base.factory.BeanFactory;
import com.ZLHW.base.factory.CustomerContextHolder;
import com.ZLHW.base.service.BaseService;
import com.ZLHW.base.table.BaseTable;


public abstract class DAO<BEAN extends BasicModel, PRIMARY extends Serializable> implements IDAO<BEAN,PRIMARY >{

	@Autowired
	protected SessionFactory sessionFactory;
	protected Class<BEAN> beanClazz;
	protected Class<PRIMARY> primaryClazz;
	private List<Trigger> triggerList=new ArrayList<Trigger>();
	public void addTrigger(Trigger t) {
		this.triggerList.add(t);
	}

	@SuppressWarnings("unchecked")
	public DAO() {
		try{
		//相当于this.clazz=T.getClass();通过反射获得真实的类
		this.beanClazz = (Class<BEAN>) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		this.primaryClazz = (Class<PRIMARY>) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[1];
		}catch(Exception e){
			
		}
	}
	/**
	 * spring设置当前session
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 用于获取当前sessionFactory
	 * @return
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	


	public Session getSession() {
		try{
		return sessionFactory.getCurrentSession();
		}catch(Exception e){
			CustomerContextHolder.setCustomerType("DataSource2");//设置为另一个数据源  
			return sessionFactory.getCurrentSession();
		}
	}
	public BEAN create(BEAN entity) throws BaseErrorModel{
		getSession().save(entity);
		return entity;
	}

	public void delete(BEAN entity) throws BaseErrorModel{
		getSession().delete(entity);
	}
	public void deleteByColumn(String column,Object value) throws BaseErrorModel{
		updateHql("delete from "+beanClazz.getName()+" t where " +
				"t."+column+" = ?", value);
	}
	public void delete(List<BEAN> entities) throws BaseErrorModel{
		for(BEAN entity : entities){
			getSession().delete(entity);
		}
	}
	public void refresh(BEAN entity) throws BaseErrorModel{
		getSession().refresh(entity);
	}

	public void update(BEAN entity) throws BaseErrorModel{
		getSession().saveOrUpdate(entity);
	}
	
	public void saveOrupdate(BEAN entity) throws BaseErrorModel{
		getSession().saveOrUpdate(entity);
	}

	public void merger(BEAN entity) throws BaseErrorModel{
		getSession().merge(entity);
	}
	
	public int updateHql(String hql, Object... args) throws BaseErrorModel{
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < args.length; i++) {
			query.setParameter(i, args[i]);
		}
		return query.executeUpdate();
	}

	public int updateNamedQuery(String nameQuery, Object... args) throws BaseErrorModel{
		Query query = getSession().getNamedQuery(
				nameQuery);
		for (int i = 0; i < args.length; i++) {
			query.setParameter(i, args[i]);
		}
		return query.executeUpdate();
	}

	public List<BEAN> findNamedQuery(String nameQuery, Object... args) {
		Query query = getSession().getNamedQuery(
				nameQuery);
		for (int i = 0; i < args.length; i++) {
			query.setParameter(i, args[i]);
		}
		return query.list();
	}
	public Page findAllByPage(Page page){
		int currentPage = page.getCurrentPage();
		int pageSize = page.getPageSize();
		Criteria criteria = getSession().createCriteria(beanClazz);
		criteria.setFirstResult((currentPage-1)*pageSize);
		criteria.setMaxResults(pageSize);
		if(page.isAotuSort())
		{
			if(page.isAscending())
				criteria.addOrder(Order.asc(page.getSortKey()));
			else
				criteria.addOrder(Order.desc(page.getSortKey()));
		}
		List result = criteria.list();
		page.setResultData(result);
		int totalClum = this.getCountOfAll();
		page.setTotalClum(totalClum);
		int totalPage; 
		if(totalClum%pageSize == 0)
			totalPage=totalClum/pageSize;
		else
			totalPage=totalClum/pageSize+1;
		page.setTotalPage(totalPage);
		return page;
	}
	/**
	 * 将所有查询条件的全等查询
	 */
	public Page findByPageWithEqCondition(Page page) {
		
		Criteria criteria = getSession().createCriteria(beanClazz);
		Criteria criteriaTotal = getSession().createCriteria(beanClazz);
		
		
		Map queryCondition = page.getQueryCondition();
		for(Object key : queryCondition.keySet()){
			Object value = queryCondition.get(key);
			if(value!=null){
				try{
					//如果查询值为0 或者 为空， 则不添加查询条件
					if(!"0".equals(key) 
							&& !"".equals(key)){
						criteria.add(Expression.eq((String)key,value));
						criteriaTotal.add(Expression.eq((String)key,value));
					}
				}catch(Exception e){
					System.out.println("查询条件异常");
					return page;
				}
			}
		}
		return processPage( page,  criteria, criteriaTotal);
	}
	private Page processPage(Page page, Criteria criteria,Criteria criteriaTotal){
		int currentPage = page.getCurrentPage();
		int pageSize = page.getPageSize();
		if(page.isAotuSort())
		{
			if(page.isAscending()){
				criteria.addOrder(Order.asc(page.getSortKey()));
				criteriaTotal.addOrder(Order.asc(page.getSortKey()));
			}
			else{
				criteria.addOrder(Order.desc(page.getSortKey()));
			    criteriaTotal.addOrder(Order.desc(page.getSortKey()));
			}
		}
		
		criteria.setFirstResult((currentPage-1)*pageSize);
		criteria.setMaxResults(pageSize);
		criteriaTotal.setProjection(Projections.rowCount());
		List result = criteria.list();
		page.setResultData(result);
		Integer totalClum = (Integer)(criteriaTotal.uniqueResult());
		page.setTotalClum(totalClum);
		int totalPage; 
		if(totalClum%pageSize == 0)
			totalPage=totalClum/pageSize;
		else
			totalPage=totalClum/pageSize+1;
		page.setTotalPage(totalPage);
		return page;
	}
	/**
	 * 将所有查询条件的使用in查询
	 */
	public Page findByPageWithInListCondition(Page page) {
		Criteria criteria = getSession().createCriteria(beanClazz);
		Criteria criteriaTotal = getSession().createCriteria(beanClazz);
		
		Map queryCondition = page.getQueryCondition();
		for(Object key : queryCondition.keySet()){
			Object value = queryCondition.get(key);
			if(List.class.isInstance(value)){
				List list = (List) value;
				try{
					criteria.add(Expression.in((String)key,list));
					criteriaTotal.add(Expression.in((String)key,list));
				}catch(Exception e){
					System.out.println("查询条件异常");
					return page;
				}
			}
		}
			
//		List list = (List) page.getQueryCondition().get("value");
//		if(list!=null){
//			try{
//				criteria.add(Expression.in((String)page.getQueryCondition().get("key"),list));
//				criteriaTotal.add(Expression.in((String)page.getQueryCondition().get("key"),list));
//			}catch(Exception e){
//				System.out.println("查询条件异常");
//			}
//		}
//		else
//			return page;
		return processPage( page,  criteria, criteriaTotal);
	}
	/**
	 * 通过查询条件模糊查询
	 */
	public Page findByPageWithLikeCondition(Page page){
		Criteria criteria = getSession().createCriteria(beanClazz);
		Criteria criteriaTotal = getSession().createCriteria(beanClazz);
		
		Map queryCondition = page.getQueryCondition();
		for(Object key : queryCondition.keySet()){
			Object value = queryCondition.get(key);
			if(value!=null &&
					String.class.isInstance(value)){
				//如果查询值为0 或者 为空， 则不添加查询条件
				if(!"0".equals(value) 
						&& !"".equals(value)){
					criteria.add(Expression.like((String)key, "%"+value+"%"));
					criteriaTotal.add(Expression.like((String)key, "%"+value+"%"));
				}
			}
		}
		
		return processPage( page,  criteria, criteriaTotal);
	}
	/**
	 *  通过匹配Page限定条件中的开始时间和结束时间进行分页查询
	 * @param page
	 * @return List
	 */
	public Page findByPageWithTimeCondition(Page page){
		Criteria criteria = getSession().createCriteria(beanClazz);
		Criteria criteriaTotal = getSession().createCriteria(beanClazz);
		Map queryCondition = page.getQueryCondition();
		Object key = queryCondition.keySet().iterator().next();
		Object value = queryCondition.get(key);
		if(value!=null){
			ArrayList timeList = (ArrayList) value;
			if(timeList.size() == 2){
				if(timeList.get(0)!=null && timeList.get(1)!=null){
					criteria.add(Expression.between((String)key, timeList.get(0),timeList.get(1)));
					criteriaTotal.add(Expression.between((String)key, timeList.get(0),timeList.get(1)));
				}
				else if(timeList.get(0)!=null){
					criteria.add(Expression.ge((String)key, timeList.get(0)));
					criteriaTotal.add(Expression.ge((String)key, timeList.get(0)));
				}
				else if(timeList.get(1)!=null){
					criteria.add(Expression.le((String)key, timeList.get(1)));
					criteriaTotal.add(Expression.le((String)key, timeList.get(1)));
				}
			}
			
		}
		else
			return page;
		return processPage( page,  criteria, criteriaTotal);
	}
	/**
	 *  通过Criterion数组查询 不通过queryCondition
	 * @param page
	 * @return List
	 */
	public Page findByPageWithExpression(Page page,List<Criterion> args){
		Criteria criteria = getSession().createCriteria(beanClazz);
		Criteria criteriaTotal = getSession().createCriteria(beanClazz);

		if(args!=null){
			for(Criterion arg : args){
				criteria.add(arg);
				criteriaTotal.add(arg);
			}
		}
		else
			return page;
		
		return processPage( page,  criteria, criteriaTotal);
	}

	/**
	 *  通过HQL查询 
	 * @param page
	 * @return List
	 */
	public Page findByPageWithHQL(Page page,String HQL, Object...args){
		if(HQL == null)
			return page;
		int currentPage = page.getCurrentPage();
		int pageSize = page.getPageSize();
		int fromIdx = currentPage*pageSize-pageSize;
		List<BEAN> result=this.findByHQL(HQL, fromIdx, pageSize, args);
		page.setResultData(result);
		String TOTALHQL="select count(*) "+HQL;
		TOTALHQL=TOTALHQL.replaceAll("order by.*desc", "");
		TOTALHQL=TOTALHQL.replaceAll("order by.*asc", "");
		int totalClum = this.getCountOfAll(TOTALHQL, args);
		int totalPage; 
		if(totalClum%pageSize == 0)
			totalPage=totalClum/pageSize;
		else
			totalPage=totalClum/pageSize+1;
		page.setTotalPage(totalPage);
		return page;
	}
	/**
	 * 通过SQL查询 
	 * @param page
	 * @return List
	 */
	public Page findByPageWithSQL(Page page,String SQL, String...args){
		if(SQL == null)
			return page;
		int currentPage = page.getCurrentPage();
		int pageSize = page.getPageSize();
		int fromIdx = currentPage*pageSize-pageSize;
		List<Object> result=this.findBySQL(SQL, fromIdx, fromIdx+pageSize,args);
		page.setResultData(result);
		String TOTALSQL=SQL.replace(SQL.substring(0, SQL.indexOf("from")), "select count(*) ");
		List<Object> totleList = this.findBySQL(TOTALSQL, args);
		int totalClum = 0;
		if(totleList!=null && totleList.size()==1)
			totalClum = ((BigDecimal) totleList.get(0)).intValue();
		int totalPage; 
		if(totalClum%pageSize == 0)
			totalPage=totalClum/pageSize;
		else
			totalPage=totalClum/pageSize+1;
		page.setTotalPage(totalPage);
		return page;
	}
	/**
	 *  通过:state形式HQL查询 
	 * @param page
	 * @return List
	 */
	public Page findByPageWithTmpHQL(Page page,String HQL, List<QueryCondition> queryConditions){
		if(HQL == null)
			return page;
		int currentPage = page.getCurrentPage();
		int pageSize = page.getPageSize();
		int fromIdx = currentPage*pageSize-pageSize;
		List<BEAN> result=this.findByTmpHQL(HQL, fromIdx, pageSize, queryConditions);
		page.setResultData(result);
		String TOTALHQL="select count(*) "+HQL;
		int totalClum = this.getCountOfAllByTmp(TOTALHQL, queryConditions);
		int totalPage; 
		if(totalClum%pageSize == 0)
			totalPage=totalClum/pageSize;
		else
			totalPage=totalClum/pageSize+1;
		page.setTotalPage(totalPage);
		page.setTotalClum(totalClum);
		return page;
	}
	
	
	public List<BEAN> findByHQL(String HQL, Object... args) {
		Query query = getSession().createQuery(HQL);
		for (int i = 0; i < args.length; i++) {
			query.setParameter(i, args[i]);
		}
		return query.list();
	}

	public List<Object> findBySQL(String SQL, String... args) {
		Query query = getSession().createSQLQuery(SQL);
		for (int i = 0; i < args.length; i++) {
			query.setParameter(i, args[i]);
		}
		return query.list();
	}
	
	public List<Object> findBySQL(String sql, int fromIdx, int fetchCount,
			String... args) {

		Query q = getSession().createSQLQuery(sql);
		if (args.length == 0)
			q.setCacheable(true);
		for (int i = 0; i < args.length; i++) {
			q.setParameter(i, args[i]);
		}
		if (fromIdx > 0)
			q.setFirstResult(fromIdx);
		if (fetchCount > 0)
			q.setMaxResults(fetchCount);
		return q.list();
	}
	
	public List<BEAN> findByHQL(String hql, int fromIdx, int fetchCount,
			Object... args) {

		Query q = getSession().createQuery(hql)
				.setReadOnly(true);
		if (args.length == 0)
			q.setCacheable(true);
		for (int i = 0; i < args.length; i++) {
			q.setParameter(i, args[i]);
		}
		if (fromIdx > 0)
			q.setFirstResult(fromIdx);
		if (fetchCount > 0)
			q.setMaxResults(fetchCount);
		return q.list();
	}
	
	public List<BEAN> findByTmpHQL(String hql,List<QueryCondition> queryConditions){
		Query query = getSession().createQuery(hql);
		if(queryConditions!=null)
		for(QueryCondition qc:queryConditions){
			query.setEntity(qc.getName(), qc.getContent());
		}
		return query.list();
	}
	
	public List<BEAN> findByTmpHQL(String hql, int fromIdx, int fetchCount,
			List<QueryCondition> queryConditions) {

		Query q = getSession().createQuery(hql)
				.setReadOnly(true);
		for(QueryCondition qc:queryConditions){
			q.setParameter(qc.getName(), qc.getContent());
		}
		if (fromIdx > 0)
			q.setFirstResult(fromIdx);
		if (fetchCount > 0)
			q.setMaxResults(fetchCount);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<BEAN> findAll() {
		return getSession().createCriteria(beanClazz).list();
	}

	@SuppressWarnings("unchecked")
	public List<BEAN> findAllAsc() {
		Criteria criteria = getSession().createCriteria(beanClazz);
		BasicModel bt ;
		try {
			bt = beanClazz.newInstance();
			criteria.addOrder(Order.asc(bt.getPrimaryColumn()));
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<BEAN> findList(int pageNo, int pageSize) {
		return getSession().createCriteria(beanClazz)
				.setFirstResult((pageNo - 1) * pageSize)
				.setMaxResults(pageSize).list();
	}

	public int getCountOfAll() {
		Integer count = (Integer) getSession()
				.createCriteria(beanClazz).setProjection(Projections.rowCount())
				.uniqueResult();
		if (null == count) {
			return 0;
		} else {
			return count.intValue();
		}
	}

	public int getCountOfAll(String HQL, Object... args) {
		Query query = getSession().createQuery(HQL);
		for (int i = 0; i < args.length; i++) {
			query.setParameter(i, args[i]);
		}
		Object o = query.list().get(0);
		Long total=(Long)o;
		return total.intValue();
	}
	public int getCountOfAllByTmp(String HQL, List<QueryCondition> queryConditions) {
		Query query = getSession().createQuery(HQL);
		for(QueryCondition qc:queryConditions){
			query.setParameter(qc.getName(), qc.getContent());
		}
		Object o = query.list().get(0);
		Long total=(Long)o;
		return total.intValue();
	}
	public int getCountOfAllBySQL(String SQL, String... args) {
		Query query = getSession().createSQLQuery(SQL);
		for (int i = 0; i < args.length; i++) {
			query.setParameter(i, args[i]);
		}
		Object o = query.list().get(0);
		BigDecimal total=(BigDecimal)o;
		return total.intValue();
	}
	
	public BEAN getById(PRIMARY id) {
		BEAN b= (BEAN) getSession().get(beanClazz, id);
		return b;
	}
	
	public BEAN loadById(PRIMARY id) {
		BEAN b= (BEAN) getSession().load(beanClazz, id);
		return b;
	}
	
	public List<BEAN> getByColumn(String columnName, Object arg) {
		Criteria criteria = getSession().createCriteria(beanClazz);
		criteria.add(Expression.eq(columnName, arg));
		return criteria.list();
	}
	/**
     * 根据某个字段获取查询数组,正序排列
     * @param id
     * @return
     */
	public List<BEAN> getByColumnAsc(String columnName, Object arg) {
		Criteria criteria = getSession().createCriteria(beanClazz);
		criteria.add(Expression.eq(columnName, arg));
		BasicModel bt ;
		try {
			bt = beanClazz.newInstance();
			criteria.addOrder(Order.asc(bt.getPrimaryColumn()));
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return criteria.list();
	}
	public void deleteById(PRIMARY id) throws BaseErrorModel{
		delete(getById(id));
	}
	

}
