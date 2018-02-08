package com.born.fcs.pm.ws.order.projectcreditcondition;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.enums.ReleaseStatusEnum;
import com.born.fcs.pm.ws.order.base.FormOrderBase;

/**
 * 项目授信条件Order
 *
 *
 * @author Ji
 *
 */
public class ProjectCreditConditionOrder extends FormOrderBase {
	
	private static final long serialVersionUID = -8537228314295606368L;
	
	/** 主键 */
	private Long id;
	
	private long confirmId;
	/** 项目编号 */
	private String projectCode;
	/** 资产系统对应资产id */
	private long assetId;
	/**
	 * f_council_summary_project_pledge_asset.id,
	 * f_council_summary_project_guarantor.id
	 */
	private long itemId;
	/** 授信条件文字描述（根据对应抵（质）押、保证生成） */
	private String itemDesc;
	/** CreditConditionTypeEnum 类型：抵押、质押、保证 */
	private String type;
	/** 是否落实 */
	private String isConfirm;
	/** 落实人ID */
	private String confirmManId;
	/** 落实人账号 */
	private String confirmManAccount;
	/** 落实人名称 */
	private String confirmManName;
	/** 落实日期 */
	private String ConfirmDateStr;
	private Date confirmDate;
	/** 合同编号权(有多个用,分开) */
	private String contractNo;
	/** 合同/协议url */
	private String contractAgreementUrl;
	/** 权利凭证(有多个用,分开) */
	private String rightVouche;
	/** 备注 */
	private String remark;
	/** 状态 CreditCheckStatusEnum */
	private String status;
	/** 解保申请状态(默认待解保) ReleaseStatusEnum */
	private String releaseStatus = ReleaseStatusEnum.WAITING.code();
	/** 解保申请的id:f_counter_guarantee_release.id */
	private long releaseId = 0;
	/** 更新时间 */
	private Date rawUpdateTime;
	
	/** 资产附件上传 */
	private List<ProjectCreditAssetAttachmentOrder> attachmentOrders;
	
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
	
	public Long getItemId() {
		return itemId;
	}
	
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	
	public String getItemDesc() {
		return itemDesc;
	}
	
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getIsConfirm() {
		return isConfirm;
	}
	
	public void setIsConfirm(String isConfirm) {
		this.isConfirm = isConfirm;
	}
	
	public String getConfirmManId() {
		return confirmManId;
	}
	
	public void setConfirmManId(String confirmManId) {
		this.confirmManId = confirmManId;
	}
	
	public void setItemId(long itemId) {
		this.itemId = itemId;
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
	
	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
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
	
	public List<ProjectCreditAssetAttachmentOrder> getAttachmentOrders() {
		return attachmentOrders;
	}
	
	public void setAttachmentOrders(List<ProjectCreditAssetAttachmentOrder> attachmentOrders) {
		this.attachmentOrders = attachmentOrders;
	}
	
	public long getAssetId() {
		return assetId;
	}
	
	public void setAssetId(long assetId) {
		this.assetId = assetId;
	}
	
	public String getConfirmDateStr() {
		return ConfirmDateStr;
	}
	
	public void setConfirmDateStr(String confirmDateStr) {
		ConfirmDateStr = confirmDateStr;
	}
	
}
