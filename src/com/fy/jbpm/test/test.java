package com.fy.jbpm.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;
import org.jbpm.test.JbpmTestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import junit.framework.TestCase;
//import service.common.impl.DepartmentServiceImpl;


public class test extends JbpmTestCase {

	
	public void test2(){
		/*部署定义
		String id = repositoryService.createDeployment().addResourceFromClasspath("jpdl/bu_men_fa_wen.jpdl.xml").deploy();
		System.out.println(id);
		id = repositoryService.createDeployment().addResourceFromClasspath("jpdl/sub.jpdl.xml").deploy();
		System.out.println(id);*/
		
		//启动流程
		/*
		String[] users = new String[]{"tom","mike","rose"};
		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put("users", users);
		variables.put("num", 3);
		variables.put("user", "root");
		ProcessInstance processInstance = 
			executionService.startProcessInstanceByKey("test1",variables);
		executionService.setVariable(processInstance.getId(), "processInstanceId",processInstance.getId());
		
		System.out.println(processInstance.getState());*/
		
		//业务受理
		//String taskid = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0).getId();
		//taskService.assignTask(taskid, "root");
		
		List<Task> t = taskService.findPersonalTasks("root");
		System.out.println("root的任务数量size="+t.size());
		Task task = t.get(0);
		System.out.println("activityName:"+task.getActivityName());
		
		taskService.completeTask(task.getId(),"to end1");
		System.out.println("root----------------over");
		
		//现场勘查
		t = taskService.findPersonalTasks("kancha");
		System.out.println("manager的任务数量size="+t.size());
		task = t.get(0);
		System.out.println("activityName:"+task.getActivityName());
		int num = (Integer) taskService.getVariable(task.getId(), "num");
		System.out.println("num="+num);
		
		taskService.completeTask(task.getId());
		System.out.println("现场勘查任务----------------完成！");
		//预评撰写
		 t = taskService.findPersonalTasks("pinggu");
		 task = t.get(0);
		 System.out.println("activityName:"+task.getActivityName());
		 taskService.completeTask(task.getId(),"to 部门审核");
		 System.out.println("评估任务----------------完成！");
		 //预评审查
		 t = taskService.findPersonalTasks("shencha");
		 task = t.get(0);
		 System.out.println("activityName:"+task.getActivityName());
		 taskService.completeTask(task.getId(),"to 返部门文秘");
		 System.out.println("manager----------------over");
		 //预评审批
		 t = taskService.findPersonalTasks("manager");
		 task = t.get(0);
		 System.out.println("activityName:"+task.getActivityName());
		 taskService.completeTask(task.getId(),"to foreach1");
		 System.out.println("manager----------------over");

	}
}
