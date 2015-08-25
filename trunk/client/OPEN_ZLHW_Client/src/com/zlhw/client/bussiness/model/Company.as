package com.zlhw.client.bussiness.model{
	import com.zlhw.client.base.model.IntTable;
	import com.zlhw.client.common.model.DataDict;

	[Bindable]
	[RemoteClass(alias="com.ZLHW.bussiness.model.Company")]
	 public class Company extends IntTable
	{
	public var name :String;// 名称
	public var representative :SafeUser;// 法人代表
	public var town :Town;// 所属乡镇
	public var phone :String;// 电话
	public var fax :String;// 传真
	public var guimo :int;// 企业规模(人数)
	public var hasAgent :int;// 是否有安全管理机构设置0:无1:有
	public var safeProvide :int;// 安全管理人员配备0:专职1:兼职
	public var type :DataDict;// 单位类型D
	public var nature :DataDict;// 单位性质C
	public var address :String;// 详细地址
	public var addDate:Date;//添加时间
	}
}