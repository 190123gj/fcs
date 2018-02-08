package com.born.fcs.pm.ws.order.projectcreditcondition;

import java.util.Date;

import com.born.fcs.pm.ws.order.base.FcsQueryPageBase;

/**
 * 项目授信条件查询Order
 * @author Ji
 */
public class ProjectCreditConditionQueryOrder extends FcsQueryPageBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1813523263089672680L;
	
	private Long id;
	/** 资产系统对应资产ID */
	private long assetId;
	private String projectCode;
	
	private String itemDesc;
	
	private String isConfirm;
	
	private String confirmManName;
	
	private Date confirmDate;
	
	private String contractNo;
	
	private String contractAgreementUrl;
	
	private String rightVouche;
	
	private String remark;
	
	private String status;
	
	private String releaseStatus;
	
	private long releaseId;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getItemDesc() {
		return itemDesc;
	}
	
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	
	public String getIsConfirm() {
		return isConfirm;
	}
	
	public void setIsConfirm(String isConfirm) {
		this.isConfirm = isConfirm;
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
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getReleaseStatus() {
		return releaseStatus;
	}
	
	public void setReleaseStatus(String releaseStatus) {
		this.releaseStatus = releaseStatus;
	}
	
	public long getReleaseId() {
		return releaseId;
	}
	
	public void setReleaseId(long releaseId) {
		this.releaseId = releaseId;
	}
	
	public String getContractAgreementUrl() {
		return contractAgreementUrl;
	}
	
	public void setContractAgreementUrl(String contractAgreementUrl) {
		this.contractAgreementUrl = contractAgreementUrl;
	}
	
	public long getAssetId() {
		return assetId;
	}
	
	public void setAssetId(long assetId) {
		this.assetId = assetId;
	}
	
}
