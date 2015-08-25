package com.zlhw.client.base.model
{
	import flash.utils.describeType;
	import flash.utils.getQualifiedClassName;
	public class BaseModel
	{
		/**
		 * 将该model中的属性组装到obj中
		 * */
		public function assignTo(obj:BaseModel):void{
			var instanceInfo:XML = describeType(this); 
			var properties:XMLList = instanceInfo..accessor.(@access != "writeonly") + instanceInfo..variable;
			for each (var propertyInfo:XML in properties){   
				var propertyName:String = propertyInfo.@name;
				obj[propertyName]=this[propertyName];
			} 
			
		}
		public function getPrimary():Object{
			return null;
		}
		
		public function equals(model:BaseModel):Boolean{
			if(this.getPrimary()==model.getPrimary())
				return true;
			else
				return false;
		}
		
	}
}