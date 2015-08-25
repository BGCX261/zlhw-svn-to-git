package com.ZLHW.bussiness.model;

import com.ZLHW.base.Field.BaseField;
import com.ZLHW.base.Field.TypeBase;
import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.IntTable;
import com.ZLHW.base.table.Optimistic;
import com.ZLHW.base.table.TableDeclare;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name="STAQ_Training")
@TableDeclare(comment="培训表", tableName="STAQ_Training")
public class Training extends HIntTable
{
	//已在beans.properties产生/更新当前类-------------------------------
	//自动生成的模型POJO-----------------------------------------
	//注释：Training:培训表
	//主键dbId类型：Integer
	@ManyToOne
  	private SafeUser safeUser;//培训对象
	private Byte trained; //是否培训过0:否1:是
	private Integer score; //培训分数
	@Temporal(TemporalType.DATE)
	private Date startDate; //培训开始日期
	@Temporal(TemporalType.DATE)
	private Date endDate; //到期日期
	private Byte state; //证书状态:0:已结束的证书1:未结束的证书
	@Column(length=50)
	private String certificate; //证书编号
	private Byte hasPic; //有无照片
	private Byte hasIdentity; //有无身份证复印件
	@Temporal(TemporalType.DATE)
	private Date train1; //第一年培训 日期
	private Byte train1Type; //第一年培训 状态0:否1:是
	private Integer train1Score; //第一年培训 成绩
	@Temporal(TemporalType.DATE)
	private Date train2; //第二年培训 日期
	private Byte train2Type; //第二年培训 状态0:否1:是
	private Integer train2Score; //第二年培训 成绩
	@Temporal(TemporalType.DATE)
	private Date train3; //第三年培训 日期
	private Byte train3Type; //第三年培训 状态0:否1:是
	private Integer train3Score; //第三年培训 成绩
	@Temporal(TemporalType.TIME)
	private Date addDate; //添加时间
	/**
	* 获取是否培训过0:否1:是
	*/
	public Byte getTrained(){
	    return this.trained;
	}
	/**
	* 设置是否培训过0:否1:是
	* @param trained
	*/
	public void setTrained(Byte trained){
	    this.trained = trained;
	}
	/**
	* 获取培训分数
	*/
	public Integer getScore(){
	    return this.score;
	}
	/**
	* 设置培训分数
	* @param score
	*/
	public void setScore(Integer score){
	    this.score = score;
	}
	/**
	* 获取培训开始日期
	*/
	public Date getStartDate(){
	    return this.startDate;
	}
	/**
	* 设置培训开始日期
	* @param startDate
	*/
	public void setStartDate(Date startDate){
	    this.startDate = startDate;
	}
	/**
	* 获取到期日期
	*/
	public Date getEndDate(){
	    return this.endDate;
	}
	/**
	* 设置到期日期
	* @param endDate
	*/
	public void setEndDate(Date endDate){
	    this.endDate = endDate;
	}
	/**
	* 获取证书状态:0:已结束的证书1:未结束的证书
	*/
	public Byte getState(){
	    return this.state;
	}
	/**
	* 设置证书状态:0:已结束的证书1:未结束的证书
	* @param state
	*/
	public void setState(Byte state){
	    this.state = state;
	}
	/**
	* 获取证书编号
	*/
	public String getCertificate(){
	    return this.certificate;
	}
	/**
	* 设置证书编号
	* @param certificate
	*/
	public void setCertificate(String certificate){
	    this.certificate = certificate;
	}
	/**
	* 获取有无照片
	*/
	public Byte getHasPic(){
	    return this.hasPic;
	}
	/**
	* 设置有无照片
	* @param hasPic
	*/
	public void setHasPic(Byte hasPic){
	    this.hasPic = hasPic;
	}
	/**
	* 获取有无身份证复印件
	*/
	public Byte getHasIdentity(){
	    return this.hasIdentity;
	}
	/**
	* 设置有无身份证复印件
	* @param hasIdentity
	*/
	public void setHasIdentity(Byte hasIdentity){
	    this.hasIdentity = hasIdentity;
	}
	/**
	* 获取第一年培训 日期
	*/
	public Date getTrain1(){
	    return this.train1;
	}
	/**
	* 设置第一年培训 日期
	* @param train1
	*/
	public void setTrain1(Date train1){
	    this.train1 = train1;
	}
	/**
	* 获取第一年培训 状态0:否1:是
	*/
	public Byte getTrain1Type(){
	    return this.train1Type;
	}
	/**
	* 设置第一年培训 状态0:否1:是
	* @param train1Type
	*/
	public void setTrain1Type(Byte train1Type){
	    this.train1Type = train1Type;
	}
	/**
	* 获取第一年培训 成绩
	*/
	public Integer getTrain1Score(){
	    return this.train1Score;
	}
	/**
	* 设置第一年培训 成绩
	* @param train1Score
	*/
	public void setTrain1Score(Integer train1Score){
	    this.train1Score = train1Score;
	}
	/**
	* 获取第二年培训 日期
	*/
	public Date getTrain2(){
	    return this.train2;
	}
	/**
	* 设置第二年培训 日期
	* @param train2
	*/
	public void setTrain2(Date train2){
	    this.train2 = train2;
	}
	/**
	* 获取第二年培训 状态0:否1:是
	*/
	public Byte getTrain2Type(){
	    return this.train2Type;
	}
	/**
	* 设置第二年培训 状态0:否1:是
	* @param train2Type
	*/
	public void setTrain2Type(Byte train2Type){
	    this.train2Type = train2Type;
	}
	/**
	* 获取第二年培训 成绩
	*/
	public Integer getTrain2Score(){
	    return this.train2Score;
	}
	/**
	* 设置第二年培训 成绩
	* @param train2Score
	*/
	public void setTrain2Score(Integer train2Score){
	    this.train2Score = train2Score;
	}
	/**
	* 获取第三年培训 日期
	*/
	public Date getTrain3(){
	    return this.train3;
	}
	/**
	* 设置第三年培训 日期
	* @param train3
	*/
	public void setTrain3(Date train3){
	    this.train3 = train3;
	}
	/**
	* 获取第三年培训 状态0:否1:是
	*/
	public Byte getTrain3Type(){
	    return this.train3Type;
	}
	/**
	* 设置第三年培训 状态0:否1:是
	* @param train3Type
	*/
	public void setTrain3Type(Byte train3Type){
	    this.train3Type = train3Type;
	}
	/**
	* 获取第三年培训 成绩
	*/
	public Integer getTrain3Score(){
	    return this.train3Score;
	}
	/**
	* 设置第三年培训 成绩
	* @param train3Score
	*/
	public void setTrain3Score(Integer train3Score){
	    this.train3Score = train3Score;
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
	public SafeUser getSafeUser() {
		return safeUser;
	}
	public void setSafeUser(SafeUser safeUser) {
		this.safeUser = safeUser;
	}
	

}