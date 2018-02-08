package com.born.fcs.pm.ws.order.contract;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.enums.BasisOfDecisionEnum;
import com.born.fcs.pm.ws.enums.LetterTypeEnum;
import com.born.fcs.pm.ws.info.contract.ProjectContractExtraValueInfo;
import com.born.fcs.pm.ws.order.base.ProcessOrder;
import com.born.fcs.pm.ws.order.common.ProjectChannelRelationOrder;
import com.yjf.common.lang.util.money.Money;

public class ProjectContractItemOrder extends ProcessOrder {
	private static final long serialVersionUID = -6754320445522118268L;
	
	private Long id;
	
	private Long contractId;

	/**本系统合同编号，合同申请单页面显示合同ID*/
	private String contractCode;
	/**手输入的合同编号，合同申请单显示合同编号*/
	private String contractCode2;
	
	private String contractName;
	
	private Long pledgeId;
	
	private Long templateId;
	
	private String contractType;
	
	private String isMain;
	
	private String stampPhase;
	
	private String cnt;
	
	private String fileUrl;
	
	private String content;
	
	private String contentMessage;
	
	private String remark;
	
	private String auditInfo;
	
	private Date signedTime;
	
	private String signPersonA;
	
	private String signPersonB;
	
	private long signPersonAId;
	
	private String signPersonBId;
	
	private String contractScanUrl;
	
	private int sortOrder;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	private String contractStatus;
	
	private String creditMeasure;
	
	private String invalidReason;
	
	private List<ProjectContractExtraValueInfo> projectContractExtraValue;
	
	// 添加字段，表明是审核员还是非审核员,审核员的修改记录需要存库
	private boolean isChecker = false;
	
	/*** 审核时的图片上传 */
	private String checkFileUrl;
	
	/*** 审核时的备注信息 */
	private String checkRemark;

	private Money approvedAmount = new Money(0, 0);

	private Date approvedTime;

	private String approvedUrl;

	private String basisOfDecision;

	private LetterTypeEnum letterType;

	private String creditConditionType;

	private String pledgeType;

	private String courtRulingUrl;

	private BasisOfDecisionEnum decisionType;

	private Date courtRulingTime;

	private Money contractAmount = new Money(0, 0);

	private String ids;

	private String formChange;

	private String riskCouncilSummary;

	private String projectApproval;

	private String projectSetUp;

	private String referAttachment;

	private String returnRemark;

	private String isNeedStamp;

	private String isContract;

	public String getIsContract() {
		return isContract;
	}

	public void setIsContract(String isContract) {
		this.isContract = isContract;
	}

	public String getIsNeedStamp() {
		return isNeedStamp;
	}

	public void setIsNeedStamp(String isNeedStamp) {
		this.isNeedStamp = isNeedStamp;
	}

	/** 资金渠道*/
	private List<ProjectContractChannelOrder> channels;


	public String getReturnRemark() {
		return returnRemark;
	}

	public void setReturnRemark(String returnRemark) {
		this.returnRemark = returnRemark;
	}

	public List<ProjectContractChannelOrder> getChannels() {
		return channels;
	}

	public void setChannels(List<ProjectContractChannelOrder> channels) {
		this.channels = channels;
	}

	public String getReferAttachment() {
		return referAttachment;
	}

	public void setReferAttachment(String referAttachment) {
		this.referAttachment = referAttachment;
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

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Money getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(Money contractAmount) {
		this.contractAmount = contractAmount;
	}

	public Date getCourtRulingTime() {
		return courtRulingTime;
	}

	public void setCourtRulingTime(Date courtRulingTime) {
		this.courtRulingTime = courtRulingTime;
	}

	public BasisOfDecisionEnum getDecisionType() {
		return decisionType;
	}

	public void setDecisionType(BasisOfDecisionEnum decisionType) {
		this.decisionType = decisionType;
	}

	public String getCourtRulingUrl() {
		return courtRulingUrl;
	}

	public void setCourtRulingUrl(String courtRulingUrl) {
		this.courtRulingUrl = courtRulingUrl;
	}

	public String getCreditConditionType() {
		return creditConditionType;
	}

	public void setCreditConditionType(String creditConditionType) {
		this.creditConditionType = creditConditionType;
	}

	public String getPledgeType() {
		return pledgeType;
	}

	public void setPledgeType(String pledgeType) {
		this.pledgeType = pledgeType;
	}

	public LetterTypeEnum getLetterType() {
		return letterType;
	}

	public void setLetterType(LetterTypeEnum letterType) {
		this.letterType = letterType;
	}

	public String getBasisOfDecision() {
		return basisOfDecision;
	}

	public void setBasisOfDecision(String basisOfDecision) {
		this.basisOfDecision = basisOfDecision;
	}

	public Money getApprovedAmount() {
		return approvedAmount;
	}

	public void setApprovedAmount(Money approvedAmount) {
		this.approvedAmount = approvedAmount;
	}

	public Date getApprovedTime() {
		return approvedTime;
	}

	public void setApprovedTime(Date approvedTime) {
		this.approvedTime = approvedTime;
	}

	public String getApprovedUrl() {
		return approvedUrl;
	}

	public void setApprovedUrl(String approvedUrl) {
		this.approvedUrl = approvedUrl;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	public String getContractName() {
		return contractName;
	}
	
	public String getCheckFileUrl() {
		return this.checkFileUrl;
	}
	
	public void setCheckFileUrl(String checkFileUrl) {
		this.checkFileUrl = checkFileUrl;
	}
	
	public String getCheckRemark() {
		return this.checkRemark;
	}
	
	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}
	
	public boolean isChecker() {
		return this.isChecker;
	}
	
	public void setChecker(boolean isChecker) {
		this.isChecker = isChecker;
	}
	
	public List<ProjectContractExtraValueInfo> getProjectContractExtraValue() {
		return this.projectContractExtraValue;
	}
	
	public void setProjectContractExtraValue(	List<ProjectContractExtraValueInfo> projectContractExtraValue) {
		this.projectContractExtraValue = projectContractExtraValue;
	}
	
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	
	public String getContentMessage() {
		return this.contentMessage;
	}
	
	public void setContentMessage(String contentMessage) {
		this.contentMessage = contentMessage;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getContractId() {
		return this.contractId;
	}
	
	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}
	
	public Long getPledgeId() {
		return this.pledgeId;
	}
	
	public void setPledgeId(Long pledgeId) {
		this.pledgeId = pledgeId;
	}
	
	public Long getTemplateId() {
		return this.templateId;
	}
	
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	
	public String getContractType() {
		return contractType;
	}
	
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	
	public String getIsMain() {
		return isMain;
	}
	
	public void setIsMain(String isMain) {
		this.isMain = isMain;
	}
	
	public String getStampPhase() {
		return stampPhase;
	}
	
	public void setStampPhase(String stampPhase) {
		this.stampPhase = stampPhase;
	}

	public String getCnt() {
		return cnt;
	}

	public void setCnt(String cnt) {
		this.cnt = cnt;
	}

	public String getFileUrl() {
		return fileUrl;
	}
	
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getAuditInfo() {
		return auditInfo;
	}
	
	public void setAuditInfo(String auditInfo) {
		this.auditInfo = auditInfo;
	}
	
	public int getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
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
	
	public String getContractCode() {
		return contractCode;
	}
	
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	
	public String getContractStatus() {
		return contractStatus;
	}
	
	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}
	
