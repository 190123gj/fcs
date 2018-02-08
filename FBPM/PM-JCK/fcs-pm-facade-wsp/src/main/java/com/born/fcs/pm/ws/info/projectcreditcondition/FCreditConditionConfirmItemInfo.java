package com.born.fcs.pm.ws.info.projectcreditcondition;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CreditCheckStatusEnum;
import com.born.fcs.pm.ws.enums.CreditConditionTypeEnum;
import com.born.fcs.pm.ws.info.common.SimpleFormProjectInfo;

public class FCreditConditionConfirmItemInfo extends SimpleFormProjectInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5223659859834332502L;
	
	private long id;
	
	private long confirmId;
	
	private String projectCode;
	
	private long assetId;
	
	private long itemId;
	
	private String itemDesc;
	
	private CreditConditionTypeEnum type;
	
	private BooleanEnum isConfirm;
	
	private String confirmManId;
	
	private String confirmManAccount;
	
	private String confirmManName;
	
	private Date confirmDate;
	
	private String contractNo;
	
	private String contractAgreementUrl;
	
	private String rightVouche;
	
	private String remark;
	
	private CreditCheckStatusEnum status;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	private String isPostBack;
	
	List<ProjectCreditAssetAttachmentInfo> listAttachmentInfos;
	
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
	
	public long getConfirmId() {
		return confirmId;
	}
	
	public void setConfirmId(long confirmId) {
		this.confirmId = confirmId;
	}
	
	public String getContractAgreementUrl() {
		return contractAgreementUrl;
	}
	
	public void setContractAgreementUrl(String contractAgreementUrl) {
		this.contractAgreementUrl = contractAgreementUrl;
	}
	
	public String getIsPostBack() {
		return isPostBack;
	}
	
	public void setIsPostBack(String isPostBack) {
		this.isPostBack = isPostBack;
	}
	
	public List<ProjectCreditAssetAttachmentInfo> getListAttachmentInfos() {
		return listAttachmentInfos;
	}
	
	public void setListAttachmentInfos(List<ProjectCreditAssetAttachmentInfo> listAttachmentInfos) {
		this.listAttachmentInfos = listAttachmentInfos;
	}
	
	public long getAssetId() {
		return assetId;
	}
	
	public void setAssetId(long assetId) {
		this.assetId = assetId;
	}
	
}
