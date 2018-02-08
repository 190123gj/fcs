package com.born.fcs.pm.ws.info.contract;

import java.util.Date;

import com.born.fcs.pm.ws.enums.*;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 合同模板
 * 
 * 
 * @author hehao
 * 
 */
public class ContractTemplateInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -8416641357448400959L;
	
	private long templateId;
	
	private String name;
	
	private ContractTemplateTypeEnum contractType;
	
	private String busiType;
	
	private String busiTypeName;
	
	private String isMain;
	
	private String creditConditionType;
	
	private String pledgeType;
	
	private StampPhaseEnum stampPhase;
	
	private String templateFile;
	
	private String templateContent;
	
	private String status;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;

	private String attachment;

	private String remark;

	private TemplateTypeEnum templateType;

	private LetterTypeEnum letterType;

	public TemplateTypeEnum getTemplateType() {
		return templateType;
	}

	public void setTemplateType(TemplateTypeEnum templateType) {
		this.templateType = templateType;
	}

	public LetterTypeEnum getLetterType() {
		return letterType;
	}

	public void setLetterType(LetterTypeEnum letterType) {
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

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	public long getTemplateId() {
		return templateId;
	}
	
	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public ContractTemplateTypeEnum getContractType() {
		return contractType;
	}

	public void setContractType(ContractTemplateTypeEnum contractType) {
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
	
	public StampPhaseEnum getStampPhase() {
		return stampPhase;
	}
	
	public void setStampPhase(StampPhaseEnum stampPhase) {
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
		builder.append("ContractTemplateInfo [templateId=");
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
