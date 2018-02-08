/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dataobject;

import java.util.Date;
import java.util.List;

import com.yjf.common.lang.util.money.Money;

/**
 * 放用款申请单
 *
 * @author wuzj
 */
public class LoanUseApplyFormDO extends SimpleFormProjectDO {
	
	private static final long serialVersionUID = -44104692636540442L;
	
	/** 申请单部分 */
	private long applyId;
	
	private long notificationId; //对应通知单ID
	
	private String applyType; //申请类型
	
	private Date applyAddTime;
	
	private Date applyUpdateTime;
	
	private Money applyAmount = Money.zero();
	
	/** 回执部分 */
	private long receiptId; //回执ID
	
	private Money actualAmount;
	
	private Money actualDepositAmount;
	
	private Date receiptAddTime;
	
	private Date receiptUpdateTime;
	
	//查询条件部分
	
	private String hasReceipt;
	
	List<Long> deptIdList;
	
	List<String> statusList;
	
	long loginUserId;
	
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
	
	public long getNotificationId() {
		return this.notificationId;
	}
	
	public void setNotificationId(long notificationId) {
		this.notificationId = notificationId;
	}
	
	public String getApplyType() {
		return this.applyType;
	}
	
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}
	
	public Date getApplyAddTime() {
		return this.applyAddTime;
	}
	
	public void setApplyAddTime(Date applyAddTime) {
		this.applyAddTime = applyAddTime;
	}
	
	public Date getApplyUpdateTime() {
		return this.applyUpdateTime;
	}
	
	public void setApplyUpdateTime(Date applyUpdateTime) {
		this.applyUpdateTime = applyUpdateTime;
	}
	
	public long getReceiptId() {
		return this.receiptId;
	}
	
	public void setReceiptId(long receiptId) {
		this.receiptId = receiptId;
	}
	
	public Date getReceiptAddTime() {
		return this.receiptAddTime;
	}
	
	public void setReceiptAddTime(Date receiptAddTime) {
		this.receiptAddTime = receiptAddTime;
	}
	
	public Date getReceiptUpdateTime() {
		return this.receiptUpdateTime;
	}
	
	public void setReceiptUpdateTime(Date receiptUpdateTime) {
		this.receiptUpdateTime = receiptUpdateTime;
	}
	
	public List<Long> getDeptIdList() {
		return this.deptIdList;
	}
	
	public void setDeptIdList(List<Long> deptIdList) {
		this.deptIdList = deptIdList;
	}
	
	public List<String> getStatusList() {
		return this.statusList;
	}
	
	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}
	
	public long getLoginUserId() {
		return this.loginUserId;
	}
	
	public void setLoginUserId(long loginUserId) {
		this.loginUserId = loginUserId;
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
	
	public Money getApplyAmount() {
		return this.applyAmount;
	}
	
	public void setApplyAmount(Money applyAmount) {
		this.applyAmount = applyAmount;
	}
	
	public String getHasReceipt() {
		return this.hasReceipt;
	}
	
	public void setHasReceipt(String hasReceipt) {
		this.hasReceipt = hasReceipt;
	}
	
	public Money getActualAmount() {
		return this.actualAmount;
	}
	
	public void setActualAmount(Money actualAmount) {
		this.actualAmount = actualAmount;
	}
	
	public Money getActualDepositAmount() {
		return this.actualDepositAmount;
	}
	
	public void setActualDepositAmount(Money actualDepositAmount) {
		this.actualDepositAmount = actualDepositAmount;
	}
}
