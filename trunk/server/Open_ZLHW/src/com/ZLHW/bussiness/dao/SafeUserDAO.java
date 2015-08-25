package com.ZLHW.bussiness.dao;


import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.dao.DAO;
import com.ZLHW.bussiness.model.Company;
import com.ZLHW.bussiness.model.SafeUser;
import com.ZLHW.bussiness.model.Training;
@Repository
public class SafeUserDAO extends DAO<SafeUser, Integer>
{
  private static final Log log = LogFactory.getLog(SafeUserDAO.class);
  @Autowired
  private TrainingDAO trainingDao;
  @Autowired
  private CompanyDAO companyDao;

  public SafeUser create(SafeUser safeUser)
    throws BaseErrorModel
  {
    safeUser.setAddDate(new Date());
    safeUser.setHasTrained((byte)0);
    super.create(safeUser);
    if (safeUser.getType().byteValue() == 0) {
      Company company = (Company)this.companyDao.loadById(safeUser.getCompany().getDbId());
      if (company.getRepresentative() != null)
        throw new BaseErrorModel("已有法人代表", "");

      company.setRepresentative(safeUser);
      this.companyDao.update(company);
    }
    return safeUser;
  }

  public void delete(SafeUser safeUser) throws BaseErrorModel {
    refresh(safeUser);
    List list = this.trainingDao.findByHQL("from Training t where t.safeUser=?", new Object[] { safeUser });
    for (Iterator localIterator = list.iterator(); localIterator.hasNext(); ) { Training train = (Training)localIterator.next();
      this.trainingDao.delete(train);
    }
    Company company = safeUser.getCompany();
    this.companyDao.refresh(safeUser.getCompany());
    if ((company.getRepresentative() != null) && (company.getRepresentative().equals(safeUser))) {
      company.setRepresentative(null);
      this.companyDao.update(company);
    }
    super.delete(safeUser);
  }

  public void update(SafeUser safeUser) throws BaseErrorModel {
    Company company = (Company)this.companyDao.loadById(safeUser.getCompany().getDbId());
    SafeUser oldOne = (SafeUser)loadById(safeUser.getDbId());

    if (!(oldOne.getType().equals(safeUser.getType()))) {
      if (safeUser.getType().byteValue() == 0) {
        if (company.getRepresentative() != null)
          throw new BaseErrorModel("已有法人代表", "");

        company.setRepresentative(safeUser);
      } else {
        company.setRepresentative(null);
      }
      this.companyDao.merger(company);
    }
    super.merger(safeUser);
  }

}