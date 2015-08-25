package com.zlhw.client.common.admin
{
	import com.zlhw.client.base.action.BaseAction;
	import com.zlhw.client.base.event.CommonArrayAddEvent;
	import com.zlhw.client.base.event.CommonArrayListEvent;
	import com.zlhw.client.base.event.CommonEvent;
	import com.zlhw.client.base.event.CommonObjectEvent;
	import com.zlhw.client.common.model.Admin;
	import com.zlhw.client.common.model.Job;
	import com.zlhw.client.common.model.Organ_Job;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.events.ListEvent;
	
	public class AdminAction extends BaseAction
	{
		//----------------------------------
		//  视图对象
		//----------------------------------
		public var adminPanel :AdminPanel;
		public var adminAddPanel :AdminAddPanel;
		public var adminEditPanel :AdminEditPanel;
		//----------------------------------
		//  数据对象
		//----------------------------------
		[Bindable]
		public var selectedAdmin : Admin;
		[Bindable]
		public var selectedJob : Organ_Job;
		[Bindable]
		public var jobTreeList : ArrayCollection  = new ArrayCollection;
		[Bindable]
		public var adminList : ArrayCollection  = new ArrayCollection;
		
		[Bindable]
		public var adminStatusList : ArrayCollection  = new ArrayCollection;
		
		[Bindable]
		public var adminStatusMap : Object  = new Object;
		
		public override function init():void{
			var evt : CommonArrayListEvent = new CommonArrayListEvent("AdminService","getAdminStatusList");
			evt.dataArray = adminStatusList;
			adminPanel.dispatchEvent(evt);
			
			var event : CommonObjectEvent = new CommonObjectEvent("AdminService","getAdminStatusMap",adminStatusMap);
			adminPanel.dispatchEvent(event);
		}
		
		public override function rebuildAction():void{
			if(adminPanel !=null)
				adminPanel.adminAction = this;
			if(adminAddPanel !=null)
				adminAddPanel.adminAction = this;
			if(adminEditPanel !=null)
				adminEditPanel.adminAction = this;
		}
		public function loadJobList():void{
			var evt : CommonArrayListEvent = new CommonArrayListEvent("AdminService","getJobTree");
			evt.dataArray = jobTreeList;
			adminPanel.dispatchEvent(evt);
			
		}
		public function loadAdminList(organ_Job:Organ_Job):void{
			var evt : CommonArrayListEvent = new CommonArrayListEvent("AdminService","getByColumn","organ_Job",organ_Job);
			evt.dataArray = adminList;
			adminPanel.dispatchEvent(evt);
			
		}
		public function jobClick(event:ListEvent):void{
			var selectedData:Object = event.itemRenderer.data.data;
			if(selectedData is Organ_Job)
				selectedJob = Organ_Job(selectedData);
			else
				selectedJob = null;
			loadAdminList(selectedJob);
		}
		public function adminClick(event:ListEvent):void{
			var selectedData:Object = event.itemRenderer.data;
			selectedAdmin = Admin(selectedData);
		}
		public function adminAdd():void{
			adminAddPanel = new AdminAddPanel;
			rebuildAction();
			popUp(adminAddPanel);
			
		}
		public function adminEdit():void{
			if(selectedAdmin==null){
				Alert.show("请选择要修改的员工","提示");
				return;
			}
			adminEditPanel = new AdminEditPanel;
			rebuildAction();
			popUp(adminEditPanel);
		}
		public function saveAdmin(admin : Admin):void{
			var evt :CommonArrayAddEvent = new CommonArrayAddEvent("AdminService","create",admin);
			evt.dataArray = adminList;
			evt.successFunction = adminAddPanel.successExit;
			adminPanel.dispatchEvent(evt);
		}
		public function editAdmin(admin : Admin):void{
			var evt :CommonEvent = new CommonEvent("AdminService","update",admin);
			evt.successFunction=function(){
				adminEditPanel.exit();
				loadAdminList(selectedJob);
			}
			adminPanel.dispatchEvent(evt);
		}
		public function deleteAdmin():void{
			if(selectedAdmin==null){
				Alert.show("请选择要修改的员工","提示");
				return;
			}
			var evt :CommonEvent = new CommonEvent("AdminService","delete",selectedAdmin);
			evt.successFunction=function(){
				loadAdminList(selectedJob);
			}
			adminPanel.dispatchEvent(evt);
		}
	}
}