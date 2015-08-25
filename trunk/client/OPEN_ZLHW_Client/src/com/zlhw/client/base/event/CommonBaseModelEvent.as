package  com.zlhw.client.base.event
{
	import com.zlhw.client.base.blazeDs.BlazeDsEvent;
	import com.zlhw.client.base.model.BaseModel;

	///当返回结果为一个普通的model对象，并希望赋值给一个被正被绑定的对象时，可调用此方法
	public class CommonBaseModelEvent extends BlazeDsEvent
	{
		
		public var model :BaseModel = null;
		public function CommonBaseModelEvent(serviceName:String,functionName:String,model :BaseModel)
		{
			var array:Array=new Array;
			this.model = model;
			array.push(model);
			super(serviceName,functionName,array);
		}
		public  override function doSuccess( result : Object ) : void//命令运行正常的处理函数
		{
			var obj:BaseModel =BaseModel(result);
			obj.assignTo(model);
			super.doSuccess(result);
		}
		
	}
}