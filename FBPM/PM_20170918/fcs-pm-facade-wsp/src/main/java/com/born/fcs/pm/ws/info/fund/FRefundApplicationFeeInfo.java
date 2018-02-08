package com.born.fcs.pm.ws.info.fund;

import java.util.Date;

import com.born.fcs.pm.ws.enums.BasisOfDecisionEnum;
import com.born.fcs.pm.ws.enums.FeeTypeEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

public class FRefundApplicationFeeInfo extends BaseToStringInfo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1364584596863479294L;
	
	private long id;
	
	private long refundId;
	
	private FeeTypeEnum refundReason;
	
	private Money refundAmount = new Money(0, 0);
	
	private String remark;
	
	private String basisOfDecision;
	
	private BasisOfDecisionEnum decisionType;
	
	private String formChange;
	
	private String riskCouncilSummary;
	
	private String projectApproval;
	
	private String contract;//决策依据-合同;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getRefundId() {
		return refundId;
	}
	
	public void setRefundId(long refundId) {
		this.refundId = refundId;
	}
	
	public FeeTypeEnum getRefundReason() {
		return refundReason;
	}
	
	public void setRefundReason(FeeTypeEnum refundReason) {
		this.refundReason = refundReason;
	}
	
	public Money getRefundAmount() {
		return refundAmount;
	}
	
	public void setRefundAmount(Money refundAmount) {
		this.refundAmount = refundAmount;
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
	
	public String getBasisOfDecision() {
		return basisOfDecision;
	}
	
	public void setBasisOfDecision(String basisOfDecision) {
		this.basisOfDecision = basisOfDecision;
	}
	
	public BasisOfDecisionEnum getDecisionType() {
		return decisionType;
	}
	
	public void setDecisionType(BasisOfDecisionEnum decisionType) {
		this.decisionType = decisionType;
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
	
	public String getContract() {
		return contract;
	}
	
	public void setContract(String contract) {
		this.contract = contract;
	}
	
}
