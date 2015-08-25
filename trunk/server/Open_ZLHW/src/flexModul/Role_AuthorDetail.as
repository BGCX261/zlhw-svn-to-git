package com.jnlxc.client.model{
	[Bindable]
	[RemoteClass(alias="com.ZLHW.common.model.Role_AuthorDetail")]
	 public class Role_AuthorDetail extends IntTable
	{
	public var role :Role;// 角色
	public var authorDetail :AuthorDetail;// 权限明细表
	}
}