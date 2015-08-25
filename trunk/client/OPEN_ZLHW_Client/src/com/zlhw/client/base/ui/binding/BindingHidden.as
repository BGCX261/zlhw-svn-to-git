package com.zlhw.client.base.ui.binding
{
	import flash.events.Event;
	
	import mx.core.UIComponent;
	
	public class BindingHidden extends UIComponent
	{
		private var _bingdingObject : Object;
		private var _bingdingProperty : String;
		private var _data : Object;
		public static const _HiddenDataEvent = "hiddenDataEvent";
		public function BindingHidden()
		{
			this.addEventListener(_HiddenDataEvent,dataChangeListenr);
		}
		public  function set bingdingObject(value:*){
			this._bingdingObject = value;
			this.dispatchEvent(new Event(_HiddenDataEvent));
		}
		public  function set bingdingProperty(value:String){
			this._bingdingProperty = value;
			this.dispatchEvent(new Event(_HiddenDataEvent));
		}
		public  function set data(value:Object){
			this._data = value;
			this.dispatchEvent(new Event(_HiddenDataEvent));
		}
		private function dataChangeListenr(evt:Event):void{
			if(_bingdingObject!= null && _bingdingProperty!=null)
				_bingdingObject[_bingdingProperty] = this._data;
		}
	}
}