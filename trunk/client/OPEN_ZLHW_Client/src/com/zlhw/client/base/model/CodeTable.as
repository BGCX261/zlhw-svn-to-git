package com.zlhw.client.base.model
{
	public class CodeTable extends BaseModel
	{
		public var code :String;// 数值形主键
		
		public override function getPrimary():Object{
			return this.code;
		}
	}
}