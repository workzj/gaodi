package com.fy.viewer;

import java.util.List;
import java.util.ArrayList;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpSession;

import org.jbpm.api.history.HistoryTask;
import org.jbpm.api.task.Task;

import com.fy.services.ProjectService;
import com.fy.services.SearchProject;
import com.fy.servicesImpl.JBPMUtil;

import com.fy.database.ProjectFy;
import com.fy.database.UserFy;

public class KanchaProject {
	//��������Ŀ�б�
	//����б�
	private DataModel allprj = new ListDataModel();
	private ProjectService projectservice;
	private JBPMUtil jBPMUtil;
	int unassign = 0;	//0,�����ҵ�����1������δ��������2��������ʷ����
	private String title;
	private String activename;
	private UserFy userfy;
	private boolean renderMyTask;
	private boolean renderUnassignTask;
	private boolean renderHistoryTask;

	private HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);

	SearchProject searchprj;

	public boolean isRenderMyTask() {
		return renderMyTask;
	}

	public void setRenderMyTask(boolean renderMyTask) {
		this.renderMyTask = renderMyTask;
	}

	public boolean isRenderUnassignTask() {
		return renderUnassignTask;
	}

	public void setRenderUnassignTask(boolean renderUnassignTask) {
		this.renderUnassignTask = renderUnassignTask;
	}

	public boolean isRenderHistoryTask() {
		return renderHistoryTask;
	}

	public void setRenderHistoryTask(boolean renderHistoryTask) {
		this.renderHistoryTask = renderHistoryTask;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public SearchProject getSearchprj() {
		if(searchprj == null){
			searchprj = new SearchProject();
		}

		return searchprj;
	}

	public void setSearchprj(SearchProject searchprj) {
		this.searchprj = searchprj;
	}

	public int getUnassign() {
		return unassign;
	}

	public void setUnassign(int unassign) {
		this.unassign = unassign;
	}

	public JBPMUtil getjBPMUtil() {
		return jBPMUtil;
	}

	public void setjBPMUtil(JBPMUtil jBPMUtil) {
		this.jBPMUtil = jBPMUtil;
	}
	
	public ProjectService getProjectService() {
		return projectservice;
	}

	public void setProjectService(ProjectService newValue) {
		this.projectservice = newValue;
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public KanchaProject(){
		userfy = (UserFy)session.getAttribute("user");
		renderMyTask = renderHistoryTask = true;
		renderUnassignTask = false;
		if("kancha".equals(userfy.getRolename())){
			title = "�ֳ�ʵ��";
			activename = "�ֳ�����";
			renderUnassignTask = true;
		}else if ("pinggu".equals(userfy.getRolename())){
			title = "����׫д";
			activename = "Ԥ��׫д";
			renderUnassignTask = true;
		}else if ("manager".equals(userfy.getRolename())){
			title = "Ԥ�����";
			activename = "Ԥ�����";
		}else if ("worker".equals(userfy.getRolename())){
			title = "�����ʹ�";
			activename = "�ʹﱨ��";
		}else if ("shenpi".equals(userfy.getRolename())){
			title = "Ԥ������";
			activename = "Ԥ������";
		}else if ("shouli".equals(userfy.getRolename())){
			title = "��������";
			activename = "��������";
			renderMyTask = false;
		}
	}
	
	public String MyTaskList(){
		//��ѯ�ҵ������б�
		List<Task> t = null;
		if(renderUnassignTask){
			searchprj.setPidlist( jBPMUtil.getPersonalTasks(userfy.getName()) );
			//t = jBPMUtil.getTaskService().findPersonalTasks(userfy.getName());
		}else{
			searchprj.setPidlist( jBPMUtil.getGroupTasks(userfy.getName()) );
			//t = jBPMUtil.getTaskService().findGroupTasks(userfy.getName());
		}

		List<ProjectFy> ls = projectservice.SearchProject(searchprj);
		allprj.setWrappedData(ls);
		unassign = 0;
		return "";
	}

	
	public String UnassignTaskList(){
		//��ѯδ���������б�
		searchprj.setPidlist( jBPMUtil.getGroupTasks(userfy.getName()) );
		List<ProjectFy> ls =projectservice.SearchProject(searchprj);
		allprj.setWrappedData(ls);
		unassign = 1;
		return "";
	}

	public String HistoryTaskList(){
		//��ѯ��ʷ�����б�
		searchprj.setPidlist( jBPMUtil.getHistoryTasks(userfy.getName()) );
		List<ProjectFy> ls =projectservice.SearchProject(searchprj);
		allprj.setWrappedData(ls);
		unassign = 2;
		return "";
	}
	
	public String RefreshAction(){
		if(unassign == 1){
			UnassignTaskList();
		}else if(unassign == 0){
			MyTaskList();
		}else if(unassign == 2){
			HistoryTaskList();
		}

		return "";
	}
	
	
	public void AssignAction(ActionEvent event){
		//��������
		String sid = (String)event.getComponent().getAttributes().get("instanceId");
		Task task = jBPMUtil.getTaskService().createTaskQuery().
				processInstanceId(sid).activityName(activename).uniqueResult();
		UserFy userfy = (UserFy)session.getAttribute("user");
		jBPMUtil.getTaskService().takeTask(task.getId(), userfy.getName());
		
		if("Ԥ��׫д".equals(activename)){
			//Ԥ��׫д����Ҫ��¼��Ԥ����Ա��Ϊ����ĳ���Ԥ���ͳ�����ʽ��׼��
			jBPMUtil.getExecutionService().setVariable(sid,"pinggu", userfy.getName());
		}

		UnassignTaskList();
	}
	
	//ת���༭ҳ�棬����ĿID������Ϊ���顣
	public void KanchaAction(ActionEvent event) {	
		Long pid = (Long)event.getComponent().getAttributes().get("projectId");
		session.setAttribute("projectId", pid);
		//session.setAttribute("operatetype", userfy.getRolename());
	}
	
	//�鿴��ϸҳ�档
	public void DetailAction(ActionEvent event) {	
		Long pid = (Long)event.getComponent().getAttributes().get("projectId");
		session.setAttribute("projectId", pid);
		//session.setAttribute("operatetype", "edit");
	}
	
	public DataModel getAllprj() {
		return allprj;
	}

	//����
	public void UndoAction(ActionEvent event){
		Long pid = (Long)event.getComponent().getAttributes().get("projectId");
		ProjectFy project = projectservice.GetProjectByPId(pid);
		Task task = jBPMUtil.getTaskService().createTaskQuery().
				processInstanceId(project.getProcessInstanceId()).activityName(activename).uniqueResult();
		UserFy userfy = (UserFy)session.getAttribute("user");
		jBPMUtil.getTaskService().assignTask(task.getId(), userfy.getName());
		jBPMUtil.getTaskService().addTaskComment(task.getId(), "������������");
		jBPMUtil.getTaskService().completeTask(task.getId(),"����");
		project.setCurStatus("����");
		projectservice.SaveOrUpdateProject(project);
		RefreshAction();
	}
	
	//�ʹ����
	public void SendCompleted(ActionEvent event) {	
		Long pid = (Long)event.getComponent().getAttributes().get("projectId");
		ProjectFy project = projectservice.GetProjectByPId(pid);
		Task task = jBPMUtil.getTaskService().createTaskQuery().
				processInstanceId(project.getProcessInstanceId()).activityName(activename).uniqueResult();
		UserFy userfy = (UserFy)session.getAttribute("user");
		jBPMUtil.getTaskService().takeTask(task.getId(), userfy.getName());
		jBPMUtil.getTaskService().addTaskComment(task.getId(), "���������ʹ");
		jBPMUtil.getTaskService().completeTask(task.getId(),"to �鵵");
		project.setCurStatus("�ѹ鵵");
		projectservice.SaveOrUpdateProject(project);
		RefreshAction();
	}
}
