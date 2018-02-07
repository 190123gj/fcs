package com.born.fcs.face.integration.bpm.service.order;

import org.springframework.util.Assert;

import com.born.fcs.pm.ws.base.QueryPageBase;

public class TaskSearchOrder extends QueryPageBase {
	
	private static final long serialVersionUID = -4777384038062334377L;
	String taskNodeName;
	String subject;
	String processName;
	String orderField;
	String orderSeq;
	String userName;
	long userId;
	
	//创建时间
	private String beginCreateTime;
	private String endCreateTime;
	//创建人
	private String creator;
	
	private String billNo; //单据号
	
	@Override
	public void check() {
		Assert.isTrue(userId > 0 || (userName != null && !"".equals(userName.trim())), "用户不能为空");
	}
	
	public String getTaskNodeName() {
		return this.taskNodeName;
	}
	
	public void setTaskNodeName(String taskNodeName) {
		this.taskNodeName = taskNodeName;
	}
	
	public String getSubject() {
		return this.subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getProcessName() {
		return this.processName;
	}
	
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	
	public String getOrderField() {
		return this.orderField;
	}
	
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
	
	public String getOrderSeq() {
		return this.orderSeq;
	}
	
	public void setOrderSeq(String orderSeq) {
		this.orderSeq = orderSeq;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public long getUserId() {
		return this.userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getBeginCreateTime() {
		return this.beginCreateTime;
	}
	
	public void setBeginCreateTime(String beginCreateTime) {
		this.beginCreateTime = beginCreateTime;
	}
	
	public String getEndCreateTime() {
		return this.endCreateTime;
	}
	
	public void setEndCreateTime(String endCreateTime) {
		this.endCreateTime = endCreateTime;
	}
	
	public String getCreator() {
		return this.creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	public String getBillNo() {
		return this.billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TaskSearchOrder [taskNodeName=");
		builder.append(taskNodeName);
		builder.append(", subject=");
		builder.append(subject);
		builder.append(", processName=");
		builder.append(processName);
		builder.append(", orderField=");
		builder.append(orderField);
		builder.append(", orderSeq=");
		builder.append(orderSeq);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", beginCreateTime=");
		builder.append(beginCreateTime);
		builder.append(", endCreateTime=");
		builder.append(endCreateTime);
		builder.append(", creator=");
		builder.append(creator);
		builder.append("]");
		return builder.toString();
	}
	
}
