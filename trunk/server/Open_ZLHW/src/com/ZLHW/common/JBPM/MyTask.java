package com.ZLHW.common.JBPM;

import java.util.Date;

import org.jbpm.pvm.internal.task.TaskImpl;
/**
 * 转化下JBPM自带的Task
 * @author Administrator
 *
 */
public class MyTask {
	private String id; //任务id
	private String name; //任务名
	private Date createTime; //发起流程时间
	private Date duedate;//截止时间
	private String excutionId;//当前流程id
	private String executionName;//当前流程名称
	private String formResourceName;//对应前台表单路径
	private String assignee;//任务负责人
	private String key;//业务主键
	public MyTask(TaskImpl task){
		this.id=task.getId();
		this.name=task.getName();
		this.createTime=task.getCreateTime();
		this.duedate=task.getDuedate();
		this.excutionId=task.getProcessInstance().getId();
		this.formResourceName=task.getFormResourceName();
		this.assignee=task.getAssignee();
		this.key=task.getProcessInstance().getKey();
	}
	
	
	public String getExecutionName() {
		return executionName;
	}


	public void setExecutionName(String executionName) {
		this.executionName = executionName;
	}


	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}


	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}


	public String getAssignee(){
		return assignee;
	}
	
	public Date getDuedate() {
		return duedate;
	}

	public void setDuedate(Date duedate) {
		this.duedate = duedate;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getExcutionId() {
		return excutionId;
	}
	public void setExcutionId(String excutionId) {
		this.excutionId = excutionId;
	}
	public String getFormResourceName() {
		return formResourceName;
	}
	public void setFormResourceName(String formResourceName) {
		this.formResourceName = formResourceName;
	}
	
	
}
