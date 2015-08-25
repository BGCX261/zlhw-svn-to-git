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
import com.ZLHW.bussiness.model.SafeUser;
import com.ZLHW.bussiness.model.Training;
@Repository
public class TrainingDAO extends DAO<Training, Integer>
{
  private static final Log log = LogFactory.getLog(TrainingDAO.class);
  @Autowired
  private SafeUserDAO safeUserDao;

  public Training create(Training training)
    throws BaseErrorModel
  {
    training.setAddDate(new Date());
    SafeUser safeUser = (SafeUser)this.safeUserDao.loadById(training.getSafeUser().getDbId());
    int trainTimes = safeUser.getHasTrained().byteValue() + 1;
    safeUser.setHasTrained(Byte.valueOf((byte)trainTimes));
    this.safeUserDao.update(safeUser);
    training.setState((byte)1);
    super.create(training);
    List list = findByHQL("from Training t where t.safeUser=?", new Object[] { safeUser });

    if (list.size() != 0)
      for (Iterator localIterator = list.iterator(); localIterator.hasNext(); ) { Training train = (Training)localIterator.next();

        train.setState((byte)0);
        update(train);
      }

    safeUser.setCurrentTrain(training);
    return training;
  }

  public void delete(Training training) throws BaseErrorModel
  {
    SafeUser safeUser = (SafeUser)this.safeUserDao.loadById(training.getSafeUser().getDbId());
    int trainTimes = safeUser.getHasTrained().byteValue() - 1;
    safeUser.setHasTrained(Byte.valueOf((byte)trainTimes));
    safeUser.setCurrentTrain(null);
    this.safeUserDao.update(safeUser);
    super.delete(training);
  }
}