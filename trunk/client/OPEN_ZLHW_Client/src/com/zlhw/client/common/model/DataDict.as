package com.zlhw.client.common.model{
	import com.zlhw.client.base.model.IntTable;

	[Bindable]
	[RemoteClass(alias="com.ZLHW.common.model.DataDict")]
	 public class DataDict extends IntTable
	{
	public var name :String;// 名称
	public var dataDictCatalog :DataDictCatalog;// 数据字典目录
	public var str1 :String;// 备用1
	public var str2 :String;// 备用2
	public var str3 :String;// 备用3
	}
}