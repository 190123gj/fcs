package com.born.fcs.pm.ws.info.contract;

import java.util.Date;

import com.born.fcs.pm.ws.enums.*;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 项目合同集- 合同
 * 
 * 
 * @author hehao
 * 
 */
public class ProjectContractItemInfo extends BaseToStringInfo {
	private static final long serialVersionUID = -256822645075236481L;
	
	private long id;
	
	private long contractId;
	
	private String contractCode;
	
	private String contractName;
	
	private long pledgeId;
	
	private long templateId;
	
	private ContractTypeEnum contractType;
	
	private String isMain;
	
	private StampPhaseEnum stampPhase;
	
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
	
	private ContractStatusEnum contractStatus;
	
	private int sortOrder;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	private String creditConditionType;
	
	private String creditMeasure;
	
	private String fileName;
	
	private String serverPath;
	
	private String splitFileUrl;
	
	private String invalidReason;
	
	private String templateFileUrl;

	private Money approvedAmount = new Money(0, 0);

	private Date approvedTime;

	private String approvedUrl;

	private String basisOfDecision;

	private LetterTypeEnum letterType;

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

	public String getTemplateFileUrl() {
		return templateFileUrl;
	}
	
	public void setTemplateFileUrl(String templateFileUrl) {
		this.templateFileUrl = templateFileUrl;
	}
	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getContractId() {
		return contractId;
	}
	
	public void setContractId(long contractId) {
		this.contractId = contractId;
	}
	
	public String getContractName() {
		return contractName;
	}
	
	public String getContentMessage() {
		return this.contentMessage;
	}
	
	public void setContentMessage(String contentMessage) {
		this.contentMessage = contentMessage;
	}
	
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	
	public long getPledgeId() {
		return pledgeId;
	}
	
	public void setPledgeId(long pledgeId) {
		this.pledgeId = pledgeId;
	}
	
	public long getTemplateId() {
		return templateId;
	}
	
	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}
	
	public String getIsMain() {
		return isMain;
	}
	
	public void setIsMain(String isMain) {
		this.isMain = isMain;
	}
	
	public ContractTypeEnum getContractType() {
		return contractType;
	}
	
	public String getContractTypeMessage() {
		return contractType == null ? "" : contractType.message();
	}
	
	public void setContractType(ContractTypeEnum contractType) {
		this.contractType = contractType;
	}
	
	public StampPhaseEnum getStampPhase() {
		return stampPhase;
	}
	
	public String getStampPhaseMessage() {
		return stampPhase == null ? "" : stampPhase.message();
	}
	
	public void setStampPhase(StampPhaseEnum stampPhase) {
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
	
	public ContractStatusEnum getContractStatus() {
		return contractStatus;
	}
	
	public String getContractStatusMessage() {
		return contractStatus == null ? "" : contractStatus.message();
	}
	
	public void setContractStatus(ContractStatusEnum contractStatus) {
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
	
	public String getCreditConditionType() {
		return creditConditionType;
	}
	
	public void setCreditConditionType(String creditConditionType) {
		this.creditConditionType = creditConditionType;
	}
	
	public String getCreditMeasure() {
		return creditMeasure;
	}
	
	public void setCreditMeasure(String creditMeasure) {
		this.creditMeasure = creditMeasure;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getServerPath() {
		return serverPath;
	}
	
	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}
	
	public String getSplitFileUrl() {
		return splitFileUrl;
	}
	
	public void setSplitFileUrl(String splitFileUrl) {
		this.splitFileUrl = splitFileUrl;
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
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProjectContractItemInfo [id=");
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
		builder.append(", contractStatus=");
		builder.append(contractStatus);
		builder.append(", sortOrder=");
		builder.append(sortOrder);
		builder.append(", rawAddTime=");
		builder.append(rawAddTime);
		builder.append(", rawUpdateTime=");
		builder.append(rawUpdateTime);
		builder.append(", creditConditionType=");
		builder.append(creditConditionType);
		builder.append(", creditMeasure=");
		builder.append(creditMeasure);
		builder.append(", fileName=");
		builder.append(fileName);
		builder.append(", serverPath=");
		builder.append(serverPath);
		builder.append(", splitFileUrl=");
		builder.append(splitFileUrl);
		builder.append(", invalidReason=");
		builder.append(invalidReason);
		builder.append("]");
		return builder.toString();
	}
}
