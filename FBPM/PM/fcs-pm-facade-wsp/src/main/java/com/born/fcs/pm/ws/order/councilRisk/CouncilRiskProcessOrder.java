package com.born.fcs.pm.ws.order.councilRisk;

import java.util.Date;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * Created by wqh on 2016/9/8.
 */
public class CouncilRiskProcessOrder extends ProcessOrder {
	
	private static final long serialVersionUID = -779686821497636408L;
	
	private long councilId;
	
	private String councilCode;
	
	private String councilType;
	
	private String councilPlace;
	
	private String councilSubject;
	
	private Date beginTime;
	
	private long customerId;
	
	private String customerName;
	
	private String projectCode;
	
	private String projectName;
	
	private long applyManId;
	
	private String applyManName;
	
	private String councilStatus;
	
	private String participantIds;
	
	private String participantNames;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	private String deptCode;
	
	public long getCouncilId() {
		return councilId;
	}
	
	public void setCouncilId(long councilId) {
		this.councilId = councilId;
	}
	
	public String getCouncilCode() {
		return councilCode;
	}
	
	public void setCouncilCode(String councilCode) {
		this.councilCode = councilCode;
	}
	
	public String getCouncilType() {
		return councilType;
	}
	
	public void setCouncilType(String councilType) {
		this.councilType = councilType;
	}
	
	public String getCouncilPlace() {
		return councilPlace;
	}
	
	public void setCouncilPlace(String councilPlace) {
		this.councilPlace = councilPlace;
	}
	
	public String getCouncilSubject() {
		return councilSubject;
	}
	
	public void setCouncilSubject(String councilSubject) {
		this.councilSubject = councilSubject;
	}
	
	public Date getBeginTime() {
		return beginTime;
	}
	
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	
	public long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public long getApplyManId() {
		return applyManId;
	}
	
	public void setApplyManId(long applyManId) {
		this.applyManId = applyManId;
	}
	
	public String getApplyManName() {
		return applyManName;
	}
	
	public void setApplyManName(String applyManName) {
		this.applyManName = applyManName;
	}
	
	public String getCouncilStatus() {
		return councilStatus;
	}
	
	public void setCouncilStatus(String councilStatus) {
		this.councilStatus = councilStatus;
	}
	
	public String getParticipantIds() {
		return participantIds;
	}
	
	public void setParticipantIds(String participantIds) {
		this.participantIds = participantIds;
	}
	
	public String getParticipantNames() {
		return participantNames;
	}
	
	public void setParticipantNames(String participantNames) {
		this.participantNames = participantNames;
	}
	
	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
	public String getDeptCode() {
		return deptCode;
	}
	
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
}
