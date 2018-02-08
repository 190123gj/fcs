package com.born.fcs.pm.ws.info.setup;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ChargeTypeEnum;
import com.born.fcs.pm.ws.enums.EvaluateCompanyRegionEnum;
import com.born.fcs.pm.ws.info.common.ProjectChannelRelationInfo;

public class FProjectGuaranteeEntrustedInfo extends FProjectInfo {
	
	private static final long serialVersionUID = -4957484975742001172L;
	
	private long id;
	
	private long projectChannelId;
	
	private String projectChannelName;
	
	private String projectChannelCode;
	
	private String projectChannelType;
	
	private long projectSubChannelId;
	
	private String projectSubChannelCode;
	
	private String projectSubChannelType;
	
	private String projectSubChannelName;
	
	/** 单资金渠道 */
	private long capitalChannelId;
	
	private String capitalChannelCode;
	
	private String capitalChannelType;
	
	private String capitalChannelName;
	
	private long capitalSubChannelId;
	
	private String capitalSubChannelCode;
	
	private String capitalSubChannelType;
	
	private String capitalSubChannelName;
	/** 多资金渠道 */
	private List<ProjectChannelRelationInfo> capitalChannels;
	
	private String loanPurpose;
	
	private String repaySource;
	
	private String repayPlan;
	
	private BooleanEnum hasPledge;
	
	private BooleanEnum hasEvaluateCompany;
	
	private double deposit;
	
	private ChargeTypeEnum depositType;
	
	private String depositAccount;
	
	private long evaluateCompanyId;
	
	private String evaluateCompanyName;
	
	private EvaluateCompanyRegionEnum evaluateCompanyRegion;
	
	private long riskManagerId;
	
	private String riskManagerAccount;
	
	private String riskManagerName;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getProjectChannelId() {
		return this.projectChannelId;
	}
	
	public void setProjectChannelId(long projectChannelId) {
		this.projectChannelId = projectChannelId;
	}
	
	public String getProjectChannelName() {
		return this.projectChannelName;
	}
	
	public void setProjectChannelName(String projectChannelName) {
		this.projectChannelName = projectChannelName;
	}
	
	public String getProjectChannelCode() {
		return this.projectChannelCode;
	}
	
	public void setProjectChannelCode(String projectChannelCode) {
		this.projectChannelCode = projectChannelCode;
	}
	
	public long getProjectSubChannelId() {
		return this.projectSubChannelId;
	}
	
	public void setProjectSubChannelId(long projectSubChannelId) {
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
	
	public long getCapitalChannelId() {
		return this.capitalChannelId;
	}
	
	public void setCapitalChannelId(long capitalChannelId) {
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
	
	public long getCapitalSubChannelId() {
		return this.capitalSubChannelId;
	}
	
	public void setCapitalSubChannelId(long capitalSubChannelId) {
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
	
	public List<ProjectChannelRelationInfo> getCapitalChannels() {
		return this.capitalChannels;
	}
	
	public void setCapitalChannels(List<ProjectChannelRelationInfo> capitalChannels) {
		this.capitalChannels = capitalChannels;
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
	
	public BooleanEnum getHasPledge() {
		return this.hasPledge;
	}
	
	public void setHasPledge(BooleanEnum hasPledge) {
		this.hasPledge = hasPledge;
	}
	
	public BooleanEnum getHasEvaluateCompany() {
		return this.hasEvaluateCompany;
	}
	
	public void setHasEvaluateCompany(BooleanEnum hasEvaluateCompany) {
		this.hasEvaluateCompany = hasEvaluateCompany;
	}
	
	public double getDeposit() {
		return this.deposit;
	}
	
	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}
	
	public ChargeTypeEnum getDepositType() {
		return this.depositType;
	}
	
	public void setDepositType(ChargeTypeEnum depositType) {
		this.depositType = depositType;
	}
	
	public String getDepositAccount() {
		return this.depositAccount;
	}
	
	public void setDepositAccount(String depositAccount) {
		this.depositAccount = depositAccount;
	}
	
	public long getEvaluateCompanyId() {
		return this.evaluateCompanyId;
	}
	
	public void setEvaluateCompanyId(long evaluateCompanyId) {
		this.evaluateCompanyId = evaluateCompanyId;
	}
	
	public String getEvaluateCompanyName() {
		return this.evaluateCompanyName;
	}
	
	public void setEvaluateCompanyName(String evaluateCompanyName) {
		this.evaluateCompanyName = evaluateCompanyName;
	}
	
	public EvaluateCompanyRegionEnum getEvaluateCompanyRegion() {
		return this.evaluateCompanyRegion;
	}
	
	public void setEvaluateCompanyRegion(EvaluateCompanyRegionEnum evaluateCompanyRegion) {
		this.evaluateCompanyRegion = evaluateCompanyRegion;
	}
	
	public long getRiskManagerId() {
		return this.riskManagerId;
	}
	
	public void setRiskManagerId(long riskManagerId) {
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
	
	public Date getRawAddTime() {
		return this.rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return this.rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
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
