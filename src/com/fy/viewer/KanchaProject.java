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
	//待勘查项目列表
	//浏览列表
	private DataModel allprj = new ListDataModel();
	private ProjectService projectservice;
	private JBPMUtil jBPMUtil;
	int unassign = 0;	//0,代表我的任务；1，代表未分配任务；2，代表历史任务
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
			title = "现场实勘";
			activename = "现场勘查";
			renderUnassignTask = true;
		}else if ("pinggu".equals(userfy.getRolename())){
			title = "评估撰写";
			activename = "预评撰写";
			renderUnassignTask = true;
		}else if ("manager".equals(userfy.getRolename())){
			title = "预评审查";
			activename = "预评审查";
		}else if ("worker".equals(userfy.getRolename())){
			title = "评估送达";
			activename = "送达报告";
		}else if ("shenpi".equals(userfy.getRolename())){
			title = "预评审批";
			activename = "预评审批";
		}else if ("shouli".equals(userfy.getRolename())){
			title = "案件受理";
			activename = "案件受理";
			renderMyTask = false;
		}
	}
	
	public String MyTaskList(){
		//查询我的任务列表
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
		//查询未分配任务列表
		searchprj.setPidlist( jBPMUtil.getGroupTasks(userfy.getName()) );
		List<ProjectFy> ls =projectservice.SearchProject(searchprj);
		allprj.setWrappedData(ls);
		unassign = 1;
		return "";
	}

	public String HistoryTaskList(){
		//查询历史任务列表
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
		//接受任务
		String sid = (String)event.getComponent().getAttributes().get("instanceId");
		Task task = jBPMUtil.getTaskService().createTaskQuery().
				processInstanceId(sid).activityName(activename).uniqueResult();
		UserFy userfy = (UserFy)session.getAttribute("user");
		jBPMUtil.getTaskService().takeTask(task.getId(), userfy.getName());
		
		if("预评撰写".equals(activename)){
			//预评撰写环节要记录下预评人员，为后面的出具预评和出具正式做准备
			jBPMUtil.getExecutionService().setVariable(sid,"pinggu", userfy.getName());
		}

		UnassignTaskList();
	}
	
	//转到编辑页面，把项目ID、类型为勘查。
	public void KanchaAction(ActionEvent event) {	
		Long pid = (Long)event.getComponent().getAttributes().get("projectId");
		session.setAttribute("projectId", pid);
		//session.setAttribute("operatetype", userfy.getRolename());
	}
	
	//查看详细页面。
	public void DetailAction(ActionEvent event) {	
		Long pid = (Long)event.getComponent().getAttributes().get("projectId");
		session.setAttribute("projectId", pid);
		//session.setAttribute("operatetype", "edit");
	}
	
	public DataModel getAllprj() {
		return allprj;
	}

	//撤件
	public void UndoAction(ActionEvent event){
		Long pid = (Long)event.getComponent().getAttributes().get("projectId");
		ProjectFy project = projectservice.GetProjectByPId(pid);
		Task task = jBPMUtil.getTaskService().createTaskQuery().
				processInstanceId(project.getProcessInstanceId()).activityName(activename).uniqueResult();
		UserFy userfy = (UserFy)session.getAttribute("user");
		jBPMUtil.getTaskService().assignTask(task.getId(), userfy.getName());
		jBPMUtil.getTaskService().addTaskComment(task.getId(), "【撤销案件】");
		jBPMUtil.getTaskService().completeTask(task.getId(),"撤件");
		project.setCurStatus("撤件");
		projectservice.SaveOrUpdateProject(project);
		RefreshAction();
	}
	
	//送达完成
	public void SendCompleted(ActionEvent event) {	
		Long pid = (Long)event.getComponent().getAttributes().get("projectId");
		ProjectFy project = projectservice.GetProjectByPId(pid);
		Task task = jBPMUtil.getTaskService().createTaskQuery().
				processInstanceId(project.getProcessInstanceId()).activityName(activename).uniqueResult();
		UserFy userfy = (UserFy)session.getAttribute("user");
		jBPMUtil.getTaskService().takeTask(task.getId(), userfy.getName());
		jBPMUtil.getTaskService().addTaskComment(task.getId(), "【卷宗已送达】");
		jBPMUtil.getTaskService().completeTask(task.getId(),"to 归档");
		project.setCurStatus("已归档");
		projectservice.SaveOrUpdateProject(project);
		RefreshAction();
	}
}
