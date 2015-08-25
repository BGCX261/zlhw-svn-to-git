package com.ZLHW.bussiness.service;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.service.BaseService;
import com.ZLHW.bussiness.dao.TownDAO;
import com.ZLHW.bussiness.model.Town;

@Transactional
@Service("TownService")
@RemotingDestination(channels={"my-amf"},serviceAdapter="flexcontrol") 
public class TownService extends BaseService<Town, Integer>
{
	@Autowired
	private TownDAO townDao;
  private static final Log log = LogFactory.getLog(TownService.class);

  public Town findTownByName(String name)
    throws BaseErrorModel
  {
    List l = getDao().findByHQL("from Town t where t.name=?", new Object[] { name });
    if (l.size() != 0)
      return ((Town)l.get(0));

    Town town = new Town();
    town.setName(name);
    getDao().create(town);
    return town;
  }
}