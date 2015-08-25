package com.ZLHW.common.JBPM;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jbpm.pvm.internal.history.model.HistoryTaskInstanceImpl;

public class HistoryTask {
	public HistoryTask(HistoryTaskInstanceImpl bean){
		taskid=bean.getHistoryTask().getId();
		activityName=bean.getActivityName();
		startTime=bean.getStartTime();
		endTime=bean.getEndTime();
		excutionId=bean.getHistoryProcessInstance().getProcessInstanceId();
		assiginee=bean.getHistoryTask().getAssignee();
		state=bean.getHistoryTask().getState();
		transition=bean.getTransitionName();
	}
	//任务ID
	private String taskid;
	//节点名
	private String activityName;
	//流程编号
	private String excutionId;
	//节点状态 completed或null
	private String state;
	//创建节点时间
	private Date startTime;
	//节点完成时间
	private Date endTime;
	//流入箭头名
	private String transition;
	//任务接受人
	private String assiginee;
	//评论
	private String comment;
	
	
	public String getTaskid() {
		return taskid;
	}
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public void setExcutionId(String excutionId) {
		this.excutionId = excutionId;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public void setTransition(String transition) {
		this.transition = transition;
	}
	public void setAssiginee(String assiginee) {
		this.assiginee = assiginee;
	}
	public String getTaskId() {
		return taskid;
	}
	public String getActivityName() {
		return activityName;
	}
	public String getExcutionId() {
		return excutionId;
	}
	public String getState() {
		return state;
	}
	public String getStartTime() {
		return startTime==null?"":startTime.toString();
	}
	public String getEndTime() {
		return endTime==null?"":endTime.toString();
	}
	public String getTransition() {
		return transition;
	}
	public String getAssiginee() {
		return assiginee;
	}
	
	public Map toHashMap(){
		Map map = new HashMap();
		map.put("taskid", getTaskId());
		map.put("state", getState());
		map.put("startTime", getStartTime());
		map.put("endTime", getEndTime());
		map.put("assiginee", getAssiginee());
		map.put("activityName", getActivityName());
		map.put("transition", getTransition());
		map.put("comment", getComment());
		return map;
	}
}
