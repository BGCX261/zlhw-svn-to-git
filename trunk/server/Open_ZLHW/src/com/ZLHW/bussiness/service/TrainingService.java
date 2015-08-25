package com.ZLHW.bussiness.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

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
import com.ZLHW.bussiness.dao.TrainingDAO;
import com.ZLHW.bussiness.model.SafeUser;
import com.ZLHW.bussiness.model.TrainDetail;
import com.ZLHW.bussiness.model.Training;

@Transactional
@Service("TrainingService")
@RemotingDestination(channels={"my-amf"},serviceAdapter="flexcontrol") 
public class TrainingService extends BaseService<Training, Integer>
{
	@Autowired
	TrainingDAO trainDao;
  private static final Log log = LogFactory.getLog(TrainingService.class);

  public List<Training> findBySafeUser(SafeUser safeUser)
  {
    return getDao().findByHQL("from Training t where t.safeUser=?", new Object[] { safeUser });
  }

  public Page loadTrainings(Page page) {
    List queryConditions = new ArrayList();
    Map conditionMap = page.getQueryCondition();

    if (conditionMap == null) {
      findByPageWithHQL(page, "from Training t order by t.dbId desc", new Object[0]);
    }
    else if (conditionMap.get("type").equals("simpleSearch")) {
      Date startDate = (Date)conditionMap.get("startDate");
      Date endDate = (Date)conditionMap.get("endDate");
      findByPageWithHQL(page, "from Training t where t.startDate>=? and t.startDate<=? order by t.id desc", new Object[] { startDate, endDate });
    } else if (conditionMap.get("type").equals("complexSearch")) {
      StringBuffer hql = new StringBuffer("from Training t where 1=1 ");
      if (conditionMap.get("certificate") != null)
        hql.append(" and t.certificate like '%").append(conditionMap.get("certificate")).append("%'");

      if (conditionMap.get("companyName") != null)
        hql.append(" and t.safeUser.company.name like '%").append(conditionMap.get("companyName")).append("%'");

      if (conditionMap.get("safeUserName") != null)
        hql.append(" and t.safeUser.name like '%").append(conditionMap.get("safeUserName")).append("%'");

      if (conditionMap.get("zsdq") != null) {
        Date zsdqDate = (Date)conditionMap.get("zsdq");
        hql.append(" and t.endDate<=:zsdqDate");
        queryConditions.add(new QueryCondition("zsdqDate", zsdqDate));
      }
      getDao().findByPageWithTmpHQL(page, hql.toString(), queryConditions);
    }

    return page;
  }

  public String downLoadTraining(Page page) throws Exception {
    List queryConditions = new ArrayList();
    Map conditionMap = page.getQueryCondition();

    DateFormat format1 = new SimpleDateFormat("yyyyMMddHHmmss");
    String title = format1.format(new Date());
    List list = null;

    if (conditionMap == null) {
      list = getDao().findByHQL("from Training t order by t.dbId desc");
    }
    else if (conditionMap.get("type").equals("simpleSearch")) {
      Date startDate = (Date)conditionMap.get("startDate");
      Date endDate = (Date)conditionMap.get("endDate");
      list = getDao().findByHQL("from Training t where t.startDate>=? and t.startDate<=? order by t.id desc",  startDate, endDate );
    } else if (conditionMap.get("type").equals("complexSearch")) {
      StringBuffer hql = new StringBuffer("from Training t where 1=1 ");
      if (conditionMap.get("certificate") != null)
        hql.append(" and t.certificate like '%").append(conditionMap.get("certificate")).append("%'");

      if (conditionMap.get("companyName") != null)
        hql.append(" and t.safeUser.company.name like '%").append(conditionMap.get("companyName")).append("%'");

      if (conditionMap.get("safeUserName") != null)
        hql.append(" and t.safeUser.name like '%").append(conditionMap.get("id")).append("%'");

      if (conditionMap.get("zsdq") != null) {
        Date zsdqDate = (Date)conditionMap.get("zsdq");
        hql.append(" and t.endDate<=:zsdqDate");
        queryConditions.add(new QueryCondition("zsdqDate", zsdqDate));
      }
      list = getDao().findByTmpHQL(hql.toString(), queryConditions);
    }

    ExportXls(list, title);
    return title;
  }

  public void editYear1(TrainDetail train1) throws BaseErrorModel {
    Training newTrain = (Training)getDao().loadById(train1.getTraining().getDbId());
    newTrain.setTrain1(train1.getTrainDate());
    newTrain.setTrain1Type(train1.getTrainType());
    newTrain.setTrain1Score(train1.getTrainScore());
    update(newTrain); }