	public Date getSignedTime() {
		return signedTime;
	}
	
	public void setSignedTime(Date signedTime) {
		this.signedTime = signedTime;
	}
	
	public String getSignPersonA() {
		return signPersonA;
	}
	
	public void setSignPersonA(String signPersonA) {
		this.signPersonA = signPersonA;
	}
	
	public String getSignPersonB() {
		return signPersonB;
	}
	
	public void setSignPersonB(String signPersonB) {
		this.signPersonB = signPersonB;
	}
	
	public String getContractScanUrl() {
		return contractScanUrl;
	}
	
	public void setContractScanUrl(String contractScanUrl) {
		this.contractScanUrl = contractScanUrl;
	}
	
	public String getCreditMeasure() {
		return creditMeasure;
	}
	
	public void setCreditMeasure(String creditMeasure) {
		this.creditMeasure = creditMeasure;
	}
	
	public long getSignPersonAId() {
		return signPersonAId;
	}
	
	public void setSignPersonAId(long signPersonAId) {
		this.signPersonAId = signPersonAId;
	}

	public String getSignPersonBId() {
		return signPersonBId;
	}

	public void setSignPersonBId(String signPersonBId) {
		this.signPersonBId = signPersonBId;
	}

	public String getInvalidReason() {
		return invalidReason;
	}
	
	public void setInvalidReason(String invalidReason) {
		this.invalidReason = invalidReason;
	}

	public String getProjectSetUp() {
		return projectSetUp;
	}

	public void setProjectSetUp(String projectSetUp) {
		this.projectSetUp = projectSetUp;
	}

	public String getContractCode2() {
		return contractCode2;
	}

	public void setContractCode2(String contractCode2) {
		this.contractCode2 = contractCode2;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProjectContractItemOrder [id=");
		builder.append(id);
		builder.append(", contractId=");
		builder.append(contractId);
		builder.append(", contractCode=");
		builder.append(contractCode);
		builder.append(", contractName=");
		builder.append(contractName);
		builder.append(", pledgeId=");
		builder.append(pledgeId);
		builder.append(", templateId=");
		builder.append(templateId);
		builder.append(", contractType=");
		builder.append(contractType);
		builder.append(", isMain=");
		builder.append(isMain);
		builder.append(", stampPhase=");
		builder.append(stampPhase);
		builder.append(", cnt=");
		builder.append(cnt);
		builder.append(", fileUrl=");
		builder.append(fileUrl);
		builder.append(", remark=");
		builder.append(remark);
		builder.append(", auditInfo=");
		builder.append(auditInfo);
		builder.append(", signedTime=");
		builder.append(signedTime);
		builder.append(", signPersonA=");
		builder.append(signPersonA);
		builder.append(", signPersonB=");
		builder.append(signPersonB);
		builder.append(", signPersonAId=");
		builder.append(signPersonAId);
		builder.append(", signPersonBId=");
		builder.append(signPersonBId);
		builder.append(", contractScanUrl=");
		builder.append(contractScanUrl);
		builder.append(", sortOrder=");
		builder.append(sortOrder);
		builder.append(", rawAddTime=");
		builder.append(rawAddTime);
		builder.append(", rawUpdateTime=");
		builder.append(rawUpdateTime);
		builder.append(", contractStatus=");
		builder.append(contractStatus);
		builder.append(", creditMeasure=");
		builder.append(creditMeasure);
		builder.append(", invalidReason=");
		builder.append(invalidReason);
		builder.append(", projectContractExtraValue=");
		builder.append(projectContractExtraValue);
		builder.append("]");
		return builder.toString();
	}
}
