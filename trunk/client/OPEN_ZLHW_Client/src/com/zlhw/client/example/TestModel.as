package com.zlhw.client.example
{
	public class TestModel
	{
		[validate(method = "validateTest",clazz="com.zlhw.client.base.validate.CommonValidateTest")]
		public var column1 : String;
		public var column2 : String;
		public var selected : int;
	}
}