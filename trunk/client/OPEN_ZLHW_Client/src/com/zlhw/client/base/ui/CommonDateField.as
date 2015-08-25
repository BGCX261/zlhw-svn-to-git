package com.zlhw.client.base.ui
{
	import mx.controls.DateField;

	public class CommonDateField extends  DateField
	{
		public function CommonDateField()
		{
			this.yearNavigationEnabled=true;
			this.dayNames=["日","一","二","三","四","五","六"];
			this.monthNames=["一月","二月","三月","四月","五月","六月","七月","八月","九月","十"," 十一月","十二月"];
			this.formatString="YYYY/MM/DD"
		}
	}
}