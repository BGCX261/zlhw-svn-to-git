package com.ZLHW.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jbpm.api.Execution;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.IdentityService;
import org.jbpm.api.ManagementService;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.jbpm.api.model.ActivityCoordinates;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.env.EnvironmentFactory;
import org.jbpm.pvm.internal.env.EnvironmentImpl;
import org.jbpm.pvm.internal.history.model.HistoryActivityInstanceImpl;
import org.jbpm.pvm.internal.history.model.HistoryProcessInstanceImpl;
import org.jbpm.pvm.internal.history.model.HistoryTaskInstanceImpl;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.model.TransitionImpl;
import org.jbpm.pvm.internal.task.ParticipationImpl;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.Form.Page;
import com.ZLHW.base.service.BaseService;
import com.ZLHW.common.JBPM.FlowStateImage;
import com.ZLHW.common.JBPM.HistoryTask;
import com.ZLHW.common.JBPM.ImgLocate;
import com.ZLHW.common.JBPM.MyRequest;
import com.ZLHW.common.JBPM.MyTask;
@Service("FlowService")
@RemotingDestination(channels={"my-amf"},serviceAdapter="flexcontrol") 
public class FlowService extends BaseService{
	private ProcessEngine processEngine;
	
	private Session getSession(){
		return this.getDao().getSession();
	}
	
	public String getkbsFlows(){
		return "../../kbsFlows/";
	}
	
	/**
	 * 工作流引擎，用于获取服务(通过spring注入获取)
	 * @return
	 */
	public ProcessEngine getProcessEngine() {
		return processEngine;
	}
	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	/**
	 * 包含了用来管理发布流程的所有方法
	 * @return
	 */
	public RepositoryService getRepositoryService() {
		return processEngine.getRepositoryService();
	}

	/**
	 * 用来执行流程
	 * @return
	 */
	public ExecutionService getExecutionService() {
		return processEngine.getExecutionService();
	}

	/**
	 * 提供对任务列表的访问途径和任务处理
	 * @return
	 */
	public TaskService getTaskService() {
		return processEngine.getTaskService();
	}

	/**
	 * 历史记录服务
	 * @return
	 */
	public HistoryService getHistoryService() {
		return processEngine.getHistoryService();
	}

	/**
	 * JBPM自带的用户-用户组管理服务
	 * @return
	 */
	public IdentityService getIdentityService() {
		return processEngine.getIdentityService();
	}

	/**
	 * 管理服务通常用来管理job
	 * @return
	 */
	public ManagementService getManagementService() {
		return processEngine.getManagementService();
	}
	
	public void getCurrentTask(String excutionId){
		Execution execution=this.getExecutionService().findExecutionById(excutionId);
		if(execution!=null){
			ProcessInstance processInstance=(ProcessInstance) execution.getProcessInstance();
			Set<String> activityNames = processInstance.findActiveActivityNames();
			for(String activityName:activityNames){
				this.getSession().createQuery("from TaskImpl t where t.processInstance.id = '"+processInstance.getId()+"' and name='"+activityName+"'");
			}
		}
		
	}
	/**
	 * 查找当前用户的所有任务
	 * @param request
	 * @return
	 */
	@Transactional
	public Page  findAllTask(Page page){
		String hybh=this.getAdmin().getAccount();
		String startTime=(String) page.getQueryCondition().get("startTime");
		String endTime=(String) page.getQueryCondition().get("endTime");
		String excutionkey=(String) page.getQueryCondition().get("excutionkey");
		String excutionType=(String) page.getQueryCondition().get("excutionType");
		int fromIdx =page.getFromIdx();
		int pageSize =page.getPageSize();
		 StringBuilder hql = new StringBuilder();
		    hql.append(" from ").append(TaskImpl.class.getName()).append(" t where 1=1 and t.name<>'发起请求' ");
		    hql.append(" and (t.assignee = '"+hybh+"'");
		    hql.append(" or t in (select p.task from "+ParticipationImpl.class.getName()+" p where p.type='candidate' and p.userId='"+hybh+"'))");
		    if(startTime!=null&&endTime!=null){
		    	hql.append(" and (to_char(t.createTime)>='"+startTime+"' and to_char(t.createTime)<='"+endTime+"')");
		    }
		    if(excutionkey!=null){
		    	hql.append(" and t.processInstance.key  ='"+excutionkey+"'");
		    }
		    if(excutionType!=null){
		    	hql.append(" and t.processInstance like '"+excutionType+"%'");
		    }
		    hql.append(" order by t.createTime desc");
		    List<TaskImpl> tasks=this.getSession().createQuery(hql.toString()).setFirstResult(fromIdx).setMaxResults(pageSize).list();
			List<MyTask> myTask=new ArrayList();
			for(TaskImpl t:tasks){
				myTask.add(new MyTask(t));
			}
			Object o = this.getSession().createQuery("select count(*) "+hql.toString()).list().get(0);
			Long total=(Long)o;
			page.setResultData(myTask);
			page.setTotalClum(total.intValue());
			page.setTotalPage(total.intValue()/pageSize+1);
			return page;
	}
	
