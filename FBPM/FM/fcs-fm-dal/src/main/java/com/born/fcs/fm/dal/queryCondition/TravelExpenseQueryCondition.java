package com.born.fcs.fm.dal.queryCondition;

import java.util.List;

import com.born.fcs.fm.dataobject.SimpleFormDO;

public class TravelExpenseQueryCondition extends SimpleFormDO {
	
	private static final long serialVersionUID = -2648586988796130867L;

	private long travelId;
	
	private String billNo;
	
	private String isOfficialCard;
	
	private String applyTimeStart;
	
	private String applyTimeEnd;
	
	private long amountStart;
	
	private long amountEnd;
	
	private String officialCard;
	
	private long curUserId;
	
	private String agent;
	
	private String process;
	
	private List<String> formStatusList;
	
	private List<String> excFormStatusList;
	
	private List<String> searchUserIdList;
	
	private String voucherStatus;
	private String voucherNo;
	private String payee;
	
	private String accountStatus;
	
	//分支机构付款状态
	private String branchPayStatus;
	
	public List<String> getSearchUserIdList() {
		return searchUserIdList;
	}
	
	public String getAccountStatus() {
		return this.accountStatus;
	}
	
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	
	public void setSearchUserIdList(List<String> searchUserIdList) {
		this.searchUserIdList = searchUserIdList;
	}
	
	public List<String> getFormStatusList() {
		return formStatusList;
	}
	
	public void setFormStatusList(List<String> formStatusList) {
		this.formStatusList = formStatusList;
	}
	
	public String getPayee() {
		return this.payee;
	}
	
	public void setPayee(String payee) {
		this.payee = payee;
	}
	
	public String getVoucherNo() {
		return this.voucherNo;
	}
	
	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}
	
	public String getVoucherStatus() {
		return this.voucherStatus;
	}
	
	public void setVoucherStatus(String voucherStatus) {
		this.voucherStatus = voucherStatus;
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
	
	public long getTravelId() {
		return travelId;
	}
	
	public void setTravelId(long travelId) {
		this.travelId = travelId;
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
	
	public long getCurUserId() {
		return curUserId;
	}
	
	public void setCurUserId(long curUserId) {
		this.curUserId = curUserId;
	}

	public String getBranchPayStatus() {
		return this.branchPayStatus;
	}

	public void setBranchPayStatus(String branchPayStatus) {
		this.branchPayStatus = branchPayStatus;
	}
}
