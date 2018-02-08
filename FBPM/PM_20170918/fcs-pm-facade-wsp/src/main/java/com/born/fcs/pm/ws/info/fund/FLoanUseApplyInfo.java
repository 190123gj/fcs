package com.born.fcs.pm.ws.info.fund;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.LoanUseApplyTypeEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 放用款申请
 *
 * @author wuzj
 */
public class FLoanUseApplyInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 2668038607136201660L;
	
	/** 费用收取情况 */
	private List<FLoanUseApplyFeeInfo> feeList;
	
	/**
	 * 回执
	 */
	private List<FLoanUseApplyReceiptInfo> receipt;
	
	/** 申请ID */
	private long applyId;
	/** 收费通知ID */
	private long notificationId;
	/** 表单ID */
	private long formId;
	/** 项目编号ID */
	private String projectCode;
	/** 放用款类型 */
	private LoanUseApplyTypeEnum applyType;
	/** 本次申请金额 */
	private Money amount = new Money(0, 0);
	/** 授信金额 */
	private Money originalAmount = new Money(0, 0);
	/** 已放款金额 */
	private Money loanedAmount = new Money(0, 0);
	/** 已用款金额 */
	private Money usedAmount = new Money(0, 0);
	/** 已解保金额 */
	private Money releasedAmount = new Money(0, 0);
	/** 已经发行金额 */
	private Money issueAmount = new Money(0, 0);
	/** 申请中放款金额 */
	private Money applyingLoanAmount = new Money(0, 0);
	/** 申请中用款金额 */
	private Money applyingUseAmount = new Money(0, 0);
	/** 是否最高授信额 */
	private BooleanEnum isMaximumAmount;
	/** 计划放款时间 */
	private Date expectLoanDate;
	/** 结息周期 */
	private String interestSettlementCycle;
	/** 备注 */
	private String remark;
	/** 客户保证金 */
	private Money customerDepositCharge = new Money(0, 0);
	/** 已退换客户保证金 */
	private Money customerDepositRefund = new Money(0, 0);
	/** 保证金 */
	private Money cashDepositAmount = new Money(0, 0);
	/** 划出 */
	private String cashDepositBank;
	/** 存入期限 */
	private int cashDepositTimeLimit;
	/** 期限单位 */
	private TimeUnitEnum cashDepositTimeUnit;
	/** 保证金比例 */
	private double cashDepositRatio;
	/** 保证金划付截止时间 */
	private Date cashDepositEndTime;
	/** 客户经理意见 */
	private String busiManagerStatement;
	/** 收款用户名 */
	private String receiptName;
	/** 收款银行 */
	private String receiptBank;
	/** 收款账号 */
	private String receiptAccount;
	
	private Date rawAddTime;
	private Date rawUpdateTime;
	
	public List<FLoanUseApplyFeeInfo> getFeeList() {
		return this.feeList;
	}
	
	public void setFeeList(List<FLoanUseApplyFeeInfo> feeList) {
		this.feeList = feeList;
	}
	
	public List<FLoanUseApplyReceiptInfo> getReceipt() {
		return this.receipt;
	}
	
	public void setReceipt(List<FLoanUseApplyReceiptInfo> receipt) {
		this.receipt = receipt;
	}
	
	public long getApplyId() {
		return this.applyId;
	}
	
	public void setApplyId(long applyId) {
		this.applyId = applyId;
	}
	
	public long getNotificationId() {
		return this.notificationId;
	}
	
	public void setNotificationId(long notificationId) {
		this.notificationId = notificationId;
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public LoanUseApplyTypeEnum getApplyType() {
		return this.applyType;
	}
	
	public void setApplyType(LoanUseApplyTypeEnum applyType) {
		this.applyType = applyType;
	}
	
	public Money getAmount() {
		return this.amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public Money getOriginalAmount() {
		return this.originalAmount;
	}
	
	public void setOriginalAmount(Money originalAmount) {
		this.originalAmount = originalAmount;
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
	
	public Money getReleasedAmount() {
		return this.releasedAmount;
	}
	
	public void setReleasedAmount(Money releasedAmount) {
		this.releasedAmount = releasedAmount;
	}
	
	public Money getIssueAmount() {
		return this.issueAmount;
	}
	
	public void setIssueAmount(Money issueAmount) {
		this.issueAmount = issueAmount;
	}
	
	public Money getApplyingLoanAmount() {
		return this.applyingLoanAmount;
	}
	
	public void setApplyingLoanAmount(Money applyingLoanAmount) {
		this.applyingLoanAmount = applyingLoanAmount;
	}
	
	public Money getApplyingUseAmount() {
		return this.applyingUseAmount;
	}
	
	public void setApplyingUseAmount(Money applyingUseAmount) {
		this.applyingUseAmount = applyingUseAmount;
	}
	
	public BooleanEnum getIsMaximumAmount() {
		return this.isMaximumAmount;
	}
	
	public void setIsMaximumAmount(BooleanEnum isMaximumAmount) {
		this.isMaximumAmount = isMaximumAmount;
	}
	
	public Date getExpectLoanDate() {
		return this.expectLoanDate;
	}
	
	public void setExpectLoanDate(Date expectLoanDate) {
		this.expectLoanDate = expectLoanDate;
	}
	
	public String getInterestSettlementCycle() {
		return this.interestSettlementCycle;
	}
	
	public void setInterestSettlementCycle(String interestSettlementCycle) {
		this.interestSettlementCycle = interestSettlementCycle;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Money getCashDepositAmount() {
		return this.cashDepositAmount;
	}
	
	public void setCashDepositAmount(Money cashDepositAmount) {
		this.cashDepositAmount = cashDepositAmount;
	}
	
	public String getCashDepositBank() {
		return this.cashDepositBank;
	}
	
	public void setCashDepositBank(String cashDepositBank) {
		this.cashDepositBank = cashDepositBank;
	}
	
	public int getCashDepositTimeLimit() {
		return this.cashDepositTimeLimit;
	}
	
	public void setCashDepositTimeLimit(int cashDepositTimeLimit) {
		this.cashDepositTimeLimit = cashDepositTimeLimit;
	}
	
	public TimeUnitEnum getCashDepositTimeUnit() {
		return this.cashDepositTimeUnit;
	}
	
	public void setCashDepositTimeUnit(TimeUnitEnum cashDepositTimeUnit) {
		this.cashDepositTimeUnit = cashDepositTimeUnit;
	}
	
	public double getCashDepositRatio() {
		return this.cashDepositRatio;
	}
	
	public void setCashDepositRatio(double cashDepositRatio) {
		this.cashDepositRatio = cashDepositRatio;
	}
	
	public Date getCashDepositEndTime() {
		return this.cashDepositEndTime;
	}
	
	public void setCashDepositEndTime(Date cashDepositEndTime) {
		this.cashDepositEndTime = cashDepositEndTime;
	}
	
	public String getBusiManagerStatement() {
		return this.busiManagerStatement;
	}
	
	public void setBusiManagerStatement(String busiManagerStatement) {
		this.busiManagerStatement = busiManagerStatement;
	}
	
	public String getReceiptName() {
		return this.receiptName;
	}
	
	public void setReceiptName(String receiptName) {
		this.receiptName = receiptName;
	}
	
	public String getReceiptBank() {
		return this.receiptBank;
	}
	
	public void setReceiptBank(String receiptBank) {
		this.receiptBank = receiptBank;
	}
	
	public String getReceiptAccount() {
		return this.receiptAccount;
	}
	
	public void setReceiptAccount(String receiptAccount) {
		this.receiptAccount = receiptAccount;
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
	
	public Money getCustomerDepositCharge() {
		return this.customerDepositCharge;
	}
	
	public void setCustomerDepositCharge(Money customerDepositCharge) {
		this.customerDepositCharge = customerDepositCharge;
	}
	
	public Money getCustomerDepositRefund() {
		return this.customerDepositRefund;
	}
	
	public void setCustomerDepositRefund(Money customerDepositRefund) {
		this.customerDepositRefund = customerDepositRefund;
	}
	
}
