package com.jnlxc.client.model{
	[Bindable]
	[RemoteClass(alias="com.ZLHW.common.model.AuthorDetail")]
	 public class AuthorDetail extends IntTable
	{
	public var name :String;// 名称
	public var path :String;// returnView的包路径
	public var beanName :String;// bean名称
	public var beanFunction :String;// bean函数
	public var status :int;// 当前状态:0菜单可见,1菜单不可见
	public var author :Author;// 权限表
	}
}