package com.zlhw.client.bussiness.model
{
	import com.zlhw.client.base.model.BaseModel;

	[Bindable]
	[RemoteClass(alias="com.ZLHW.bussiness.model.TrainDetail")]
	public class TrainDetail extends BaseModel
	{
		public var times:String;
		public var trainDate:Date;
		public var trainType:int;
		public var trainScore:int;
		public var training:Training;
	}
}