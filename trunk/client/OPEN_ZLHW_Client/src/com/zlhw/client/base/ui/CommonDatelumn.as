package com.zlhw.client.base.ui
{
	import com.zlhw.client.base.util.DateUtil;
	
	import mx.controls.dataGridClasses.DataGridColumn;
	
	public class CommonDatelumn extends DataGridColumn
	{
		public var dateFormatString :String;
		public function CommonDatelumn(columnName:String=null)
		{
			
			super(columnName);
			this.labelFunction = function(item:Object, column:DataGridColumn):String
			{
				if(dateFormatString!=null)
					return DateUtil.format(item[column.dataField],dateFormatString);
				else
					return DateUtil.format(item[column.dataField],DateUtil._DateFormatString);
			}
		}
	}
}