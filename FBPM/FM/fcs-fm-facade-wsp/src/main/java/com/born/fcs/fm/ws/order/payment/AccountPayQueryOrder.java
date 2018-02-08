package com.born.fcs.fm.ws.order.payment;

import java.util.List;

import com.born.fcs.fm.ws.enums.AccountStatusEnum;
import com.born.fcs.pm.ws.base.QueryPermissionPageBase;
import com.yjf.common.lang.util.money.Money;

public class AccountPayQueryOrder extends QueryPermissionPageBase {
	
	private static final long serialVersionUID = 3341703956568452529L;
	
	// 单据编号
	private String billNo;
	
	private String applyTimeStart;
	
	private String applyTimeEnd;
	// 金额 起止
	private Money amountStart = Money.zero();
	
	private Money amountEnd = Money.zero();
	
	private List<String> expenseTypes;
	
	private String direction;
	
	private AccountStatusEnum accountStatus;
	
	//报销部门
	private String expenseDeptId;
	private String DeptName;
	//经办人 form.userid
	private String agentId;
	private String agent;
	//过账时间 起止
	private String voucherSyncFinishTimeStart;
	private String voucherSyncFinishTimeEnd;
	
	//收款人
	private String payee;
	// 银行账户
	private String bankAccount;
	// 事由
	private String reason;
	
	//凭证号
	private String voucherNo;
	
	public String getBillNo() {
		return this.billNo;
	}
	
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	
	public String getExpenseDeptId() {
		return this.expenseDeptId;
	}
	
	public void setExpenseDeptId(String expenseDeptId) {
		this.expenseDeptId = expenseDeptId;
	}
	
	public String getAgentId() {
		return this.agentId;
	}
	
	public String getDeptName() {
		return this.DeptName;
	}
	
	public void setDeptName(String deptName) {
		DeptName = deptName;
	}
	
	public String getAgent() {
		return this.agent;
	}
	
	public void setAgent(String agent) {
		this.agent = agent;
	}
	
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	
	public String getVoucherSyncFinishTimeStart() {
		return this.voucherSyncFinishTimeStart;
	}
	
	public void setVoucherSyncFinishTimeStart(String voucherSyncFinishTimeStart) {
		this.voucherSyncFinishTimeStart = voucherSyncFinishTimeStart;
	}
	
	public String getVoucherSyncFinishTimeEnd() {
		return this.voucherSyncFinishTimeEnd;
	}
	
	public void setVoucherSyncFinishTimeEnd(String voucherSyncFinishTimeEnd) {
		this.voucherSyncFinishTimeEnd = voucherSyncFinishTimeEnd;
	}
	
	public String getPayee() {
		return this.payee;
	}
	
	public void setPayee(String payee) {
		this.payee = payee;
	}
	
	public String getBankAccount() {
		return this.bankAccount;
	}
	
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	
	public String getReason() {
		return this.reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getVoucherNo() {
		return this.voucherNo;
	}
	
	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}
	
	public String getApplyTimeStart() {
		return this.applyTimeStart;
	}
	
	public void setApplyTimeStart(String applyTimeStart) {
		this.applyTimeStart = applyTimeStart;
	}
	
	public String getApplyTimeEnd() {
		return this.applyTimeEnd;
	}
	
	public void setApplyTimeEnd(String applyTimeEnd) {
		this.applyTimeEnd = applyTimeEnd;
	}
	
	public Money getAmountStart() {
		return this.amountStart;
	}
	
	public void setAmountStart(Money amountStart) {
		this.amountStart = amountStart;
	}
	
	public Money getAmountEnd() {
		return this.amountEnd;
	}
	
	public void setAmountEnd(Money amountEnd) {
		this.amountEnd = amountEnd;
	}
	
	public List<String> getExpenseTypes() {
		return this.expenseTypes;
	}
	
	public void setExpenseTypes(List<String> expenseTypes) {
		this.expenseTypes = expenseTypes;
	}
	
	public String getDirection() {
		return this.direction;
	}
	
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	public AccountStatusEnum getAccountStatus() {
		return this.accountStatus;
	}
	
	public void setAccountStatus(AccountStatusEnum accountStatus) {
		this.accountStatus = accountStatus;
	}
	
}
