package com.ZLHW.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;

import com.ZLHW.common.model.AuthorDetail;


/**
 * 初始化服务类
 * @author Administrator
 *
 */
@Service("InitService")
@RemotingDestination(channels={"my-amf"},serviceAdapter="flexcontrol") 
public class InitService {
	@Autowired
	private AdminService adminService;

	public Map init(){
		Map<String,Object> map=new HashMap();
		
		//菜单内容
		map.put("menu", adminService.getMenu());
		//用户信息
		map.put("userInfo", adminService.getAdmin());
		//后台url
		map.put("serverUrl", adminService.getServerUrl());
		//后台端口号
		map.put("serverPort", adminService.getServerPort());
		//权限明细放到HashMap中
		List<AuthorDetail> adlist=adminService.getAuthorDetails();
		Map<String,AuthorDetail> admap=new HashMap();
		for(AuthorDetail ad:adlist){
			admap.put(ad.getName(), ad);
		}
		map.put("authorDetails", admap);
		return map;
	}


	
}
