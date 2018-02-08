package com.born.fcs.pm.ws.order.bpm;

import java.io.Serializable;
import java.util.Date;

public class BpmFinishTaskInfo implements Serializable {
	
	private static final long serialVersionUID = -6558066365256746416L;
	/**
	 * processInstanceId
	 */
	String actInstId;
	String exeFullname;
	String opinion;
	String taskName;
	String checkStatus;
	long durTime;
	Date endTime;
	String exeUserId;
	String opinionId;
	Date startTime;
	String formUrl;
	String subject;
	String taskKey;
	String taskId;
	
	public String getActInstId() {
		return this.actInstId;
	}
	
	public void setActInstId(String actInstId) {
		this.actInstId = actInstId;
	}
	
	public String getExeFullname() {
		return this.exeFullname;
	}
	
	public void setExeFullname(String exeFullname) {
		this.exeFullname = exeFullname;
	}
	
	public String getOpinion() {
		return this.opinion;
	}
	
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	
	public String getTaskName() {
		return this.taskName;
	}
	
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	public String getCheckStatus() {
		return this.checkStatus;
	}
	
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	
	public long getDurTime() {
		return this.durTime;
	}
	
	public void setDurTime(long durTime) {
		this.durTime = durTime;
	}
	
	public Date getEndTime() {
		return this.endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public String getExeUserId() {
		return this.exeUserId;
	}
	
	public void setExeUserId(String exeUserId) {
		this.exeUserId = exeUserId;
	}
	
	public String getOpinionId() {
		return this.opinionId;
	}
	
	public void setOpinionId(String opinionId) {
		this.opinionId = opinionId;
	}
	
	public Date getStartTime() {
		return this.startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public String getFormUrl() {
		return this.formUrl;
	}
	
	public void setFormUrl(String formUrl) {
		this.formUrl = formUrl;
	}
	
	public String getSubject() {
		return this.subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getTaskKey() {
		return this.taskKey;
	}
	
	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}
	
	public String getTaskId() {
		return this.taskId;
	}
	
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BpmFinishTaskInfo [actInstId=");
		builder.append(actInstId);
		builder.append(", exeFullname=");
		builder.append(exeFullname);
		builder.append(", opinion=");
		builder.append(opinion);
		builder.append(", taskName=");
		builder.append(taskName);
		builder.append(", checkStatus=");
		builder.append(checkStatus);
		builder.append(", durTime=");
		builder.append(durTime);
		builder.append(", endTime=");
		builder.append(endTime);
		builder.append(", exeUserId=");
		builder.append(exeUserId);
		builder.append(", opinionId=");
		builder.append(opinionId);
		builder.append(", startTime=");
		builder.append(startTime);
		builder.append(", formUrl=");
		builder.append(formUrl);
		builder.append(", subject=");
		builder.append(subject);
		builder.append(", taskKey=");
		builder.append(taskKey);
		builder.append(", taskId=");
		builder.append(taskId);
		builder.append("]");
		return builder.toString();
	}
	
}
