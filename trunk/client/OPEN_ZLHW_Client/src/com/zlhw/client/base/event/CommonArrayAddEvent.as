package com.zlhw.client.base.event
{
	import com.zlhw.client.base.blazeDs.BlazeDsEvent;
	
	import mx.collections.ArrayCollection;

	public class CommonArrayAddEvent extends BlazeDsEvent
	{
		public var dataArray : ArrayCollection;
		public function CommonArrayAddEvent(serviceName:String,functionName:String,...parameters)
		{
			super(serviceName,functionName,parameters);
		}
		
		
		public override function doSuccess( result : Object ) : void//命令运行正常的处理函数
		{	
			if(dataArray!=null){
				dataArray.addItem(result);
			}
			super.doSuccess(result);
		}
	}
}