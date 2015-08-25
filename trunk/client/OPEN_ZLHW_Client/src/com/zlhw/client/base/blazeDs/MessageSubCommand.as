package com.zlhw.client.base.blazeDs
{
	import mx.collections.ArrayCollection;
	import mx.messaging.ChannelSet;
	import mx.messaging.Consumer;
	import mx.messaging.channels.StreamingAMFChannel;
	import mx.messaging.events.ChannelEvent;
	import mx.messaging.events.MessageEvent;

	public class MessageSubCommand
	{
		public static const _MsgSubChannel : StreamingAMFChannel = new StreamingAMFChannel("msgamf","http://localhost:80/messagebroker/streamingamf");
		public static const _MsgSubDestination : String = "message-push-destination";
		public static var consumerList:ArrayCollection = new ArrayCollection;
		private var messageEvent : MessageSubEvent;
		public function MessageSubCommand(messageEvent : MessageSubEvent)
		{
			this.messageEvent = messageEvent;
		}
		public function doCommand():void{
			if(messageEvent.unSubMsg)
				unSubMsg(messageEvent.subtopic);
			else
				submsg(messageEvent.subtopic,messageEvent.messageHandler);
		}
		/**
		 * 消息服务，订阅消息
		 * */
		public function submsg(subtopic:String,messageHandler:Function):void  
		{   
			var consumer:Consumer = new Consumer();   
			consumer.destination = _MsgSubDestination;   
			consumer.subtopic = subtopic;   
			consumer.channelSet = new ChannelSet(); 
			consumer.channelSet.addChannel(_MsgSubChannel);
			if(messageHandler!=null)
				consumer.addEventListener(MessageEvent.MESSAGE, messageHandler);   
			consumer.addEventListener(ChannelEvent.DISCONNECT,function(){
				submsg(subtopic,messageHandler);
			});
			consumer.subscribe();
			consumerList.addItem(consumer);
		}
		/**
		 * 取消订阅
		 * */
		public function unSubMsg(subtopic:String):void  
		{   
			for each(var consumer : Consumer in consumerList){
				if(consumer!=null && consumer.connected && consumer.subtopic == subtopic){
					consumer.unsubscribe();
					consumer.disconnect();
				}
			}
		}
	}
}