package com.zlhw.client.common.model
{
	import com.zlhw.client.base.model.BaseModel;
	[Bindable]
	[RemoteClass(alias="com.ZLHW.common.JBPM.MyTask")]
	public class MyTask extends BaseModel
	{
		public var id:String; //任务id
		public var name:String; //任务名
		public var createTime:Date; //发起流程时间
		public var duedate:Date;//截止时间
		public var excutionId:String;//当前流程id
		public var executionName:String;//当前流程名称
		public var formResourceName:String;//对应前台表单路径
		public var assignee:String;//任务负责人
		public var key:String;//业务主键
	}
}