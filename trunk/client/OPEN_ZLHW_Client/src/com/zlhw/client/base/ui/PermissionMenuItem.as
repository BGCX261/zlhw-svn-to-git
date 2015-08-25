package com.zlhw.client.base.ui
{
	import com.jnlxc.client.base.Interface.IPermission;
	import com.jnlxc.client.modelLocator.ViewTableModelLocator;
	
	import flash.ui.ContextMenuItem;
	
	public class PermissionMenuItem  implements IPermission
	{	
		private var menuItem : ContextMenuItem;
		
		public function get MenuItem():ContextMenuItem{
			return this.menuItem;
		}
		
		public function PermissionMenuItem(labelName:String,permission:String):void{
			this.menuItem=new ContextMenuItem(labelName);
			this.Permission=permission;
		}
		[Bindable]
		private var permission:String;
		[Bindable("change")]
		[Bindable("dataChange")]
		public function set Permission(value:String):void{
			this.permission=value;
			if(ViewTableModelLocator.getInstance().authorDetails[permission]!=null)
				menuItem.enabled=true;	
			else
				menuItem.enabled=false;
		}
		
		public function hasPermission():Boolean{
			if(ViewTableModelLocator.getInstance().authorDetails[permission]!=null)
				return true;
			else
				return false;
		}
	}
}