	/**
	 * 查找当前用户的所有请求
	 * @param request
	 * @return
	 */
	@Transactional
	public Page  findAllRequestTasks(Page page){
		String hybh=this.getAdmin().getAccount();
		String startTime=(String) page.getQueryCondition().get("startTime");
		String endTime=(String) page.getQueryCondition().get("endTime");
		String excutionkey=(String) page.getQueryCondition().get("excutionkey");
		String excutionType=(String) page.getQueryCondition().get("excutionType");
		String nodeName=(String) page.getQueryCondition().get("nodeName");
		String excutionState=(String) page.getQueryCondition().get("excutionState");//excutionState为active或ended
		int fromIdx =page.getFromIdx();
		int pageSize =page.getPageSize();		StringBuffer hql=new StringBuffer(" from "+HistoryActivityInstanceImpl.class.getName()+" t where 1=1 ");
		hql.append("and t.activityName='发起请求' ");
		hql.append("and t.historyTask.assignee='"+this.getAdmin().getAccount()+"' ");
		if(startTime!=null&&!startTime.equals("")){
			hql.append(" and to_char(t.historyProcessInstance.startTime,'YYYY-MM-DD')>='"+startTime+"' ");
		}
		if(endTime!=null&&!endTime.equals("")){
			hql.append(" and to_char(t.historyProcessInstance.startTime,'YYYY-MM-DD')<='"+endTime+"' ");
		}
		
		if(excutionState!=null&&!excutionState.equals("")){
			hql.append(" and t.historyProcessInstance.state='"+excutionState+"' ");
		}
		if(excutionkey!=null&&!excutionkey.equals("")){
			hql.append(" and t.historyProcessInstance.key  ='"+excutionkey+"'");
		}
		if(excutionType!=null&&!excutionType.equals("")){
			hql.append(" and t.historyProcessInstance.processDefinitionId like '"+excutionType+"%'");
		}
		if(nodeName!=null&&!nodeName.equals("")){
			hql.append(" and t.historyProcessInstance in (select t2.historyProcessInstance from "+HistoryActivityInstanceImpl.class.getName()+" t2 where  t2.activityName='"+nodeName+"' and t2.endTime is null )");
		}

		List<HistoryProcessInstanceImpl> excutions=this.getSession().createQuery("select distinct t.historyProcessInstance  "+hql.append(" order by t.historyProcessInstance.startTime desc ").toString()).setFirstResult(fromIdx).setMaxResults(pageSize).list();
		List<MyRequest> myRequests=new ArrayList();
		for(HistoryProcessInstanceImpl t:excutions){
			MyRequest myRequest=new MyRequest(t);
			myRequests.add(myRequest);
			if(!"ended".equals(excutionState)){
				HistoryTask task = getUniqueCurrentTask(t.getProcessInstanceId());
				if( task != null){
					myRequest.setCurrentTaskId(task.getTaskId());
					myRequest.setCurrentTaskName(task.getActivityName());
					myRequest.setCurrentType(task.getState());
					myRequest.setLastTransition(task.getTransition());
				}
				
			}
		}
		Object o = this.getSession().createQuery("select count(distinct t.historyProcessInstance.processDefinitionId ) "+hql.toString()).list().get(0);
		Long total=(Long)o;
		page.setResultData(myRequests);
		page.setTotalClum(total.intValue());
		page.setTotalPage(total.intValue()/pageSize+1);
		return page;
	}
	/**
	 * 获取流程当前的任务
	 * @param excutionId
	 * @return
	 */
	public List<HistoryTask> getCurrentTasks(String excutionId){
		String HQL = "from "+HistoryActivityInstanceImpl.class.getName()
		+" t where t.historyProcessInstance.processInstanceId = '"+excutionId+"' " +
				"and t.type='task' " +
				"and t.HistoryTaskInstanceImpl.state is null";
		List<HistoryTaskInstanceImpl> list=this.getSession().createQuery(HQL).list();
		List<HistoryTask> l=new ArrayList();
		for(HistoryTaskInstanceImpl hai:list){
			l.add(new HistoryTask(hai));
		}
		return l;
	}
	/**
	 * 在单流程的情况下查找唯一的当前任务
	 * @param excutionId
	 * @return
	 */
	public HistoryTask getUniqueCurrentTask(String excutionId){
		String HQL = "from "+HistoryActivityInstanceImpl.class.getName()
		+" t where t.historyProcessInstance.processInstanceId = '"+excutionId+"' " +
				"and t.type='task' " +
				"and t.historyTask.state is null";
		List l=this.getSession().createQuery(HQL).list();
		if(l.size()==0)
			return null;
		else{
			HistoryTaskInstanceImpl i=(HistoryTaskInstanceImpl)l.get(0);
			HistoryTask h=new HistoryTask(i);
			h.setComment(this.getComment(h.getTaskid()));
			return h;
		}
	}

	
	/**
	 * 获取用户指定类型的任务记录
	 * @param workFlow
	 * @param admin
	 * @return
	 */
	public List<HistoryTask> getHistoryByExcutionId(String excutionId){
		String HQL = "from "+HistoryActivityInstanceImpl.class.getName()+" t where t.historyProcessInstance.processInstanceId = '"+excutionId+"' and type='task' order by startTime desc";
		List<HistoryTaskInstanceImpl> list=this.getSession().createQuery(HQL).list();
		List<HistoryTask> l=new ArrayList();
		for(HistoryTaskInstanceImpl hai:list){
			HistoryTask h=new HistoryTask(hai);
			h.setComment(this.getComment(h.getTaskid()));
			l.add(h);
		}
		return l;
	}

