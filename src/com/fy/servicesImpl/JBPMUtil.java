package com.fy.servicesImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.ManagementService;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.jbpm.api.history.HistoryTask;
import org.jbpm.api.task.Task;
import org.jbpm.api.IdentityService;

/**
 * jBPM4.4������
 * 
 * @author ht
 * 
 */
public class JBPMUtil {
	
	private ProcessEngine processEngine;
	private RepositoryService repositoryService = null;
	private ExecutionService executionService = null;
	private TaskService taskService = null;
	private HistoryService historyService = null;
	private ManagementService managementService = null;
	private IdentityService identityService = null;
	
	public JBPMUtil(){
		
	}
	public JBPMUtil(ProcessEngine processEngine) {
		this.processEngine = processEngine;
		repositoryService = processEngine.getRepositoryService();
		executionService = processEngine.getExecutionService();
		taskService = processEngine.getTaskService();
		historyService = processEngine.getHistoryService();
		managementService = processEngine.getManagementService();
		identityService = processEngine.getIdentityService();
	}

	

	public IdentityService getIdentityService() {
		return identityService;
	}
	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}
	public ProcessEngine getProcessEngine() {
		return processEngine;
	}

	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
		System.out.println("processEngine="+processEngine);
		repositoryService = processEngine.getRepositoryService();
		executionService = processEngine.getExecutionService();
		taskService = processEngine.getTaskService();
		historyService = processEngine.getHistoryService();
		managementService = processEngine.getManagementService();
		identityService = processEngine.getIdentityService();
	}

	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	public ExecutionService getExecutionService() {
		return executionService;
	}

	public void setExecutionService(ExecutionService executionService) {
		this.executionService = executionService;
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public HistoryService getHistoryService() {
		return historyService;
	}

	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	public ManagementService getManagementService() {
		return managementService;
	}

	public void setManagementService(ManagementService managementService) {
		this.managementService = managementService;
	}
	
	
	
	/**
	 * ���������̶���
	 * @param resourceName
	 * @return �������̶���id
	 */
	public String deployNew(String resourceName) {
		return repositoryService.createDeployment().addResourceFromClasspath(
				resourceName).deploy();
	}

	
	/**
	 * ���������̶���(zip)
	 * @param resourceName
	 * @return �������̶���id
	 */
	public String deployZipNew(String resourceZipName){
		ZipInputStream zis = new ZipInputStream(this.getClass().getResourceAsStream(resourceZipName));
		
		return repositoryService.createDeployment().addResourcesFromZipInputStream(zis).deploy();	
		
	}
	
	
	/**
	 * ��ʼһ������ʵ��
	 * @param id
	 * @param map
	 * @return
	 */
	
	public ProcessInstance startPIById(String id,Map<String,?> map){	
		
		return executionService.startProcessInstanceById(id, map);		
		
	}
	
	/**
	 * �������
	 * @param taskId
	 * @param map
	 */
	
	public void completeTask(String taskId,Map map){
		
		taskService.completeTask(taskId, map);	
	}
	
	/**
	 * �������
	 * @param taskId
	 */
	public void completeTask(String taskId){
		taskService.completeTask(taskId);
	}
	
	
	/**
	 * ��������ת��ָ�����ֵ�����outcome��ȥ
	 * @param taskId
	 * @param outcome
	 */
	public void completeTask(String taskId,String outcome){
		taskService.completeTask(taskId, outcome);
	}

	/**
	 * ������з����˵�����
	 * @return
	 */
	public List<ProcessDefinition> getAllPdList(){
		return repositoryService.createProcessDefinitionQuery().list();
	}
	
	/**
	 * �����������ʵ��
	 * @return
	 */
	public List<ProcessInstance> getAllPiList(){
		return executionService.createProcessInstanceQuery().list();
	}
	
	/**
	 * ��������ʵ��Id����executionId��ȡָ���ı���ֵ
	 * @param executionId
	 * @param variableName
	 * @return
	 */
	public Object getVariableByexecutionId(String executionId,String variableName){
		return executionService.getVariable(executionId, variableName);
	}
	
	
	/**
	 * ��������id����taskId��ȡָ������ֵ
	 * @param taskId
	 * @param variableName
	 * @return
	 */
	public Object getVariableByTaskId(String taskId,String variableName){
		return taskService.getVariable(taskId, variableName);
	}
	
	/**
	 * ��ȡָ���û����ֵ�����
	 * @param userName
	 * @return
	 */
	public List<Task> findPersonalTasks(String userName){
		return taskService.findPersonalTasks(userName);
	}
	
	/**
	 * ��������id��ȡ����
	 * @param taskId
	 * @return
	 */
	public Task getTask(String taskId) {
		return taskService.getTask(taskId);
		
	}
	
	

	/**
	 * ����ɾ�����̶��壬ֱ��ɾ�������̶����µ�����ʵ��
	 * 
	 * @param deploymentId  ���̶���id
	 */
	public void deleteDeploymentCascade(String deploymentId) {
		repositoryService.deleteDeploymentCascade(deploymentId);
	}


	/**
	 * �õ�PID LIST �û������б�
	 * 
	 * @param username  �û���
	 */
	public List<String> getPersonalTasks(String username) {
		List<Task> t = taskService.findPersonalTasks(username);
		List<String> pids = new ArrayList<String>();
		for (Task obj : t) {
			String processInstanceId = (String)taskService.getVariable(obj.getId(),"processInstanceId");
			if(processInstanceId != null ){
				pids.add(processInstanceId);
			}
		}
		return pids;
	}
	
	/**
	 * �õ�PID LIST �û��������б�
	 * 
	 * @param username  �û���
	 */
	public List<String> getGroupTasks(String username) {
		List<Task> t = taskService.findGroupTasks(username);
		List<String> pids = new ArrayList<String>();
		for (Task obj : t) {
			String processInstanceId = (String)taskService.getVariable(obj.getId(),"processInstanceId");
			if(processInstanceId != null ){
				pids.add(processInstanceId);
			}
		}
		return pids;
	}
	
	/**
	 * �õ�PID LIST �û���ʷ�����б�
	 * 
	 * @param username  �û���
	 */
	public List<String> getHistoryTasks(String username) {
		//��ѯ��ʷ�����б�
		List<HistoryTask> ht = historyService.createHistoryTaskQuery().assignee(username).list();
		List<String> pids = new ArrayList<String>();
		for (HistoryTask obj : ht) {
			String processInstanceId = obj.getExecutionId();
			if(processInstanceId != null ){
				pids.add(processInstanceId);
			}
		}
		return pids;
	}
}
