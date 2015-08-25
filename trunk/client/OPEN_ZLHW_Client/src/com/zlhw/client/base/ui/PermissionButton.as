package com.zlhw.client.base.ui
{
	
	import com.zlhw.client.base.modelLocator.CommonModelLocator;
	
	import mx.binding.utils.BindingUtils;
	import mx.binding.utils.ChangeWatcher;
	import mx.controls.Button;
	
	public class PermissionButton extends Button 
	{

		[Bindable]
		private var permission:String;
		
		public function PermissionButton():void{
			super();
			var watcherSetter:ChangeWatcher =  BindingUtils.bindSetter(onChange, CommonModelLocator.getInstance(), "authorDetails");
		}
		
		[Bindable("change")]
		[Bindable("dataChange")]
		public function set Permission(value:String):void{
			this.permission=value;
			if(CommonModelLocator.getInstance().authorDetails[permission]!=null)
				this.enabled=true;
			else
				this.enabled=false;
		}
		
		private function onChange(authorDetails:Object):void{
			if(permission!=null&&authorDetails!=null){
			if(authorDetails[permission]!=null)
				this.enabled=true;
			else
				this.enabled=false;
			}
		}
		

		public function hasPermission():Boolean{
			if(CommonModelLocator.getInstance().authorDetails[permission]!=null)
				return true;
			else
				return false;
		}
	}
}