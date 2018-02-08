/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.info.revovery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.ProjectRecoveryDescribeTypeEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.born.fcs.pm.ws.info.common.CommonAttachmentInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 追偿跟踪表 - 诉讼-执行
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryLitigationExecuteInfo extends BaseToStringInfo {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	private long id;
	
	/**
	 * 追偿主表主键
	 */
	private long projectRecoveryId;
	
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
	private Money recoveryTotalAmount = new Money(0, 0);
	
	/**
	 * 备注
	 */
	private String memo;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	// 附件 开始 
	
	/*** 诉讼-执行-强制执行申请-附件 **/
	private String recoveryLitigationExecuteExecuteApplyUrl;
	/*** 诉讼-执行-强制执行申请-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationExecuteExecuteApply;
	/*** 诉讼-执行-立案-附件 **/
	private String recoveryLitigationExecutePlaceOnFileUrl;
	/*** 诉讼-执行-立案-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationExecutePlaceOnFile;
	/*** 诉讼-执行-受理法院-附件 **/
	private String recoveryLitigationExecuteAcceptingCourtUrl;
	/*** 诉讼-执行-受理法院-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationExecuteAcceptingCourt;
	/*** 诉讼-执行-执行和解-附件 **/
	private String recoveryLitigationExecuteCompromiseUrl;
	/*** 诉讼-执行-执行和解-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationExecuteCompromise;
	/*** 诉讼-执行-调解-附件 **/
	private String recoveryLitigationExecuteConciliationUrl;
	/*** 诉讼-执行-调解-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationExecuteConciliation;
	/*** 诉讼-执行-评估-附件 **/
	private String recoveryLitigationExecuteEstimateUrl;
	/*** 诉讼-执行-评估-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationExecuteEstimate;
	
	/*** 诉讼-执行-备注-附件 **/
	private String recoveryLitigationExecuteMemoUrl;
	/*** 诉讼-执行-备注-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationExecuteMemo;
	
	// 子表 
	/** 追偿跟踪表 - 诉讼-执行-执行内容 **/
	private List<ProjectRecoveryLitigationExecuteStuffInfo> projectRecoveryLitigationExecuteStuffInfos;
	/** 追偿跟踪表 - 诉讼-执行-执行内容-拍卖 **/
	private List<ProjectRecoveryLitigationExecuteStuffInfo> projectRecoveryLitigationExecuteStuffInfosPM;
	/** 追偿跟踪表 - 诉讼-执行-执行内容 -变卖 **/
	private List<ProjectRecoveryLitigationExecuteStuffInfo> projectRecoveryLitigationExecuteStuffInfosBM;
	/*** 追偿跟踪表 - 债务人重整或破产清算表-回收金额明细 **/
	private List<ProjectRecoveryDebtorReorganizationAmountDetailInfo> projectRecoveryDebtorReorganizationAmountDetailInfos;
	
	/*** 追偿跟踪表 - 债务人重整或破产清算表-抵质押资产抵债明细 **/
	private List<ProjectRecoveryDebtorReorganizationPledgeInfo> projectRecoveryDebtorReorganizationPledgeInfos;
	
	/*** 追偿跟踪表 - 债务人重整或破产清算表-抵质押资产抵债明细-总金额 **/
	private Money projectRecoveryDebtorReorganizationPledgeTotalAmount;
	
	//========== getters and setters ==========
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public List<ProjectRecoveryLitigationExecuteStuffInfo> getProjectRecoveryLitigationExecuteStuffInfos() {
		return this.projectRecoveryLitigationExecuteStuffInfos;
	}
	
	/** 拍卖 **/
	public List<ProjectRecoveryLitigationExecuteStuffInfo> getProjectRecoveryLitigationExecuteStuffInfosPM() {
		if (projectRecoveryLitigationExecuteStuffInfos == null) {
			return null;
		}
		if (projectRecoveryLitigationExecuteStuffInfosPM == null) {
			projectRecoveryLitigationExecuteStuffInfosPM = new ArrayList<ProjectRecoveryLitigationExecuteStuffInfo>();
			for (ProjectRecoveryLitigationExecuteStuffInfo stuffInfo : projectRecoveryLitigationExecuteStuffInfos) {
				if (ProjectRecoveryDescribeTypeEnum.AUCTION == stuffInfo.getDescribeType()) {
					projectRecoveryLitigationExecuteStuffInfosPM.add(stuffInfo);
				}
			}
		}
		return projectRecoveryLitigationExecuteStuffInfosPM;
		
	}
	
	/** 变卖 **/
	public List<ProjectRecoveryLitigationExecuteStuffInfo> getProjectRecoveryLitigationExecuteStuffInfosBM() {
		if (projectRecoveryLitigationExecuteStuffInfos == null) {
			return null;
		}
		if (projectRecoveryLitigationExecuteStuffInfosBM == null) {
			projectRecoveryLitigationExecuteStuffInfosBM = new ArrayList<ProjectRecoveryLitigationExecuteStuffInfo>();
			for (ProjectRecoveryLitigationExecuteStuffInfo stuffInfo : projectRecoveryLitigationExecuteStuffInfos) {
				if (ProjectRecoveryDescribeTypeEnum.SELL_OFF == stuffInfo.getDescribeType()) {
					projectRecoveryLitigationExecuteStuffInfosBM.add(stuffInfo);
				}
			}
		}
		return projectRecoveryLitigationExecuteStuffInfosBM;
	}
	
	public void setProjectRecoveryLitigationExecuteStuffInfos(	List<ProjectRecoveryLitigationExecuteStuffInfo> projectRecoveryLitigationExecuteStuffInfos) {
		this.projectRecoveryLitigationExecuteStuffInfos = projectRecoveryLitigationExecuteStuffInfos;
	}
	
	public List<ProjectRecoveryDebtorReorganizationAmountDetailInfo> getProjectRecoveryDebtorReorganizationAmountDetailInfos() {
		return this.projectRecoveryDebtorReorganizationAmountDetailInfos;
	}
	
	public void setProjectRecoveryDebtorReorganizationAmountDetailInfos(List<ProjectRecoveryDebtorReorganizationAmountDetailInfo> projectRecoveryDebtorReorganizationAmountDetailInfos) {
		this.projectRecoveryDebtorReorganizationAmountDetailInfos = projectRecoveryDebtorReorganizationAmountDetailInfos;
	}
	
	public List<ProjectRecoveryDebtorReorganizationPledgeInfo> getProjectRecoveryDebtorReorganizationPledgeInfos() {
		return this.projectRecoveryDebtorReorganizationPledgeInfos;
	}
	
	public void setProjectRecoveryDebtorReorganizationPledgeInfos(	List<ProjectRecoveryDebtorReorganizationPledgeInfo> projectRecoveryDebtorReorganizationPledgeInfos) {
		this.projectRecoveryDebtorReorganizationPledgeInfos = projectRecoveryDebtorReorganizationPledgeInfos;
	}
	
	public Money getProjectRecoveryDebtorReorganizationPledgeTotalAmount() {
		if (projectRecoveryDebtorReorganizationPledgeTotalAmount != null) {
			return projectRecoveryDebtorReorganizationPledgeTotalAmount;
		}
		if (projectRecoveryDebtorReorganizationPledgeInfos != null) {
			projectRecoveryDebtorReorganizationPledgeTotalAmount = Money.zero();
			for (ProjectRecoveryDebtorReorganizationPledgeInfo pledgeInfo : projectRecoveryDebtorReorganizationPledgeInfos) {
				projectRecoveryDebtorReorganizationPledgeTotalAmount.addTo(pledgeInfo
					.getPledgeAmount());
			}
		}
		
		return projectRecoveryDebtorReorganizationPledgeTotalAmount;
	}
	
	public void setProjectRecoveryDebtorReorganizationPledgeTotalAmount(Money projectRecoveryDebtorReorganizationPledgeTotalAmount) {
		this.projectRecoveryDebtorReorganizationPledgeTotalAmount = projectRecoveryDebtorReorganizationPledgeTotalAmount;
	}
	
	public String getRecoveryLitigationExecuteMemoUrl() {
		return this.recoveryLitigationExecuteMemoUrl;
	}
	
	public void setRecoveryLitigationExecuteMemoUrl(String recoveryLitigationExecuteMemoUrl) {
		this.recoveryLitigationExecuteMemoUrl = recoveryLitigationExecuteMemoUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationExecuteMemo() {
		return this.recoveryLitigationExecuteMemo;
	}
	
	public void setRecoveryLitigationExecuteMemo(	List<CommonAttachmentInfo> recoveryLitigationExecuteMemo) {
		this.recoveryLitigationExecuteMemo = recoveryLitigationExecuteMemo;
	}
	
	public long getProjectRecoveryId() {
		return projectRecoveryId;
	}
	
	public void setProjectRecoveryId(long projectRecoveryId) {
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
	
	public List<CommonAttachmentInfo> getRecoveryLitigationExecuteExecuteApply() {
		return this.recoveryLitigationExecuteExecuteApply;
	}
	
	public void setRecoveryLitigationExecuteExecuteApply(	List<CommonAttachmentInfo> recoveryLitigationExecuteExecuteApply) {
		this.recoveryLitigationExecuteExecuteApply = recoveryLitigationExecuteExecuteApply;
	}
	
	public String getRecoveryLitigationExecutePlaceOnFileUrl() {
		return this.recoveryLitigationExecutePlaceOnFileUrl;
	}
	
	public void setRecoveryLitigationExecutePlaceOnFileUrl(	String recoveryLitigationExecutePlaceOnFileUrl) {
		this.recoveryLitigationExecutePlaceOnFileUrl = recoveryLitigationExecutePlaceOnFileUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationExecutePlaceOnFile() {
		return this.recoveryLitigationExecutePlaceOnFile;
	}
	
	public void setRecoveryLitigationExecutePlaceOnFile(List<CommonAttachmentInfo> recoveryLitigationExecutePlaceOnFile) {
		this.recoveryLitigationExecutePlaceOnFile = recoveryLitigationExecutePlaceOnFile;
	}
	
	public String getRecoveryLitigationExecuteAcceptingCourtUrl() {
		return this.recoveryLitigationExecuteAcceptingCourtUrl;
	}
	
	public void setRecoveryLitigationExecuteAcceptingCourtUrl(	String recoveryLitigationExecuteAcceptingCourtUrl) {
		this.recoveryLitigationExecuteAcceptingCourtUrl = recoveryLitigationExecuteAcceptingCourtUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationExecuteAcceptingCourt() {
		return this.recoveryLitigationExecuteAcceptingCourt;
	}
	
	public void setRecoveryLitigationExecuteAcceptingCourt(	List<CommonAttachmentInfo> recoveryLitigationExecuteAcceptingCourt) {
		this.recoveryLitigationExecuteAcceptingCourt = recoveryLitigationExecuteAcceptingCourt;
	}
	
	public String getRecoveryLitigationExecuteCompromiseUrl() {
		return this.recoveryLitigationExecuteCompromiseUrl;
	}
	
	public void setRecoveryLitigationExecuteCompromiseUrl(	String recoveryLitigationExecuteCompromiseUrl) {
		this.recoveryLitigationExecuteCompromiseUrl = recoveryLitigationExecuteCompromiseUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationExecuteCompromise() {
		return this.recoveryLitigationExecuteCompromise;
	}
	
	public void setRecoveryLitigationExecuteCompromise(	List<CommonAttachmentInfo> recoveryLitigationExecuteCompromise) {
		this.recoveryLitigationExecuteCompromise = recoveryLitigationExecuteCompromise;
	}
	
	public String getRecoveryLitigationExecuteConciliationUrl() {
		return this.recoveryLitigationExecuteConciliationUrl;
	}
	
	public void setRecoveryLitigationExecuteConciliationUrl(String recoveryLitigationExecuteConciliationUrl) {
		this.recoveryLitigationExecuteConciliationUrl = recoveryLitigationExecuteConciliationUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationExecuteConciliation() {
		return this.recoveryLitigationExecuteConciliation;
	}
	
	public void setRecoveryLitigationExecuteConciliation(	List<CommonAttachmentInfo> recoveryLitigationExecuteConciliation) {
		this.recoveryLitigationExecuteConciliation = recoveryLitigationExecuteConciliation;
	}
	
	public String getRecoveryLitigationExecuteEstimateUrl() {
		return this.recoveryLitigationExecuteEstimateUrl;
	}
	
	public void setRecoveryLitigationExecuteEstimateUrl(String recoveryLitigationExecuteEstimateUrl) {
		this.recoveryLitigationExecuteEstimateUrl = recoveryLitigationExecuteEstimateUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationExecuteEstimate() {
		return this.recoveryLitigationExecuteEstimate;
	}
	
	public void setRecoveryLitigationExecuteEstimate(	List<CommonAttachmentInfo> recoveryLitigationExecuteEstimate) {
		this.recoveryLitigationExecuteEstimate = recoveryLitigationExecuteEstimate;
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
	
	public Money getRecoveryTotalAmount() {
		return this.recoveryTotalAmount;
	}
	
	public void setRecoveryTotalAmount(Money recoveryTotalAmount) {
		this.recoveryTotalAmount = recoveryTotalAmount;
	}
	
	public void setProjectRecoveryLitigationExecuteStuffInfosPM(List<ProjectRecoveryLitigationExecuteStuffInfo> projectRecoveryLitigationExecuteStuffInfosPM) {
		this.projectRecoveryLitigationExecuteStuffInfosPM = projectRecoveryLitigationExecuteStuffInfosPM;
	}
	
	public void setProjectRecoveryLitigationExecuteStuffInfosBM(List<ProjectRecoveryLitigationExecuteStuffInfo> projectRecoveryLitigationExecuteStuffInfosBM) {
		this.projectRecoveryLitigationExecuteStuffInfosBM = projectRecoveryLitigationExecuteStuffInfosBM;
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
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
