package com.ZLHW.base.HTable;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.TableGenerator;

import com.ZLHW.base.Field.BaseField;
import com.ZLHW.base.table.BaseTable;

@MappedSuperclass
public abstract class HIntTable extends HBaseTable{
	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@GeneratedValue(generator="generater",strategy=GenerationType.TABLE)
	@TableGenerator(name="generater",allocationSize=1,initialValue=1,table="TEST_Generate")
	protected Integer dbId;
	public Integer getDbId() {
		return dbId;
	}
	public void setDbId(Integer dbId) {
		this.dbId = dbId;
	}
	public Serializable primary(){
		return dbId;
	}
	// 获取主键名称
	public  String getPrimaryColumn(){
		return "dbId";
	}
	
}
