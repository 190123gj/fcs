package com.born.fcs.fm.dal.queryCondition;

import java.util.List;

public class BusiQueryCondition extends QueryConditionBaseDO {
	
	private static final long serialVersionUID = 1L;
	
	private String billNo;
	
	private String isOfficialCard;
	
	private String applyTimeStart;
	
	private String applyTimeEnd;
	
	private long amountStart;
	
	private long amountEnd;
	
	private long curUserId;
	
	private List<String> formStatusList;
	
	private List<String> excFormStatusList;
	
	private List<String> searchUserIdList;
	
	private long userId;//报销人
	
	private String userName;
	
	private long expenseDeptId;//报销部门
	
	private String deptName;
	
	public List<String> getSearchUserIdList() {
		return searchUserIdList;
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
	
	public long getCurUserId() {
		return curUserId;
	}
	
	public void setCurUserId(long curUserId) {
		this.curUserId = curUserId;
	}
	
	public long getExpenseDeptId() {
		return expenseDeptId;
	}
	
	public void setExpenseDeptId(long expenseDeptId) {
		this.expenseDeptId = expenseDeptId;
	}
	
	public String getDeptName() {
		return deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
