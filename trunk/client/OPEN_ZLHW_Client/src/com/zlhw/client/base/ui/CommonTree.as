package com.zlhw.client.base.ui
{
	import flash.events.Event;
	
	import mx.controls.Alert;
	import mx.controls.Tree;
	import mx.core.ClassFactory;
	import mx.events.FlexEvent;
	import mx.events.ListEvent;
	
	public class CommonTree extends Tree
	{
		private var defaultField : String = "text";
		public function CommonTree(){
			this.labelField = defaultField;
			this.addEventListener(ListEvent.ITEM_CLICK,dataChangeLister);
		}
		public function set showCheckBox(value:Boolean){
			if(value)
				this.itemRenderer = new ClassFactory(TreeCheckBoxRenderer);
		}
		/**
		 * 刷新树的节点
		 * */
		private function dataChangeLister(event:Event){
			this.expandChildrenOf(this.selectedItem,false);
			this.expandChildrenOf(this.selectedItem,true);
		}
	}
}