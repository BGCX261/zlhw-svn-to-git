package com.zlhw.client.base.blazeDs
{
	
	import com.zlhw.client.base.error.BaseError;
	import com.zlhw.client.base.modelLocator.CommonModelLocator;
	
	import flash.events.Event;
	
	import mx.controls.Alert;
	import mx.messaging.ChannelSet;
	import mx.messaging.channels.AMFChannel;
	import mx.messaging.channels.StreamingAMFChannel;
	import mx.rpc.AbstractOperation;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;

	public class BlazeDsCommand
	{
		private var evt : BlazeDsEvent;
		private var remoteService:RemoteObject;
		private var op :AbstractOperation;
		public static const _AmfChannel : AMFChannel = new AMFChannel("amf",CommonModelLocator.getInstance().url+"messagebroker/amf");
		
		public function BlazeDsCommand( evt : BlazeDsEvent)
		{
			this.evt = evt;
			this.remoteService=new RemoteObject(evt.serviceName);
			var amfChannelSet : ChannelSet = new ChannelSet;
			amfChannelSet.addChannel(_AmfChannel);
			this.remoteService.channelSet = amfChannelSet;
			this.op= remoteService.getOperation(evt.functionName);
			op.addEventListener(ResultEvent.RESULT,doCallBack);
			op.addEventListener(FaultEvent.FAULT,faultCallBack);
		}
		public function doCommand():void{
			if(evt.doValidate){
				if(evt.validate())
					send();
			}
			else
				send();
		}
		
		/**
		 * 调用远程方法
		 * */
		public function send(): void//添加联系人处理函数
		{
			op.send(evt.parameters);
		}
		/**
		 * 调用远程方法成功后回调函数
		 * */
		private function doCallBack(event:ResultEvent):void{
			if(BaseError.hasError(event))
				this.evt.doFail(event.result);
			else
				this.evt.doSuccess(event.result);
		}
		
		/**
		 * 调用远程方法失败后回调函数
		 * */
		private function faultCallBack(event:FaultEvent):void{
			Alert.show(event.fault.faultDetail,"错误信息")
		}
		
	}
}