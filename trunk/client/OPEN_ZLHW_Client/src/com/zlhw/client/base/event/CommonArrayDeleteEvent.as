package com.zlhw.client.base.event
{
	import com.zlhw.client.base.blazeDs.BlazeDsEvent;
	import com.zlhw.client.base.model.BaseModel;
	
	import mx.collections.ArrayCollection;

	public class CommonArrayDeleteEvent extends BlazeDsEvent
	{
		public var dataArray : ArrayCollection;
		public var removeNode:BaseModel;
		public function CommonArrayDeleteEvent(serviceName:String,functionName:String,removeNode:BaseModel)
		{
			this.removeNode=removeNode;
			var parameters:Array=new Array;
			parameters.push(removeNode);
			super(serviceName,functionName,parameters);
		}
		
		
		public override function doSuccess( result : Object ) : void//命令运行正常的处理函数
		{	
			
			for(var i:int=0; i < dataArray.length; i++) {
				if( BaseModel(dataArray[i]).getPrimary() == removeNode.getPrimary() ) {
					dataArray.removeItemAt(i);
					break;
				}
			}
			super.doSuccess(result);
		}
	}
}