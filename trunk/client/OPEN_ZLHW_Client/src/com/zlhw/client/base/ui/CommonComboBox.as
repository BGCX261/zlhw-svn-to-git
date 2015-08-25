package com.zlhw.client.base.ui
{
	import flash.events.Event;
	
	import mx.collections.ArrayCollection;
	import mx.collections.IList;
	import mx.events.CollectionEvent;
	import mx.events.ListEvent;
	
	import spark.components.ComboBox;
	import spark.events.IndexChangeEvent;

	/**
	 * 设置combobox的选中值
	 * 
	 * */
	public class CommonComboBox extends ComboBox
	{
		public var showDefaultItem : Boolean = false;
		public var  defaultItem : String = "请选择";
		public var  defaultSelectedIndex : int = 0;
		private var _selectedKey : String;
		private var _selectedValue : Object;
		public override function set dataProvider(value:IList):void{
			if(showDefaultItem){
				if(value!=null){
					if(value.length == 0){
						value.addItem(defaultItem);
					}
					else if(value.getItemAt(0)!=defaultItem){
						value.addItemAt(defaultItem,0);
					}
				} 
			}
			super.dataProvider = value;
			
			//如果没有被选中的项目，且dataProvider长度大于默认的索引值，则选中默认的是选项
			if((this.selectedItem == null || this.selectedIndex == -1) 
				&& dataProvider.length > defaultSelectedIndex)
				this.selectedIndex = defaultSelectedIndex;
			
			selected();
		}
		
		public function set selectedKey(value:String){
			_selectedKey = value;
			selected();
		}
		public function set selectedValue(value:*){
			_selectedValue = value;
			selected();
		}
		private function selected():void{
			if(_selectedKey!=null &&_selectedValue!=null){
				for(var index : String in dataProvider){
					 if(dataProvider[index]!=defaultItem&&dataProvider[index][_selectedKey]== _selectedValue){
						this.selectedIndex = int(index);
					}
				}
			}else if(_selectedValue!=null){
				for(var index : String in dataProvider){
					if(dataProvider[index]!=defaultItem && dataProvider[index]== _selectedValue){
						this.selectedIndex = int(index);
					}
				}
			}
		}
		override protected function dataProvider_collectionChangeHandler(event : Event):void{
			this.dataProvider = dataProvider;
			super.dataProvider_collectionChangeHandler(event);
		}
		
	}
}