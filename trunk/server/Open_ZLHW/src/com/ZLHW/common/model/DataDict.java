package com.ZLHW.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.jbpm.api.identity.User;

import com.ZLHW.base.Field.BaseField;
import com.ZLHW.base.Field.TypeBase;
import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.IntTable;
import com.ZLHW.base.table.TableDeclare;
@Entity
@Table(name="Base_DataDict")
@TableDeclare(comment = "数据字典", tableName = "Base_DataDict")
public class DataDict extends HIntTable{
	//已在beans.properties产生/更新当前类-------------------------------
	//自动生成的模型POJO-----------------------------------------
	//注释：JNLXC_DataDict:数据字典
	//主键id类型：Integer
	@Column(length=60)
	private String name; //名称
	@ManyToOne
	private DataDictCatalog dataDictCatalog; //数据字典目录
	private String str1; //备用1
	private String str2; //备用2
	private String str3; //备用3
	/**
	* 获取名称
	*/
	public String getName(){
	    return this.name;
	}
	/**
	* 设置名称
	* @param name
	*/
	public void setName(String name){
	    this.name = name;
	}
	/**
	* 获取数据字典目录
	*/
	public DataDictCatalog getDataDictCatalog(){
	    return this.dataDictCatalog;
	}
	/**
	* 设置数据字典目录
	* @param dataDictCatalog
	*/
	public void setDataDictCatalog(DataDictCatalog dataDictCatalog){
	    this.dataDictCatalog = dataDictCatalog;
	}
	/**
	* 获取备用1
	*/
	public String getStr1(){
	    return this.str1;
	}
	/**
	* 设置备用1
	* @param str1
	*/
	public void setStr1(String str1){
	    this.str1 = str1;
	}
	/**
	* 获取备用2
	*/
	public String getStr2(){
	    return this.str2;
	}
	/**
	* 设置备用2
	* @param str2
	*/
	public void setStr2(String str2){
	    this.str2 = str2;
	}
	/**
	* 获取备用3
	*/
	public String getStr3(){
	    return this.str3;
	}
	/**
	* 设置备用3
	* @param str3
	*/
	public void setStr3(String str3){
	    this.str3 = str3;
	}

}
