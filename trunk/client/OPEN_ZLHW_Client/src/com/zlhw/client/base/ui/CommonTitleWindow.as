package com.zlhw.client.base.ui
{
	
	import com.zlhw.client.base.modelLocator.CommonModelLocator;
	
	import flash.display.DisplayObject;
	import flash.utils.getQualifiedClassName;
	
	import mx.core.Application;
	import mx.events.CloseEvent;
	import mx.managers.PopUpManager;
	
	import spark.components.TitleWindow;
	import spark.layouts.VerticalLayout;

	public class CommonTitleWindow extends TitleWindow
	{
		public var afterExit :Function;
		public function CommonTitleWindow()
		{
			super();
			this.layout=new VerticalLayout;
			this.addEventListener(CloseEvent.CLOSE,closeWindow);
		}
		public function popUp(parent:DisplayObject=null):void{
			if(parent==null)
				parent = ClientMain(Application.application);
			PopUpManager.addPopUp(this,parent,true);
			PopUpManager.centerPopUp(this);
			CommonModelLocator.getInstance()[this.getClassName()]=this;
		}
		
		public function closeWindow(event:CloseEvent):void{
			exit();
		}
		public  function successExit(result: Object):void{
			this.enabled=true;
			exit();
		}
		public  function faultExit(result:Object):void{
			this.enabled=true;
		}
		public function exit():void{
			PopUpManager.removePopUp(this);
			CommonModelLocator.getInstance()[this.getClassName()]=null;
		}
		public function getClassName():String{
			return getQualifiedClassName(this).replace("::",".");
		}
		
		public function refresh():void{
		}
	}
}