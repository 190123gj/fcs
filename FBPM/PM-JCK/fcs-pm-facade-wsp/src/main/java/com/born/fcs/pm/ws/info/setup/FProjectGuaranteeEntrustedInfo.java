package com.born.fcs.pm.ws.info.setup;

import java.util.Date;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ChargeTypeEnum;
import com.born.fcs.pm.ws.enums.EvaluateCompanyRegionEnum;

public class FProjectGuaranteeEntrustedInfo extends FProjectInfo {
	
	private static final long serialVersionUID = -4957484975742001172L;
	
	private long id;
	
	private long projectChannelId;
	
	private String projectChannelName;
	
	private long projectSubChannelId;
	
	private String projectSubChannelName;
	
	private long capitalChannelId;
	
	private String capitalChannelName;
	
	private long capitalSubChannelId;
	
	private String capitalSubChannelName;
	
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
	
	public long getCapitalChannelId() {
		return this.capitalChannelId;
	}
	
	public void setCapitalChannelId(long capitalChannelId) {
		this.capitalChannelId = capitalChannelId;
	}
	
	public String getCapitalChannelName() {
		return this.capitalChannelName;
	}
	
	public void setCapitalChannelName(String capitalChannelName) {
		this.capitalChannelName = capitalChannelName;
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
	
	public long getProjectSubChannelId() {
		return this.projectSubChannelId;
	}
	
	public void setProjectSubChannelId(long projectSubChannelId) {
		this.projectSubChannelId = projectSubChannelId;
	}
	
	public String getProjectSubChannelName() {
		return this.projectSubChannelName;
	}
	
	public void setProjectSubChannelName(String projectSubChannelName) {
		this.projectSubChannelName = projectSubChannelName;
	}
	
	public long getCapitalSubChannelId() {
		return this.capitalSubChannelId;
	}
	
	public void setCapitalSubChannelId(long capitalSubChannelId) {
		this.capitalSubChannelId = capitalSubChannelId;
	}
	
	public String getCapitalSubChannelName() {
		return this.capitalSubChannelName;
	}
	
	public void setCapitalSubChannelName(String capitalSubChannelName) {
		this.capitalSubChannelName = capitalSubChannelName;
	}
	
}
