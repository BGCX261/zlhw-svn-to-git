package  com.zlhw.client.base.event
{
	import com.zlhw.client.base.model.BaseModel;
	import com.zlhw.client.base.util.ObjectUtil;

	public class CommonRefreshEvent extends CommonBaseModelEvent
	{
		public function CommonRefreshEvent(model:BaseModel)
		{
			var serviceName:String=ObjectUtil.getClassName(model)+"Service";
			super(serviceName,"refresh",model);
		}
	}
}