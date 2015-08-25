package com.jnlxc.client.model{
	[Bindable]
	[RemoteClass(alias="com.ZLHW.common.model.TaskExt")]
	 public class TaskExt extends BaseModel
	{
	public var taskId :String;// 任务编号
	public var processInstanceId :String;// 所属流程编号
	public var comment :String;// 评论
	}
}