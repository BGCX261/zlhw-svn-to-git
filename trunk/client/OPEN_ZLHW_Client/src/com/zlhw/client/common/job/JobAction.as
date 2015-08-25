package com.zlhw.client.common.job
{
	import com.zlhw.client.base.action.BaseAction;
	import com.zlhw.client.base.event.CommonArrayAddEvent;
	import com.zlhw.client.base.event.CommonArrayDeleteEvent;
	import com.zlhw.client.base.event.CommonArrayListEvent;
	import com.zlhw.client.base.event.CommonEvent;
	import com.zlhw.client.common.model.Job;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.events.ListEvent;
	import mx.managers.PopUpManager;
	
	import spark.components.ButtonBar;
	import spark.events.IndexChangeEvent;
	
	public class JobAction extends BaseAction
	{
		//----------------------------------
		//  视图对象
		//----------------------------------
		public var jobPanel :JobPanel;
		public var jobAddPanel :JobAddPanel;
		public var jobEditPanel :JobEditPanel;
		public var roleDistributePanel : RoleDistributePanel;
		//----------------------------------
		//  数据对象
		//----------------------------------
		[Bindable]
		public var jobList : ArrayCollection = new ArrayCollection;
		[Bindable]
		public var selectedJob : Job;
		[Bindable]
		public var roleList : ArrayCollection = new ArrayCollection;
		
		
		public override function init():void{
			jobList  = new ArrayCollection;
		}
		
		public override function rebuildAction():void{
			if(jobPanel !=null)
				jobPanel.jobAction = this;
			if(jobAddPanel !=null)
				jobAddPanel.jobAction = this;
			if(jobEditPanel !=null)
				jobEditPanel.jobAction = this;roleDistributePanel
			if(roleDistributePanel !=null)
				roleDistributePanel.jobAction = this;
		}
		
		
		
		public function loadJobList(){
			
			var loadJobListEvent : CommonArrayListEvent = new CommonArrayListEvent("JobService","findAll");
			loadJobListEvent.dataArray = jobList;
			jobPanel.dispatchEvent(loadJobListEvent);
		}
		public function loadRolelList(job : Job){
			
			var loadRoleListEvent : CommonArrayListEvent = new CommonArrayListEvent("JobService","getRoleByJob",job);
			loadRoleListEvent.dataArray = roleList;
			jobPanel.dispatchEvent(loadRoleListEvent);
		}
		
		public function jobClick(event:ListEvent): void {
			selectedJob = Job(event.itemRenderer.data);
			loadRolelList(selectedJob)
		}
		public function jobAdd():void{
			jobAddPanel = new JobAddPanel;
			rebuildAction();
			popUp(jobAddPanel);
		}
		public function jobEdit():void{
			if(selectedJob==null){
				Alert.show("请选择要修改的内容","提示");
				return;
			}
			jobEditPanel = new JobEditPanel;
			rebuildAction();
			PopUpManager.addPopUp(jobEditPanel,jobPanel,true);
		}
		public function roleDistribute():void{
			roleDistributePanel = new RoleDistributePanel;
			rebuildAction();
			popUp(roleDistributePanel);
			var loadRoleListEvent : CommonArrayListEvent = new CommonArrayListEvent("JobService","getRoleTreeByJob",selectedJob);
			loadRoleListEvent.dataArray = roleDistributePanel.roleList;
			jobPanel.dispatchEvent(loadRoleListEvent);
		}
		public function disTributeRole(list:ArrayCollection):void{
			var evt :CommonEvent = new CommonEvent("JobService","disTributeRole",list,selectedJob);
			evt.successFunction=function(result){
				roleDistributePanel.exit();
				loadRolelList(selectedJob);
			}
			jobPanel.dispatchEvent(evt);
		}
		public function saveJob(job:Job):void{
			var evt :CommonArrayAddEvent = new CommonArrayAddEvent("JobService","create",job);
			evt.dataArray = jobList;
			evt.successFunction=jobAddPanel.successExit;
			jobPanel.dispatchEvent(evt);
		}
		public function editJob(job:Job):void{
			var evt :CommonEvent = new CommonEvent("JobService","update",job);
			evt.successFunction=function(){
				jobEditPanel.exit();
				loadJobList();
			}
			jobPanel.dispatchEvent(evt);
		}
		public function deleteJob():void{
			if(selectedJob==null){
				Alert.show("请选择要修改的内容","提示");
				return;
			}
			var evt :CommonArrayDeleteEvent = new CommonArrayDeleteEvent("JobService","delete",selectedJob);
			evt.dataArray = jobList;
			jobPanel.dispatchEvent(evt);
		}
	}
}