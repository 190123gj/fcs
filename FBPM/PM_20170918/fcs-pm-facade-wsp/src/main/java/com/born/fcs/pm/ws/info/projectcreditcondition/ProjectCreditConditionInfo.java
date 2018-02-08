package com.born.fcs.pm.ws.info.projectcreditcondition;

/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CreditCheckStatusEnum;
import com.born.fcs.pm.ws.enums.CreditConditionTypeEnum;
import com.born.fcs.pm.ws.enums.ReleaseStatusEnum;
import com.born.fcs.pm.ws.info.common.SimpleFormProjectInfo;

/**
 * 项目授信条件
 *
 * @author Ji
 *
 */
public class ProjectCreditConditionInfo extends SimpleFormProjectInfo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3023627465485137316L;
	/** 主键 */
	private long id;
	/** 项目编号 */
	private String projectCode;
	/** 资产系统对应资产ID */
	private long assetId;
	/**
	 * f_council_summary_project_pledge_asset.id,
	 * f_council_summary_project_guarantor.id
	 */
	private long itemId;
	/** 授信条件文字描述（根据对应抵（质）押、保证生成） */
	private String itemDesc;
	/** 类型：抵押、质押、保证 */
	private CreditConditionTypeEnum type;
	/** 是否落实 */
	private BooleanEnum isConfirm;
	/** 落实人ID */
	private String confirmManId;
	/** 落实人账号 */
	private String confirmManAccount;
	/** 落实人名称 */
	private String confirmManName;
	/** 落实日期 */
	private Date confirmDate;
	/** 合同编号权(有多个用,分开) */
	private String contractNo;
	/** 合同/协议url */
	private String contractAgreementUrl;
	/** 附件文本信息 */
	private String textInfo;
	/** 权利凭证(有多个用,分开) */
	private String rightVouche;
	/** 备注 */
	private String remark;
	/** 状态 */
	private CreditCheckStatusEnum status;
	/** 解保申请状态(默认待解保) */
	private ReleaseStatusEnum releaseStatus;
	/** 解保申请的id:f_counter_guarantee_release.id */
	private long releaseId;
	/** 解除的原因 */
	private String releaseReason;
	/** 解除的依据 */
	private String releaseGist;
	/** 新增时间 */
	private Date rawAddTime;
	/** 修改时间 */
	private Date rawUpdateTime;
	
	List<ProjectCreditAssetAttachmentInfo> listAttachmentInfos;

	/** 资产凭证号 */
	private String warrantNo;
	
	//========== getters and setters ==========


	public String getWarrantNo() {
		return warrantNo;
	}

	public void setWarrantNo(String warrantNo) {
		this.warrantNo = warrantNo;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public String getProjectCode() {
		return projectCode;
	}
	
	@Override
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public long getItemId() {
		return itemId;
	}
	
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	
	public String getItemDesc() {
		return itemDesc;
	}
	
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	
	public long getAssetId() {
		return this.assetId;
	}
	
	public void setAssetId(long assetId) {
		this.assetId = assetId;
	}
	
	public CreditConditionTypeEnum getType() {
		return type;
	}
	
	public void setType(CreditConditionTypeEnum type) {
		this.type = type;
	}
	
	public BooleanEnum getIsConfirm() {
		return isConfirm;
	}
	
	public void setIsConfirm(BooleanEnum isConfirm) {
		this.isConfirm = isConfirm;
	}
	
	public String getConfirmManId() {
		return confirmManId;
	}
	
	public void setConfirmManId(String confirmManId) {
		this.confirmManId = confirmManId;
	}
	
	public String getConfirmManAccount() {
		return confirmManAccount;
	}
	
	public void setConfirmManAccount(String confirmManAccount) {
		this.confirmManAccount = confirmManAccount;
	}
	
	public String getConfirmManName() {
		return confirmManName;
	}
	
	public void setConfirmManName(String confirmManName) {
		this.confirmManName = confirmManName;
	}
	
	public Date getConfirmDate() {
		return confirmDate;
	}
	
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	
	public String getContractNo() {
		return contractNo;
	}
	
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	public String getRightVouche() {
		return rightVouche;
	}
	
	public void setRightVouche(String rightVouche) {
		this.rightVouche = rightVouche;
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
	
	public CreditCheckStatusEnum getStatus() {
		return status;
	}
	
	public void setStatus(CreditCheckStatusEnum status) {
		this.status = status;
	}
	
	public ReleaseStatusEnum getReleaseStatus() {
		return releaseStatus;
	}
	
	public void setReleaseStatus(ReleaseStatusEnum releaseStatus) {
		this.releaseStatus = releaseStatus;
	}
	
	public long getReleaseId() {
		return releaseId;
	}
	
	public void setReleaseId(long releaseId) {
		this.releaseId = releaseId;
	}
	
	public String getReleaseReason() {
		return releaseReason;
	}
	
	public void setReleaseReason(String releaseReason) {
		this.releaseReason = releaseReason;
	}
	
	public String getReleaseGist() {
		return releaseGist;
	}
	
	public void setReleaseGist(String releaseGist) {
		this.releaseGist = releaseGist;
	}
	
	public String getContractAgreementUrl() {
		return contractAgreementUrl;
	}
	
	public void setContractAgreementUrl(String contractAgreementUrl) {
		this.contractAgreementUrl = contractAgreementUrl;
	}
	
	public List<ProjectCreditAssetAttachmentInfo> getListAttachmentInfos() {
		return listAttachmentInfos;
	}
	
	public void setListAttachmentInfos(List<ProjectCreditAssetAttachmentInfo> listAttachmentInfos) {
		this.listAttachmentInfos = listAttachmentInfos;
	}
	
	public String getTextInfo() {
		return textInfo;
	}
	
	public void setTextInfo(String textInfo) {
		this.textInfo = textInfo;
	}
	
}
