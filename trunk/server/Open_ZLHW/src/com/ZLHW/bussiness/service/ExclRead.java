package com.ZLHW.bussiness.service;

import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.factory.BeanFactory;
import com.ZLHW.bussiness.model.Company;
import com.ZLHW.bussiness.model.SafeUser;
import com.ZLHW.bussiness.model.Town;

public class ExclRead
{
  public static void generate()
    throws BaseErrorModel, BiffException, IOException
  {
    CompanyService service = (CompanyService)BeanFactory.LookUp("CompanyService");
    SafeUserService userService = (SafeUserService)BeanFactory.LookUp("SafeUserService");
    TownService townService = (TownService)BeanFactory.LookUp("TownService");
    File xlsFile = new File(ExclRead.class.getClassLoader().getResource("../../XLS/").getPath() + "全县企业名单.xls");
    System.out.println(xlsFile.getAbsolutePath());
    Workbook wwb = Workbook.getWorkbook(xlsFile);
    Sheet sheet = wwb.getSheet(0);
    for (int i = 1; i < sheet.getColumns(); ++i) {
      System.out.println();
      Company company = new Company();
      service.create(company);
      for (int j = 0; j < 11; ++j) {
        Cell cell = sheet.getCell(j, i);
        String value = cell.getContents();
        System.out.print(value + " ");
        if ((value != null) && (!(value.equals("")))) {
          if (j == 0) {
            company.setName(value);
          } else if (j == 1) {
            SafeUser faren = new SafeUser();
            faren.setCompany(company);
            faren.setName(value);
            faren.setType((byte)0);
            faren.setHasTrained((byte)0);
            userService.create(faren);
            company.setRepresentative(faren);
          } else if (j != 2)
          {
            if (j == 3) {
              Town town = townService.findTownByName(value);
              company.setTown(town);
            } else if (j == 4) {
              company.setAddress(value);
            } else if (j == 5) {
              company.setPhone(value);
            }
            else if ((j != 6) && 
              (j != 7) && 
              (j != 8) && 
              (j != 9) && 
              (j != 10));
          }

        }

      }

      service.update(company);
    }
  }
}