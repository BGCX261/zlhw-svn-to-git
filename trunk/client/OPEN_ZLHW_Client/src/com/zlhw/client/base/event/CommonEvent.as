package com.zlhw.client.base.event
{
	import com.zlhw.client.base.blazeDs.BlazeDsEvent;

	public class CommonEvent extends BlazeDsEvent
	{
		public function CommonEvent(serviceName:String,functionName:String,...parameters)
		{
			super(serviceName,functionName,parameters);
		}
	}
}