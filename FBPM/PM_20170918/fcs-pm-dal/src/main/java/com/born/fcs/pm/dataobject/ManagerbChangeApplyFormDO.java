package com.born.fcs.pm.dataobject;

import java.util.Date;
import java.util.List;

import com.yjf.common.lang.util.money.Money;

/**
 * 自己写的sql对应的B角更换
 *
 * @author wuzj
 */
public class ManagerbChangeApplyFormDO extends SimpleFormDO {
	
	private static final long serialVersionUID = -8105235754332396935L;
	
	private long applyId;
	
	private String projectCode;
	
	private String projectName;
	
	private long customerId;
	
	private String customerName;
	
	private String busiType;
	
	private String busiTypeName;
	
	private Money amount = new Money(0, 0);
	
	private long oldBid;
	
	private String oldBaccount;
	
	private String oldBname;
	
	private long newBid;
	
	private String newBaccount;
	
	private String newBname;
	
	private long managerId;
	
	private String managerAccount;
	
	private String managerName;
	
	private String changeWay;
	
	private String changePhases;
	
	private Date changeStartTime;
	
	private Date changeEndTime;
	
	private String remark;
	
	private String status;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	//查询部分
	private long loginUserId;
	
	private List<Long> deptIdList;
	
	long pageSize;
	
	long limitStart;
	
	String sortCol;
	
	String sortOrder;
	
	public long getApplyId() {
		return this.applyId;
	}
	
	public void setApplyId(long applyId) {
		this.applyId = applyId;
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
	
	public Money getAmount() {
		return this.amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public long getOldBid() {
		return this.oldBid;
	}
	
	public void setOldBid(long oldBid) {
		this.oldBid = oldBid;
	}
	
	public String getOldBaccount() {
		return this.oldBaccount;
	}
	
	public void setOldBaccount(String oldBaccount) {
		this.oldBaccount = oldBaccount;
	}
	
	public String getOldBname() {
		return this.oldBname;
	}
	
	public void setOldBname(String oldBname) {
		this.oldBname = oldBname;
	}
	
	public long getNewBid() {
		return this.newBid;
	}
	
	public void setNewBid(long newBid) {
		this.newBid = newBid;
	}
	
	public String getNewBaccount() {
		return this.newBaccount;
	}
	
	public void setNewBaccount(String newBaccount) {
		this.newBaccount = newBaccount;
	}
	
	public String getNewBname() {
		return this.newBname;
	}
	
	public void setNewBname(String newBname) {
		this.newBname = newBname;
	}
	
	public long getManagerId() {
		return this.managerId;
	}
	
	public void setManagerId(long managerId) {
		this.managerId = managerId;
	}
	
	public String getManagerAccount() {
		return this.managerAccount;
	}
	
	public void setManagerAccount(String managerAccount) {
		this.managerAccount = managerAccount;
	}
	
	public String getManagerName() {
		return this.managerName;
	}
	
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	
	public String getChangeWay() {
		return this.changeWay;
	}
	
	public void setChangeWay(String changeWay) {
		this.changeWay = changeWay;
	}
	
	public String getChangePhases() {
		return this.changePhases;
	}
	
	public void setChangePhases(String changePhases) {
		this.changePhases = changePhases;
	}
	
	public Date getChangeStartTime() {
		return this.changeStartTime;
	}
	
	public void setChangeStartTime(Date changeStartTime) {
		this.changeStartTime = changeStartTime;
	}
	
	public Date getChangeEndTime() {
		return this.changeEndTime;
	}
	
	public void setChangeEndTime(Date changeEndTime) {
		this.changeEndTime = changeEndTime;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
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
	
	public long getLoginUserId() {
		return this.loginUserId;
	}
	
	public void setLoginUserId(long loginUserId) {
		this.loginUserId = loginUserId;
	}
	
	public List<Long> getDeptIdList() {
		return this.deptIdList;
	}
	
	public void setDeptIdList(List<Long> deptIdList) {
		this.deptIdList = deptIdList;
	}
	
	public long getPageSize() {
		return this.pageSize;
	}
	
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}
	
	public long getLimitStart() {
		return this.limitStart;
	}
	
	public void setLimitStart(long limitStart) {
		this.limitStart = limitStart;
	}
	
	public String getSortCol() {
		return this.sortCol;
	}
	
	public void setSortCol(String sortCol) {
		this.sortCol = sortCol;
	}
	
	public String getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
}
