package com.ZLHW.bussiness.dao;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.dao.DAO;
import com.ZLHW.bussiness.model.Company;
@Repository
public class CompanyDAO extends DAO<Company, Integer>
{
  private static final Log log = LogFactory.getLog(CompanyDAO.class);
  @Autowired
  private SafeUserDAO safeUserDao;

  public Company create(Company company)
    throws BaseErrorModel
  {
    if (findByHQL("from Company t where t.name=?", new Object[] { company.getName() }).size() != 0)
      throw new BaseErrorModel("企业重名，请重新命名", "");
    company.setAddDate(new Date());
    return ((Company)super.create(company));
  }

  public void update(Company company) throws BaseErrorModel {
    Company oldCompany = (Company)loadById(company.getDbId());
    if (!(oldCompany.getName().equals(company.getName())))
      if (findByHQL("from Company t where t.name=?", new Object[] { company.getName() }).size() != 0)
        throw new BaseErrorModel("企业重名，请重新命名", "");
    super.merger(company);
  }

  public void delete(Company company) throws BaseErrorModel {
    int usercount = this.safeUserDao.getCountOfAll("select count(*) from SafeUser t where t.company=?",  company );
    if (usercount != 0)
      throw new BaseErrorModel("删除企业单位前请先确认删除相关安全员信息！", "");
    super.delete(company);
  }


}