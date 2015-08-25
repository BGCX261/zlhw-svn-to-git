package com.ZLHW.common.JBPM;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jbpm.api.ExecutionService;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.junit.Test;

import com.ZLHW.base.factory.BeanFactory;


public class clear {
	ProcessEngine processEngine = (ProcessEngine)BeanFactory.LookUp("processEngine");
	RepositoryService repositoryService = processEngine.getRepositoryService();
	ExecutionService executionService = processEngine.getExecutionService();
	TaskService taskService = processEngine.getTaskService();
	@Test
	public void test(){
		List<ProcessDefinition> pdList = repositoryService.createProcessDefinitionQuery().list();
		for (ProcessDefinition pd : pdList) {
			repositoryService.deleteDeploymentCascade(pd.getDeploymentId());
		}
		deploy();
	}
	
	public void deploy(){
		repositoryService.createDeployment().addResourceFromClasspath(
		"./conf/flow/mouldFlow.jpdl.xml").deploy();
		repositoryService.createDeployment().addResourceFromClasspath(
		"./conf/flow/orderFlow.jpdl.xml").deploy();
		repositoryService.createDeployment().addResourceFromClasspath(
		"./conf/flow/productRecordFlow.jpdl.xml").deploy();
		repositoryService.createDeployment().addResourceFromClasspath(
		"./conf/flow/schemeFlow.jpdl.xml").deploy();
		repositoryService.createDeployment().addResourceFromClasspath(
		"./conf/flow/EditMouldFlow.jpdl.xml").deploy();
	}
	
}
