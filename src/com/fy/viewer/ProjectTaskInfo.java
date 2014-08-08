package com.fy.viewer;

import java.util.List;

public class ProjectTaskInfo {
	private String 	assignee;		//���������
	private Integer completedNum;	//���������
	private Integer processNum;		//���ڴ���������
	private Long 	totalTime;		//������ʱ��
	private String 	taskname;		//��������
	private List<String> pids;
	
	public ProjectTaskInfo(String assignee, Integer completedNum,
			Integer processNum, Long totalTime, String taskname) {
		super();
		this.assignee = assignee;
		this.completedNum = completedNum;
		this.processNum = processNum;
		this.totalTime = totalTime;
		if("kancha".equals(taskname)){
			this.taskname = "�ֳ�����";
		}else if ("pinggu".equals(taskname)){
			this.taskname = "Ԥ��׫д";
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
