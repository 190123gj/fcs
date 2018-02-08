/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.info.revovery;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ProjectRecoveryProcedureTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectRecoveryRefereeTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectRecoveryStatusEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/***
 * 追偿跟踪主表
 * 
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryInfo extends BaseToStringInfo {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	private long id;
	
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
	private long legalManagerId;
	
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
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	// 添加子表信息 
	/**
	 * 追偿跟踪表 - 债务人重整或破产清算表
	 */
	private ProjectRecoveryDebtorReorganizationInfo projectRecoveryDebtorReorganizationInfo;
	
	/** 追偿跟踪表 - 诉讼-诉前保全 */
	private ProjectRecoveryLitigationBeforePreservationInfo projectRecoveryLitigationBeforePreservationInfo;
	
	/** 追偿跟踪表 - 诉讼-立案 */
	private ProjectRecoveryLitigationPlaceOnFileInfo projectRecoveryLitigationPlaceOnFileInfo;
	
	/** 追偿跟踪表 - 诉讼-诉讼保全 */
	private ProjectRecoveryLitigationPreservationInfo projectRecoveryLitigationPreservationInfo;
	
	/** 追偿跟踪表 - 诉讼-庭前准备 */
	private ProjectRecoveryLitigationBeforeTrailInfo projectRecoveryLitigationBeforeTrailInfo;
	
	/** 追偿跟踪表 - 诉讼-开庭 */
	private List<ProjectRecoveryLitigationOpeningInfo> ProjectRecoveryLitigationOpeningInfos;
	
	/** 追偿跟踪表 - 诉讼-裁判 */
	private List<ProjectRecoveryLitigationRefereeInfo> projectRecoveryLitigationRefereeInfos;
	
	/** 追偿跟踪表 - 诉讼-二审上述 */
	private List<ProjectRecoveryLitigationSecondAppealInfo> projectRecoveryLitigationSecondAppealInfos;
	
	/** 追偿跟踪表 - 诉讼-实现担保物权特别程序 */
	private ProjectRecoveryLitigationSpecialProcedureInfo projectRecoveryLitigationSpecialProcedureInfo;
	
	/** 追偿跟踪表 - 诉讼-强制执行公证执行证书 */
	private ProjectRecoveryLitigationCertificateInfo projectRecoveryLitigationCertificateInfo;
	
	/** 追偿跟踪表 - 诉讼-执行 */
	private ProjectRecoveryLitigationExecuteInfo projectRecoveryLitigationExecuteInfo;
	
	/** 追偿跟踪表 - 诉讼-再审程序-一审 */
	private List<ProjectRecoveryLitigationAdjournedProcedureInfo> projectRecoveryLitigationAdjournedProcedureInfos;
	
	/** 追偿跟踪表 - 诉讼-再审程序-二审 */
	private ProjectRecoveryLitigationAdjournedSecondInfo projectRecoveryLitigationAdjournedSecondInfo;
	
	/** 追偿跟踪表 - 诉讼-执行回转 */
	private ProjectRecoveryLitigationExecuteGyrationInfo projectRecoveryLitigationExecuteGyrationInfo;
	
	// 添加函件 
	
	/**
	 * 函件
	 */
	private List<ProjectRecoveryNoticeLetterInfo> noticeLetters;
	
	//========== getters and setters ==========
	
	public long getId() {
		return id;
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
	
	public void setId(long id) {
		this.id = id;
	}
	
	public ProjectRecoveryDebtorReorganizationInfo getProjectRecoveryDebtorReorganizationInfo() {
		return this.projectRecoveryDebtorReorganizationInfo;
	}
	
	public void setProjectRecoveryDebtorReorganizationInfo(	ProjectRecoveryDebtorReorganizationInfo projectRecoveryDebtorReorganizationInfo) {
		this.projectRecoveryDebtorReorganizationInfo = projectRecoveryDebtorReorganizationInfo;
	}
	
	public ProjectRecoveryLitigationBeforePreservationInfo getProjectRecoveryLitigationBeforePreservationInfo() {
		return this.projectRecoveryLitigationBeforePreservationInfo;
	}
	
	public void setProjectRecoveryLitigationBeforePreservationInfo(	ProjectRecoveryLitigationBeforePreservationInfo projectRecoveryLitigationBeforePreservationInfo) {
		this.projectRecoveryLitigationBeforePreservationInfo = projectRecoveryLitigationBeforePreservationInfo;
	}
	
	public ProjectRecoveryLitigationAdjournedSecondInfo getProjectRecoveryLitigationAdjournedSecondInfo() {
		return this.projectRecoveryLitigationAdjournedSecondInfo;
	}
	
	public void setProjectRecoveryLitigationAdjournedSecondInfo(ProjectRecoveryLitigationAdjournedSecondInfo projectRecoveryLitigationAdjournedSecondInfo) {
		this.projectRecoveryLitigationAdjournedSecondInfo = projectRecoveryLitigationAdjournedSecondInfo;
	}
	
	public List<ProjectRecoveryNoticeLetterInfo> getNoticeLetters() {
		return this.noticeLetters;
	}
	
	public void setNoticeLetters(List<ProjectRecoveryNoticeLetterInfo> noticeLetters) {
		this.noticeLetters = noticeLetters;
	}
	
	public ProjectRecoveryLitigationPlaceOnFileInfo getProjectRecoveryLitigationPlaceOnFileInfo() {
		return this.projectRecoveryLitigationPlaceOnFileInfo;
	}
	
	public Money getEstimateLoseAmount() {
		return this.estimateLoseAmount;
	}
	
	public void setEstimateLoseAmount(Money estimateLoseAmount) {
		this.estimateLoseAmount = estimateLoseAmount;
	}
	
	public Long getCloseFormId() {
		return this.closeFormId;
	}
	
	public void setCloseFormId(Long closeFormId) {
		this.closeFormId = closeFormId;
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
	
	public void setProjectRecoveryLitigationPlaceOnFileInfo(ProjectRecoveryLitigationPlaceOnFileInfo projectRecoveryLitigationPlaceOnFileInfo) {
		this.projectRecoveryLitigationPlaceOnFileInfo = projectRecoveryLitigationPlaceOnFileInfo;
	}
	
	public ProjectRecoveryLitigationPreservationInfo getProjectRecoveryLitigationPreservationInfo() {
		return this.projectRecoveryLitigationPreservationInfo;
	}
	
	public void setProjectRecoveryLitigationPreservationInfo(	ProjectRecoveryLitigationPreservationInfo projectRecoveryLitigationPreservationInfo) {
		this.projectRecoveryLitigationPreservationInfo = projectRecoveryLitigationPreservationInfo;
	}
	
	public ProjectRecoveryLitigationBeforeTrailInfo getProjectRecoveryLitigationBeforeTrailInfo() {
		return this.projectRecoveryLitigationBeforeTrailInfo;
	}
	
	public void setProjectRecoveryLitigationBeforeTrailInfo(ProjectRecoveryLitigationBeforeTrailInfo projectRecoveryLitigationBeforeTrailInfo) {
		this.projectRecoveryLitigationBeforeTrailInfo = projectRecoveryLitigationBeforeTrailInfo;
	}
	
	public List<ProjectRecoveryLitigationOpeningInfo> getProjectRecoveryLitigationOpeningInfos() {
		return this.ProjectRecoveryLitigationOpeningInfos;
	}
	
	public void setProjectRecoveryLitigationOpeningInfos(	List<ProjectRecoveryLitigationOpeningInfo> projectRecoveryLitigationOpeningInfos) {
		ProjectRecoveryLitigationOpeningInfos = projectRecoveryLitigationOpeningInfos;
	}
	
	public List<ProjectRecoveryLitigationRefereeInfo> getProjectRecoveryLitigationRefereeInfos() {
		return this.projectRecoveryLitigationRefereeInfos;
	}
	
	/** 按照类型获取裁判 */
	public ProjectRecoveryLitigationRefereeInfo getProjectRecoveryLitigationRefereeInfo(String type) {
		if (projectRecoveryLitigationRefereeInfos == null || type == null) {
			return null;
		}
		ProjectRecoveryRefereeTypeEnum typeEnum = ProjectRecoveryRefereeTypeEnum.getByCode(type);
		if (typeEnum == null) {
			return null;
		}
		for (ProjectRecoveryLitigationRefereeInfo info : projectRecoveryLitigationRefereeInfos) {
			if (typeEnum == info.getProjectRecoveryRefereeType()) {
				return info;
			}
		}
		return null;
	}
	
	public void setProjectRecoveryLitigationRefereeInfos(	List<ProjectRecoveryLitigationRefereeInfo> projectRecoveryLitigationRefereeInfos) {
		this.projectRecoveryLitigationRefereeInfos = projectRecoveryLitigationRefereeInfos;
	}
	
	public List<ProjectRecoveryLitigationSecondAppealInfo> getProjectRecoveryLitigationSecondAppealInfos() {
		return this.projectRecoveryLitigationSecondAppealInfos;
	}
	
	public void setProjectRecoveryLitigationSecondAppealInfos(	List<ProjectRecoveryLitigationSecondAppealInfo> projectRecoveryLitigationSecondAppealInfos) {
		this.projectRecoveryLitigationSecondAppealInfos = projectRecoveryLitigationSecondAppealInfos;
	}
	
	public ProjectRecoveryLitigationSpecialProcedureInfo getProjectRecoveryLitigationSpecialProcedureInfo() {
		return this.projectRecoveryLitigationSpecialProcedureInfo;
	}
	
	public void setProjectRecoveryLitigationSpecialProcedureInfo(	ProjectRecoveryLitigationSpecialProcedureInfo projectRecoveryLitigationSpecialProcedureInfo) {
		this.projectRecoveryLitigationSpecialProcedureInfo = projectRecoveryLitigationSpecialProcedureInfo;
	}
	
	public ProjectRecoveryLitigationCertificateInfo getProjectRecoveryLitigationCertificateInfo() {
		return this.projectRecoveryLitigationCertificateInfo;
	}
	
	public void setProjectRecoveryLitigationCertificateInfo(ProjectRecoveryLitigationCertificateInfo projectRecoveryLitigationCertificateInfo) {
		this.projectRecoveryLitigationCertificateInfo = projectRecoveryLitigationCertificateInfo;
	}
	
	public ProjectRecoveryLitigationExecuteInfo getProjectRecoveryLitigationExecuteInfo() {
		return this.projectRecoveryLitigationExecuteInfo;
	}
	
	public void setProjectRecoveryLitigationExecuteInfo(ProjectRecoveryLitigationExecuteInfo projectRecoveryLitigationExecuteInfo) {
		this.projectRecoveryLitigationExecuteInfo = projectRecoveryLitigationExecuteInfo;
	}
	
	public List<ProjectRecoveryLitigationAdjournedProcedureInfo> getProjectRecoveryLitigationAdjournedProcedureInfos() {
		return this.projectRecoveryLitigationAdjournedProcedureInfos;
	}
	
	/** 按照类型获取诉讼-再审程序 */
	public ProjectRecoveryLitigationAdjournedProcedureInfo getProjectRecoveryLitigationAdjournedProcedureInfos(	String type) {
		if (projectRecoveryLitigationRefereeInfos == null || type == null) {
			return null;
		}
		ProjectRecoveryProcedureTypeEnum typeEnum = ProjectRecoveryProcedureTypeEnum
			.getByCode(type);
		if (typeEnum == null) {
			return null;
		}
		for (ProjectRecoveryLitigationAdjournedProcedureInfo info : projectRecoveryLitigationAdjournedProcedureInfos) {
			if (typeEnum == info.getProcedureType()) {
				return info;
			}
		}
		return null;
	}
	
	public void setProjectRecoveryLitigationAdjournedProcedureInfos(List<ProjectRecoveryLitigationAdjournedProcedureInfo> projectRecoveryLitigationAdjournedProcedureInfos) {
		this.projectRecoveryLitigationAdjournedProcedureInfos = projectRecoveryLitigationAdjournedProcedureInfos;
	}
	
	public ProjectRecoveryLitigationExecuteGyrationInfo getProjectRecoveryLitigationExecuteGyrationInfo() {
		return this.projectRecoveryLitigationExecuteGyrationInfo;
	}
	
	public void setProjectRecoveryLitigationExecuteGyrationInfo(ProjectRecoveryLitigationExecuteGyrationInfo projectRecoveryLitigationExecuteGyrationInfo) {
		this.projectRecoveryLitigationExecuteGyrationInfo = projectRecoveryLitigationExecuteGyrationInfo;
	}
	
	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
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
	
	public long getLegalManagerId() {
		return legalManagerId;
	}
	
	public void setLegalManagerId(long legalManagerId) {
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
