package com.born.fcs.pm.ws.order.setup;

import java.util.List;

import com.born.fcs.pm.ws.order.common.ProjectChannelRelationOrder;

/**
 * 项目立项 - 担保委贷项目详情Order
 *
 * @author wuzj
 *
 */
public class FProjectGuaranteeEntrustedOrder extends FProjectOrder {
	
	private static final long serialVersionUID = 1730932950503175407L;
	
	private Long id;
	
	/** 项目渠道 */
	private Long projectChannelId;
	
	private String projectChannelCode;
	
	private String projectChannelType;
	
	private String projectChannelName;
	
	private Long projectSubChannelId;
	
	private String projectSubChannelCode;
	
	private String projectSubChannelType;
	
	private String projectSubChannelName;
	
	/** 单资金渠道 */
	private Long capitalChannelId;
	
	private String capitalChannelCode;
	
	private String capitalChannelType;
	
	private String capitalChannelName;
	
	private Long capitalSubChannelId;
	
	private String capitalSubChannelCode;
	
	private String capitalSubChannelType;
	
	private String capitalSubChannelName;
	
	/** 多资金渠道 */
	private List<ProjectChannelRelationOrder> capitalChannelOrder;
	
	private String loanPurpose;
	
	private String repaySource;
	
	private String repayPlan;
	
	private String hasPledge;
	
	private String hasEvaluateCompany;
	
	private Double deposit;
	
	private String depositType;
	
	private String depositAccount;
	
	private Long evaluateCompanyId;
	
	private String evaluateCompanyName;
	
	private String evaluateCompanyRegion;
	
	private Long riskManagerId;
	
	private String riskManagerAccount;
	
	private String riskManagerName;
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getProjectChannelId() {
		return this.projectChannelId;
	}
	
	public void setProjectChannelId(Long projectChannelId) {
		this.projectChannelId = projectChannelId;
	}
	
	public String getProjectChannelCode() {
		return this.projectChannelCode;
	}
	
	public void setProjectChannelCode(String projectChannelCode) {
		this.projectChannelCode = projectChannelCode;
	}
	
	public String getProjectChannelName() {
		return this.projectChannelName;
	}
	
	public void setProjectChannelName(String projectChannelName) {
		this.projectChannelName = projectChannelName;
	}
	
	public Long getProjectSubChannelId() {
		return this.projectSubChannelId;
	}
	
	public void setProjectSubChannelId(Long projectSubChannelId) {
		this.projectSubChannelId = projectSubChannelId;
	}
	
	public String getProjectSubChannelCode() {
		return this.projectSubChannelCode;
	}
	
	public void setProjectSubChannelCode(String projectSubChannelCode) {
		this.projectSubChannelCode = projectSubChannelCode;
	}
	
	public String getProjectSubChannelName() {
		return this.projectSubChannelName;
	}
	
	public void setProjectSubChannelName(String projectSubChannelName) {
		this.projectSubChannelName = projectSubChannelName;
	}
	
	public Long getCapitalChannelId() {
		return this.capitalChannelId;
	}
	
	public void setCapitalChannelId(Long capitalChannelId) {
		this.capitalChannelId = capitalChannelId;
	}
	
	public String getCapitalChannelCode() {
		return this.capitalChannelCode;
	}
	
	public void setCapitalChannelCode(String capitalChannelCode) {
		this.capitalChannelCode = capitalChannelCode;
	}
	
	public String getCapitalChannelName() {
		return this.capitalChannelName;
	}
	
	public void setCapitalChannelName(String capitalChannelName) {
		this.capitalChannelName = capitalChannelName;
	}
	
	public Long getCapitalSubChannelId() {
		return this.capitalSubChannelId;
	}
	
	public void setCapitalSubChannelId(Long capitalSubChannelId) {
		this.capitalSubChannelId = capitalSubChannelId;
	}
	
	public String getCapitalSubChannelCode() {
		return this.capitalSubChannelCode;
	}
	
	public void setCapitalSubChannelCode(String capitalSubChannelCode) {
		this.capitalSubChannelCode = capitalSubChannelCode;
	}
	
