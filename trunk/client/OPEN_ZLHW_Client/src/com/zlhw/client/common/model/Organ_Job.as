package com.zlhw.client.common.model{
	import com.zlhw.client.base.model.IntTable;

	[Bindable]
	[RemoteClass(alias="com.ZLHW.common.model.Organ_Job")]
	 public class Organ_Job extends IntTable
	{
	public var organ :Organ;// 用户表
	public var job :Job;// 岗位
	}
}