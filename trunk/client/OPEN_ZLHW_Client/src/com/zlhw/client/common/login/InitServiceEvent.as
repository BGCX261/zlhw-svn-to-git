package com.zlhw.client.common.login
{
	import com.zlhw.client.base.blazeDs.BlazeDsEvent;
	import com.zlhw.client.base.modelLocator.CommonModelLocator;
	
	public class InitServiceEvent extends BlazeDsEvent
	{
		public function InitServiceEvent()
		{
			super("InitService","init");
		}
		public override function doSuccess(result : Object):void{
			CommonModelLocator.getInstance().menu=result.menu;
			CommonModelLocator.getInstance().authorDetails=result.authorDetails;
			super.doSuccess(result);
		}
}
}