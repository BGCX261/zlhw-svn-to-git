package com.zlhw.client.common.login
{
	import com.adobe.ac.mxeffects.DistortionConstants;
	import com.adobe.ac.mxeffects.Gate;
	import com.zlhw.client.base.blazeDs.BlazeDsEvent;
	import com.zlhw.client.base.modelLocator.CommonModelLocator;
	import com.zlhw.client.common.login.LoginPanel;
	
	import flash.net.SharedObject;
	
	import mx.containers.ViewStack;
	import mx.controls.Alert;
	import mx.core.Application;
	import mx.effects.Parallel;
	import mx.effects.Resize;
	import mx.effects.Sequence;
	
	
	public class LoginEvent extends BlazeDsEvent
	{	
		public function LoginEvent(serviceName:String,functionName:String,...parameters )//构造函数
		{
			super(serviceName,functionName,parameters);//继承父类CairngormEvent的构造函数
		}
		public override function doSuccess( result : Object ) : void//命令运行正常的处理函数
		{	
			
			CommonModelLocator.getInstance().admin = result.admin;
			var mainApplication:ClientMain=ClientMain(Application.application);
			var loginPanel:LoginPanel=mainApplication.loginPanel;
			var mainPanel:MainPanel=new MainPanel;
			CommonModelLocator.getInstance().mainPanel=mainPanel;
			var loginStack:ViewStack=mainApplication.loginStack;
			loginStack.addChild(mainPanel);
			//将登陆信息缓存到cookie
			trace("登陆成功，用户名: "+result.admin.account);
			
			var mySequence:Sequence = new Sequence();
			
			var gate:Gate = new Gate(loginPanel);
			gate.siblings = [ mainPanel ];
			gate.direction = DistortionConstants.LEFT;    
			gate.smooth = true;
			gate.distortion = 10;
			gate.mode = Gate.OPEN;
			gate.duration = 1000;
			mySequence.addChild(gate);
			
			var parallel:Parallel = new Parallel();
			var resize:Resize = new Resize();
			resize.target = loginStack;
			resize.widthTo = mainApplication.width;
			resize.heightTo = mainApplication.height;
			resize.duration = 1000;
			parallel.addChild(resize);
			
			resize = new Resize();
			resize.target = mainPanel;
			resize.widthTo = mainApplication.width;
			resize.heightTo = mainApplication.height;
			resize.duration = 1000;
			parallel.addChild(resize);             
			mySequence.addChild(parallel);
			mySequence.play();
		}
	}	
}