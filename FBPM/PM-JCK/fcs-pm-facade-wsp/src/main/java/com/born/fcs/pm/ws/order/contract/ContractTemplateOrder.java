package com.born.fcs.pm.ws.order.contract;

import java.util.Date;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 合同模板Order
 * 
 * @author heh
 * 
 */
public class ContractTemplateOrder extends ProcessOrder {
	
	private static final long serialVersionUID = -907102866946209429L;
	
	private Long templateId;
	
	private String name;
	
	private String contractType;
	
	private String busiType;
	
	private String busiTypeName;
	
	private String isMain;
	
	private String creditConditionType;
	
	private String pledgeType;
	
	private String stampPhase;
	
	private String templateFile;
	
	private String templateContent;
	
	private String status;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;

	private String checkStatus;

	private String attachment;

	private String remark;

	private String templateType;

	private String letterType;

	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public String getLetterType() {
		return letterType;
	}

	public void setLetterType(String letterType) {
		this.letterType = letterType;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	public Long getTemplateId() {
		return templateId;
	}
	
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getContractType() {
		return contractType;
	}
	
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	
	public String getBusiType() {
		return busiType;
	}
	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	public String getIsMain() {
		return isMain;
	}
	
	public void setIsMain(String isMain) {
		this.isMain = isMain;
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
	
	public String getStampPhase() {
		return stampPhase;
	}
	
	public void setStampPhase(String stampPhase) {
		this.stampPhase = stampPhase;
	}
	
	public String getTemplateFile() {
		return templateFile;
	}
	
	public void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}
	
	public String getTemplateContent() {
		return templateContent;
	}
	
	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
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
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getBusiTypeName() {
		return busiTypeName;
	}
	
	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ContractTemplateOrder [templateId=");
		builder.append(templateId);
		builder.append(", name=");
		builder.append(name);
		builder.append(", contractType=");
		builder.append(contractType);
		builder.append(", busiType=");
		builder.append(busiType);
		builder.append(", busiTypeName=");
		builder.append(busiTypeName);
		builder.append(", isMain=");
		builder.append(isMain);
		builder.append(", creditConditionType=");
		builder.append(creditConditionType);
		builder.append(", pledgeType=");
		builder.append(pledgeType);
		builder.append(", stampPhase=");
		builder.append(stampPhase);
		builder.append(", templateFile=");
		builder.append(templateFile);
		builder.append(", status=");
		builder.append(status);
		builder.append(", rawAddTime=");
		builder.append(rawAddTime);
		builder.append(", rawUpdateTime=");
		builder.append(rawUpdateTime);
		builder.append("]");
		return builder.toString();
	}
	
}
