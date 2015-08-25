package com.zlhw.client.common.model{
	import com.zlhw.client.base.model.CodeTable;

	[Bindable]
	[RemoteClass(alias="com.ZLHW.common.model.DataDictCatalog")]
	 public class DataDictCatalog extends CodeTable
	{
	public var name :String;// 名称
	public var description :String;// 描述
	}
}