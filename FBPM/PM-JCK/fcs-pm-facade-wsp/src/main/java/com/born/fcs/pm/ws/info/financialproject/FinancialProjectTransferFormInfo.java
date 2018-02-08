/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.info.financialproject;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.ProjectCouncilEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilStatusEnum;
import com.born.fcs.pm.ws.info.common.FormVOInfo;

/**
 * 理财项目转让列表info
 *
 * @author wuzj
 */
public class FinancialProjectTransferFormInfo extends FormVOInfo {
	
	private static final long serialVersionUID = 7004732449736362609L;
	
	//申请单
	private long applyId;
	
	private String projectCode;
	
	private long holdNum;
	
	private long transferPrice;
	
	private long transferNum;
	
	private Date transferTimeStart;
	
	private Date transferTimeEnd;
	
	private String transferReason;
	
	private String attach;
	
	/** 上会申请ID */
	private long councilApplyId;
	/** 上会类型 */
	private ProjectCouncilEnum councilType;
	/** 上会状态 */
	private ProjectCouncilStatusEnum councilStatus;
	
	//转让信息
	private ProjectFinancialTradeTansferInfo trade;
	//项目信息
	private ProjectFinancialInfo project;
	
	public long getApplyId() {
		return this.applyId;
	}
	
	public void setApplyId(long applyId) {
		this.applyId = applyId;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public long getHoldNum() {
		return this.holdNum;
	}
	
	public void setHoldNum(long holdNum) {
		this.holdNum = holdNum;
	}
	
	public long getTransferPrice() {
		return this.transferPrice;
	}
	
	public void setTransferPrice(long transferPrice) {
		this.transferPrice = transferPrice;
	}
	
	public long getTransferNum() {
		return this.transferNum;
	}
	
	public void setTransferNum(long transferNum) {
		this.transferNum = transferNum;
	}
	
	public Date getTransferTimeStart() {
		return this.transferTimeStart;
	}
	
	public void setTransferTimeStart(Date transferTimeStart) {
		this.transferTimeStart = transferTimeStart;
	}
	
	public Date getTransferTimeEnd() {
		return this.transferTimeEnd;
	}
	
	public void setTransferTimeEnd(Date transferTimeEnd) {
		this.transferTimeEnd = transferTimeEnd;
	}
	
	public String getTransferReason() {
		return this.transferReason;
	}
	
	public void setTransferReason(String transferReason) {
		this.transferReason = transferReason;
	}
	
	public String getAttach() {
		return this.attach;
	}
	
	public void setAttach(String attach) {
		this.attach = attach;
	}
	
	public ProjectFinancialTradeTansferInfo getTrade() {
		return this.trade;
	}
	
	public void setTrade(ProjectFinancialTradeTansferInfo trade) {
		this.trade = trade;
	}
	
	public ProjectFinancialInfo getProject() {
		return this.project;
	}
	
	public void setProject(ProjectFinancialInfo project) {
		this.project = project;
	}
	
	public long getCouncilApplyId() {
		return councilApplyId;
	}
	
	public void setCouncilApplyId(long councilApplyId) {
		this.councilApplyId = councilApplyId;
	}
	
	public ProjectCouncilEnum getCouncilType() {
		return councilType;
	}
	
	public void setCouncilType(ProjectCouncilEnum councilType) {
		this.councilType = councilType;
	}
	
	public ProjectCouncilStatusEnum getCouncilStatus() {
		return councilStatus;
	}
	
	public void setCouncilStatus(ProjectCouncilStatusEnum councilStatus) {
		this.councilStatus = councilStatus;
	}
	
	/**
	 * @return
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
