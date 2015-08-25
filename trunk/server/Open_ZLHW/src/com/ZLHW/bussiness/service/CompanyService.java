package com.ZLHW.bussiness.service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.Form.Page;
import com.ZLHW.base.dao.QueryCondition;
import com.ZLHW.base.service.BaseService;
import com.ZLHW.bussiness.dao.CompanyDAO;
import com.ZLHW.bussiness.dao.SafeUserDAO;
import com.ZLHW.bussiness.model.Company;
import com.ZLHW.bussiness.model.SafeUser;

@Transactional
@Service("CompanyService")
@RemotingDestination(channels={"my-amf"},serviceAdapter="flexcontrol") 
public class CompanyService extends BaseService<Company, Integer>
{
  private static final Log log = LogFactory.getLog(CompanyService.class);
  
  @Autowired
  CompanyDAO companyDao;
  @Autowired
  SafeUserDAO safeUserDao;

  public void autoGenerate()
    throws BiffException, BaseErrorModel, IOException
  {
    ExclRead.generate();
  }
  
  public Company create(Company company,String faren){
	  this.create(company);
	  if(faren!=null&&!faren.equals("")){
	  SafeUser user=new SafeUser();
	  user.setAddDate(new Date());
	  user.setType((byte)0);
	  user.setName(faren);
	  user.setCompany(company);
	  safeUserDao.create(user);
	  }
	return company;
	  
  }

  public Page loadCompany(Page page) {
    List queryConditions = new ArrayList();
    Map conditionMap = page.getQueryCondition();

    if (conditionMap == null)
      findByPageWithHQL(page, "from Company t order by id desc");
    else if (conditionMap.get("type") != null)
      if (conditionMap.get("type").equals("simpleSearch")) {
        findByPageWithHQL(page, 
          "from Company t where t.name like '%" + 
          page.getQueryCondition().get("value") + "%' order by id desc");
      }
      else if (conditionMap.get("type").equals("complexSearch")) {
        StringBuffer hql = new StringBuffer("from Company t where 1=1 ");
        if (conditionMap.get("companyName") != null)
          hql.append(" and t.name like '%").append(conditionMap.get("companyName")).append("%'");

        if (conditionMap.get("id") != null)
          hql.append(" and t.id like '%").append(conditionMap.get("id")).append("%'");

        if (conditionMap.get("townId") != null)
          hql.append(" and t.town.id = ").append(conditionMap.get("townId"));

        if (conditionMap.get("hasAgent") != null)
          hql.append(" and t.hasAgent =").append(conditionMap.get("hasAgent"));

        if (conditionMap.get("safeProvide") != null)
          hql.append(" and t.safeProvide =").append(conditionMap.get("safeProvide"));

        if (conditionMap.get("companyTypeId") != null)
          hql.append(" and t.type.id =").append(conditionMap.get("companyTypeId"));

        if (conditionMap.get("natureId") != null)
          hql.append(" and t.nature.id =").append(conditionMap.get("natureId"));

        if (conditionMap.get("startDate") != null) {
          Date startDate = (Date)conditionMap.get("startDate");
          hql.append(" and t.addDate >:startDate");
          queryConditions.add(new QueryCondition("startDate", startDate));
        }
        if (conditionMap.get("endDate") != null) {
          Date endDate = (Date)conditionMap.get("endDate");
          hql.append(" and t.addDate <:endDate");
          queryConditions.add(new QueryCondition("endDate", endDate));
        }
        if (conditionMap.get("hasTrained") != null) {
          int value = ((Integer)conditionMap.get("hasTrained")).intValue();
          if (value == 0)
            hql.append(" and t in(select su.company from SafeUser su where su.hasTrained=0)");
          else
            hql.append(" and t in(select su.company from SafeUser su where su.hasTrained>0)");
        }
        hql.append(" order by id desc");
        System.out.println(hql.toString());
        getDao().findByPageWithTmpHQL(page, hql.toString(), queryConditions);
      }


    return page;
  }
}