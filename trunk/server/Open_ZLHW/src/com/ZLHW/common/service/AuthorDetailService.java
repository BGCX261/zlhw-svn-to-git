package com.ZLHW.common.service;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ZLHW.base.factory.BeanFactory;
import com.ZLHW.base.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.common.dao.AuthorDAO;
import com.ZLHW.common.dao.AuthorDetailDAO;
import com.ZLHW.common.enums.EnumAuthorDetailStatus;
import com.ZLHW.common.model.AuthorDetail;
@Transactional
@Service("AuthorDetailService")
@RemotingDestination(channels={"my-amf"},serviceAdapter="flexcontrol") 
public class AuthorDetailService extends BaseService<AuthorDetail, Integer> {
	@Autowired
	private AuthorDetailDAO authorDetailDao;
	public static void main(String[] args) {
		AuthorDetailService a = (AuthorDetailService) BeanFactory.LookUp("AuthorDetailService");
	}
	public List getAuthorDetailStatusList(){
		return EnumAuthorDetailStatus.toList();
	}
	public Map getAuthorDetailStatusMap(){
		return EnumAuthorDetailStatus.toMap();
	}
}
