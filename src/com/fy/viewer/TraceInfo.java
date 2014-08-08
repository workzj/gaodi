package com.fy.viewer;

import java.util.Date;

public class TraceInfo {

	//发起人
	private String assignee;
	//阶段
	private String nodeName;
	//发起人意见
	private String opinion;
	//开始时间
	private Date beginTime;
	//结束时间
	private Date endTime;

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public TraceInfo() {

    }
   
}
