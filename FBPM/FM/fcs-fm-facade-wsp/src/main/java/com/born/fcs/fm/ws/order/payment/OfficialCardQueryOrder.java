package com.born.fcs.fm.ws.order.payment;

import com.born.fcs.pm.ws.base.QueryPermissionPageBase;

public class OfficialCardQueryOrder extends QueryPermissionPageBase {
	
	private static final long serialVersionUID = 3341703956568452529L;
	
	private String billNo;
	
	private String applyTimeStart;
	
	private String applyTimeEnd;
	
	private long curUserId;
	
	private long userId;//报销人
	
	private String userName;
	
	private long expenseDeptId;
	
	private String deptName;
	
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
