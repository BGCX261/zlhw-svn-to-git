package com.ZLHW.base.Interface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.IdentityService;
import org.jbpm.api.ManagementService;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.jbpm.api.task.Task;

import com.ZLHW.base.Form.Page;
import com.ZLHW.common.JBPM.MyTask;

public interface IFlow {
	
	/**
	 * 工作流引擎，用于获取服务(通过spring注入获取)
	 * @return
	 */
	public ProcessEngine getProcessEngine();
	
	public void setProcessEngine(ProcessEngine processEngine);

	/**
	 * 包含了用来管理发布流程的所有方法
	 * @return
	 */
	public RepositoryService getRepositoryService() ;

	/**
	 * 用来执行流程
	 * @return
	 */
	public ExecutionService getExecutionService();

	/**
	 * 提供对任务列表的访问途径和任务处理
	 * @return
	 */
	public TaskService getTaskService() ;

	/**
	 * 历史记录服务
	 * @return
	 */
	public HistoryService getHistoryService() ;

	/**
	 * JBPM自带的用户-用户组管理服务
	 * @return
	 */
	public IdentityService getIdentityService() ;

	/**
	 * 管理服务通常用来管理job
	 * @return
	 */
	public ManagementService getManagementService() ;

	/**
	 * 获取当前用户的任务
	 * @return
	 */
	public List<MyTask> findPersonalTasks();
	/**
	 * 获取当前用户组的任务
	 * @return
	 */
	public List<MyTask> findGroupTasks();
	/**
	 * 跟去流程名和关键字查找组任务
	 * @param flowName 流程民称
	 * @param keyid 关键字
	 * @return
	 */
	public List<MyTask> findGroupTasksByKey(String flowName,String keyid);
	/**
	 * 查询所有关键字为keyid的组任务
	 * @param keyid
	 * @return
	 */
	public List<MyTask> findAllGroupTasksByKey(String keyid);
	/**
	 * 跟去流程名和关键字查找个人任务
	 * @param flowName 流程民称
	 * @param keyid 关键字
	 * @return
	 */
	public List<MyTask> findPersonalTasksByKey(String flowName,String keyid);
	/**
	 * 查询所有关键字为id的个人任务
	 * @param keyid
	 * @return
	 */
	public List<MyTask> findAllPersonalTasksByKey(String keyid);

	/**
	 * 接受一个任务（针对于任务分配给用户组的情况）
	 * @param taskId 任务id
	 */
	public void takeTask(String taskId);
	

	/**
	 * 相对于流程图中无选择的情况
	 * @param taskId 任务号
	 */
	public void completeTask(String taskId);
	/**
	 * 相对于流程图中无选择的情况,带返回参数
	 * @param taskId
	 * @param map 传递给流程的参数
	 */
	public void completeTask(String taskId,Map map);
	
	/**
	 * 有选择的情况下
	 * @param taskId 任务号
	 * @param nextName 箭头名
	 */
	public void completeTask(String taskId,String nextName);
	
	/**
	 * 有选择的情况下,带返回参数
	 * @param taskId 任务号
	 * @param nextName 箭头名
	 * @param map 传递给流程的参数
	 */
	public void completeTask(String taskId,String nextName,Map map);

	/**
	 * 发布一个新流程
	 * @param path 流程图jpdl位置（比如"./com/zb/JBPM/orderFlow.jpdl.xml"）
	 * @return
	 */
	public String deployFlow(String path);
	/**
	 * 删除流程图版本
	 * @param id
	 */
	public void deleteDeploymentCascade(String id);
	/**
	 * 凡是启动一个流程，必须有定义唯一的key，通常是相关流程涉及主表的主键id
	 * @param FlowName 发布的流程名
	 * @param id 流程涉及主表的主键id作为key
	 * @return
	 */
	public ProcessInstance startProcessInstanceByKey(String FlowName,String id);
	/**
	 * 删除一个正在执行的流程
	 * @param id 流程id
	 */
	public void deleteProcessInstanceCascade(String id);
	/**
	 * 凡是启动一个流程，必须有定义唯一的key，通常是相关流程涉及主表的主键id
	 * @param FlowName 发布的流程名
	 * @param id 流程涉及主表的主键id作为key
	 * @param map 开启流程所需的变量
	 * @return
	 */
	public ProcessInstance startProcessInstanceByKey(String FlowName,String id,Map map);
	/**
	 * 根据流程名和key获取流程实例
	 * @param FlowName 流程名
	 * @param id 主键key
	 * @return
	 */
	public ProcessInstance findProcessInstanceByKey(String FlowName,String id);
	
	/**
	 * 获取流程实例的内容
	 * @param excutionid 流程实例id
	 * @param key 
	 * @return
	 */
	public Object getContentMap(String taskId,String key);
}
