package com.zlhw.client.common.author
{
	import com.zlhw.client.base.action.BaseAction;
	import com.zlhw.client.base.event.CommonArrayAddEvent;
	import com.zlhw.client.base.event.CommonArrayDeleteEvent;
	import com.zlhw.client.base.event.CommonArrayListEvent;
	import com.zlhw.client.base.event.CommonEvent;
	import com.zlhw.client.base.event.CommonObjectEvent;
	import com.zlhw.client.common.model.Author;
	import com.zlhw.client.common.model.AuthorDetail;
	
	import flash.display.DisplayObject;
	import flash.events.Event;
	import flash.events.MouseEvent;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.events.ListEvent;
	import mx.managers.PopUpManager;
	
	import spark.components.List;
	
	public class AuthorAction extends BaseAction
	{
		//----------------------------------
		//  视图对象
		//----------------------------------
		public var authorPanel :AuthorPanel;
		public var authorAddPanel : AuthorAddPanel;
		public var authorEditPanel : AuthorEditPanel;
		public var authorDetailAddPanel : AuthorDetailAddPanel;
		public var authorDetailEditPanel : AuthorDetailEditPanel;
		//----------------------------------
		//  数据对象
		//----------------------------------
		public var authorList : ArrayCollection = new ArrayCollection;
		public var authorDetailList : ArrayCollection = new ArrayCollection;
		public var selectedAuthor : Author;
		public var selectedAuthorDetail : AuthorDetail;
		
		
		
		public override function rebuildAction():void{
			if(authorPanel !=null)
				authorPanel.authorAction = this;
			if(authorAddPanel!=null)
				authorAddPanel.authorAction = this;
			if(authorEditPanel!=null)
				authorEditPanel.authorAction = this;
			if(authorDetailAddPanel !=null)
				authorDetailAddPanel.authorAction = this;
			if(authorDetailEditPanel !=null)
				authorDetailEditPanel.authorAction = this;
		}
		/**
		 * 初始化权限列表
		 * */
		public override function init():void{
			if(authorPanel==null)
				return;
			var loadAuthorDetailStatusEvent : CommonObjectEvent = new CommonObjectEvent("AuthorDetailService","getAuthorDetailStatusMap",authorPanel.authorDetailStatusMap);
			authorPanel.dispatchEvent(loadAuthorDetailStatusEvent);
		}
		public function loadAuthorList(){
			var loadAuthorListEvent : CommonArrayListEvent = new CommonArrayListEvent("AuthorService","findAll");
			loadAuthorListEvent.dataArray = authorList;
			authorPanel.dispatchEvent(loadAuthorListEvent);
		}
		public function authorClick(event:ListEvent):void{
			selectedAuthor = Author(event.itemRenderer.data);
			loadAuthorDetailList(int(selectedAuthor.getPrimary()));
		}
		public function authorDetailClick(event:ListEvent):void{
			selectedAuthorDetail = AuthorDetail(event.itemRenderer.data);
		}
		/**
		 * 载入权限明细列表
		 * */
		private function loadAuthorDetailList(id:int){
			if(id <=0)
				return;
			var author : Author = new Author;
			author.dbId = id;
			var loadAuthorDetailListEvent : CommonArrayListEvent = new CommonArrayListEvent("AuthorDetailService","getByColumnAsc","author",author);
			loadAuthorDetailListEvent.dataArray = authorDetailList;
			authorPanel.dispatchEvent(loadAuthorDetailListEvent);
		}
		/**
		 * 权限添加界面
		 * */
		public function authorAdd():void{
			authorAddPanel = new AuthorAddPanel;
			authorAddPanel.author = new Author;
			rebuildAction();
			popUp(authorAddPanel);
		}
		/**
		 * 权限修改界面
		 * */
		public function authorEdit():void{
			if(selectedAuthor==null){
				Alert.show("请选择要修改的内容","提示");
				return;
			}
			var getAuthorEvent : CommonEvent = new CommonEvent("AuthorService","getById",selectedAuthor.getPrimary());
			getAuthorEvent.successFunction = function(result){
				authorEditPanel = new AuthorEditPanel;
				authorEditPanel.author = result;
				rebuildAction();
				popUp(authorEditPanel);
			}
			authorPanel.dispatchEvent(getAuthorEvent);
			
		}
		
		/**
		 * 权限添加界面
		 * */
		public function authorDetailAdd():void{
			if(selectedAuthor==null){
				Alert.show("请选择对应的权限","提示");
				return;
			}
			authorDetailAddPanel = new AuthorDetailAddPanel;
			var arrayListEvent : CommonArrayListEvent = new CommonArrayListEvent("AuthorDetailService","getAuthorDetailStatusList");
			arrayListEvent.dataArray = authorDetailAddPanel.authorDetailStatus;
			authorPanel.dispatchEvent(arrayListEvent);
			rebuildAction();
			popUp(authorDetailAddPanel);
		}
		/**
		 * 权限修改界面
		 * */
		public function authorDetailEdit():void{
			if(selectedAuthorDetail==null){
				Alert.show("请选择要修改的内容","提示");
				return;
			}
			authorDetailEditPanel = new AuthorDetailEditPanel;
			
			//载入权限明细
			var getAuthorDetialEvent : CommonEvent = new CommonEvent("AuthorDetailService","getById",selectedAuthorDetail.getPrimary());
			getAuthorDetialEvent.successFunction = function(result){
				authorDetailEditPanel.authorDetail = result;
				rebuildAction();
				popUp(authorDetailEditPanel);
			}
			authorPanel.dispatchEvent(getAuthorDetialEvent);
			//载入权限明细状态
			var arrayListEvent : CommonArrayListEvent = new CommonArrayListEvent("AuthorDetailService","getAuthorDetailStatusList");
			arrayListEvent.dataArray = authorDetailEditPanel.authorDetailStatus;
			authorPanel.dispatchEvent(arrayListEvent);
			
		}
		/**
		 * 添加权限
		 * */
		public function saveAuthor(author : Author){
			var evt :CommonArrayAddEvent = new CommonArrayAddEvent("AuthorService","create",author);
			evt.dataArray = authorList;
			evt.successFunction=authorAddPanel.successExit;
			authorPanel.dispatchEvent(evt);
			
		}
		/**
		 * 修改权限
		 * */
		public function editAuthor(author : Author){
			var evt :CommonEvent = new CommonEvent("AuthorService","update",author);
			evt.successFunction=function(){
				authorEditPanel.exit();
				loadAuthorList();
			}
			authorPanel.dispatchEvent(evt);
			
		}
		/**
		 * 删除权限
		 * */
		public function deleteAuthor():void{
			if(selectedAuthor==null){
				Alert.show("请选择要删除的内容","提示");
				return;
			}
			var evt :CommonArrayDeleteEvent = new CommonArrayDeleteEvent("AuthorService","delete",selectedAuthor);
			evt.dataArray = authorList;
			authorPanel.dispatchEvent(evt);
		}
		/**
		 * 添加权限明细
		 * */
		public function saveAuthorDetail(author : AuthorDetail){
			var evt :CommonArrayAddEvent = new CommonArrayAddEvent("AuthorService","create",author);
			evt.dataArray = authorDetailList;
			evt.successFunction=authorDetailAddPanel.successExit;
			authorPanel.dispatchEvent(evt);
			
		}
		/**
		 * 修改权限明细
		 * */
		public function editAuthorDetail(author : AuthorDetail){
			var evt :CommonEvent = new CommonEvent("AuthorService","update",author);
			evt.successFunction=function(){
				authorDetailEditPanel.exit();
				loadAuthorDetailList(author.author.dbId);
				selectedAuthorDetail = null;
			}
			authorPanel.dispatchEvent(evt);
			
		}
		/**
		 * 删除权限明细
		 * */
		public function deleteAuthorDetail():void{
			if(selectedAuthorDetail==null){
				Alert.show("请选择要删除的内容","提示");
				return;
			}
			var evt :CommonArrayDeleteEvent = new CommonArrayDeleteEvent("AuthorDetailService","delete",selectedAuthorDetail);
			evt.dataArray = authorDetailList;
			authorPanel.dispatchEvent(evt);
		}
		
	}
}