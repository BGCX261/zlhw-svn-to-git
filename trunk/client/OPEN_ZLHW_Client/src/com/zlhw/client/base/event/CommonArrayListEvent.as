package com.zlhw.client.base.event
{
	import com.zlhw.client.base.blazeDs.BlazeDsEvent;
	import com.zlhw.client.base.util.ObjectUtil;
	
	import mx.collections.ArrayCollection;
	import mx.collections.ArrayList;

	public class CommonArrayListEvent extends BlazeDsEvent
	{
		public var dataArray : ArrayCollection;
		public function CommonArrayListEvent(serviceName:String, functionName:String,...parameters)
		{
			super(serviceName, functionName, parameters);
		}
		public override function doSuccess(result : Object):void{
			if(dataArray!=null){
				this.dataArray.removeAll();
				if(result is ArrayList)
					this.dataArray.addAll(ArrayList(result));
				else if(result is ArrayCollection)
					this.dataArray.addAll(ArrayCollection(result));
				else{
					this.dataArray.addAll(ObjectUtil.objToCollection(result));
				}
			}
			super.doSuccess(result);
		}
	}
}