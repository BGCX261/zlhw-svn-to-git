package com.ZLHW.bussiness.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.base.Form.Page;
import com.ZLHW.base.dao.QueryCondition;
import com.ZLHW.base.service.BaseService;
import com.ZLHW.bussiness.dao.SafeUserDAO;
import com.ZLHW.bussiness.model.Company;
import com.ZLHW.bussiness.model.SafeUser;

@Transactional
@Service("SafeUserService")
@RemotingDestination(channels={"my-amf"},serviceAdapter="flexcontrol") 
public class SafeUserService extends BaseService<SafeUser, Integer>
{
  private static final Log log = LogFactory.getLog(SafeUserService.class);
  
  @Autowired
  SafeUserDAO SafeUserDao;

  public List<SafeUser> findByCompany(Company company)
  {
    return getDao().findByHQL("from SafeUser t where t.company=?", company );
  }

  public Page loadSafeUser(Page page) {
    List queryConditions = new ArrayList();
    Map conditionMap = page.getQueryCondition();

    if (conditionMap == null) {
      findByPageWithHQL(page, "from SafeUser order by id desc");
    }
    else if (conditionMap.get("type").equals("simpleSearch")) {
      findByPageWithHQL(page, 
        "from SafeUser t where t.name like '%" + 
        page.getQueryCondition().get("value") + "%' and t.type = "+conditionMap.get("safeUserType")+" order by id desc");
    }
    else if (conditionMap.get("type").equals("complexSearch")) {
      StringBuffer hql = new StringBuffer("from SafeUser t where 1=1 ");
      if (conditionMap.get("safeUserName") != null)
        hql.append(" and t.name like '%").append(conditionMap.get("safeUserName")).append("%'");

      if (conditionMap.get("companyName") != null)
        hql.append(" and t.company.name like '%").append(conditionMap.get("companyName")).append("%'");

      if (conditionMap.get("id") != null)
        hql.append(" and t.id like '%").append(conditionMap.get("id")).append("%'");

      if (conditionMap.get("townId") != null)
        hql.append(" and t.company.town.id = ").append(conditionMap.get("townId"));

      if (conditionMap.get("safeUserType") != null)
        hql.append(" and t.type = ").append(conditionMap.get("safeUserType"));

      if (conditionMap.get("sex") != null)
        hql.append(" and t.sex = '").append(conditionMap.get("sex")).append("'");

      if (conditionMap.get("phoneType") != null) {
        Integer phoneType = (Integer)conditionMap.get("phoneType");
        hql.append(" and (phone1Type=:phoneType or phone2Type=:phoneType)");
        queryConditions.add(new QueryCondition("phoneType", Byte.valueOf(phoneType.byteValue())));
      }
      if (conditionMap.get("identityID") != null)
        hql.append(" and t.identityID = '").append(conditionMap.get("identityID")).append("'");

      if (conditionMap.get("nationalId") != null)
        hql.append(" and t.national = ").append(conditionMap.get("nationalId"));

      if (conditionMap.get("companyTypeId") != null)
        hql.append(" and t.company.type.id =").append(conditionMap.get("companyTypeId"));

      if (conditionMap.get("natureId") != null)
        hql.append(" and t.company.nature.id =").append(conditionMap.get("natureId"));

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
          hql.append(" and t.currentTrain is null");
        else
          hql.append(" and t.currentTrain is not null");
      }
      if (conditionMap.get("zsdq") != null) {
        Date zsdqDate = (Date)conditionMap.get("zsdq");
        hql.append(" and t.currentTrain.endDate<=:zsdqDate");
        queryConditions.add(new QueryCondition("zsdqDate", zsdqDate));
      }
      hql.append(" order by id desc");
      System.out.println(hql.toString());
      getDao().findByPageWithTmpHQL(page, hql.toString(), queryConditions);
    }
    else if (conditionMap.get("type").equals("getSafeUser")) {
      Integer safeUserId = (Integer)conditionMap.get("safeUserId");
      findByPageWithHQL(page, "from SafeUser su where su.id=?",safeUserId);
    }

    return page;
  }
}