	/**
	 * 接受一个任务（针对于任务分配给用户组的情况）
	 * @param taskId 任务id
	 */
	public void takeTask(String taskId,String userId){
		this.getTaskService().takeTask(taskId,userId);
	}

	/**
	 * 取消接受任务
	 * @param taskId
	 * @throws BaseErrorModel 
	 * @throws HsException
	 */
	public void cancleTakeTask(String taskId) throws BaseErrorModel {
		Query query=this.getSession().createQuery(" from org.jbpm.pvm.internal.task.ParticipationImpl t where t.task.dbid = :dbid");
		query.setParameter("dbid", taskId);
		List<ParticipationImpl> list=query.list();
		if(list.size()==0)
			throw new BaseErrorModel("私有任务，无法取消已接受任务","");
		this.getTaskService().assignTask(taskId, null);
//		TaskImpl task = (TaskImpl) this.getDao().getSession().get(TaskImpl.class, Long.parseLong(taskId));
//		task.setAssignee(null);
//		this.getDao().getSession().update(task);
	}
	
	

	
	   /** 
	   * 动态创建连接当前任务节点至名称为destName的节点的Transition 
	   * @param taskId 任务节点ID 
	   * @param sourceTask 源节点Task
	   * @param destName  目标节点名称 
	   * @param variables 流程参数
	   */
	  public void addOutTransition(String taskIds,String destName,String transName,Map variables){
		  TaskImpl sourceTask=(TaskImpl) this.getTaskService().getTask(taskIds);
		  EnvironmentFactory environmentFactory = (EnvironmentFactory) processEngine;
		  EnvironmentImpl env=null;
		  try {
			  ProcessDefinitionImpl pd=(ProcessDefinitionImpl) this.getRepositoryService().createProcessDefinitionQuery().processDefinitionId(sourceTask.getProcessInstance().getProcessDefinitionId()).uniqueResult();
			  env = environmentFactory.openEnvironment();
			  //取得当前流程的活动定定义
			  ActivityImpl sourceActivity = pd.findActivity(sourceTask.getActivityName());
			  //取得目标的活动定义
			  ActivityImpl destActivity=pd.findActivity(destName);
			  //为两个节点创建连接
			  TransitionImpl transition = sourceActivity.createOutgoingTransition();
			  transition.setName(transName);
			  transition.setDestination(destActivity);
			  sourceActivity.addOutgoingTransition(transition);
			  System.out.println("sourceActivity.getName() = "+sourceActivity.getName());
			  System.out.println("destActivity.getName() = "+destActivity.getName());
			  String comment=(String) variables.get("comment");
			  this.getTaskService().completeTask(sourceTask.getId(),transition.getName(),variables);
			  this.addComment(sourceTask.getId(), comment);
		  }catch(Exception ex){
			  ex.getMessage();
		  }finally{  
			  env.close();
		  }
	}
	
