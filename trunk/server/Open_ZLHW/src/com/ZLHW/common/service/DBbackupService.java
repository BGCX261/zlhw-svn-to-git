package com.ZLHW.common.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;

import com.ZLHW.base.factory.BeanFactory;
import com.ZLHW.base.service.BaseService;


@Service("DBbackupService")
@RemotingDestination(channels={"my-amf"},serviceAdapter="flexcontrol") 
public class DBbackupService extends BaseService<Object, Integer>{
	
	
	/** 数据库用户名*/
	@Value("${db.DataSource1.username}")
	private String username;

	/** 数据库密码 */
	@Value("${db.DataSource1.password}")
	private String password;
	

	public String backUp(){
		SimpleDateFormat formate=new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
		String fileName=formate.format(new Date());
		String[] md = new String[3];
		md[0] = "cmd.exe";
		md[1] = "/C";
		md[2] = "md d:\\STAQBack";
		runCmd(md);
		String[] exp = new String[3];
		exp[0] = "cmd.exe";
		exp[1] = "/C";
		exp[2] = "exp "+username+"/"+password+"@orcl file=d:\\STAQBack\\"+fileName+".dmp owner=ZLHW";
		runCmd(exp);
		return "d:\\STAQBack\\"+fileName+".dmp";
	}
	
	public boolean runCmd(String[] cmd){
		Runtime runtime = Runtime.getRuntime();
		BufferedReader br = null;
		try {
			Process pr = runtime.exec(cmd);
			System.out.println("cmd开始执行");
			try {  
				   String line = null;  
				   br =new BufferedReader(new InputStreamReader(pr.getErrorStream()));  
				   //读取ErrorStream很关键，这个解决了挂起的问题。  
				   System.out.println(br);
				   while ((line = br.readLine()) != null){  
				        System.out.println(line);
				    }  
				   br = new BufferedReader(new InputStreamReader(pr.getInputStream()));  
				   while ((line = br.readLine()) != null){
					   System.out.println(line);  
				    }
				   pr.waitFor();  
				   System.out.println("cmd执行成功");
				   return true;
				}   
				catch (Exception ioe) {
					ioe.printStackTrace();
					pr.destroy();
				}  
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("cmd执行失败");
		return false;
	}
}
