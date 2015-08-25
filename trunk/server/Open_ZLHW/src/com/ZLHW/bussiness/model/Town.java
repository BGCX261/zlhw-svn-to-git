package com.ZLHW.bussiness.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ZLHW.base.Field.BaseField;
import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.IntTable;
import com.ZLHW.base.table.TableDeclare;


@Entity
@Table(name="STAQ_Town")
@TableDeclare(comment="乡镇", tableName="STAQ_Town")
public class Town extends HIntTable
{
	@Column(length=60)
  private String name;

  public String getName()
  {
    return this.name; }

  public void setName(String name) {
    this.name = name;
  }
}