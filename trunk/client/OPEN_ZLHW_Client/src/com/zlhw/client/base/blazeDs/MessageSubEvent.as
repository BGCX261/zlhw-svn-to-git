package com.zlhw.client.base.blazeDs
{
	import flash.events.Event;
	
	import mx.messaging.channels.StreamingAMFChannel;

	public class MessageSubEvent extends Event
	{
		public var messageHandler:Function;
		public var subtopic:String;
		public var unSubMsg : Boolean = false
		public static const _MessageSubEvent:String  = "MessageSubEvent";
		public function MessageSubEvent(subtopic:String,messageHandler:Function = null){
			this.messageHandler = messageHandler;
			this.subtopic = subtopic;
			super(_MessageSubEvent);
		}
	}
}