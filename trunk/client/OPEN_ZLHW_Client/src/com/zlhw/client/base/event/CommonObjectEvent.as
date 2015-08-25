package com.zlhw.client.base.event
{
	import com.zlhw.client.base.blazeDs.BlazeDsEvent;
	import com.zlhw.client.base.util.ObjectUtil;

	public class CommonObjectEvent extends BlazeDsEvent
	{
		public var obj :Object = null;
		public function CommonObjectEvent(serviceName:String,functionName:String,obj :Object,...parameters)
		{
			this.obj = obj;
			super(serviceName,functionName,parameters);
		}
		public  override function doSuccess( result : Object ) : void//命令运行正常的处理函数
		{
			ObjectUtil.objectCopy(result , obj);
		}
	}
}