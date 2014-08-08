package com.fy.viewer;

import java.util.List;
import java.util.ArrayList;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpSession;

import org.jbpm.api.task.Task;

import com.fy.services.ProjectService;
import com.fy.servicesImpl.JBPMUtil;

import com.fy.database.ProjectFy;
import com.fy.database.UserFy;

public class PingguProject {
	//��������Ŀ�б�
	//����б�
	private DataModel allprj = new ListDataModel();
	private ProjectService projectservice;
	private JBPMUtil jBPMUtil;
	int unassign = 0;	//0,�����ҵ�����1������δ��������
	private HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);


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

	public PingguProject(){
	}
	
	public String MyTaskList(){
		//��ѯ�ҵ������б�--����
		List<ProjectFy> ls = new ArrayList<ProjectFy>();
		UserFy userfy = (UserFy)session.getAttribute("user");
		List<Task> t = jBPMUtil.getTaskService().findPersonalTasks(userfy.getName());
		for (Task obj : t) {
			
			String processInstanceId = (String)jBPMUtil.getTaskService().getVariable(obj.getId(),"processInstanceId");
			if(processInstanceId != null ){
				List lp = projectservice.GetProjectBySId(processInstanceId);
				if(lp.size()>0){
					ls.add((ProjectFy)lp.get(0));
				}
			}
		}
		allprj.setWrappedData(ls);
		unassign = 0;
		return "";
	}
	
	public String UnassignTaskList(){
		//��ѯδ���������б�---����
		List<ProjectFy> ls = new ArrayList<ProjectFy>();
		UserFy userfy = (UserFy)session.getAttribute("user");
		List<Task> t = jBPMUtil.getTaskService().findGroupTasks(userfy.getName());
		for (Task obj : t) {
			String processInstanceId = (String)jBPMUtil.getTaskService().getVariable(obj.getId(),"processInstanceId");
			if(processInstanceId != null ){
				List lp = projectservice.GetProjectBySId(processInstanceId);
				if(lp.size()>0){
					ls.add((ProjectFy)lp.get(0));
				}
			}
		}
		allprj.setWrappedData(ls);
		unassign = 1;
		return "";
	}
	
	public String RefreshAction(){
		if(unassign == 1){
			UnassignTaskList();
		}else if(unassign == 0){
			MyTaskList();
		}

		return "";
	}
	
	
	public void AssignAction(ActionEvent event){
		//������������
		String sid = (String)event.getComponent().getAttributes().get("instanceId");
		Task task = jBPMUtil.getTaskService().createTaskQuery().
				processInstanceId(sid).activityName("Ԥ��׫д").uniqueResult();
		UserFy userfy = (UserFy)session.getAttribute("user");
		jBPMUtil.getTaskService().takeTask(task.getId(), userfy.getName());
		UnassignTaskList();
	}
	
	//ת���༭ҳ�棬����ĿID������Ϊ������
	public void PingguAction(ActionEvent event) {
		/*FacesContext facesContext = FacesContext.getCurrentInstance();
    	ExternalContext extContext = facesContext.getExternalContext();
		HttpSession session = (HttpSession) extContext.getSession(true);*/		
		Long pid = (Long)event.getComponent().getAttributes().get("projectId");
		session.setAttribute("projectId", pid);
		//session.setAttribute("operatetype", "pinggu");
	}
	
	public DataModel getAllprj() {
		return allprj;
	}

}
