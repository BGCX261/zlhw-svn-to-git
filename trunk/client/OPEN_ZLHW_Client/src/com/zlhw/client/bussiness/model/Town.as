package com.zlhw.client.bussiness.model{
	import com.zlhw.client.base.model.IntTable;

	[Bindable]
	[RemoteClass(alias="com.ZLHW.bussiness.model.Town")]
	 public class Town extends IntTable
	{
	public var name :String;// 名称
	}
}