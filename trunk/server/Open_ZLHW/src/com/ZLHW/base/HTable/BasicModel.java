package com.ZLHW.base.HTable;

import java.io.Serializable;


public abstract class BasicModel implements Serializable,IExtendForFlex{
	/**
	 * 获取主键值
	 */
	public abstract Serializable primary();
	/**
	 * 获取主键名称
	 */
	public abstract String getPrimaryColumn();
	/**
	 * 返回所有的FLEX GET和SET方法
	 */
	public abstract String printFLEXList();
	
	public String getExtendTableName(){
		return "BaseModel";
	}
	
	/**
	 * 判断两个对象是否相等
	 * @param tb
	 * @return
	 */
	public boolean equalWithByPrimary(BasicModel tb){
		if(tb==null)
			return false;
		if(tb.getClass()==this.getClass()&&this.primary().equals(tb.primary())){
			return true;
		}else{
			return false;
		}
	}
}
