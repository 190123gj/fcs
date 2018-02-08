/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.recovery;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ProjectRecoveryStatusEnum;
import com.born.fcs.pm.ws.info.revovery.ProjectRecoveryNoticeLetterInfo;
import com.born.fcs.pm.ws.order.base.ProcessOrder;
import com.yjf.common.lang.util.money.Money;

/***
 * 追偿跟踪主表
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryOrder extends ProcessOrder {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	private Long id;
	
	/**
	 * 项目编号
	 */
	private String projectCode;
	
	/**
	 * 代偿金额
	 */
	private Money recoveryAmount = new Money(0, 0);
	
	/**
	 * 法务经理ID
	 */
	private Long legalManagerId;
	
	/**
	 * 法务经理名称
	 */
	private String legalManagerName;
	
	/**
	 * 项目状态
	 */
	private ProjectRecoveryStatusEnum recoveryStatus;
	
	/***
	 * 状态更新时间
	 */
	private Date statusUpdateTime;
	
	/**
	 * 代偿本金金额
	 */
	private Money recoveryPrincipalAmount = new Money(0, 0);
	
	/**
	 * 代偿利息金额
	 */
	private Money recoveryInterestAmount = new Money(0, 0);
	
	/**
	 * 代偿罚息金额
	 */
	private Money recoveryInterestPenaltyAmount = new Money(0, 0);
	
	/**
	 * 代偿违约金金额
	 */
	private Money recoveryCompensationAmount = new Money(0, 0);
	
	/**
	 * 代偿其他金额
	 */
	private Money recoveryOtherAmount = new Money(0, 0);
	
	/**
	 * 是否执行诉讼
	 */
	private BooleanEnum litigationOn;
	
	/**
	 * 是否执行债务人重整或破产清算
	 */
	private BooleanEnum debtorReorganizationOn;
	
	/**
	 * 是否执行其他
	 */
	private BooleanEnum otherOn;
	
	/**
	 * 预估损失金额
	 */
	private Money estimateLoseAmount = new Money(0, 0);
	
	/**
	 * 分摊损失金额
	 */
	private Money apportionLoseAmount = new Money(0, 0);
	
	/**
	 * 损失认定金额
	 */
	private Money loseCognizanceAmount = new Money(0, 0);
	
	/**
	 * 追偿关闭的表单id
	 */
	private Long closeFormId;
	
	private Long applyUserId;
	
	private String applyUserAccount;
	
	private String applyUserName;
	
	private Long applyDeptId;
	
	private String applyDeptCode;
	
	private String applyDeptName;
	
	/** 方案名称 */
	private String recoveryName;
	
	/** 是否派生方案 */
	private String isAppend;
	
	/** 派生源 */
	private long appendRecoveryId;
	
	private String appendRecoveryName;
	
	/** 备注 */
	private String appendRemark;
	
	// 添加子表信息 
	/**
	 * 追偿跟踪表 - 债务人重整或破产清算表
	 */
	private ProjectRecoveryDebtorReorganizationOrder projectRecoveryDebtorReorganizationOrder;
	
	/** 追偿跟踪表 - 诉讼-诉前保全 */
	private List<ProjectRecoveryLitigationBeforePreservationOrder> projectRecoveryLitigationBeforePreservationOrder;
	
	/** 追偿跟踪表 - 诉讼-立案 */
	private List<ProjectRecoveryLitigationPlaceOnFileOrder> projectRecoveryLitigationPlaceOnFileOrder;
	
	/** 追偿跟踪表 - 诉讼-诉讼保全 */
	private List<ProjectRecoveryLitigationPreservationOrder> projectRecoveryLitigationPreservationOrder;
	
	/** 追偿跟踪表 - 诉讼-庭前准备 */
	private List<ProjectRecoveryLitigationBeforeTrailOrder> projectRecoveryLitigationBeforeTrailOrder;
	
	/** 追偿跟踪表 - 诉讼-开庭 */
	private List<ProjectRecoveryLitigationOpeningOrder> projectRecoveryLitigationOpeningOrder;
	
	/** 追偿跟踪表 - 诉讼-裁判 */
	private List<ProjectRecoveryLitigationRefereeOrder> projectRecoveryLitigationRefereeOrder;
	
	/** 追偿跟踪表 - 诉讼-二审上述 */
	private List<ProjectRecoveryLitigationSecondAppealOrder> projectRecoveryLitigationSecondAppealOrder;
	
	/** 追偿跟踪表 - 诉讼-实现担保物权特别程序 */
	private List<ProjectRecoveryLitigationSpecialProcedureOrder> projectRecoveryLitigationSpecialProcedureOrder;
	
	/** 追偿跟踪表 - 诉讼-强制执行公证执行证书 */
	private List<ProjectRecoveryLitigationCertificateOrder> projectRecoveryLitigationCertificateOrder;
	
	/** 追偿跟踪表 - 诉讼-执行 */
	private List<ProjectRecoveryLitigationExecuteOrder> projectRecoveryLitigationExecuteOrder;
	
	/** 追偿跟踪表 - 诉讼-再审程序-一审 */
	private List<ProjectRecoveryLitigationAdjournedProcedureOrder> projectRecoveryLitigationAdjournedProcedureOrder;
	
	/** 追偿跟踪表 - 诉讼-再审程序-二审 */
	private List<ProjectRecoveryLitigationAdjournedSecondOrder> projectRecoveryLitigationAdjournedSecondOrder;
	
	/** 追偿跟踪表 - 诉讼-执行回转 */
	private List<ProjectRecoveryLitigationExecuteGyrationOrder> projectRecoveryLitigationExecuteGyrationOrder;
	
	/*** 其他信息order */
	private ProjectRecoveryOtherOrder projectRecoveryOtherOrder;
	/**
	 * 函件
	 */
	private List<ProjectRecoveryNoticeLetterInfo> noticeLetters;
	
	/** 是否撤销关闭 **/
	private BooleanEnum reClose;
	
	//========== getters and setters ==========
	
	public List<ProjectRecoveryNoticeLetterInfo> getNoticeLetters() {
		return this.noticeLetters;
	}
	
	public Long getApplyUserId() {
		return this.applyUserId;
	}
	
	public void setApplyUserId(Long applyUserId) {
		this.applyUserId = applyUserId;
	}
	
	public String getApplyUserAccount() {
		return this.applyUserAccount;
	}
	
	public void setApplyUserAccount(String applyUserAccount) {
		this.applyUserAccount = applyUserAccount;
	}
	
	public String getApplyUserName() {
		return this.applyUserName;
	}
	
	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}
	
	public Long getApplyDeptId() {
		return this.applyDeptId;
	}
	
	public void setApplyDeptId(Long applyDeptId) {
		this.applyDeptId = applyDeptId;
	}
	
	public String getApplyDeptCode() {
		return this.applyDeptCode;
	}
	
	public void setApplyDeptCode(String applyDeptCode) {
		this.applyDeptCode = applyDeptCode;
	}
	
	public String getApplyDeptName() {
		return this.applyDeptName;
	}
	
	public void setApplyDeptName(String applyDeptName) {
		this.applyDeptName = applyDeptName;
	}
	
	public BooleanEnum getReClose() {
		return this.reClose;
	}
	
	public void setReClose(BooleanEnum reClose) {
		this.reClose = reClose;
	}
	
	public void setNoticeLetters(List<ProjectRecoveryNoticeLetterInfo> noticeLetters) {
		this.noticeLetters = noticeLetters;
	}
	
	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public ProjectRecoveryDebtorReorganizationOrder getProjectRecoveryDebtorReorganizationOrder() {
		return this.projectRecoveryDebtorReorganizationOrder;
	}
	
	public void setProjectRecoveryDebtorReorganizationOrder(ProjectRecoveryDebtorReorganizationOrder projectRecoveryDebtorReorganizationOrder) {
		this.projectRecoveryDebtorReorganizationOrder = projectRecoveryDebtorReorganizationOrder;
	}
	
	public List<ProjectRecoveryLitigationOpeningOrder> getProjectRecoveryLitigationOpeningOrder() {
		return this.projectRecoveryLitigationOpeningOrder;
	}
	
	public void setProjectRecoveryLitigationOpeningOrder(	List<ProjectRecoveryLitigationOpeningOrder> projectRecoveryLitigationOpeningOrder) {
		this.projectRecoveryLitigationOpeningOrder = projectRecoveryLitigationOpeningOrder;
	}
	
	public List<ProjectRecoveryLitigationRefereeOrder> getProjectRecoveryLitigationRefereeOrder() {
		return this.projectRecoveryLitigationRefereeOrder;
	}
	
	public ProjectRecoveryOtherOrder getProjectRecoveryOtherOrder() {
		return this.projectRecoveryOtherOrder;
	}
	
	public void setProjectRecoveryOtherOrder(ProjectRecoveryOtherOrder projectRecoveryOtherOrder) {
		this.projectRecoveryOtherOrder = projectRecoveryOtherOrder;
	}
	
	public void setProjectRecoveryLitigationRefereeOrder(	List<ProjectRecoveryLitigationRefereeOrder> projectRecoveryLitigationRefereeOrder) {
		this.projectRecoveryLitigationRefereeOrder = projectRecoveryLitigationRefereeOrder;
	}
	
	public List<ProjectRecoveryLitigationSecondAppealOrder> getProjectRecoveryLitigationSecondAppealOrder() {
		return this.projectRecoveryLitigationSecondAppealOrder;
	}
	
	public void setProjectRecoveryLitigationSecondAppealOrder(	List<ProjectRecoveryLitigationSecondAppealOrder> projectRecoveryLitigationSecondAppealOrder) {
		this.projectRecoveryLitigationSecondAppealOrder = projectRecoveryLitigationSecondAppealOrder;
	}
	
	public List<ProjectRecoveryLitigationAdjournedProcedureOrder> getProjectRecoveryLitigationAdjournedProcedureOrder() {
		return this.projectRecoveryLitigationAdjournedProcedureOrder;
	}
	
	public void setProjectRecoveryLitigationAdjournedProcedureOrder(List<ProjectRecoveryLitigationAdjournedProcedureOrder> projectRecoveryLitigationAdjournedProcedureOrder) {
		this.projectRecoveryLitigationAdjournedProcedureOrder = projectRecoveryLitigationAdjournedProcedureOrder;
	}
	
	public List<ProjectRecoveryLitigationBeforePreservationOrder> getProjectRecoveryLitigationBeforePreservationOrder() {
		return this.projectRecoveryLitigationBeforePreservationOrder;
	}
	
	public void setProjectRecoveryLitigationBeforePreservationOrder(List<ProjectRecoveryLitigationBeforePreservationOrder> projectRecoveryLitigationBeforePreservationOrder) {
		this.projectRecoveryLitigationBeforePreservationOrder = projectRecoveryLitigationBeforePreservationOrder;
	}
	
	public List<ProjectRecoveryLitigationPlaceOnFileOrder> getProjectRecoveryLitigationPlaceOnFileOrder() {
		return this.projectRecoveryLitigationPlaceOnFileOrder;
	}
	
	public void setProjectRecoveryLitigationPlaceOnFileOrder(	List<ProjectRecoveryLitigationPlaceOnFileOrder> projectRecoveryLitigationPlaceOnFileOrder) {
		this.projectRecoveryLitigationPlaceOnFileOrder = projectRecoveryLitigationPlaceOnFileOrder;
	}
	
	public List<ProjectRecoveryLitigationPreservationOrder> getProjectRecoveryLitigationPreservationOrder() {
		return this.projectRecoveryLitigationPreservationOrder;
	}
	
	public void setProjectRecoveryLitigationPreservationOrder(	List<ProjectRecoveryLitigationPreservationOrder> projectRecoveryLitigationPreservationOrder) {
		this.projectRecoveryLitigationPreservationOrder = projectRecoveryLitigationPreservationOrder;
	}
	
	public List<ProjectRecoveryLitigationBeforeTrailOrder> getProjectRecoveryLitigationBeforeTrailOrder() {
		return this.projectRecoveryLitigationBeforeTrailOrder;
	}
	
	public void setProjectRecoveryLitigationBeforeTrailOrder(	List<ProjectRecoveryLitigationBeforeTrailOrder> projectRecoveryLitigationBeforeTrailOrder) {
		this.projectRecoveryLitigationBeforeTrailOrder = projectRecoveryLitigationBeforeTrailOrder;
	}
	
	public List<ProjectRecoveryLitigationSpecialProcedureOrder> getProjectRecoveryLitigationSpecialProcedureOrder() {
		return this.projectRecoveryLitigationSpecialProcedureOrder;
	}
	
	public void setProjectRecoveryLitigationSpecialProcedureOrder(	List<ProjectRecoveryLitigationSpecialProcedureOrder> projectRecoveryLitigationSpecialProcedureOrder) {
		this.projectRecoveryLitigationSpecialProcedureOrder = projectRecoveryLitigationSpecialProcedureOrder;
	}
	
	public List<ProjectRecoveryLitigationCertificateOrder> getProjectRecoveryLitigationCertificateOrder() {
		return this.projectRecoveryLitigationCertificateOrder;
	}
	
	public void setProjectRecoveryLitigationCertificateOrder(	List<ProjectRecoveryLitigationCertificateOrder> projectRecoveryLitigationCertificateOrder) {
		this.projectRecoveryLitigationCertificateOrder = projectRecoveryLitigationCertificateOrder;
	}
	
	public List<ProjectRecoveryLitigationExecuteOrder> getProjectRecoveryLitigationExecuteOrder() {
		return this.projectRecoveryLitigationExecuteOrder;
	}
	
	public void setProjectRecoveryLitigationExecuteOrder(	List<ProjectRecoveryLitigationExecuteOrder> projectRecoveryLitigationExecuteOrder) {
		this.projectRecoveryLitigationExecuteOrder = projectRecoveryLitigationExecuteOrder;
	}
	
	public List<ProjectRecoveryLitigationAdjournedSecondOrder> getProjectRecoveryLitigationAdjournedSecondOrder() {
		return this.projectRecoveryLitigationAdjournedSecondOrder;
	}
	
	public void setProjectRecoveryLitigationAdjournedSecondOrder(	List<ProjectRecoveryLitigationAdjournedSecondOrder> projectRecoveryLitigationAdjournedSecondOrder) {
		this.projectRecoveryLitigationAdjournedSecondOrder = projectRecoveryLitigationAdjournedSecondOrder;
	}
	
	public List<ProjectRecoveryLitigationExecuteGyrationOrder> getProjectRecoveryLitigationExecuteGyrationOrder() {
		return this.projectRecoveryLitigationExecuteGyrationOrder;
	}
	
	public void setProjectRecoveryLitigationExecuteGyrationOrder(	List<ProjectRecoveryLitigationExecuteGyrationOrder> projectRecoveryLitigationExecuteGyrationOrder) {
		this.projectRecoveryLitigationExecuteGyrationOrder = projectRecoveryLitigationExecuteGyrationOrder;
	}
	
	public Money getRecoveryAmount() {
		return recoveryAmount;
	}
	
	public void setRecoveryAmount(Money recoveryAmount) {
		if (recoveryAmount == null) {
			this.recoveryAmount = new Money(0, 0);
		} else {
			this.recoveryAmount = recoveryAmount;
		}
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getLegalManagerId() {
		return this.legalManagerId;
	}
	
	public void setLegalManagerId(Long legalManagerId) {
		this.legalManagerId = legalManagerId;
	}
	
	public String getLegalManagerName() {
		return legalManagerName;
	}
	
	public void setLegalManagerName(String legalManagerName) {
		this.legalManagerName = legalManagerName;
	}
	
	public ProjectRecoveryStatusEnum getRecoveryStatus() {
		return this.recoveryStatus;
	}
	
	public void setRecoveryStatus(ProjectRecoveryStatusEnum recoveryStatus) {
		this.recoveryStatus = recoveryStatus;
	}
	
	public Date getStatusUpdateTime() {
		return statusUpdateTime;
	}
	
	public void setStatusUpdateTime(Date statusUpdateTime) {
		this.statusUpdateTime = statusUpdateTime;
	}
	
	public Money getRecoveryPrincipalAmount() {
		return recoveryPrincipalAmount;
	}
	
	public void setRecoveryPrincipalAmount(Money recoveryPrincipalAmount) {
		if (recoveryPrincipalAmount == null) {
			this.recoveryPrincipalAmount = new Money(0, 0);
		} else {
			this.recoveryPrincipalAmount = recoveryPrincipalAmount;
		}
	}
	
	public Money getRecoveryInterestAmount() {
		return recoveryInterestAmount;
	}
	
	public Long getCloseFormId() {
		return this.closeFormId;
	}
	
	public void setCloseFormId(Long closeFormId) {
		this.closeFormId = closeFormId;
	}
	
	public void setRecoveryInterestAmount(Money recoveryInterestAmount) {
		if (recoveryInterestAmount == null) {
			this.recoveryInterestAmount = new Money(0, 0);
		} else {
			this.recoveryInterestAmount = recoveryInterestAmount;
		}
	}
	
	public Money getRecoveryInterestPenaltyAmount() {
		return recoveryInterestPenaltyAmount;
	}
	
	public void setRecoveryInterestPenaltyAmount(Money recoveryInterestPenaltyAmount) {
		if (recoveryInterestPenaltyAmount == null) {
			this.recoveryInterestPenaltyAmount = new Money(0, 0);
		} else {
			this.recoveryInterestPenaltyAmount = recoveryInterestPenaltyAmount;
		}
	}
	
	public Money getRecoveryCompensationAmount() {
		return recoveryCompensationAmount;
	}
	
	public void setRecoveryCompensationAmount(Money recoveryCompensationAmount) {
		if (recoveryCompensationAmount == null) {
			this.recoveryCompensationAmount = new Money(0, 0);
		} else {
			this.recoveryCompensationAmount = recoveryCompensationAmount;
		}
	}
	
	public Money getRecoveryOtherAmount() {
		return recoveryOtherAmount;
	}
	
	public void setRecoveryOtherAmount(Money recoveryOtherAmount) {
		if (recoveryOtherAmount == null) {
			this.recoveryOtherAmount = new Money(0, 0);
		} else {
			this.recoveryOtherAmount = recoveryOtherAmount;
		}
	}
	
	public BooleanEnum getLitigationOn() {
		return this.litigationOn;
	}
	
	public void setLitigationOn(BooleanEnum litigationOn) {
		this.litigationOn = litigationOn;
	}
	
	public BooleanEnum getDebtorReorganizationOn() {
		return this.debtorReorganizationOn;
	}
	
	public void setDebtorReorganizationOn(BooleanEnum debtorReorganizationOn) {
		this.debtorReorganizationOn = debtorReorganizationOn;
	}
	
	public Money getEstimateLoseAmount() {
		return this.estimateLoseAmount;
	}
	
	public void setEstimateLoseAmount(Money estimateLoseAmount) {
		this.estimateLoseAmount = estimateLoseAmount;
	}
	
	public Money getApportionLoseAmount() {
		return this.apportionLoseAmount;
	}
	
	public void setApportionLoseAmount(Money apportionLoseAmount) {
		this.apportionLoseAmount = apportionLoseAmount;
	}
	
	public Money getLoseCognizanceAmount() {
		return this.loseCognizanceAmount;
	}
	
	public void setLoseCognizanceAmount(Money loseCognizanceAmount) {
		this.loseCognizanceAmount = loseCognizanceAmount;
	}
	
	public BooleanEnum getOtherOn() {
		return this.otherOn;
	}
	
	public void setOtherOn(BooleanEnum otherOn) {
		this.otherOn = otherOn;
	}
	
	public String getRecoveryName() {
		return this.recoveryName;
	}
	
	public void setRecoveryName(String recoveryName) {
		this.recoveryName = recoveryName;
	}
	
	public String getIsAppend() {
		return this.isAppend;
	}
	
	public void setIsAppend(String isAppend) {
		this.isAppend = isAppend;
	}
	
	public long getAppendRecoveryId() {
		return this.appendRecoveryId;
	}
	
	public void setAppendRecoveryId(long appendRecoveryId) {
		this.appendRecoveryId = appendRecoveryId;
	}
	
	public String getAppendRecoveryName() {
		return this.appendRecoveryName;
	}
	
	public void setAppendRecoveryName(String appendRecoveryName) {
		this.appendRecoveryName = appendRecoveryName;
	}
	
	public String getAppendRemark() {
		return this.appendRemark;
	}
	
	public void setAppendRemark(String appendRemark) {
		this.appendRemark = appendRemark;
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
