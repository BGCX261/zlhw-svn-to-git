package com.zlhw.client.base.ui.binding
{
	import com.zlhw.client.base.ui.CommonComboBox;
	
	import flash.events.Event;
	
	import mx.collections.ArrayCollection;
	import mx.events.CollectionEvent;
	
	import spark.events.IndexChangeEvent;
	

	public class BingdingComboBox extends CommonComboBox
	{
		private var _bingdingObject : Object;
		private var _bingdingProperty : String;
		private var _bingdingComboBoxProperty : String;
		public static const _BingdingEvent = "BingdingEvent";
		
		public function BingdingComboBox() 
		{
			super();
			this.addEventListener(_BingdingEvent,dataChangeListenr);
			this.addEventListener(IndexChangeEvent.CHANGE,dataChangeListenr);
		}
		public  function set bingdingObject(value:*){
			this._bingdingObject = value;
			this.dispatchEvent(new Event(_BingdingEvent));
		}
		public  function set bingdingProperty(value:String){
			this._bingdingProperty = value;
			this.dispatchEvent(new Event(_BingdingEvent));
		}
		public function set bingdingComboBoxProperty(value:String):void{
			this._bingdingComboBoxProperty=value;
			this.dispatchEvent(new Event(_BingdingEvent));
		}
		override public function set selectedIndex(index : int):void{
			super.selectedIndex = index;
			this.dispatchEvent(new Event(_BingdingEvent));
		}
		override public function set selectedItem(value : *):void{
			super.selectedItem = value;
			this.dispatchEvent(new Event(_BingdingEvent));
		}
		private function dataChangeListenr(evt:Event):void{
			if(_bingdingObject!= null && _bingdingProperty!=null && defaultItem!=selectedItem && selectedItem!=null){
				if(_bingdingComboBoxProperty==null)
					_bingdingObject[_bingdingProperty] = this.selectedItem;
				else
					_bingdingObject[_bingdingProperty] = this.selectedItem[_bingdingComboBoxProperty];
			}
				
		}
		
	}
}