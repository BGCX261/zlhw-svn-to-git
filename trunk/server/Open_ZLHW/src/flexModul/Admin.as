package com.jnlxc.client.model{
	[Bindable]
	[RemoteClass(alias="com.ZLHW.common.model.Author")]
	 public class Author extends IntTable
	{
		 public account:String; //账号
		 public name:String; //名称
		 public password:String; //密码
		 public nextClientNum:int;//下一个客户号
		 public organ_Job:Organ_Job; //机构-岗位
		 public state:int;//0:离职1:正常上班
		 public addDate:Date; //添加日期
		 public lastlogin:Date; //上次登录时间
		 public email:String; //电子邮件
		 public children:ArrayCollection; //前台需要级联显示的内容
	}
}