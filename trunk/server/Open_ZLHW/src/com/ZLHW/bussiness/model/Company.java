package com.ZLHW.bussiness.model;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.IntTable;
import com.ZLHW.base.table.TableDeclare;
import com.ZLHW.common.model.DataDict;
@Entity
@Table(name="STAQ_Company")
@TableDeclare(comment="工作单位", tableName="STAQ_Company")
public class Company extends HIntTable
{
	@Column(length=50)
  private String name;//名称
	@ManyToOne
  private SafeUser representative;//法人代表
	@ManyToOne
  private Town town;//所属乡镇
	@Column(length=20)
  private String phone;//联系电话
	@Column(length=20)
  private String fax;//传真
  private Integer guimo;//企业规模(人数)
  private Byte hasAgent;//是否有安全管理机构设置0:无1:有
  private Byte safeProvide;//安全管理人员配备0:专职1:兼职
  @ManyToOne
  private DataDict type;//单位类型D
  @ManyToOne
  private DataDict nature;//单位性质C
  @Column(length=100)
  private String address;//详细地址
  @Temporal(TemporalType.TIME)
  private Date addDate;//添加时间

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public SafeUser getRepresentative()
  {
    return this.representative;
  }

  public void setRepresentative(SafeUser representative)
  {
    this.representative = representative;
  }

  public Town getTown()
  {
    return this.town;
  }

  public void setTown(Town town)
  {
    this.town = town;
  }

  public String getPhone()
  {
    return this.phone;
  }

  public void setPhone(String phone)
  {
    this.phone = phone;
  }

  public String getFax()
  {
    return this.fax;
  }

  public void setFax(String fax)
  {
    this.fax = fax;
  }

  public Integer getGuimo()
  {
    return this.guimo;
  }

  public void setGuimo(Integer guimo)
  {
    this.guimo = guimo;
  }

  public Byte getHasAgent()
  {
    return this.hasAgent;
  }

  public void setHasAgent(Byte hasAgent)
  {
    this.hasAgent = hasAgent;
  }

  public Byte getSafeProvide()
  {
    return this.safeProvide;
  }

  public void setSafeProvide(Byte safeProvide)
  {
    this.safeProvide = safeProvide;
  }

  public DataDict getType()
  {
    return this.type;
  }

  public void setType(DataDict type)
  {
    this.type = type;
  }

  public DataDict getNature()
  {
    return this.nature;
  }

  public void setNature(DataDict nature)
  {
    this.nature = nature;
  }

  public String getAddress()
  {
    return this.address;
  }

  public void setAddress(String address)
  {
    this.address = address;
  }

  public Date getAddDate()
  {
    return this.addDate;
  }

  public void setAddDate(Date addDate)
  {
    this.addDate = addDate;
  }
}