	public String getCapitalSubChannelName() {
		return this.capitalSubChannelName;
	}
	
	public void setCapitalSubChannelName(String capitalSubChannelName) {
		this.capitalSubChannelName = capitalSubChannelName;
	}
	
	public List<ProjectChannelRelationOrder> getCapitalChannelOrder() {
		return this.capitalChannelOrder;
	}
	
	public void setCapitalChannelOrder(List<ProjectChannelRelationOrder> capitalChannelOrder) {
		this.capitalChannelOrder = capitalChannelOrder;
	}
	
	public String getLoanPurpose() {
		return this.loanPurpose;
	}
	
	public void setLoanPurpose(String loanPurpose) {
		this.loanPurpose = loanPurpose;
	}
	
	public String getRepaySource() {
		return this.repaySource;
	}
	
	public void setRepaySource(String repaySource) {
		this.repaySource = repaySource;
	}
	
	public String getRepayPlan() {
		return this.repayPlan;
	}
	
	public void setRepayPlan(String repayPlan) {
		this.repayPlan = repayPlan;
	}
	
	public String getHasPledge() {
		return this.hasPledge;
	}
	
	public void setHasPledge(String hasPledge) {
		this.hasPledge = hasPledge;
	}
	
	public String getHasEvaluateCompany() {
		return this.hasEvaluateCompany;
	}
	
	public void setHasEvaluateCompany(String hasEvaluateCompany) {
		this.hasEvaluateCompany = hasEvaluateCompany;
	}
	
	public Double getDeposit() {
		return this.deposit;
	}
	
	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}
	
	public String getDepositType() {
		return this.depositType;
	}
	
	public void setDepositType(String depositType) {
		this.depositType = depositType;
	}
	
	public String getDepositAccount() {
		return this.depositAccount;
	}
	
	public void setDepositAccount(String depositAccount) {
		this.depositAccount = depositAccount;
	}
	
	public Long getEvaluateCompanyId() {
		return this.evaluateCompanyId;
	}
	
	public void setEvaluateCompanyId(Long evaluateCompanyId) {
		this.evaluateCompanyId = evaluateCompanyId;
	}
	
	public String getEvaluateCompanyName() {
		return this.evaluateCompanyName;
	}
	
	public void setEvaluateCompanyName(String evaluateCompanyName) {
		this.evaluateCompanyName = evaluateCompanyName;
	}
	
	public String getEvaluateCompanyRegion() {
		return this.evaluateCompanyRegion;
	}
	
	public void setEvaluateCompanyRegion(String evaluateCompanyRegion) {
		this.evaluateCompanyRegion = evaluateCompanyRegion;
	}
	
	public Long getRiskManagerId() {
		return this.riskManagerId;
	}
	
	public void setRiskManagerId(Long riskManagerId) {
		this.riskManagerId = riskManagerId;
	}
	
	public String getRiskManagerAccount() {
		return this.riskManagerAccount;
	}
	
	public void setRiskManagerAccount(String riskManagerAccount) {
		this.riskManagerAccount = riskManagerAccount;
	}
	
	public String getRiskManagerName() {
		return this.riskManagerName;
	}
	
	public void setRiskManagerName(String riskManagerName) {
		this.riskManagerName = riskManagerName;
	}
	
	public String getProjectChannelType() {
		return this.projectChannelType;
	}
	
	public void setProjectChannelType(String projectChannelType) {
		this.projectChannelType = projectChannelType;
	}
	
	public String getProjectSubChannelType() {
		return this.projectSubChannelType;
	}
	
	public void setProjectSubChannelType(String projectSubChannelType) {
		this.projectSubChannelType = projectSubChannelType;
	}
	
	public String getCapitalChannelType() {
		return this.capitalChannelType;
	}
	
	public void setCapitalChannelType(String capitalChannelType) {
		this.capitalChannelType = capitalChannelType;
	}
	
	public String getCapitalSubChannelType() {
		return this.capitalSubChannelType;
	}
	
	public void setCapitalSubChannelType(String capitalSubChannelType) {
		this.capitalSubChannelType = capitalSubChannelType;
	}
	
}