	/**
	 * 根据任务id获取流程相关信息
	 * @param taskId
	 * @return
	 * @throws BaseErrorModel 
	 * @throws HsException 
	 */
	public FlowStateImage getFlowStateImage(String taskId) throws BaseErrorModel{
		Task task=this.getTaskService().getTask(taskId);
		return getFlowStateImageByExcutionId(task.getExecutionId());
	}
	/**
	 * 根据流程实例id获取流程相关信息
	 * @param excutionId
	 * @return
	 * @throws BaseErrorModel 
	 * @throws HsException 
	 */
	public FlowStateImage getFlowStateImageByExcutionId(String excutionId) throws BaseErrorModel{
		String flowName=excutionId.split("\\.")[0];
		FlowStateImage flowStateImage=new FlowStateImage();
		flowStateImage.setUrl("../kbsFlows/"+flowName+".png");
		Execution execution=this.getExecutionService().findExecutionById(excutionId);
		if(execution!=null){
			ProcessInstance processInstance=(ProcessInstance) execution.getProcessInstance();
			Set<String> activityNames = processInstance.findActiveActivityNames();
			Set<ImgLocate> imageLocates=new HashSet();
			for(String activityName:activityNames){
				ActivityCoordinates ac = this.getRepositoryService().getActivityCoordinates(processInstance.getProcessDefinitionId(),activityName);
				ImgLocate imageLocate=new ImgLocate(ac.getX(),ac.getY(),ac.getWidth(),ac.getHeight());
				imageLocates.add(imageLocate);
			}
			flowStateImage.setImageLocates(imageLocates);
			return flowStateImage;
		}else{
			throw new BaseErrorModel("无此流程","");
		}
	}
	/**
	 * 添加一条评论并放入流程中以便下个处理任务人可看到
	 * @param taskId
	 * @param comment
	 */
	private void addComment(String taskId,String comment){
//		if(taskId!=null&&!taskId.equals("")){
//			KmActivityInstance bean=new KmActivityInstance();
//			bean.setHistoryTaskId(taskId);
//			bean.setComments(comment);
//			this.getSession().save(bean);
//		}
	}
	/**
	 * 获得任务评论
	 * @param taskId
	 * @return
	 */
	private String getComment(String taskId){
//		KmActivityInstance bean=(KmActivityInstance) this.getSession().get(KmActivityInstance.class, taskId);
//		if(bean!= null)
//			return bean.getComments();
//		else
//			return "";
		return "";
	}

	/**
	 * 相对于流程图中无选择的情况
	 * @param taskId 任务号
	 * @param userId 用户号
	 * @throws BaseErrorModel 
	 * @throws HsException 
	 */
	public void completeTask(String taskId,String userId,String comment) throws BaseErrorModel {
		if(this.getTaskService().getTask(taskId).getAssignee()==null){
			takeTask(taskId,userId);
		}
		Map map=new HashMap();
		map.put("comment", comment);
		Set<String> set=this.getTaskService().getOutcomes(taskId);
		if(set.size()==1){
			this.getTaskService().completeTask(taskId,set.iterator().next(),map);
			this.addComment(taskId, comment);
		}
		else
			throw new BaseErrorModel("流程错误","");
	}
	/**
	 * 相对于流程图中无选择的情况,带返回参数
	 * @param taskId
	 * @param userId 用户号
	 * @param map 传递给流程的参数
	 * @throws BaseErrorModel 
	 */
	public void completeTask(String taskId,String userId,Map map,String comment) throws BaseErrorModel {
		if(this.getTaskService().getTask(taskId).getAssignee()==null){
			takeTask(taskId,userId);
		}
		map.put("comment", comment);
		Set<String> set=this.getTaskService().getOutcomes(taskId);
		if(set.size()==1){
			this.getTaskService().completeTask(taskId,set.iterator().next(),map);
			this.addComment(taskId, comment);
		}
		else
			throw new BaseErrorModel("流程错误","");
		
	}
	
