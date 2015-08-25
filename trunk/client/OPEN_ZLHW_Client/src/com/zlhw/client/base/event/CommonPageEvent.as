package com.zlhw.client.base.event
{
	import com.zlhw.client.base.blazeDs.BlazeDsEvent;
	import com.zlhw.client.base.model.Page;
	
	import mx.collections.ArrayCollection;

	public class CommonPageEvent extends BlazeDsEvent
	{
		public var page : Page;
		public var dataArray : ArrayCollection;
		public function CommonPageEvent(serviceName:String, functionName:String, ...parameters)
		{
			super(serviceName, functionName, parameters);
		}
		
		public override function doFail(result : Object):void{
			
			super.doFail(result);
		}
		public override function doSuccess(result : Object):void{
			this.page.currentPage = result.currentPage;
			this.page.pageSize = result.pageSize;
			this.page.queryCondition = result.queryCondition;
			this.page.resultData = result.resultData;
			this.page.totalClum = result.totalClum;
			this.page.totalPage = result.totalPage;
			
			dataArray.removeAll();
			dataArray.addAll(this.page.resultData);
			super.doSuccess(result);
		}
		
	}
}