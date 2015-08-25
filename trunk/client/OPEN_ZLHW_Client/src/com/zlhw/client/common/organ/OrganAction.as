package com.zlhw.client.common.organ
{
	import com.zlhw.client.base.action.BaseAction;
	import com.zlhw.client.base.event.CommonArrayAddEvent;
	import com.zlhw.client.base.event.CommonArrayDeleteEvent;
	import com.zlhw.client.base.event.CommonArrayListEvent;
	import com.zlhw.client.base.event.CommonEvent;
	import com.zlhw.client.base.event.CommonObjectEvent;
	import com.zlhw.client.common.model.Organ;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.events.ListEvent;
	import mx.managers.PopUpManager;
	
	import spark.components.ButtonBar;
	import spark.events.IndexChangeEvent;

	public class OrganAction extends BaseAction
	{
		//----------------------------------
		//  视图对象
		//----------------------------------
		public var organPanel :OrganPanel;
		public var organAddPanel :OrganAddPanel;
		public var organEditPanel :OrganEditPanel;
		public var jobDistributePanel : JobDistributePanel;
		//----------------------------------
		//  数据对象
		//----------------------------------
		[Bindable]
		public var organTreeList : ArrayCollection  = new ArrayCollection;
		[Bindable]
		public var selectedOrgan : Organ;
		[Bindable]
		public var selectedParentOrgan : Organ = new Organ;
		[Bindable]
		public var jobList : ArrayCollection  = new ArrayCollection;
		
		
		public override function init():void{
		}
		
		public override function rebuildAction():void{
			if(organPanel !=null)
				organPanel.organAction = this;
			if(organAddPanel !=null)
				organAddPanel.organAction = this;
			if(organEditPanel !=null)
				organEditPanel.organAction = this;
			if(jobDistributePanel !=null)
				jobDistributePanel.organAction = this;
		}
		
		public function organClick(event:ListEvent):void{
			selectedOrgan = Organ(event.itemRenderer.data.data);
			if(selectedOrgan.lever>1){
				var evt :CommonObjectEvent = new CommonObjectEvent("OrganService","getById",selectedParentOrgan,selectedOrgan.parent);
				organPanel.dispatchEvent(evt);
			}
			loadJoblList(selectedOrgan)
		}
		
		public function loadOrganList(){
			
			var loadOrganListEvent : CommonArrayListEvent = new CommonArrayListEvent("OrganService","getOrganTree");
			loadOrganListEvent.dataArray = organTreeList;
			organPanel.dispatchEvent(loadOrganListEvent);
		}
		private function loadJoblList(organ : Organ){
			var loadJobListEvent : CommonArrayListEvent = new CommonArrayListEvent("OrganService","getJobByOrgan",organ);
			loadJobListEvent.dataArray = jobList;
			organPanel.dispatchEvent(loadJobListEvent);
		}
		
		public function organAdd():void{
			organAddPanel = new OrganAddPanel;
			rebuildAction();
			popUp(organAddPanel);
		}
		public function organEdit():void{
			if(selectedOrgan==null){
				Alert.show("请选择要修改的内容","提示");
				return;
			}
			organEditPanel = new OrganEditPanel;
			rebuildAction();
			popUp(organEditPanel);
		}
		public function jobDistribute():void{
			jobDistributePanel = new JobDistributePanel;
			rebuildAction();
			jobDistributePanel.popUp();
			var evt : CommonArrayListEvent = new CommonArrayListEvent("OrganService","getJobTreeByOrgan",selectedOrgan);
			evt.dataArray = jobDistributePanel.treeList;
			organPanel.dispatchEvent(evt);
		}
		public function saveOrgan(organ : Organ):void{
			var evt :CommonEvent = new CommonEvent("OrganService","create",organ);
			evt.successFunction=function(){
				loadOrganList();
				organAddPanel.exit();
			}
			organPanel.dispatchEvent(evt);
		}
		public function editOrgan(organ : Organ):void{
			var evt :CommonEvent = new CommonEvent("OrganService","update",organ);
			evt.successFunction=function(){
				organEditPanel.exit();
				loadOrganList();
			}
			organPanel.dispatchEvent(evt);
		}
		public function deleteOrgan():void{
			if(selectedOrgan==null){
				Alert.show("请选择要修改的内容","提示");
				return;
			}
			var evt :CommonEvent = new CommonEvent("OrganService","delete",selectedOrgan);
			evt.successFunction=function(){
				loadOrganList();
			}
			organPanel.dispatchEvent(evt);
		}
		public function disTributeJob(treeList : ArrayCollection){
			var evt :CommonEvent = new CommonEvent("OrganService","disTributeJob",treeList,selectedOrgan);
			evt.successFunction=function(result){
				jobDistributePanel.exit();
				loadJoblList(selectedOrgan);
			}
			organPanel.dispatchEvent(evt);
		}
	}
}