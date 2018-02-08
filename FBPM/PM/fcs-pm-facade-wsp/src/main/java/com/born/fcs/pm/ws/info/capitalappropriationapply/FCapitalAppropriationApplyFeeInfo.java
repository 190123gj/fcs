package com.born.fcs.pm.ws.info.capitalappropriationapply;

/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.enums.PaymentMenthodEnum;
import com.born.fcs.pm.ws.info.common.SimpleFormProjectInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 资金划付申请-资金划付信息
 *
 * @author Ji
 *
 */
public class FCapitalAppropriationApplyFeeInfo extends SimpleFormProjectInfo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4559842384409819097L;
	
	private long id;
	
	private long applyId;
	
	private PaymentMenthodEnum appropriateReason;
	
	private Money appropriateAmount = new Money(0, 0);
	
	/**
	 * 被扣划、冻结、其他
	 */
	private String comType;
	
	private String remark;
	
	private String formChange;
	
	private String riskCouncilSummary;
	
	private String projectApproval;
	
	private String financeAffirmDetailId;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	private List<FCapitalAppropriationApplyFeeCompensatoryChannelInfo> compensatoryChannelInfos;//代偿信息
	
	//========== getters and setters ==========
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getApplyId() {
		return applyId;
	}
	
	public void setApplyId(long applyId) {
		this.applyId = applyId;
	}
	
	public PaymentMenthodEnum getAppropriateReason() {
		return appropriateReason;
	}
	
	public void setAppropriateReason(PaymentMenthodEnum appropriateReason) {
		this.appropriateReason = appropriateReason;
	}
	
	public Money getAppropriateAmount() {
		return appropriateAmount;
	}
	
	public void setAppropriateAmount(Money appropriateAmount) {
		this.appropriateAmount = appropriateAmount;
	}
	
	public String getComType() {
		return this.comType;
	}
	
	public void setComType(String comType) {
		this.comType = comType;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
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
	
	public String getFormChange() {
		return formChange;
	}
	
	public void setFormChange(String formChange) {
		this.formChange = formChange;
	}
	
	public String getRiskCouncilSummary() {
		return riskCouncilSummary;
	}
	
	public void setRiskCouncilSummary(String riskCouncilSummary) {
		this.riskCouncilSummary = riskCouncilSummary;
	}
	
	public String getProjectApproval() {
		return projectApproval;
	}
	
	public void setProjectApproval(String projectApproval) {
		this.projectApproval = projectApproval;
	}
	
	public String getFinanceAffirmDetailId() {
		return financeAffirmDetailId;
	}
	
	public void setFinanceAffirmDetailId(String financeAffirmDetailId) {
		this.financeAffirmDetailId = financeAffirmDetailId;
	}
	
	public List<FCapitalAppropriationApplyFeeCompensatoryChannelInfo> getCompensatoryChannelInfos() {
		return compensatoryChannelInfos;
	}
	
	public void setCompensatoryChannelInfos(List<FCapitalAppropriationApplyFeeCompensatoryChannelInfo> compensatoryChannelInfos) {
		this.compensatoryChannelInfos = compensatoryChannelInfos;
	}
	
}
