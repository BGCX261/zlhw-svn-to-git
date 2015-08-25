package com.zlhw.client.common.model{
	import com.zlhw.client.base.model.IntTable;
	
	import mx.collections.ArrayCollection;

	[Bindable]
	[RemoteClass(alias="com.ZLHW.common.model.Author")]
	 public class Author extends IntTable
	{
	public var name :String;// 名称
	public var children :ArrayCollection;//权限明细
	public var selected :Boolean;

	}
}