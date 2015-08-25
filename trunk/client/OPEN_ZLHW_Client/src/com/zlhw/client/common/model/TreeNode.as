package com.zlhw.client.common.model
{
	import mx.collections.ArrayCollection;

	[Bindable]
	[RemoteClass(alias="com.ZLHW.common.model.TreeNode")]
	public class TreeNode
	{
		public var children : ArrayCollection;
		public var selected : Boolean;
		public var data : Object;
		public var text : String;
	}
}