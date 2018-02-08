package com.born.fcs.pm.ws.order.fund;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.order.base.FormOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 放用款申请
 *
 * @author wuzj
 */
public class FLoanUseApplyOrder extends FormOrderBase {
	
	private static final long serialVersionUID = -2990406323839230219L;
	
	private List<FLoanUseApplyFeeOrder> feeOrder;
	
	/** 申请ID */
	private Long applyId;
	/** 收费通知ID */
	private Long notificationId;
	/** 项目编号ID */
	private String projectCode;
	/** 放用款类型 */
	private String applyType;
	/** 本次金额 */
	private Money amount;
	/** 计划放款时间 */
	private Date expectLoanDate;
	/** 结息周期 */
	private String interestSettlementCycle;
	/** 备注 */
	private String remark;
	/** 保证金 */
	private Money cashDepositAmount;
	/** 划出 */
	private String cashDepositBank;
	/** 存入期限 */
	private Integer cashDepositTimeLimit;
	/** 期限单位 */
	private String cashDepositTimeUnit;
	/** 保证金比例 */
	private Double cashDepositRatio;
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
	
	@Override
	public void check() {
		validateHasText(projectCode, "项目编号");
	}
	
	public List<FLoanUseApplyFeeOrder> getFeeOrder() {
		return this.feeOrder;
	}
	
	public void setFeeOrder(List<FLoanUseApplyFeeOrder> feeOrder) {
		this.feeOrder = feeOrder;
	}
	
	public Long getApplyId() {
		return this.applyId;
	}
	
	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}
	
	public Long getNotificationId() {
		return this.notificationId;
	}
	
	public void setNotificationId(Long notificationId) {
		this.notificationId = notificationId;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getApplyType() {
		return this.applyType;
	}
	
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}
	
	public Money getAmount() {
		return this.amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
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
	
	public Integer getCashDepositTimeLimit() {
		return this.cashDepositTimeLimit;
	}
	
	public void setCashDepositTimeLimit(Integer cashDepositTimeLimit) {
		this.cashDepositTimeLimit = cashDepositTimeLimit;
	}
	
	public String getCashDepositTimeUnit() {
		return this.cashDepositTimeUnit;
	}
	
	public void setCashDepositTimeUnit(String cashDepositTimeUnit) {
		this.cashDepositTimeUnit = cashDepositTimeUnit;
	}
	
	public Double getCashDepositRatio() {
		return this.cashDepositRatio;
	}
	
	public void setCashDepositRatio(Double cashDepositRatio) {
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
	
}
