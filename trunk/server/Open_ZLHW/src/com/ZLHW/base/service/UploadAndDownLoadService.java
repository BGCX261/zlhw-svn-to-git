package com.ZLHW.base.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UploadAndDownLoadService extends BaseService{
	/**
	 * 通过BlazeDS上传文件.
	 * @param content ： 文件的btye流
	 * @param fileName : 文件名
	 * @param folderName : 存放的文件夹名
	 * @return map : 返回文件名
	 * @throws Exception
	 */
    public Map uploadFile(byte[] content, String fileName,String folderName)throws Exception{
    	String rootPath = "/";
		Map map=new HashMap();
		//判断文件夹是否存在，不存在生成文件夹
		try
		{
			if(!(new File(rootPath+folderName+"/").isDirectory()))
			{
				new File(rootPath+folderName+"/").mkdir();
			}
		}
		catch(SecurityException e)
		{
		        e.printStackTrace();
		}
		
        File file = new File(rootPath+folderName+"/"+fileName);

        FileOutputStream stream = new FileOutputStream(file);

        stream.write(content);

        stream.close();

        map.put("fileName", fileName);
        
        return map;
     }
}
