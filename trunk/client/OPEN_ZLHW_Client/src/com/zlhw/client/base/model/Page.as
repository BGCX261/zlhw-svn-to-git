package com.zlhw.client.base.model
{
	import mx.collections.ArrayCollection;

	[RemoteClass(alias="com.ZLHW.base.Form.Page")]
	public class Page
	{
		public var aotuSort : Boolean = true; // 自动排序 默认开启
		
		public var sortKey :String = "id"; //默认排序关键字为 id
		
		public var ascending : Boolean = true; //true 代表升序排列, false代表降序排列 ，默认为升序
		
		public var pageSize : int =10;//每页显示条数
		[Bindable]
		public var totalPage : int =1;//总页数
		
		public var currentPage : int=1;//当前页
		
		public var totalClum : int=0;//总记录数
		
		public var queryCondition : Object;//查询条件
		
		public var resultData : ArrayCollection = new ArrayCollection;//查询结果
		
		public var version:int//乐观锁，版本号
	}
}