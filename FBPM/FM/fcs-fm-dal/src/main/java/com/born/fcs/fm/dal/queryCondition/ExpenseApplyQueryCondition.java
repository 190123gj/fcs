package com.born.fcs.fm.dal.queryCondition;

import java.util.List;

import com.born.fcs.fm.dataobject.SimpleFormDO;

public class ExpenseApplyQueryCondition extends SimpleFormDO {
	
	private static final long serialVersionUID = -2795344939941366226L;
	
	private long expenseApplicationId;
	
	private String billNo;
	
	private String isOfficialCard;
	
	private String applyTimeStart;
	
	private String applyTimeEnd;
	
	private long amountStart;
	
	private long amountEnd;
	
	private String officialCard;
	
	private String agent;
	
	private String process;
	
	private long curUserId;
	
	private List<String> formStatusList;
	
	private List<String> excFormStatusList;
	
	private List<String> searchUserIdList;
	
	private String voucherStatus;
	private String voucherNo;
	
	private String payee;
	
	private String expenseType;
	
	private String bank;
	
	private String bankAccount;
	
	private String direction;
	
	private String accountStatus;
	
	private String branchPayStatus;
	
	private List<String> expenseTypes;
	
	public String getAccountStatus() {
		return this.accountStatus;
	}
	
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	
	public String getExpenseType() {
		return this.expenseType;
	}
	
	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}
	
	public String getDirection() {
		return this.direction;
	}
	
	public List<String> getExpenseTypes() {
		return this.expenseTypes;
	}
	
	public void setExpenseTypes(List<String> expenseTypes) {
		this.expenseTypes = expenseTypes;
	}
	
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	public String getVoucherStatus() {
		return this.voucherStatus;
	}
	
	public String getBank() {
		return this.bank;
	}
	
	public void setBank(String bank) {
		this.bank = bank;
	}
	
	public String getBankAccount() {
		return this.bankAccount;
	}
	
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	
	public String getVoucherNo() {
		return this.voucherNo;
	}
	
	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}
	
	public void setVoucherStatus(String voucherStatus) {
		this.voucherStatus = voucherStatus;
	}
	
	public List<String> getSearchUserIdList() {
		return searchUserIdList;
	}
	
	public void setSearchUserIdList(List<String> searchUserIdList) {
		this.searchUserIdList = searchUserIdList;
	}
	
	public List<String> getFormStatusList() {
		return formStatusList;
	}
	
	public String getPayee() {
		return this.payee;
	}
	
	public void setPayee(String payee) {
		this.payee = payee;
	}
	
	public void setFormStatusList(List<String> formStatusList) {
		this.formStatusList = formStatusList;
	}
	
	public List<String> getExcFormStatusList() {
		return excFormStatusList;
	}
	
	public void setExcFormStatusList(List<String> excFormStatusList) {
		this.excFormStatusList = excFormStatusList;
	}
	
	public String getIsOfficialCard() {
		return isOfficialCard;
	}
	
	public void setIsOfficialCard(String isOfficialCard) {
		this.isOfficialCard = isOfficialCard;
	}
	
	public long getExpenseApplicationId() {
		return expenseApplicationId;
	}
	
	public void setExpenseApplicationId(long expenseApplicationId) {
		this.expenseApplicationId = expenseApplicationId;
	}
	
	public long getCurUserId() {
		return curUserId;
	}
	
	public void setCurUserId(long curUserId) {
		this.curUserId = curUserId;
	}
	
	public String getBillNo() {
		return billNo;
	}
	
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	
	public String getApplyTimeStart() {
		return applyTimeStart;
	}
	
	public void setApplyTimeStart(String applyTimeStart) {
		this.applyTimeStart = applyTimeStart;
	}
	
	public String getApplyTimeEnd() {
		return applyTimeEnd;
	}
	
	public void setApplyTimeEnd(String applyTimeEnd) {
		this.applyTimeEnd = applyTimeEnd;
	}
	
	public long getAmountStart() {
		return amountStart;
	}
	
	public void setAmountStart(long amountStart) {
		this.amountStart = amountStart;
	}
	
	public long getAmountEnd() {
		return amountEnd;
	}
	
	public void setAmountEnd(long amountEnd) {
		this.amountEnd = amountEnd;
	}
	
	public String getOfficialCard() {
		return officialCard;
	}
	
	public void setOfficialCard(String officialCard) {
		this.officialCard = officialCard;
	}
	
	public String getAgent() {
		return agent;
	}
	
	public void setAgent(String agent) {
		this.agent = agent;
	}
	
	public String getProcess() {
		return process;
	}
	
	public void setProcess(String process) {
		this.process = process;
	}
	
	public String getBranchPayStatus() {
		return branchPayStatus;
	}
	
	public void setBranchPayStatus(String branchPayStatus) {
		this.branchPayStatus = branchPayStatus;
	}
	
}
