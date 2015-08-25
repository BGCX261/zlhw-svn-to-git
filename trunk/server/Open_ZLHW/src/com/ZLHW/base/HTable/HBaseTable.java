package com.ZLHW.base.HTable;

import java.lang.reflect.Field;

import com.ZLHW.base.Field.BaseField;
import com.ZLHW.base.table.Optimistic;

public abstract class HBaseTable extends BasicModel{

	/**
	 * 返回所有的FLEX GET和SET方法
	 */
	public String printFLEXList() {
//		try {
//			StringBuffer content=new StringBuffer("package com.jnlxc.client.model{\r\n");
//			content.append("\t[Bindable]\r\n");
//			content.append("\t[RemoteClass(alias=\""+this.getClass().getName()+"\")]\r\n");
//			content.append("\t public class ").append(this.getClass().getSimpleName()).append(" extends ")
//			.append(this.getExtendTableName()).append("\r\n\t{\r\n");
//			StringBuffer beanDeclare = new StringBuffer();
//			for (Field f : this.getClass().getDeclaredFields()) {
//				if(BaseField.class.isAssignableFrom(f.getType())){
//				BaseField trueField;
//				// 反射获取实例
//				trueField = (BaseField) f.get(this);
//				beanDeclare.append(trueField.FLEXbeanDeclare());
////				getsetMethod.append(trueField.getMethod()).append(
////						trueField.setMethod());
//				}
//			}
//			if(this.getClass().getAnnotation(Optimistic.class)!=null)
//				content.append(FIELD_Version.FLEXbeanDeclare());
//			content.append(beanDeclare).append("\t}\r\n}");
//			return content.toString();
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return null;
	}

}
