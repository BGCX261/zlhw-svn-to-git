package com.zlhw.client.common.model{
	import com.zlhw.client.base.model.BaseModel;

	[Bindable]
	[RemoteClass(alias="com.ZLHW.common.model.ExcutionExt")]
	 public class ExcutionExt extends BaseModel
	{
	public var processInstanceId :String;// 流程编号
	public var str1 :String;// 附加字段1
	public var str2 :String;// 附加字段2
	public var str3 :String;// 附加字段3
	}
}