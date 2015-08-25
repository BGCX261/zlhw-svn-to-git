package com.ZLHW.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.ZLHW.base.factory.BeanFactory;
import com.ZLHW.base.service.BaseService;

public class Download extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
//    	String shemeId=request.getParameter("id");
//    	SchemeService service=(SchemeService) BeanFactory.LookUp("SchemeService");
//    	Scheme scheme=service.getById(Integer.parseInt(shemeId));
//    	String path="";
//			path = BaseService.getImagesPath()+"\\";
//        String fileName = scheme.getDwgSrc();
//        File file = new File(path+fileName);
//        response.setContentType("application/x-download");
////        response.setHeader("Content-disposition", "attachment;filename="
////                + scheme.getId()+".dwg");
//
//        // 以流的形式下载文件。
//        InputStream fis = new BufferedInputStream(new FileInputStream(file));
//        byte[] a=new byte[1024];
//        OutputStream out = response.getOutputStream();
//        while(fis.read(a)!=-1){
//        	out.write(a);
//        }
//        out.flush();
//        out.close();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }

}
