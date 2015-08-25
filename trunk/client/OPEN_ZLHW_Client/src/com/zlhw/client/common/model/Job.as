package com.zlhw.client.common.model{
	import com.zlhw.client.base.model.IntTable;

	[Bindable]
	[RemoteClass(alias="com.ZLHW.common.model.Job")]
	 public class Job extends IntTable
	{
	public var name :String;// 岗位名称
	}
}