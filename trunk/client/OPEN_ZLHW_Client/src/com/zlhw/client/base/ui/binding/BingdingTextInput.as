package com.zlhw.client.base.ui.binding
{
	import flash.events.Event;
	
	import spark.components.TextInput;
	import spark.events.TextOperationEvent;
	
	public class BingdingTextInput extends TextInput 
	{
		private var _bingdingObject : Object;
		private var _bingdingProperty : String;
		private var textChangeFlag = false;
		public static const _BingdingEvent = "BingdingEvent";
		public function BingdingTextInput()
		{
			super();
			this.addEventListener(_BingdingEvent,bingdingDataListenr);
			this.addEventListener(TextOperationEvent.CHANGE,dataChangeListenr);
		}
		public  function set bingdingObject(value:*){
			this._bingdingObject = value;
			this.dispatchEvent(new Event(_BingdingEvent));
		}
		public  function set bingdingProperty(value:String){
			this._bingdingProperty = value;
			this.dispatchEvent(new Event(_BingdingEvent));
		}
		/**
		 * 重写text方法，如果被初始化赋值,textChangeFlag标示为true
		 * */
		override public function set text(value:String):void
		{
			super.text = value;
			this.textChangeFlag = true;
			this.dispatchEvent(new Event(_BingdingEvent));
		}
		/**
		 * 如果监听到text的值变化，textChangeFlag标示也为true
		 * */
		private function dataChangeListenr(evt:Event):void{
			if(!textChangeFlag)
				this.textChangeFlag = true;
			this.dispatchEvent(new Event(_BingdingEvent));
		}
		private function bingdingDataListenr(evt:Event):void{
			if(_bingdingObject!= null && _bingdingProperty!=null &&textChangeFlag)
			_bingdingObject[_bingdingProperty] = this.text;
		}
		
	}
}