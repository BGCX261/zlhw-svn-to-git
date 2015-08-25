package com.ZLHW.common.JBPM;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jbpm.pvm.internal.history.model.HistoryActivityInstanceImpl;
import org.jbpm.pvm.internal.history.model.HistoryProcessInstanceImpl;

public class MyRequest {
	private String id;//流程流水号
	private Date createTime; //发起流程时间
	private Date endTime; //流程结束时间,未完成则为空
	private String executionId;//当前流程id
	private String executionName;//当前流程名称
	private String currentTaskId;//当前任务id
	private String currentTaskName;//当前任务名
	private String currentType;//当前状态
	private String lastTransition;//流入箭头名
	private String key;//业务主键
	
	public MyRequest(HistoryProcessInstanceImpl bean){
		id=bean.getDbid()+"";
		createTime=bean.getStartTime();
		endTime=bean.getEndTime();
		executionId=bean.getProcessInstanceId();
		currentType=bean.getState()=="active"?"已完成":"未完成";
		key=bean.getKey();
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateTime() {
		return createTime==null?"":createTime.toString();
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getCurrentTaskId() {
		return currentTaskId;
	}

	public void setCurrentTaskId(String currentTaskId) {
		this.currentTaskId = currentTaskId;
	}

	public String getCurrentTaskName() {
		return currentTaskName;
	}

	public void setCurrentTaskName(String currentTaskName) {
		this.currentTaskName = currentTaskName;
	}

	public String getCurrentType() {
		return currentType;
	}

	public void setCurrentType(String currentType) {
		this.currentType = currentType;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getEndTime() {
		return endTime==null?"":endTime.toString();
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getLastTransition() {
		return lastTransition;
	}

	public void setLastTransition(String lastTransition) {
		this.lastTransition = lastTransition;
	}
	
	public String getExecutionName() {
		return executionName;
	}

	public void setExecutionName(String executionName) {
		this.executionName = executionName;
	}
}
