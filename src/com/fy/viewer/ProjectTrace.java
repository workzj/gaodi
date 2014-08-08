package com.fy.viewer;

import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpSession;
import com.fy.servicesImpl.JBPMUtil;
import org.jbpm.api.task.Task;
import org.jbpm.api.history.*;
import org.jbpm.pvm.internal.history.model.HistoryActivityInstanceImpl;

//流程历史查看
public class ProjectTrace {

	private HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	//流程历史列表
	private DataModel allprj = new ListDataModel();
	
	private JBPMUtil jBPMUtil;
	private String projectCode;	//项目编号
	private String nodeName;	//当前阶段
	
	//get set METHOD
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public JBPMUtil getjBPMUtil() {
		return jBPMUtil;
	}

	public void setjBPMUtil(JBPMUtil jBPMUtil) {
		this.jBPMUtil = jBPMUtil;
		projectCode = (String)session.getAttribute("projectCode");
		session.removeAttribute("projectCode");
		String sid = (String)session.getAttribute("ProcessInstanceId");
		session.removeAttribute("ProcessInstanceID");
		this.nodeName = (String)session.getAttribute("nodeName");
		session.removeAttribute("nodeName");

		List<TraceInfo> lt = new ArrayList<TraceInfo>();
		if(sid != null){
			List<HistoryActivityInstance> haInstance = jBPMUtil.getHistoryService()  
				    .createHistoryActivityInstanceQuery().processInstanceId(sid).list();
			List<HistoryTask> ht = jBPMUtil.getHistoryService()  
				    .createHistoryTaskQuery().executionId(sid).list();
			
			String assignee="";
			for(int i=0;i<haInstance.size();i++){
				HistoryActivityInstanceImpl tes = 
						(HistoryActivityInstanceImpl)haInstance.get(i);
				
				TraceInfo tmp = new TraceInfo();
				tmp.setBeginTime(tes.getStartTime());
				tmp.setEndTime(tes.getEndTime());
				tmp.setNodeName(tes.getActivityName());
				
				
				assignee = ht.get(i).getAssignee();
				List<HistoryComment> comments = jBPMUtil.getTaskService().getTaskComments(ht.get(i).getId());
				if(comments.size() > 0)
					tmp.setOpinion(comments.get(0).getMessage());
				if(assignee == null){
					//如果历史任务没有，就从激活的任务中找分配人。
					List<Task> ltk = jBPMUtil.getTaskService().
							createTaskQuery().executionId(tes.getExecutionId()).list();
					assignee = ltk.get(0).getAssignee();
					if(assignee == null){
						assignee = "未分配";
					}
				}
				tmp.setAssignee(assignee);
				lt.add(tmp);
			}
			if("已归档".equals(nodeName) || "撤件".equals(nodeName)){
				HistoryActivityInstanceImpl tes = 
						(HistoryActivityInstanceImpl)haInstance.get(haInstance.size()-1);
				
				TraceInfo tmp = new TraceInfo();
				tmp.setBeginTime(tes.getStartTime());
				tmp.setEndTime(tes.getEndTime());
				tmp.setNodeName(nodeName);
				tmp.setOpinion(nodeName);
				tmp.setAssignee(assignee);
				lt.add(tmp);
			}
		}
		allprj.setWrappedData(lt);
	}

	public ProjectTrace(){
	}
	
	public DataModel getAllprj() {
		return allprj;
	}
	
	//业务流程

}
