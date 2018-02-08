package com.born.fcs.fm.dal.queryCondition;

import java.util.List;

public class FormTransferQueryCondition extends QueryConditionBaseDO {
	
	private static final long serialVersionUID = 1L;
	
	private long transferId;
	
	private String billNo;
	
	private String applyTimeStart;
	
	private String applyTimeEnd;
	
	private long amountStart;
	
	private long amountEnd;
	
	private long curUserId;
	
	private String agent;
	
	private String process;
	
	private List<String> formStatusList;
	
	private List<String> excFormStatusList;
	
	private List<String> searchUserIdList;
	
	private String voucherStatus;
	
	public List<String> getSearchUserIdList() {
		return searchUserIdList;
	}
	
	public void setSearchUserIdList(List<String> searchUserIdList) {
		this.searchUserIdList = searchUserIdList;
	}
	
	public List<String> getFormStatusList() {
		return formStatusList;
	}
	
	public String getVoucherStatus() {
		return this.voucherStatus;
	}
	
	public void setVoucherStatus(String voucherStatus) {
		this.voucherStatus = voucherStatus;
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
	
	public long getTransferId() {
		return transferId;
	}
	
	public void setTransferId(long transferId) {
		this.transferId = transferId;
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
}
