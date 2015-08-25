package com.ZLHW.bussiness.dao;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.ZLHW.base.dao.DAO;
import com.ZLHW.bussiness.model.Town;
@Repository
public class TownDAO extends DAO<Town, Integer>
{
  private static final Log log = LogFactory.getLog(TownDAO.class);
}