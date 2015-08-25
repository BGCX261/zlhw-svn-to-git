package com.jnlxc.client.model{
	[Bindable]
	[RemoteClass(alias="com.ZLHW.common.model.Organ")]
	 public class Organ extends IntTable
	{
	public var code :String;// 机构代号000100010001的形式
	public var name :String;// 机构名
	public var lever :int;// 机构层级,1级表示顶级机构
	public var parent :int;// 机构层级
	}
}