  public void editYear2(TrainDetail train2) throws BaseErrorModel {
    Training newTrain = (Training)getDao().loadById(train2.getTraining().getDbId());
    if (newTrain.getTrain1Type().byteValue() == 0)
      throw new BaseErrorModel("第一年是未培训状态,无法修改", "");
    newTrain.setTrain2(train2.getTrainDate());
    newTrain.setTrain2Type(train2.getTrainType());
    newTrain.setTrain2Score(train2.getTrainScore());
    update(newTrain); }

  public void editYear3(TrainDetail train3) throws BaseErrorModel {
    Training newTrain = (Training)getDao().loadById(train3.getTraining().getDbId());
    if (newTrain.getTrain1Type().byteValue() == 0)
      throw new BaseErrorModel("第二年是未培训状态,无法修改", "");
    newTrain.setTrain3(train3.getTrainDate());
    newTrain.setTrain3Type(train3.getTrainType());
    newTrain.setTrain3Score(train3.getTrainScore());
    update(newTrain);
  }

  public void ExportXls(List<Training> list, String title) throws Exception {
    OutputStream os = null;
    WritableWorkbook wwb = null;
    try {
      System.out.println(getClass().getClassLoader().getResource("../../XLS").getPath());
      File exportFile = new File(getClass().getClassLoader().getResource("../../XLSBak/").getPath() + title + ".xls");
      if (!(exportFile.exists()))
        exportFile.createNewFile();
      os = new FileOutputStream(exportFile);
      File importFile = new File(getClass().getClassLoader().getResource("../../XLS/").getPath() + "培训证书打印.xls");
      wwb = Workbook.createWorkbook(exportFile, Workbook.getWorkbook(importFile));
      WritableSheet sheet = wwb.getSheet(0);
      for (int r = 0; r < list.size(); ++r) {
        Training train = (Training)list.get(r);

        Number l0 = new Number(0, r + 3, r + 1);
        sheet.addCell(l0);

        Label l1 = new Label(1, r + 3, train.getSafeUser().getName());
        sheet.addCell(l1);

        Label l2 = new Label(2, r + 3, train.getScore()+"");
        sheet.addCell(l2);

        Label l3 = new Label(3, r + 3, train.getSafeUser().getSex());
        sheet.addCell(l3);

        Label l4 = new Label(4, r + 3, train.getSafeUser().getZhiCheng());
        sheet.addCell(l4);

        if (train.getSafeUser().getEducation() != null) {
          Label l5 = new Label(5, r + 3, train.getSafeUser().getEducation().getName());
          sheet.addCell(l5);
        }

        Label l6 = new Label(6, r + 3, train.getSafeUser().getIdentityID());
        sheet.addCell(l6);

        Label l7 = new Label(7, r + 3, train.getSafeUser().getCompany().getName());
        sheet.addCell(l7);

        Date startDate = train.getStartDate();
        DateFormat format1 = new SimpleDateFormat("yyyy年MM月dd日");
        if (startDate != null) {
          String startYear = (startDate.getYear() + 1900)+"";
          String startMonth = (startDate.getMonth() + 1)+"";
          String startDay = startDate.getDate()+"";
          Label l8 = new Label(8, r + 3, format1.format(startDate));
          sheet.addCell(l8);

          Label l10 = new Label(10, r + 3, startYear);
          sheet.addCell(l10);
          Label l11 = new Label(11, r + 3, startMonth);
          sheet.addCell(l11);
          Label l12 = new Label(12, r + 3, startDay);
          sheet.addCell(l12);
        }

        Date endDate = train.getEndDate();
        if (endDate != null) {
          Label l17;
          String endYear = (endDate.getYear() + 1900)+"";
          String endMonth = (endDate.getMonth() + 1)+"";
          String endDay = endDate.getDate()+"";
          Label l9 = new Label(9, r + 3, format1.format(endDate));
          sheet.addCell(l9);
          Label l13 = new Label(13, r + 3, endYear);
          sheet.addCell(l13);
          Label l14 = new Label(14, r + 3, endMonth);
          sheet.addCell(l14);
          Label l15 = new Label(15, r + 3, endDay);
          sheet.addCell(l15);

          Label l16 = new Label(16, r + 3, train.getCertificate());
          sheet.addCell(l16);

          if (train.getSafeUser().getType().equals((byte)0)) {
            l17 = new Label(17, r + 3, "主要负责人");
            sheet.addCell(l17);
          } else {
            l17 = new Label(17, r + 3, "安管员");
            sheet.addCell(l17);
          }

          if (train.getSafeUser().getCompany().getType() != null) {
            Label l18 = new Label(18, r + 3, train.getSafeUser().getCompany().getType().getName());
            sheet.addCell(l18);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();

      wwb.write();
      wwb.close();
      os.close();
    }
    finally
    {
      wwb.write();
      wwb.close();
      os.close();
    }
  }
}