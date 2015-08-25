package com.zlhw.client.base.error
{
	import com.adobe.ac.mxeffects.DistortionConstants;
	import com.adobe.ac.mxeffects.Gate;
	import com.zlhw.client.base.modelLocator.CommonModelLocator;
	import com.zlhw.client.common.login.LoginPanel;
	
	import mx.containers.ViewStack;
	import mx.controls.Alert;
	import mx.core.Application;
	import mx.effects.Parallel;
	import mx.effects.Resize;
	import mx.effects.Sequence;
	import mx.rpc.events.ResultEvent;


	public class BaseError
	{
		public function BaseError()
		{
		}
		public static function hasError(event : ResultEvent) : Boolean
		{
			
			if(event.result == null)
			{
				Alert.show("数据传输错误");
				return true;
			}
			try{
				var errorObject : Object = event.result.error;
				if(errorObject != null)
				{
					var errorMessage :String = BaseErrorModel(errorObject).errorMessage
					Alert.show(errorMessage);
					if(BaseErrorModel(errorObject).errorView=="0000"){
						//关门效果
						var mainApplication:ClientMain=ClientMain(Application.application);
						//						mainApplication.refresh();
						var loginPanel:LoginPanel=mainApplication.loginPanel;
						var loginStack:ViewStack=mainApplication.loginStack;
						var mySequence:Sequence = new Sequence();
						
						var gate:Gate = new Gate(CommonModelLocator.getInstance().mainPanel);
						gate.siblings = [ loginPanel ];
						gate.direction = DistortionConstants.LEFT;    
						gate.smooth = true;
						gate.distortion = 10;
						gate.mode = Gate.CLOSE;
						gate.duration = 1000;
						mySequence.addChild(gate);
						
						var parallel:Parallel = new Parallel();
						var resize:Resize = new Resize();
						resize.target = loginStack;
						resize.duration = 1000;
						resize.widthTo=300;
						resize.heightTo=290;
						parallel.addChild(resize);
						resize = new Resize();
						resize.target = loginPanel;
						resize.duration = 1000;
						resize.widthTo=300;
						resize.heightTo=290;
						parallel.addChild(resize);             
						mySequence.addChild(parallel);
						mySequence.play();
					}
					return true;
				}
			}catch(e:Error){
				
			}
			return false;
		}
	}
}