package com.zlhw.client.bussiness.model{
	import com.zlhw.client.base.model.IntTable;

	[Bindable]
	[RemoteClass(alias="com.ZLHW.bussiness.model.Training")]
	 public class Training extends IntTable
	{
	public var version :int;// 乐观锁:版本号
	public var safeUser :SafeUser;// 培训对象
	public var state:int;// 证书状态:0:已结束的证书1:未结束的证书
	public var trained :int;// 是否培训过0:否1:是
	public var score :int;// 培训分数
	public var startDate :Date;// 培训开始日期
	public var endDate :Date;// 培训截止日期
	public var certificate :String;// 证书编号
	public var hasPic :int;// 有无照片
	public var hasIdentity :int;// 有无身份证复印件
	public var train1 :Date;// 第一年培训 日期
	public var train1Type :int;// 第一年培训 状态0:否1:是
	public var train1Score :int;// 第一年培训 成绩
	public var train2 :Date;// 第二年培训 日期
	public var train2Type :int;// 第二年培训 状态0:否1:是
	public var train2Score :int;// 第二年培训 成绩
	public var train3 :Date;// 第三年培训 日期
	public var train3Type :int;// 第三年培训 状态0:否1:是
	public var train3Score :int;// 第三年培训 成绩
	public var addDate:Date;//添加时间
	}
}