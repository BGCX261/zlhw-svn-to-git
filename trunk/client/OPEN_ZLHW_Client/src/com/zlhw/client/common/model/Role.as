package com.zlhw.client.common.model{
	import com.zlhw.client.base.model.IntTable;

	[Bindable]
	[RemoteClass(alias="com.ZLHW.common.model.Role")]
	 public class Role extends IntTable
	{
	public var name :String;// 名称
	}
}