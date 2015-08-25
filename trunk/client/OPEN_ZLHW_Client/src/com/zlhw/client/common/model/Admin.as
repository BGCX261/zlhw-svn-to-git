package com.zlhw.client.common.model{
	import com.zlhw.client.base.model.IntTable;
	
	import mx.collections.ArrayCollection;

	[Bindable]
	[RemoteClass(alias="com.ZLHW.common.model.Admin")]
	 public class Admin extends IntTable
	{
		 public var account:String; //账号
		 public var name:String; //名称
		 public var password:String; //密码
		 public var nextClientNum:int;//下一个客户号
		 public var organ_Job:Organ_Job; //机构-岗位
		 public var state:int;//0:离职1:正常上班
		 public var addDate:Date; //添加日期
		 public var lastlogin:Date; //上次登录时间
		 public var email:String; //电子邮件
		 public var children:ArrayCollection; //前台需要级联显示的内容
		 public var version:int;//版本号
	
}
}