package com.zlhw.client.base.modelLocator
{
	import com.zlhw.client.base.blazeDs.BlazeDsCommand;
	import com.zlhw.client.base.validate.CommonValidateTest;
	import com.zlhw.client.bussiness.company.AddCompany;
	import com.zlhw.client.bussiness.company.CompanyPanel;
	import com.zlhw.client.bussiness.company.EditCompany;
	import com.zlhw.client.bussiness.company.ViewCompany;
	import com.zlhw.client.bussiness.safeUser.FarenPanel;
	import com.zlhw.client.bussiness.safeUser.SafeUserPanel;
	import com.zlhw.client.bussiness.town.TownPanel;
	import com.zlhw.client.bussiness.train.TrainPanel;
	import com.zlhw.client.common.admin.AdminPanel;
	import com.zlhw.client.common.author.AuthorPanel;
	import com.zlhw.client.common.dict.DictAddPanel;
	import com.zlhw.client.common.dict.DictEditPanel;
	import com.zlhw.client.common.dict.DictPanel;
	import com.zlhw.client.common.flow.FlowMain;
	import com.zlhw.client.common.job.JobPanel;
	import com.zlhw.client.common.login.InitServiceEvent;
	import com.zlhw.client.common.model.Admin;
	import com.zlhw.client.common.model.Job_Role;
	import com.zlhw.client.common.model.Organ_Job;
	import com.zlhw.client.common.model.TreeNode;
	import com.zlhw.client.common.organ.OrganPanel;
	import com.zlhw.client.common.role.RolePanel;
	
	import mx.collections.ArrayCollection;
	import mx.core.UIComponent;

	public dynamic class CommonModelLocator extends UIComponent
	{
		//定义ModelLocator类变量modelLocator
		private static var modelLocator : CommonModelLocator;
		//getInstance方法用以获取类的实例
		public static function getInstance() : CommonModelLocator 
		{
			if ( modelLocator == null )
				modelLocator = new CommonModelLocator;			
			return modelLocator;
		}	
		
		public function refresh():void{
			var e : InitServiceEvent = new InitServiceEvent();
			new BlazeDsCommand(e).send();
		}
		public function clear():void{
			modelLocator=null;
			modelLocator=new CommonModelLocator;
		}
		
//		public var url:String="../";
		public var url:String="http://localhost:8080/ZLHW/";
		
		//需要全局声明的变量
		public var commonValidateTest : CommonValidateTest;
		public var admin:Admin;
		public var mainPanel:MainPanel;
		[Bindable]
		public var menu:ArrayCollection;
		[Bindable]
		public var authorDetails:Object;
		
		
		//以下对象仅为反射类服务,对象名可任意
		private var v1:DictPanel;
		private var v2:DictAddPanel;
		private var v3:DictEditPanel;
		private var treeNode:TreeNode;
		private var job_Role:Job_Role;
		private var organ_Job:Organ_Job;
		private var f1:FlowMain;
		private var a1:AdminPanel;
		private var a2:OrganPanel;
		private var a3:RolePanel;
		private var a4:JobPanel;
		private var a5:AuthorPanel;
		private var a6:CompanyPanel;
		private var a7:SafeUserPanel;
		private var a8:TrainPanel;
		private var a9:FarenPanel;
		private var a10:TownPanel;
	}
}