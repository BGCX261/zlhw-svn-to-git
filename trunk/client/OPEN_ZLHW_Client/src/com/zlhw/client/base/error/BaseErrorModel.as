package com.zlhw.client.base.error
{
	[Bindable]
	[RemoteClass(alias="com.ZLHW.base.Exception.BaseErrorModel")]
	public class BaseErrorModel
	{
		public var errorView:String ;
		public var errorMessage:String;
	}
}