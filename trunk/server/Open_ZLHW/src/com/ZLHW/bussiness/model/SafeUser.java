package com.ZLHW.bussiness.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ZLHW.base.Field.BaseField;
import com.ZLHW.base.Field.TypeBase;
import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.IntTable;
import com.ZLHW.base.table.Optimistic;
import com.ZLHW.base.table.TableDeclare;
import com.ZLHW.common.model.DataDict;
@Entity
@Table(name="STAQ_SafeUser")
@TableDeclare(comment="用户表", tableName="STAQ_SafeUser")
public class SafeUser extends HIntTable
{
	//已在beans.properties产生/更新当前类-------------------------------
	//自动生成的模型POJO-----------------------------------------
	//注释：SafeUser:用户表
	//主键dbId类型：Integer
  @ManyToOne
	private Company company; //工作单位
	private Byte type; //用户类型0:负责人1:安全管理人员
	@ManyToOne
	private Training currentTrain; //当前培训记录
	private Byte hasTrained; //如果有一条培训记录即经过培训0:未培训1:已培训
	@Column(length=50)
	private String name; //姓名
	@Column(length=50)
	private String address; //详细地址
	@Column(length=50)
	private String sex; //性别
	@ManyToOne
	private DataDict national; //民族E
	@Column(length=50)
	private String zhiWu; //职务
	@Column(length=50)
	private String identityID; //身份证号码
	@Column(length=50)
	private String zhiCheng; //职称
	@ManyToOne
	private DataDict education; //文化程度A
	@Column(length=50)
	private String school; //毕业院校
	@ManyToOne
	private DataDict health; //健康状况B
	@Column(length=50)
	private String phone1; //手机1
	private Byte phone1Type; //手机1入网方式:0移动1联通2电信
	@Column(length=50)
	private String phone2; //手机2
	private Byte phone2Type; //手机2入网方式:0移动1联通2电信
	@Column(length=50)
	private String qq; //qq号码
	@Column(length=50)
	private String email; //email
	@Column(length=50)
	private String govTel; //政府网
	@Column(length=50)
	private String telphone; //家庭电话
	private String remarks; //主要学习与工作简历
	@Temporal(TemporalType.TIME)
	private Date addDate; //添加时间
	/**
	* 获取工作单位
	*/
	public Company getCompany(){
	    return this.company;
	}
	/**
	* 设置工作单位
	* @param company
	*/
	public void setCompany(Company company){
	    this.company = company;
	}
	/**
	* 获取用户类型0:负责人1:安全管理人员
	*/
	public Byte getType(){
	    return this.type;
	}
	/**
	* 设置用户类型0:负责人1:安全管理人员
	* @param type
	*/
	public void setType(Byte type){
	    this.type = type;
	}
	/**
	* 获取当前培训记录
	*/
	public Training getCurrentTrain(){
	    return this.currentTrain;
	}
	/**
	* 设置当前培训记录
	* @param currentTrain
	*/
	public void setCurrentTrain(Training currentTrain){
	    this.currentTrain = currentTrain;
	}
	/**
	* 获取如果有一条培训记录即经过培训0:未培训1:已培训
	*/
	public Byte getHasTrained(){
	    return this.hasTrained;
	}
	/**
	* 设置如果有一条培训记录即经过培训0:未培训1:已培训
	* @param hasTrained
	*/
	public void setHasTrained(Byte hasTrained){
	    this.hasTrained = hasTrained;
	}
	/**
	* 获取姓名
	*/
	public String getName(){
	    return this.name;
	}
	/**
	* 设置姓名
	* @param name
	*/
	public void setName(String name){
	    this.name = name;
	}
	/**
	* 获取详细地址
	*/
	public String getAddress(){
	    return this.address;
	}
	/**
	* 设置详细地址
	* @param address
	*/
	public void setAddress(String address){
	    this.address = address;
	}
	/**
	* 获取性别
	*/
	public String getSex(){
	    return this.sex;
	}
	/**
	* 设置性别
	* @param sex
	*/
	public void setSex(String sex){
	    this.sex = sex;
	}
	/**
	* 获取民族E
	*/
	public DataDict getNational(){
	    return this.national;
	}
	/**
	* 设置民族E
	* @param national
	*/
	public void setNational(DataDict national){
	    this.national = national;
	}
	/**
	* 获取职务
	*/
	public String getZhiWu(){
	    return this.zhiWu;
	}
	/**
	* 设置职务
	* @param zhiWu
	*/
	public void setZhiWu(String zhiWu){
	    this.zhiWu = zhiWu;
	}
	/**
	* 获取身份证号码
	*/
	public String getIdentityID(){
	    return this.identityID;
	}
	/**
	* 设置身份证号码
	* @param identityID
	*/
	public void setIdentityID(String identityID){
	    this.identityID = identityID;
	}
	/**
	* 获取职称
	*/
	public String getZhiCheng(){
	    return this.zhiCheng;
	}
	/**
	* 设置职称
	* @param zhiCheng
	*/
	public void setZhiCheng(String zhiCheng){
	    this.zhiCheng = zhiCheng;
	}
	/**
	* 获取文化程度A
	*/
	public DataDict getEducation(){
	    return this.education;
	}
	/**
	* 设置文化程度A
	* @param education
	*/
	public void setEducation(DataDict education){
	    this.education = education;
	}
	/**
	* 获取毕业院校
	*/
	public String getSchool(){
	    return this.school;
	}
	/**
	* 设置毕业院校
	* @param school
	*/
	public void setSchool(String school){
	    this.school = school;
	}
	/**
	* 获取健康状况B
	*/
	public DataDict getHealth(){
	    return this.health;
	}
	/**
	* 设置健康状况B
	* @param health
	*/
	public void setHealth(DataDict health){
	    this.health = health;
	}
	/**
	* 获取手机1
	*/
	public String getPhone1(){
	    return this.phone1;
	}
	/**
	* 设置手机1
	* @param phone1
	*/
	public void setPhone1(String phone1){
	    this.phone1 = phone1;
	}
	/**
	* 获取手机1入网方式:0移动1联通2电信
	*/
	public Byte getPhone1Type(){
	    return this.phone1Type;
	}
	/**
	* 设置手机1入网方式:0移动1联通2电信
	* @param phone1Type
	*/
	public void setPhone1Type(Byte phone1Type){
	    this.phone1Type = phone1Type;
	}
	/**
	* 获取手机2
	*/
	public String getPhone2(){
	    return this.phone2;
	}
	/**
	* 设置手机2
	* @param phone2
	*/
	public void setPhone2(String phone2){
	    this.phone2 = phone2;
	}
	/**
	* 获取手机2入网方式:0移动1联通2电信
	*/
	public Byte getPhone2Type(){
	    return this.phone2Type;
	}
	/**
	* 设置手机2入网方式:0移动1联通2电信
	* @param phone2Type
	*/
	public void setPhone2Type(Byte phone2Type){
	    this.phone2Type = phone2Type;
	}
	/**
	* 获取qq号码
	*/
	public String getQq(){
	    return this.qq;
	}
	/**
	* 设置qq号码
	* @param qq
	*/
	public void setQq(String qq){
	    this.qq = qq;
	}
	/**
	* 获取email
	*/
	public String getEmail(){
	    return this.email;
	}
	/**
	* 设置email
	* @param email
	*/
	public void setEmail(String email){
	    this.email = email;
	}
	/**
	* 获取政府网
	*/
	public String getGovTel(){
	    return this.govTel;
	}
	/**
	* 设置政府网
	* @param govTel
	*/
	public void setGovTel(String govTel){
	    this.govTel = govTel;
	}
	/**
	* 获取家庭电话
	*/
	public String getTelphone(){
	    return this.telphone;
	}
	/**
	* 设置家庭电话
	* @param telphone
	*/
	public void setTelphone(String telphone){
	    this.telphone = telphone;
	}
	/**
	* 获取主要学习与工作简历
	*/
	public String getRemarks(){
	    return this.remarks;
	}
	/**
	* 设置主要学习与工作简历
	* @param remarks
	*/
	public void setRemarks(String remarks){
	    this.remarks = remarks;
	}
	/**
	* 获取添加时间
	*/
	public Date getAddDate(){
	    return this.addDate;
	}
	/**
	* 设置添加时间
	* @param addDate
	*/
	public void setAddDate(Date addDate){
	    this.addDate = addDate;
	}

}