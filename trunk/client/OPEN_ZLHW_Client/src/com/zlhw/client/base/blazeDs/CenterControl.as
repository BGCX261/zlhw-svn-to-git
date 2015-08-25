package com.zlhw.client.base.blazeDs
{
	import com.zlhw.client.base.util.DateUtil;
	import com.zlhw.client.base.util.ObjectUtil;
	
	import flash.events.Event;
	
	import mx.controls.Alert;
	import mx.core.UIComponent;
	
	import spark.components.Application;
	
	public class CenterControl extends UIComponent
	{
		
		public function CenterControl(){
			this.initializationComplete()
		}
		public override function initialize():void{
			super.initialize();
			this.parentApplication.addEventListener(BlazeDsEvent._RemoteEvent,doPRCListener,true);
			this.parentApplication.addEventListener(BlazeDsEvent._RemoteEvent,doPRCListener,false);
			this.parentApplication.addEventListener(MessageSubEvent._MessageSubEvent,doMessageSubListener,true);
			this.parentApplication.addEventListener(MessageSubEvent._MessageSubEvent,doMessageSubListener,false);
		}
		private function doPRCListener(evt : Event){
			try{
				var command : BlazeDsCommand = new BlazeDsCommand(BlazeDsEvent(evt));
				trace(DateUtil.format(new Date,DateUtil._SecondDateFormatString)+" : catch a RemoteEvent >> "+ObjectUtil.getClassName(evt));
				command.doCommand();
			}catch(e:Error){
				Alert.show("类型转换失败，你派发的事件不是com.zlhw.client.base.blazeDs.RemteEvent类型");
				return;
			}
			
		}
		private function doMessageSubListener(evt : Event){
			try{
				var command : MessageSubCommand = new MessageSubCommand(MessageSubEvent(evt));
				trace(DateUtil.format(new Date,DateUtil._SecondDateFormatString)+" : catch a MessageEvent >> "+ObjectUtil.getClassName(evt));
				command.doCommand();
			}catch(e:Error){
				Alert.show("类型转换失败，你派发的事件不是com.zlhw.client.base.blazeDs.MessageEvent类型");
				return;
			}
		}
	}
}