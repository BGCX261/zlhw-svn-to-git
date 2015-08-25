package com.zlhw.client.common.role
{
	import com.zlhw.client.base.action.BaseAction;
	import com.zlhw.client.base.event.CommonArrayAddEvent;
	import com.zlhw.client.base.event.CommonArrayDeleteEvent;
	import com.zlhw.client.base.event.CommonArrayListEvent;
	import com.zlhw.client.base.event.CommonEvent;
	import com.zlhw.client.base.event.CommonObjectEvent;
	import com.zlhw.client.common.model.Author;
	import com.zlhw.client.common.model.Role;
	import com.zlhw.client.common.model.TreeNode;
	
	import flash.events.Event;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.controls.Tree;
	import mx.events.ListEvent;
	import mx.managers.PopUpManager;
	
	import spark.components.ButtonBar;
	import spark.events.IndexChangeEvent;
	
	public class RoleAction extends BaseAction
	{
		//----------------------------------
		//  视图对象
		//----------------------------------
		public var rolePanel :RolePanel;
		public var roleAddPanel :RoleAddPanel;
		public var roleEditPanel :RoleEditPanel;
		public var authorDistributePanel : AuthorDistributePanel;
		//----------------------------------
		//  数据对象
		//----------------------------------
		[Bindable]
		public var roleList : ArrayCollection = new ArrayCollection;
		[Bindable]
		public var selectedRole : Role;
		[Bindable]
		public var authorDetailList : ArrayCollection = new ArrayCollection;
		
		
		public override function init():void{
			var loadAuthorDetailStatusEvent : CommonObjectEvent = new CommonObjectEvent("AuthorDetailService","getAuthorDetailStatusMap",rolePanel.authorDetailStatusMap);
			rolePanel.dispatchEvent(loadAuthorDetailStatusEvent);
		}
		
		public function loadRoleList(){
			var loadRoleListEvent : CommonArrayListEvent = new CommonArrayListEvent("RoleService","findAll");
			loadRoleListEvent.dataArray = roleList;
			rolePanel.dispatchEvent(loadRoleListEvent);
		}
		/**
		 * 载入权限明细列表
		 * */
		private function loadAuthorDetailList(role : Role){
			if(role == null)
				return;
			var loadAuthorDetailListEvent : CommonArrayListEvent = new CommonArrayListEvent("RoleService","getAuthorDetailByRole",role);
			loadAuthorDetailListEvent.dataArray = authorDetailList;
			rolePanel.dispatchEvent(loadAuthorDetailListEvent);
		}
		public override function rebuildAction():void{
			if(rolePanel !=null)
				rolePanel.roleAction = this;
			if(roleAddPanel !=null)
				roleAddPanel.roleAction = this;
			if(roleEditPanel !=null)
				roleEditPanel.roleAction = this;authorDistributePanel
			if(authorDistributePanel !=null)
				authorDistributePanel.roleAction = this;
		}
		
		public function roleClick(event:ListEvent){
			selectedRole = Role(event.itemRenderer.data);
			loadAuthorDetailList(selectedRole)
		}
		public function authorDetailClick(event:ListEvent){
		}
		
		
		public function roleAdd():void{
			roleAddPanel = new RoleAddPanel;
			rebuildAction();
			popUp(roleAddPanel);
		}
		public function roleEdit():void{
			if(selectedRole==null){
				Alert.show("请选择要修改的内容","提示");
				return;
			}
			roleEditPanel = new RoleEditPanel;
			rebuildAction();
			popUp(roleEditPanel);
		}
		public function authorDistribute():void{
			if(selectedRole==null){
				Alert.show("请选择要修改的内容","提示");
				return;
			}
			authorDistributePanel = new AuthorDistributePanel;
			rebuildAction();
			popUp(authorDistributePanel);
			var loadAuthorDetailListEvent : CommonArrayListEvent = new CommonArrayListEvent("RoleService","getAuthorTreeByRole",selectedRole);
			loadAuthorDetailListEvent.dataArray = authorDistributePanel.treeList;
			rolePanel.dispatchEvent(loadAuthorDetailListEvent);
		}
		public function saveAuthorDetailForRole(treeList : ArrayCollection):void{
			var evt :CommonEvent = new CommonEvent("RoleService","updateAuthorTreeByRole",treeList,selectedRole,true);
			evt.successFunction=function(result){
				authorDistributePanel.exit();
				loadAuthorDetailList(selectedRole);
			}
			rolePanel.dispatchEvent(evt);
		}
		public function saveRole(role : Role):void{
			var evt :CommonArrayAddEvent = new CommonArrayAddEvent("RoleService","create",role);
			evt.dataArray = roleList;
			evt.successFunction=roleAddPanel.successExit;
			rolePanel.dispatchEvent(evt);
		}
		public function editRole(role : Role):void{
			var evt :CommonEvent = new CommonEvent("RoleService","update",role);
			evt.successFunction=function(){
				roleEditPanel.exit();
				loadRoleList();
			}
			rolePanel.dispatchEvent(evt);
		}
		public function deleteRole():void{
			if(selectedRole==null){
				Alert.show("请选择要修改的内容","提示");
				return;
			}
			var evt :CommonArrayDeleteEvent = new CommonArrayDeleteEvent("RoleService","delete",selectedRole);
			evt.dataArray = roleList;
			rolePanel.dispatchEvent(evt);
		}
	}
}