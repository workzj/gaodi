package com.fy.viewer;

import java.util.List;

public class ProjectTaskInfo {
	private String 	assignee;		//任务分配人
	private Integer completedNum;	//完成任务数
	private Integer processNum;		//正在处理任务数
	private Long 	totalTime;		//总任务时间
	private String 	taskname;		//任务名称
	private List<String> pids;
	
	public ProjectTaskInfo(String assignee, Integer completedNum,
			Integer processNum, Long totalTime, String taskname) {
		super();
		this.assignee = assignee;
		this.completedNum = completedNum;
		this.processNum = processNum;
		this.totalTime = totalTime;
		if("kancha".equals(taskname)){
			this.taskname = "现场勘查";
		}else if ("pinggu".equals(taskname)){
			this.taskname = "预评撰写";
		}
		
	}
	
	public List<String> getPids() {
		return pids;
	}
	public void setPids(List<String> pids) {
		this.pids = pids;
	}
	
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public Integer getCompletedNum() {
		return completedNum;
	}
	public void setCompletedNum(Integer completedNum) {
		this.completedNum = completedNum;
	}
	public Integer getProcessNum() {
		return processNum;
	}
	public void setProcessNum(Integer processNum) {
		this.processNum = processNum;
	}
	public Long getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(Long totalTime) {
		this.totalTime = totalTime;
	}
	public String getTaskname() {
		return taskname;
	}
	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}
	
}
