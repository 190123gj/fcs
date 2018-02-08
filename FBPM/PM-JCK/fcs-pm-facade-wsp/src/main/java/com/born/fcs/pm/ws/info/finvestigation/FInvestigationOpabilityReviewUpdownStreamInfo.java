/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.info.finvestigation;

import java.util.Date;

import com.born.fcs.pm.ws.enums.UpAndDownEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 客户主要上下游情况（余额前五大）（城投类客户根据实际情况可不填写）
 * 
 * @author lirz
 *
 * 2016-3-10 下午4:55:45
 */
public class FInvestigationOpabilityReviewUpdownStreamInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -5112381973799195507L;
	
	private long id;
	private long opReviewId;
	private UpAndDownEnum upOrDown; //上游或下游
	private String name; //对方名称
	private String clearingForm; //结算方式
	private String paymentTerms; //帐期
	private Money endingBalance = new Money(0, 0); //期末余额
	private String remark; //备注（产品种类、合作年限等）
	private int sortOrder;
	private Date rawAddTime;
	private Date rawUpdateTime;
	
	//========== getters and setters ==========
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getOpReviewId() {
		return opReviewId;
	}
	
	public void setOpReviewId(long opReviewId) {
		this.opReviewId = opReviewId;
	}
	
	public UpAndDownEnum getUpOrDown() {
		return this.upOrDown;
	}
	
	public void setUpOrDown(UpAndDownEnum upOrDown) {
		this.upOrDown = upOrDown;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getClearingForm() {
		return clearingForm;
	}
	
	public void setClearingForm(String clearingForm) {
		this.clearingForm = clearingForm;
	}
	
	public String getPaymentTerms() {
		return paymentTerms;
	}
	
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	
	public Money getEndingBalance() {
		return endingBalance;
	}
	
	public void setEndingBalance(Money endingBalance) {
		if (endingBalance == null) {
			this.endingBalance = new Money(0, 0);
		} else {
			this.endingBalance = endingBalance;
		}
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public int getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
}
