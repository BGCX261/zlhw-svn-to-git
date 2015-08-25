package com.zlhw.client.common.dict
{
	import com.zlhw.client.base.action.BaseAction;
	import com.zlhw.client.base.blazeDs.BlazeDsEvent;
	import com.zlhw.client.base.event.CommonArrayAddEvent;
	import com.zlhw.client.base.event.CommonArrayDeleteEvent;
	import com.zlhw.client.base.event.CommonArrayListEvent;
	import com.zlhw.client.base.event.CommonEvent;
	import com.zlhw.client.base.util.ObjectUtil;
	import com.zlhw.client.common.model.DataDict;
	import com.zlhw.client.common.model.DataDictCatalog;
	
	import flash.events.Event;
	import flash.events.MouseEvent;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.events.ListEvent;
	import mx.managers.PopUpManager;
	
	import spark.components.ButtonBar;
	import spark.components.List;
	import spark.events.IndexChangeEvent;

	public class DictAction extends BaseAction
	{
		//----------------------------------
		//  视图对象
		//----------------------------------
		public var dictPanel :DictPanel;
		public var dictAddPanel :DictAddPanel;
		public var dictEditPanel :DictEditPanel;
		//----------------------------------
		//  数据对象
		//----------------------------------
		public var dictCatList : ArrayCollection = new ArrayCollection;
		public var dictList : ArrayCollection = new ArrayCollection;
		[Bindable]
		public var selectedCatDict : DataDictCatalog;
		[Bindable]
		public var selectedDict : DataDict;
		/**
		 * 单击字典类目
		 * */
		public function dictCatClick(event:ListEvent):void{
			loadDictList(event.itemRenderer.data.code);
			selectedCatDict = DataDictCatalog(event.itemRenderer.data);
		}
		/**
		 * 单击字典
		 * */
		public function dictClick(event : ListEvent):void{
			selectedDict = DataDict(event.itemRenderer.data);
		}
		/**
		 * 添加数据字典
		 * */
		public function saveDict(dict : DataDict){
			var evt :CommonArrayAddEvent = new CommonArrayAddEvent("DataDictService","create",dict);
			evt.dataArray = dictList;
			evt.successFunction=dictAddPanel.successExit;
			dictPanel.dispatchEvent(evt);
		}
		/**
		 * 编辑数据字典
		 * */
		public function editDict(dict:DataDict){
			var evt :CommonEvent = new CommonEvent("DataDictService","update",dict);
			evt.successFunction = function(result:Object){
				dictEditPanel.exit();
				loadDictList(selectedCatDict.code);
			};
			dictPanel.dispatchEvent(evt);
		}
		/**
		 * 删除数据字典
		 * */
		public function dictDelete():void{
			if(selectedDict==null){
				Alert.show("请选择要删除的内容","提示");
				return;
			}
			var e:CommonArrayDeleteEvent=new CommonArrayDeleteEvent("DataDictService","delete",selectedDict);
			e.dataArray=dictList;
			dictPanel.dispatchEvent(e);
		}
		/**
		 * 载入字典类目列表
		 * */
		public function loadDictCatList(){
			if(dictPanel==null)
				return;
			var loadDictCatListEvent : CommonArrayListEvent = new CommonArrayListEvent("DataDictCatalogService","findAll");
			loadDictCatListEvent.dataArray = dictCatList;
			dictPanel.dispatchEvent(loadDictCatListEvent);
		}
		/**
		 *载入字典列表 
		 **/
		public function loadDictList(code:String){
			if(code == null)
				return;
			var loadDictListEvent : CommonArrayListEvent = new CommonArrayListEvent("DataDictService","loadDictList",code);
			loadDictListEvent.dataArray = dictList;
			dictPanel.dispatchEvent(loadDictListEvent);
		}
		
		/**
		 * 字典添加界面
		 * */
		public function dictAdd():void{
			if(selectedCatDict==null){
				Alert.show("请先选择目录","提示");
				return;
			}
			dictAddPanel = new DictAddPanel;
			dictAddPanel.popUp(dictPanel);
			rebuildAction();
		}
		/**
		 * 字典编辑界面
		 * */
		public function dictEdit():void{
			if(selectedDict==null){
				Alert.show("请选择要修改的内容","提示");
				return;
			}
			var getDictEvent : CommonEvent = new CommonEvent("DataDictService","getById",selectedDict.dbId);
			getDictEvent.successFunction = function(result){
				dictEditPanel = new DictEditPanel;
				dictEditPanel.popUp(dictPanel);
				dictEditPanel.dict = result;
				rebuildAction();
			}
			dictPanel.dispatchEvent(getDictEvent);
		}
		override public function rebuildAction():void{
			if(dictAddPanel!=null)
				dictAddPanel.dictAction=this;
			if(dictEditPanel!=null)
				dictEditPanel.dictAction=this;
		}

	}
}