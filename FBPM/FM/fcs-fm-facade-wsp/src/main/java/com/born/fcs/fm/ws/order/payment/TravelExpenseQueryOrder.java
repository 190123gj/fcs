package com.born.fcs.fm.ws.order.payment;

import java.util.List;

import com.born.fcs.fm.ws.enums.AccountStatusEnum;
import com.born.fcs.fm.ws.enums.TExpQueryProcessEnum;
import com.born.fcs.pm.ws.base.QueryPermissionPageBase;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.yjf.common.lang.util.money.Money;

public class TravelExpenseQueryOrder extends QueryPermissionPageBase {
	
	private static final long serialVersionUID = 3341703956568452529L;
	
	private long travelId;
	
	private String billNo;
	
	private String applyTimeStart;
	
	private String applyTimeEnd;
	
	private Money amountStart;
	
	private Money amountEnd;
	
	private String officialCard;
	
	private long curUserId;
	
	private String agent;
	
	private TExpQueryProcessEnum process;
	
	private boolean isDetail;
	
	private List<FormStatusEnum> formStatusList;
	
	private List<FormStatusEnum> excFormStatusList;
	
	private List<String> searchUserIdList;
	private String voucherStatus;
	private String voucherNo;
	
	private String payee;
	
	private AccountStatusEnum accountStatus;
	
	//分支机构付款状态
	private String branchPayStatus;
	
	public List<String> getSearchUserIdList() {
		return searchUserIdList;
	}
	
	public AccountStatusEnum getAccountStatus() {
		return this.accountStatus;
	}
	
	public void setAccountStatus(AccountStatusEnum accountStatus) {
		this.accountStatus = accountStatus;
	}
	
	public void setSearchUserIdList(List<String> searchUserIdList) {
		this.searchUserIdList = searchUserIdList;
	}
	
	public String getVoucherStatus() {
		return this.voucherStatus;
	}
	
	public String getVoucherNo() {
		return this.voucherNo;
	}
	
	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}
	
	public String getPayee() {
		return this.payee;
	}
	
	public void setPayee(String payee) {
		this.payee = payee;
	}
	
	public void setVoucherStatus(String voucherStatus) {
		this.voucherStatus = voucherStatus;
	}
	
	public List<FormStatusEnum> getFormStatusList() {
		return formStatusList;
	}
	
	public void setFormStatusList(List<FormStatusEnum> formStatusList) {
		this.formStatusList = formStatusList;
	}
	
	public List<FormStatusEnum> getExcFormStatusList() {
		return excFormStatusList;
	}
	
	public void setExcFormStatusList(List<FormStatusEnum> excFormStatusList) {
		this.excFormStatusList = excFormStatusList;
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
	
	public Money getAmountStart() {
		return amountStart;
	}
	
	public void setAmountStart(Money amountStart) {
		this.amountStart = amountStart;
	}
	
	public Money getAmountEnd() {
		return amountEnd;
	}
	
	public void setAmountEnd(Money amountEnd) {
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
	
	public TExpQueryProcessEnum getProcess() {
		return process;
	}
	
	public void setProcess(TExpQueryProcessEnum process) {
		this.process = process;
	}
	
	public boolean isDetail() {
		return isDetail;
	}
	
	public void setDetail(boolean isDetail) {
		this.isDetail = isDetail;
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
