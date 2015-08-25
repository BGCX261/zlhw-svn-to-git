package com.zlhw.client.bussiness.model{
	import com.zlhw.client.base.model.IntTable;
	import com.zlhw.client.common.model.DataDict;

	[Bindable]
	[RemoteClass(alias="com.ZLHW.bussiness.model.SafeUser")]
	 public class SafeUser extends IntTable
	{
	public var version :int;// 乐观锁:版本号
	public var hasTrained:int;//如果有一条培训记录即经过培训0:未培训1:已培训
	public var currentTrain:Training; //当前培训记录
	public var company :Company;// 工作单位
	public var type :int;// 用户类型0:负责人1:安全管理人员
	public var name :String;// 姓名
	public var address :String;// 详细地址
	public var sex :String;// 性别
	public var national :DataDict;// 名族
	public var zhiWu :String;// 职务
	public var identityID :String;// 身份制号码
	public var zhiCheng :String;// 职称
	public var education :DataDict;// 文化程度A
	public var school :String;// 毕业院校
	public var health :DataDict;// 健康状况B
	public var phone1 :String;// 手机1
	public var phone1Type :int;// 手机1入网方式:0移动1联通2电信
	public var phone2 :String;// 手机2
	public var phone2Type :int;// 手机2入网方式:0移动1联通2电信
	public var qq :String;// qq号码
	public var email :String;// email
	public var govTel :String;// 政府网
	public var telphone :String;// 家庭电话
	public var remarks :String;// 主要学习与工作简历
	public var addDate:Date;//添加时间
	}
}