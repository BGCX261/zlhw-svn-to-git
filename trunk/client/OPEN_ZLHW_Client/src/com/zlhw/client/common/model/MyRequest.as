package com.zlhw.client.common.model
{
	import com.zlhw.client.base.model.BaseModel;
	[Bindable]
	[RemoteClass(alias="com.ZLHW.common.JBPM.MyRequest")]
	public class MyRequest extends BaseModel
	{
		public var id:String;//流程流水号
		public var createTime:Date; //发起流程时间
		public var endTime:Date; //流程结束时间,未完成则为空
		public var executionId:String;//当前流程id
		public var executionName:String;//当前流程名称
		public var currentTaskId:String;//当前任务id
		public var currentTaskName:String;//当前任务名
		public var currentType:String;//当前状态
		public var lastTransition:String;//流入箭头名
		public var key:String;//业务主键
	}
}