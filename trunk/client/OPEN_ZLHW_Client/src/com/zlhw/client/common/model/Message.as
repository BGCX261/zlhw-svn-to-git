package com.zlhw.client.common.model{
	import com.zlhw.client.base.model.IntTable;

	[Bindable]
	[RemoteClass(alias="com.zb.jnlxc.model.Message")]
	 public class Message extends IntTable
	{
	public var sender :Admin;// 发信息人
	public var received :Admin;// 收信息人
	public var received_Organ :Organ; //收信息部门
	public var addTime :Date;// 添加时间
	public var title :String;// 短信标题
	public var content :String;// 短信内容
	public var status :int;// 当前状态
	public var replyto :int;// 如果是答复短信则不为空
	public var version:int//乐观锁，版本号
	}
}