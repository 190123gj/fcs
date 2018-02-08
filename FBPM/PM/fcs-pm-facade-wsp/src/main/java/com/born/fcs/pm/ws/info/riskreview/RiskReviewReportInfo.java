package com.born.fcs.pm.ws.info.riskreview;

import java.util.Date;

import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.info.common.SimpleFormProjectInfo;
import com.yjf.common.lang.util.money.Money;

public class RiskReviewReportInfo extends SimpleFormProjectInfo {
	
	private static final long serialVersionUID = 2864958776220410956L;
	
	private long id;
	
	private String reportContent;
	
	private String caseInfo;
	
	private String preserveContent;
	
	private String auditOpinion;
	
	private String synthesizeOpinion;
	
	private Money amount = new Money(0, 0);
	
	private String busiManagerName;
	
	private FormStatusEnum formStatus;
	
	private Date riskReviewAddTime;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getReportContent() {
		return this.reportContent;
	}
	
	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
	}
	
	public Money getAmount() {
		return this.amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public String getBusiManagerName() {
		return this.busiManagerName;
	}
	
	public void setBusiManagerName(String busiManagerName) {
		this.busiManagerName = busiManagerName;
	}
	
	public FormStatusEnum getFormStatus() {
		return this.formStatus;
	}
	
	public void setFormStatus(FormStatusEnum formStatus) {
		this.formStatus = formStatus;
	}
	
	public Date getRiskReviewAddTime() {
		return this.riskReviewAddTime;
	}
	
	public void setRiskReviewAddTime(Date riskReviewAddTime) {
		this.riskReviewAddTime = riskReviewAddTime;
	}
	
	public String getCaseInfo() {
		return this.caseInfo;
	}
	
	public void setCaseInfo(String caseInfo) {
		this.caseInfo = caseInfo;
	}
	
	public String getPreserveContent() {
		return this.preserveContent;
	}
	
	public void setPreserveContent(String preserveContent) {
		this.preserveContent = preserveContent;
	}
	
	public String getAuditOpinion() {
		return this.auditOpinion;
	}
	
	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}
	
	public String getSynthesizeOpinion() {
		return this.synthesizeOpinion;
	}
	
	public void setSynthesizeOpinion(String synthesizeOpinion) {
		this.synthesizeOpinion = synthesizeOpinion;
	}
	
	@Override
	public String toString() {
		return "RiskReviewReportInfo [id=" + id + ", reportContent=" + reportContent + "]";
	}
	
}
