/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.info.revovery;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.ProjectRecoveryProcedureTypeEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.born.fcs.pm.ws.info.common.CommonAttachmentInfo;

/**
 * 
 * 追偿跟踪表 - 诉讼-再审程序
 * 
 * @author hjiajie
 * 
 */
public class ProjectRecoveryLitigationAdjournedProcedureInfo extends BaseToStringInfo {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	private long id;
	
	/**
	 * 追偿主表主键
	 */
	private long projectRecoveryId;
	
	/**
	 * 一审/二审
	 */
	private ProjectRecoveryProcedureTypeEnum procedureType;
	
	/**
	 * 开庭时间
	 */
	private Date openingTime;
	
	/**
	 * 出庭律师
	 */
	private String witnessAttorney;
	
	/**
	 * 我方主要诉讼请求或答辩意见
	 */
	private String weLitigationDemand;
	
	/**
	 * 对方主要诉讼请求或答辩意见
	 */
	private String otherSideLitigationDemand;
	
	/**
	 * 争议焦点
	 */
	private String controversyFocus;
	
	/**
	 * 补充证据
	 */
	private String additionalEvidence;
	
	/**
	 * 备注
	 */
	private String memo;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	// 附件  开始 
	
	/*** 诉讼-再审程序-我方主要诉讼请求或答辩意见-附件 **/
	private String recoveryLitigationAdjournedProcedureWeLitigationDemandUrl;
	/*** 诉讼-再审程序-我方主要诉讼请求或答辩意见-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationAdjournedProcedureWeLitigationDemand;
	
	/*** 诉讼-再审程序-补充证据-附件 **/
	private String recoveryLitigationAdjournedProcedureAdditionalEvidenceUrl;
	/*** 诉讼-再审程序-补充证据-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationAdjournedProcedureAdditionalEvidence;
	
	/*** 诉讼-再审程序-备注-附件 **/
	private String recoveryLitigationAdjournedProcedureMemoUrl;
	/*** 诉讼-再审程序-备注-附件s **/
	private List<CommonAttachmentInfo> recoveryLitigationAdjournedProcedureMemo;
	
	//========== getters and setters ==========
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getRecoveryLitigationAdjournedProcedureWeLitigationDemandUrl() {
		return this.recoveryLitigationAdjournedProcedureWeLitigationDemandUrl;
	}
	
	public void setRecoveryLitigationAdjournedProcedureWeLitigationDemandUrl(	String recoveryLitigationAdjournedProcedureWeLitigationDemandUrl) {
		this.recoveryLitigationAdjournedProcedureWeLitigationDemandUrl = recoveryLitigationAdjournedProcedureWeLitigationDemandUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationAdjournedProcedureWeLitigationDemand() {
		return this.recoveryLitigationAdjournedProcedureWeLitigationDemand;
	}
	
	public void setRecoveryLitigationAdjournedProcedureWeLitigationDemand(	List<CommonAttachmentInfo> recoveryLitigationAdjournedProcedureWeLitigationDemand) {
		this.recoveryLitigationAdjournedProcedureWeLitigationDemand = recoveryLitigationAdjournedProcedureWeLitigationDemand;
	}
	
	public String getRecoveryLitigationAdjournedProcedureAdditionalEvidenceUrl() {
		return this.recoveryLitigationAdjournedProcedureAdditionalEvidenceUrl;
	}
	
	public void setRecoveryLitigationAdjournedProcedureAdditionalEvidenceUrl(	String recoveryLitigationAdjournedProcedureAdditionalEvidenceUrl) {
		this.recoveryLitigationAdjournedProcedureAdditionalEvidenceUrl = recoveryLitigationAdjournedProcedureAdditionalEvidenceUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationAdjournedProcedureAdditionalEvidence() {
		return this.recoveryLitigationAdjournedProcedureAdditionalEvidence;
	}
	
	public void setRecoveryLitigationAdjournedProcedureAdditionalEvidence(	List<CommonAttachmentInfo> recoveryLitigationAdjournedProcedureAdditionalEvidence) {
		this.recoveryLitigationAdjournedProcedureAdditionalEvidence = recoveryLitigationAdjournedProcedureAdditionalEvidence;
	}
	
	public String getRecoveryLitigationAdjournedProcedureMemoUrl() {
		return this.recoveryLitigationAdjournedProcedureMemoUrl;
	}
	
	public void setRecoveryLitigationAdjournedProcedureMemoUrl(	String recoveryLitigationAdjournedProcedureMemoUrl) {
		this.recoveryLitigationAdjournedProcedureMemoUrl = recoveryLitigationAdjournedProcedureMemoUrl;
	}
	
	public List<CommonAttachmentInfo> getRecoveryLitigationAdjournedProcedureMemo() {
		return this.recoveryLitigationAdjournedProcedureMemo;
	}
	
	public void setRecoveryLitigationAdjournedProcedureMemo(List<CommonAttachmentInfo> recoveryLitigationAdjournedProcedureMemo) {
		this.recoveryLitigationAdjournedProcedureMemo = recoveryLitigationAdjournedProcedureMemo;
	}
	
	public long getProjectRecoveryId() {
		return projectRecoveryId;
	}
	
	public void setProjectRecoveryId(long projectRecoveryId) {
		this.projectRecoveryId = projectRecoveryId;
	}
	
	public ProjectRecoveryProcedureTypeEnum getProcedureType() {
		return this.procedureType;
	}
	
	public void setProcedureType(ProjectRecoveryProcedureTypeEnum procedureType) {
		this.procedureType = procedureType;
	}
	
	public Date getOpeningTime() {
		return openingTime;
	}
	
	public void setOpeningTime(Date openingTime) {
		this.openingTime = openingTime;
	}
	
	public String getWitnessAttorney() {
		return witnessAttorney;
	}
	
	public void setWitnessAttorney(String witnessAttorney) {
		this.witnessAttorney = witnessAttorney;
	}
	
	public String getWeLitigationDemand() {
		return weLitigationDemand;
	}
	
	public void setWeLitigationDemand(String weLitigationDemand) {
		this.weLitigationDemand = weLitigationDemand;
	}
	
	public String getOtherSideLitigationDemand() {
		return otherSideLitigationDemand;
	}
	
	public void setOtherSideLitigationDemand(String otherSideLitigationDemand) {
		this.otherSideLitigationDemand = otherSideLitigationDemand;
	}
	
	public String getControversyFocus() {
		return controversyFocus;
	}
	
	public void setControversyFocus(String controversyFocus) {
		this.controversyFocus = controversyFocus;
	}
	
	public String getAdditionalEvidence() {
		return additionalEvidence;
	}
	
	public void setAdditionalEvidence(String additionalEvidence) {
		this.additionalEvidence = additionalEvidence;
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
