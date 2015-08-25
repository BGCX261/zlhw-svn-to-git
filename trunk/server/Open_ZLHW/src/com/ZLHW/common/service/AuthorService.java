package com.ZLHW.common.service;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.ZLHW.base.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.common.dao.AuthorDAO;
import com.ZLHW.common.dao.AuthorDetailDAO;
import com.ZLHW.common.dao.Role_AuthorDetailDAO;
import com.ZLHW.common.model.Author;
import com.ZLHW.common.model.AuthorDetail;
import com.ZLHW.common.model.Role;
import com.ZLHW.common.model.Role_AuthorDetail;
import com.ZLHW.common.model.TreeNode;
@Transactional
@Service("AuthorService")
@RemotingDestination(channels={"my-amf"},serviceAdapter="flexcontrol") 
public class AuthorService extends BaseService<Author, Integer> {
	@Autowired
	private AuthorDAO authorDao;
	@Autowired
	private AuthorDetailDAO authorDetailDao;
	
}
