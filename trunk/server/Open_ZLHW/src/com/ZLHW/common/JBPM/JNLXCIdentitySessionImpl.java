package com.ZLHW.common.JBPM;

import java.util.List;

import java.util.ArrayList;
import java.util.Arrays;
import org.jbpm.api.JbpmException;
import org.jbpm.api.identity.Group;
import org.jbpm.api.identity.User;
import org.jbpm.pvm.internal.identity.spi.IdentitySession;
import org.jbpm.pvm.internal.util.CollectionUtil;

import com.ZLHW.base.Exception.JNLXCException;
import com.ZLHW.base.factory.BeanFactory;
import com.ZLHW.common.dao.AdminDAO;
import com.ZLHW.common.dao.OrganDAO;

public class JNLXCIdentitySessionImpl implements IdentitySession {
	private AdminDAO adminDAO = (AdminDAO) BeanFactory.LookUp("adminDao");
	private OrganDAO organDAO = (OrganDAO) BeanFactory.LookUp("organDAO");
	public JNLXCIdentitySessionImpl(){
	}

//	protected Session session;
//	public void setSession(Session session) {
//	    this.session = session;
//	  }

	  public User findUserById(String userId){
		  User user=null;
	     try {
			user=(User)adminDAO.getUserByAccount(userId);
		} catch (JNLXCException e) {
			e.printStackTrace();
		}
		return user;
	  }

	  public List<User> findUsersById(String[] userIds)
	  {
		  List users=adminDAO.findByHQL("from Admin a where a.account in (?)", userIds);
	    if (userIds.length != users.size())
	      throw new JbpmException("not all users were found: " + Arrays.toString(userIds));
	    return CollectionUtil.checkList(users, User.class);
	  }

	  public List<User> findUsers() {
	    List users =adminDAO.findAll();
	    return CollectionUtil.checkList(users, User.class);
	  }

	  public List<User> findUsersByGroup(String groupId) {
		  List users=adminDAO.getUsersByGroupName(groupId);
	    return CollectionUtil.checkList(users, User.class);
	  }

	  public Group findGroupById(String groupId) {
	    return (Group)organDAO.findByName(groupId);
	  }

	  public List<Group> findGroupsByUserAndGroupType(String userId, String groupType)
	  {
		  List<Group> groups=new ArrayList();
		    try {
				groups.add(adminDAO.getUserByAccount(userId).getOrgan_Job().getOrgan());
			} catch (JNLXCException e) {
				e.printStackTrace();
			}
		    return CollectionUtil.checkList(groups, Group.class);
	  }

	  public List<Group> findGroupsByUser(String userId) {
		  List groups=new ArrayList();
	    try {
			groups.add(adminDAO.getUserByAccount(userId).getOrgan_Job().getOrgan());
		} catch (JNLXCException e) {
			e.printStackTrace();
		}
	    return CollectionUtil.checkList(groups, Group.class);
	  }

	  public List<Group> findGroups() {
	    List groups = organDAO.findAll();
	    return CollectionUtil.checkList(groups, Group.class);
	  }

	  public void deleteGroup(String groupId){}

	  public void createMembership(String userId, String groupId, String role) {}

	  public void deleteMembership(String userId, String groupId, String role) {}

	  public void deleteUser(String userId){}

	  public String createGroup(String groupName, String groupType, String parentGroupId) { return null;}
	  public String createUser(String userName, String givenName, String familyName, String businessEmail){return null;}
}
