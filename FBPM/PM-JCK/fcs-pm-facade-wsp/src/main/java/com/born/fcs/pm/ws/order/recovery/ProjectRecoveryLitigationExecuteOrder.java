/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.recovery;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 追偿跟踪表 - 诉讼-执行
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryLitigationExecuteOrder extends ProcessOrder {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	private Long id;
	
	/**
	 * 追偿主表主键
	 */
	private Long projectRecoveryId;
	
	/**
	 * 强制执行申请
	 */
	private String executeApply;
	
	/**
	 * 立案
	 */
	private String placeOnFile;
	
	/**
	 * 受理法院
	 */
	private String acceptingCourt;
	
	/**
	 * 执行和解
	 */
	private String compromise;
	
	/**
	 * 调解
	 */
	private String conciliation;
	
	/**
	 * 评估
	 */
	private String estimate;
	
	/**
	 * 回收总金额
	 */
	private String recoveryTotalAmount;
	
	/**
	 * 备注
	 */
	private String memo;
	
	// 附件 开始 
	/*** 诉讼-执行-强制执行申请-附件 **/
	private String recoveryLitigationExecuteExecuteApplyUrl;
	/*** 诉讼-执行-立案-附件 **/
	private String recoveryLitigationExecutePlaceOnFileUrl;
	/*** 诉讼-执行-受理法院-附件 **/
	private String recoveryLitigationExecuteAcceptingCourtUrl;
	/*** 诉讼-执行-执行和解-附件 **/
	private String recoveryLitigationExecuteCompromiseUrl;
	/*** 诉讼-执行-调解-附件 **/
	private String recoveryLitigationExecuteConciliationUrl;
	/*** 诉讼-执行-评估-附件 **/
	private String recoveryLitigationExecuteEstimateUrl;
	/*** 诉讼-执行-备注-附件 **/
	private String recoveryLitigationExecuteMemoUrl;
	
	// 子表 
	/** 追偿跟踪表 - 诉讼-执行-执行内容 **/
	private List<ProjectRecoveryLitigationExecuteStuffOrder> projectRecoveryLitigationExecuteStuffOrder;
	
	/*** 追偿跟踪表 - 债务人重整或破产清算表-回收金额明细 **/
	private List<ProjectRecoveryDebtorReorganizationAmountDetailOrder> projectRecoveryDebtorReorganizationAmountDetailOrder;
	
	/*** 追偿跟踪表 - 债务人重整或破产清算表-抵质押资产抵债明细 **/
	private List<ProjectRecoveryDebtorReorganizationPledgeOrder> projectRecoveryDebtorReorganizationPledgeOrder;
	
	//========== getters and setters ==========
	
	public List<ProjectRecoveryLitigationExecuteStuffOrder> getProjectRecoveryLitigationExecuteStuffOrder() {
		return this.projectRecoveryLitigationExecuteStuffOrder;
	}
	
	public void setProjectRecoveryLitigationExecuteStuffOrder(	List<ProjectRecoveryLitigationExecuteStuffOrder> projectRecoveryLitigationExecuteStuffOrder) {
		this.projectRecoveryLitigationExecuteStuffOrder = projectRecoveryLitigationExecuteStuffOrder;
	}
	
	public List<ProjectRecoveryDebtorReorganizationAmountDetailOrder> getProjectRecoveryDebtorReorganizationAmountDetailOrder() {
		return this.projectRecoveryDebtorReorganizationAmountDetailOrder;
	}
	
	public void setProjectRecoveryDebtorReorganizationAmountDetailOrder(List<ProjectRecoveryDebtorReorganizationAmountDetailOrder> projectRecoveryDebtorReorganizationAmountDetailOrder) {
		this.projectRecoveryDebtorReorganizationAmountDetailOrder = projectRecoveryDebtorReorganizationAmountDetailOrder;
	}
	
	public List<ProjectRecoveryDebtorReorganizationPledgeOrder> getProjectRecoveryDebtorReorganizationPledgeOrder() {
		return this.projectRecoveryDebtorReorganizationPledgeOrder;
	}
	
	public void setProjectRecoveryDebtorReorganizationPledgeOrder(	List<ProjectRecoveryDebtorReorganizationPledgeOrder> projectRecoveryDebtorReorganizationPledgeOrder) {
		this.projectRecoveryDebtorReorganizationPledgeOrder = projectRecoveryDebtorReorganizationPledgeOrder;
	}
	
	public String getRecoveryLitigationExecuteMemoUrl() {
		return this.recoveryLitigationExecuteMemoUrl;
	}
	
	public void setRecoveryLitigationExecuteMemoUrl(String recoveryLitigationExecuteMemoUrl) {
		this.recoveryLitigationExecuteMemoUrl = recoveryLitigationExecuteMemoUrl;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getProjectRecoveryId() {
		return this.projectRecoveryId;
	}
	
	public void setProjectRecoveryId(Long projectRecoveryId) {
		this.projectRecoveryId = projectRecoveryId;
	}
	
	public String getExecuteApply() {
		return executeApply;
	}
	
	public String getRecoveryLitigationExecuteExecuteApplyUrl() {
		return this.recoveryLitigationExecuteExecuteApplyUrl;
	}
	
	public void setRecoveryLitigationExecuteExecuteApplyUrl(String recoveryLitigationExecuteExecuteApplyUrl) {
		this.recoveryLitigationExecuteExecuteApplyUrl = recoveryLitigationExecuteExecuteApplyUrl;
	}
	
	public String getRecoveryLitigationExecutePlaceOnFileUrl() {
		return this.recoveryLitigationExecutePlaceOnFileUrl;
	}
	
	public void setRecoveryLitigationExecutePlaceOnFileUrl(	String recoveryLitigationExecutePlaceOnFileUrl) {
		this.recoveryLitigationExecutePlaceOnFileUrl = recoveryLitigationExecutePlaceOnFileUrl;
	}
	
	public String getRecoveryLitigationExecuteAcceptingCourtUrl() {
		return this.recoveryLitigationExecuteAcceptingCourtUrl;
	}
	
	public void setRecoveryLitigationExecuteAcceptingCourtUrl(	String recoveryLitigationExecuteAcceptingCourtUrl) {
		this.recoveryLitigationExecuteAcceptingCourtUrl = recoveryLitigationExecuteAcceptingCourtUrl;
	}
	
	public String getRecoveryLitigationExecuteCompromiseUrl() {
		return this.recoveryLitigationExecuteCompromiseUrl;
	}
	
	public void setRecoveryLitigationExecuteCompromiseUrl(	String recoveryLitigationExecuteCompromiseUrl) {
		this.recoveryLitigationExecuteCompromiseUrl = recoveryLitigationExecuteCompromiseUrl;
	}
	
	public String getRecoveryLitigationExecuteConciliationUrl() {
		return this.recoveryLitigationExecuteConciliationUrl;
	}
	
	public void setRecoveryLitigationExecuteConciliationUrl(String recoveryLitigationExecuteConciliationUrl) {
		this.recoveryLitigationExecuteConciliationUrl = recoveryLitigationExecuteConciliationUrl;
	}
	
	public String getRecoveryLitigationExecuteEstimateUrl() {
		return this.recoveryLitigationExecuteEstimateUrl;
	}
	
	public void setRecoveryLitigationExecuteEstimateUrl(String recoveryLitigationExecuteEstimateUrl) {
		this.recoveryLitigationExecuteEstimateUrl = recoveryLitigationExecuteEstimateUrl;
	}
	
	public void setExecuteApply(String executeApply) {
		this.executeApply = executeApply;
	}
	
	public String getPlaceOnFile() {
		return placeOnFile;
	}
	
	public void setPlaceOnFile(String placeOnFile) {
		this.placeOnFile = placeOnFile;
	}
	
	public String getAcceptingCourt() {
		return acceptingCourt;
	}
	
	public void setAcceptingCourt(String acceptingCourt) {
		this.acceptingCourt = acceptingCourt;
	}
	
	public String getCompromise() {
		return compromise;
	}
	
	public void setCompromise(String compromise) {
		this.compromise = compromise;
	}
	
	public String getConciliation() {
		return conciliation;
	}
	
	public void setConciliation(String conciliation) {
		this.conciliation = conciliation;
	}
	
	public String getEstimate() {
		return estimate;
	}
	
	public void setEstimate(String estimate) {
		this.estimate = estimate;
	}
	
	public String getRecoveryTotalAmount() {
		return this.recoveryTotalAmount;
	}
	
	public void setRecoveryTotalAmount(String recoveryTotalAmount) {
		this.recoveryTotalAmount = recoveryTotalAmount;
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
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
