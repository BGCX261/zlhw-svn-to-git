package com.ZLHW.bussiness.servlet;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ZLHW.base.Form.Page;
import com.ZLHW.base.factory.BeanFactory;
import com.ZLHW.bussiness.service.TrainingService;

public class XlsDownLoad extends HttpServlet
{
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    response.setContentType("application/x-download");
    response.setHeader("Content-disposition", "attachment;filename=a.pdf");


    OutputStream out = response.getOutputStream();

    String startDate = request.getParameter("startDate");
    String endDate = request.getParameter("endDate");
    String type = request.getParameter("type");
    Page page=new Page();
    Map map=new HashMap();
    SimpleDateFormat formate=new SimpleDateFormat("yyyy-MM-dd");
    try {
		map.put("startDate", formate.parse(startDate));
		map.put("endDate", formate.parse(endDate));
	} catch (ParseException e1) {
		e1.printStackTrace();
	}
	String title="";
	map.put("type", type);
    TrainingService service=BeanFactory.LookUp(TrainingService.class);
    try {
		title=service.downLoadTraining(page);
	} catch (Exception e) {
		e.printStackTrace();
	}
    File file = new File(getClass().getClassLoader().getResource("../../XLSBak/").getPath() + title + ".xls");

    InputStream fis = new BufferedInputStream(new FileInputStream(file));
    byte[] a = new byte[1024];
    while (fis.read(a) != -1)
      out.write(a);

    out.flush();
    out.close();
  }

  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException
  {
    doGet(req, resp);
  }
}