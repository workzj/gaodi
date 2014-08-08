package com.fy.viewer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpSession;

import com.fy.database.ProjectFy;
import com.fy.services.SearchProject;
import com.fy.services.ProjectService;
import com.fy.servicesImpl.JBPMUtil;
import javax.faces.event.ActionEvent;

public class ProjectBrowse {

	//查询参数
	private SearchProject searchPj;

	//浏览列表
	private DataModel allprj = new ListDataModel();
	
	private ProjectService projectservice;
	private JBPMUtil jBPMUtil;
	private String worker;
	private boolean htask;		//查询任务类型  true ：历史任务

	public boolean isHtask() {
		return htask;
	}

	public void setHtask(boolean htask) {
		this.htask = htask;
	}

	public String getWorker() {
		return worker;
	}

	public void setWorker(String worker) {
		this.worker = worker;
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

	public ProjectBrowse(){
	}
	
	public void SearchAction(){
		searchPj.setPidlist(null);//需要先清空一下之前的查询条件
		if( worker != null && !"".equals(worker) ){
			if(htask){
				//查询历史案件
				searchPj.setPidlist( jBPMUtil.getHistoryTasks(worker) );
			}else{
				//查询当前案件
				searchPj.setPidlist( jBPMUtil.getPersonalTasks(worker) );
			}
		}
		List<ProjectFy> ls =projectservice.SearchProject(searchPj);
		allprj.setWrappedData(ls);
	}

	//删除动作
	public void DeleteAction(ActionEvent event) {
		Long pid = (Long)event.getComponent().getAttributes().get("projectId");
		String sid = (String)event.getComponent().getAttributes().get("ProcessInstanceId");
		if(pid != null){
			projectservice.DeleteProject(pid);
		}
		
		if(sid != null){
			jBPMUtil.getExecutionService().deleteProcessInstance(sid);
		}
		
		SearchAction();
	}
	
	//转到编辑页面，把项目ID传过去
	public void EditAction(ActionEvent event) {
		Long pid = (Long)event.getComponent().getAttributes().get("projectId");
		String nodename = (String)event.getComponent().getAttributes().get("nodeName");
		FacesContext facesContext = FacesContext.getCurrentInstance();
    	ExternalContext extContext = facesContext.getExternalContext();
		HttpSession session = (HttpSession) extContext.getSession(true);
		session.setAttribute("projectId", pid);
		/*
		if("已完成".equals(nodename)){
			session.setAttribute("operatetype", "completed");
		}else{
			session.setAttribute("operatetype", "edit");
		}*/
		
	}
	
	public void ShowTrace(ActionEvent event){
		String projectCode = (String)event.getComponent().getAttributes().get("projectCode");
		String nodeName = (String)event.getComponent().getAttributes().get("nodeName");
		String sid = (String)event.getComponent().getAttributes().get("ProcessInstanceId");
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
    	ExternalContext extContext = facesContext.getExternalContext();
		HttpSession session = (HttpSession) extContext.getSession(true);
		session.setAttribute("projectCode", projectCode);
		session.setAttribute("nodeName", nodeName);
		session.setAttribute("ProcessInstanceId", sid);
	}
	
	//get set method
	public SearchProject getSearchPj() {
		if(searchPj == null){
			searchPj = new SearchProject();
		}
		return searchPj;
	}

	public void setSearchPj(SearchProject searchPj) {
		this.searchPj = searchPj;
	}
	
	public DataModel getAllprj() {
		return allprj;
	}

}
