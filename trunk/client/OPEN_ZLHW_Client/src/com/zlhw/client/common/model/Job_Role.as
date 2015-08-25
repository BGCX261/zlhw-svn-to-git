package com.zlhw.client.common.model{
	import com.zlhw.client.base.model.IntTable;

	[Bindable]
	[RemoteClass(alias="com.ZLHW.common.model.Job_Role")]
	 public class Job_Role extends IntTable
	{
	public var role :Role;// 角色
	public var job :Job;// 岗位
	}
}