package com.zlhw.client.base.action
{
	import com.zlhw.client.base.util.ObjectUtil;
	
	import flash.display.DisplayObject;
	
	
	import mx.core.Application;
	import mx.core.IFlexDisplayObject;
	import mx.managers.PopUpManager;
	

	public class BaseAction
	{
		/**
		 *  建立action,初始化数据
		 * */
		public function init():void{
			
		}
		/**
		 *  重建action与组件对象之间的关联
		 * */
		public function rebuildAction():void{
			
		}
		/**
		 * 清空Action内所有可以被访问的对象，释放内存
		 * */
		public function destory():void{
			var fieldList : Array = ObjectUtil.getFields(this);
			for each(var fieldName : String in fieldList){
				try{
					this[fieldName] = null;
				}catch(e : Error){
					trace(fieldName+"无法被访问");
				}
				
			}
		}
		public function popUp(panel:IFlexDisplayObject){
			PopUpManager.addPopUp(panel,DisplayObject(Application.application),true);
			PopUpManager.centerPopUp(panel);
		}
	
	}
}