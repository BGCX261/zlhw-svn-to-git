package com.ZLHW.base.HTable;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Index;

import com.ZLHW.base.Field.BaseField;


@Entity
@org.hibernate.annotations.Table(comment="dddd",appliesTo ="TEST_USER",indexes={@Index(name="index1", columnNames={"uname"})})
@Table(name="TEST_USER")
public class User extends HCodeTable implements Serializable{
	private static final long serialVersionUID = 1L;
	private String uname;
	private String upass;
	@ManyToOne
	private UserInfo userInfo;
	
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	
	public String getUpass() {
		return upass;
	}
	public void setUpass(String upass) {
		this.upass = upass;
	}

	
	
}