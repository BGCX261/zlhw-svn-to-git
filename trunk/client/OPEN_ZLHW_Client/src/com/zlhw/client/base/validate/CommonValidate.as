package com.zlhw.client.base.validate
{
	import mx.controls.Alert;

	public class CommonValidate
	{
		public function validateTest():Boolean{
			Alert.show("验证方法测试");
			return false;
		}
	}
}