	/**
	 * 有选择的情况下
	 * @param taskId 任务号
	 * @param userId 用户号
	 * @param nextName 箭头名
	 * @param comment 评论
	 */
	public void completeTask(String taskId,String userId,String nextName,String comment){
		if(this.getTaskService().getTask(taskId).getAssignee()==null){
			takeTask(taskId,userId);
		}
		Map map=new HashMap();
		map.put("comment", comment);
		this.getTaskService().completeTask(taskId,nextName);
		this.addComment(taskId, comment);
	}
	
	
	/**
	 * 有选择的情况下,带返回参数
	 * @param userId 用户号
	 * @param taskId 任务号
	 * @param nextName 箭头名
	 * @param map 传递给流程的参数
	 */
	public void completeTask(String taskId,String userId,String nextName,Map map,String comment){
		if(this.getTaskService().getTask(taskId).getAssignee()==null)
			takeTask(taskId,userId);
		map.put("comment", comment);
		this.getTaskService().completeTask(taskId,nextName, map);
		this.addComment(taskId, comment);
	}

	/**
	 * 发布一个新流程
	 * @param path 流程图jpdl位置（比如"./com/zb/JBPM/orderFlow.jpdl.xml"）
	 * @return
	 */
	public String deployFlow(String path){
		return this.getRepositoryService().createDeployment().addResourceFromClasspath(path).deploy();
	}
	/**
	 * 删除流程图版本
	 * @param id
	 */
	public void deleteDeploymentCascade(String id){
		this.getRepositoryService().deleteDeploymentCascade(id);
		
	}
	/**
	 * 凡是启动一个流程，必须有定义唯一的key，通常是相关流程涉及主表的主键id
	 * @param FlowName 发布的流程名
	 * @param id 流程涉及主表的主键id作为key
	 * @return
	 */
	public ProcessInstance startProcessInstanceByKey(String FlowName,String id){
		return this.getExecutionService().startProcessInstanceByKey(FlowName, id);
	}
	/**
	 * 删除一个正在执行的流程
	 * @param id 流程id
	 */
	public void deleteProcessInstanceCascade(String id){
		Execution execution=this.getExecutionService().findExecutionById(id);
		if(execution!=null){
		ProcessInstance processInstance=(ProcessInstance) execution.getProcessInstance();
		this.getExecutionService().deleteProcessInstanceCascade(processInstance.getId()); 
		}
	}
	/**
	 * 凡是启动一个流程，必须有定义唯一的key，通常是相关流程涉及主表的主键id
	 * @param FlowName 发布的流程名
	 * @param id 流程涉及主表的主键id作为key
	 * @param map 开启流程所需的变量
	 * @return
	 */
	public ProcessInstance startProcessInstanceByKey(String FlowName,String id,Map map){
		return this.getExecutionService().startProcessInstanceByKey(FlowName, map,id);
	}
	
	/**
	 * 根据流程名和key获取流程实例
	 * @param FlowName 流程名
	 * @param id 主键key
	 * @return
	 */
	public ProcessInstance findProcessInstanceByKey(String FlowName,String id){
		return this.getExecutionService().findProcessInstanceById(FlowName+"."+id);
	}
	
	/**
	 * 获取流程实例的内容
	 * @param excutionid 流程实例id
	 * @param key 
	 * @return
	 */
	public Object getContentMap(String taskId,String key){
		String ExecutiongId=this.getTaskService().getTask(taskId).getExecutionId();
		return this.getExecutionService().getVariable(ExecutiongId, key);
	}
	/**
	 * 级联删除所有流程
	 */
	public void clearAll(){
		List<ProcessDefinition> pdList = this.getRepositoryService().createProcessDefinitionQuery().list();
		for (ProcessDefinition pd : pdList) {
			this.getRepositoryService().deleteDeploymentCascade(pd.getDeploymentId());
		}
	}
	
}
