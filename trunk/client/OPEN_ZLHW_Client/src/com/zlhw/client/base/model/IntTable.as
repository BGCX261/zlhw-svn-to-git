package com.zlhw.client.base.model
{
	public class IntTable extends BaseModel
	{
		public var dbId :int;// 数值形主键
		
		public override function getPrimary():Object{
			return dbId;
		}
		
	}
}