package com.zlhw.client.base.util
{
	import mx.formatters.DateFormatter;
	
	public class DateUtil
	{
		public static const _DateFormatString : String = "YYYY-MM-DD";
		public static const _SecondDateFormatString : String = "YYYY-MM-DD HH:NN:SS"
		
		
		/**
		 * 	格式化日期,返回String
		 * */
		public static function format(date : Date,formatString : String):String{
			var dateFormat : DateFormatter = new DateFormatter;
			dateFormat.formatString = formatString;
			return dateFormat.format(date);
		}
	}
}
