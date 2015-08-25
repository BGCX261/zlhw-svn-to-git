package com.zlhw.client.base.blazeDs
{
	import com.zlhw.client.base.util.DateUtil;
	import com.zlhw.client.base.util.ObjectUtil;
	import com.zlhw.client.base.validate.CommonValidate;
	
	import flash.events.Event;
	import flash.utils.getDefinitionByName;
	
	import mx.controls.Alert;
	
	public class BlazeDsEvent extends Event
	{
		public static const _RemoteEvent:String  = "RemoteEvent";
		public var serviceName : String;
		public var functionName : String;
		public var parameters : Array;
		public var doValidate : Boolean = false;
		//事件代理中远程方法调用成功的自定义回调函数
		public var successFunction : Function;
		//事件代理中远程方法调用失败的自定义回调函数
		public var failFunction : Function;
		/**
		 * 服务名，方法名，是否进行数据验证，参数
		 * */
		public function BlazeDsEvent(serviceName : String, functionName : String , parameters : Array = null)
		{
			super(_RemoteEvent, false, false);
			this.serviceName = serviceName;
			this.functionName = functionName;
			this.parameters = parameters;
			if(parameters == null)
				this.parameters = new Array;
			else
				this.parameters = parameters;
		}
		/**
		 * 对发送的对象进行数据验证
		 * */
		public function validate():Boolean{
			var defaultValidate : CommonValidate = new CommonValidate;
			var validateFunction : Function = null;
			for each( var parameter : Object in parameters){
				var objMap : Object = ObjectUtil.getAnnotations(parameter);
				for (var fieldName : String in objMap) {
					if(fieldName!="mx_internal_uid"){
						//验证注解validate
						if(objMap[fieldName].validate!=null){
							var flag : Boolean = true;
							try{
								//如果注解中没有参数clazz，使用默认的defaultValidate类中的方法method
								if(objMap[fieldName].validate.clazz== null)
									validateFunction = defaultValidate[objMap[fieldName].validate.method]
								//如果有参数clazz,创建类的实例，获取该类中的方法method
								else{
									var classRefrence:Class = getDefinitionByName(objMap[fieldName].validate.clazz) as Class;
									validateFunction = (new classRefrence)[objMap[fieldName].validate.method]
								}
								//判断是否有参数param,调用验证方法
								if(objMap[fieldName].validate.param == null)
									flag = validateFunction.apply(this);
								else{
									var paramArray : Array = new Array;
									paramArray.push(objMap[fieldName].validate.param);
									flag =validateFunction.apply(this,paramArray);
								}
							}catch(e:Error){
								Alert.show("执行数据验证出错");
								trace(e.getStackTrace());
								flag = false;
							}
							//如果上述某个步骤验证出错,或者出现异常,返回验证不通过
							if(!flag)
								return false;
						}
					}
				}
			}
			return true;
		}
		
		public function doFail(result : Object):void{
			
			//如果有传入的回调方法,则执行
			if(failFunction!=null){
				var argArray:Array=new Array;
				argArray.push(result);
				failFunction.apply(this,argArray);
			}
			
			trace(DateUtil.format(new Date,DateUtil._SecondDateFormatString)+" : "+ObjectUtil.getClassName(this)+" callback fail!");
		}
		
		public function doSuccess(result : Object):void{
			
			//如果有传入的回调方法,则执行
			if(successFunction!=null){
				var argArray:Array=new Array;
				argArray.push(result);
				successFunction.apply(this,argArray);
			}
			
			trace(DateUtil.format(new Date,DateUtil._SecondDateFormatString)+" : "+ObjectUtil.getClassName(this)+" callback success!");
		}
	}
}