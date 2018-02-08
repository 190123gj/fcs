package com.born.fcs.pm.ws.order.common;

import java.util.Date;

import com.born.fcs.pm.ws.order.base.ProcessOrder;
import com.yjf.common.lang.util.money.Money;

public class ProjectOrder extends ProcessOrder {
	
	private static final long serialVersionUID = -7776434421680064580L;
	
	private long projectId;
	
	private String projectCode;
	
	private String projectName;
	
	private long customerId;
	
	private String customerName;
	
	private String customerType;
	
	private String busiType;
	
	private String busiTypeName;
	
	private String industryCode;
	
	private String industryName;
	
	private String loanPurpose;
	
	private int timeLimit;
	
	private String timeUnit;
	
	private Date startTime;
	
	private Date endTime;
	
	private Money amount = new Money(0, 0);
	
	private Money accumulatedIssueAmount = new Money(0, 0);
	
	private Money loanedAmount = new Money(0, 0);
	
	private Money usedAmount = new Money(0, 0);
	
	private Money repayedAmount = new Money(0, 0);
	
	private String isMaximumAmount;
	
	private String contractNo;
	
	private long spId;
	
	private String spCode;
	
	private long busiManagerId;
	
	private String busiManagerAccount;
	
	private String busiManagerName;
	
	private long busiManagerbId;
	
	private String busiManagerbAccount;
	
	private String busiManagerbName;
	
	private long deptId;
	
	private String deptName;
	
	private String phases;
	
	private String phasesStatus;
	
	private String status;
	
	private String isContinue;
	
	public long getProjectId() {
		return this.projectId;
	}
	
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getProjectName() {
		return this.projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public long getCustomerId() {
		return this.customerId;
	}
	
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return this.customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getCustomerType() {
		return this.customerType;
	}
	
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	
	public String getBusiType() {
		return this.busiType;
	}
	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	public String getBusiTypeName() {
		return this.busiTypeName;
	}
	
	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}
	
	public String getLoanPurpose() {
		return this.loanPurpose;
	}
	
	public void setLoanPurpose(String loanPurpose) {
		this.loanPurpose = loanPurpose;
	}
	
	public int getTimeLimit() {
		return this.timeLimit;
	}
	
	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	public String getTimeUnit() {
		return this.timeUnit;
	}
	
	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}
	
	public Date getStartTime() {
		return this.startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return this.endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public Money getAmount() {
		return this.amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public Money getLoanedAmount() {
		return this.loanedAmount;
	}
	
	public void setLoanedAmount(Money loanedAmount) {
		this.loanedAmount = loanedAmount;
	}
	
	public Money getUsedAmount() {
		return this.usedAmount;
	}
	
	public void setUsedAmount(Money usedAmount) {
		this.usedAmount = usedAmount;
	}
	
	public Money getRepayedAmount() {
		return this.repayedAmount;
	}
	
	public void setRepayedAmount(Money repayedAmount) {
		this.repayedAmount = repayedAmount;
	}
	
	public String getContractNo() {
		return this.contractNo;
	}
	
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	public long getSpId() {
		return this.spId;
	}
	
	public void setSpId(long spId) {
		this.spId = spId;
	}
	
	public String getSpCode() {
		return this.spCode;
	}
	
	public void setSpCode(String spCode) {
		this.spCode = spCode;
	}
	
	public long getBusiManagerId() {
		return this.busiManagerId;
	}
	
	public void setBusiManagerId(long busiManagerId) {
		this.busiManagerId = busiManagerId;
	}
	
	public String getBusiManagerAccount() {
		return this.busiManagerAccount;
	}
	
	public void setBusiManagerAccount(String busiManagerAccount) {
		this.busiManagerAccount = busiManagerAccount;
	}
	
	public String getBusiManagerName() {
		return this.busiManagerName;
	}
	
	public void setBusiManagerName(String busiManagerName) {
		this.busiManagerName = busiManagerName;
	}
	
	public long getBusiManagerbId() {
		return this.busiManagerbId;
	}
	
	public void setBusiManagerbId(long busiManagerbId) {
		this.busiManagerbId = busiManagerbId;
	}
	
	public String getBusiManagerbAccount() {
		return this.busiManagerbAccount;
	}
	
	public void setBusiManagerbAccount(String busiManagerbAccount) {
		this.busiManagerbAccount = busiManagerbAccount;
	}
	
	public String getBusiManagerbName() {
		return this.busiManagerbName;
	}
	
	public void setBusiManagerbName(String busiManagerbName) {
		this.busiManagerbName = busiManagerbName;
	}
	
	public long getDeptId() {
		return this.deptId;
	}
	
	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}
	
	public String getDeptName() {
		return this.deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public String getPhases() {
		return this.phases;
	}
	
	public void setPhases(String phases) {
		this.phases = phases;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getIsMaximumAmount() {
		return this.isMaximumAmount;
	}
	
	public void setIsMaximumAmount(String isMaximumAmount) {
		this.isMaximumAmount = isMaximumAmount;
	}
	
	public String getIndustryCode() {
		return this.industryCode;
	}
	
	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}
	
	public String getIndustryName() {
		return this.industryName;
	}
	
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	
	public String getPhasesStatus() {
		return this.phasesStatus;
	}
	
	public void setPhasesStatus(String phasesStatus) {
		this.phasesStatus = phasesStatus;
	}
	
	public String getIsContinue() {
		return isContinue;
	}
	
	public void setIsContinue(String isContinue) {
		this.isContinue = isContinue;
	}

	public Money getAccumulatedIssueAmount() {
		return accumulatedIssueAmount;
	}

	public void setAccumulatedIssueAmount(Money accumulatedIssueAmount) {
		this.accumulatedIssueAmount = accumulatedIssueAmount;
	}
	
}
