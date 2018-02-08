package com.born.fcs.pm.ws.info.capitalappropriationapply;

/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */

import java.util.Date;

import com.born.fcs.pm.ws.enums.PaymentMenthodEnum;
import com.born.fcs.pm.ws.info.common.SimpleFormProjectInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 资金划付申请-资金划付信息
 *
 * @author Ji
 *
 */
public class FCapitalAppropriationApplyFeeInfo extends SimpleFormProjectInfo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4559842384409819097L;
	
	private long id;
	
	private long applyId;
	
	private PaymentMenthodEnum appropriateReason;
	
	private Money appropriateAmount = new Money(0, 0);
	
	private String remark;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	//========== getters and setters ==========
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getApplyId() {
		return applyId;
	}
	
	public void setApplyId(long applyId) {
		this.applyId = applyId;
	}
	
	public PaymentMenthodEnum getAppropriateReason() {
		return appropriateReason;
	}
	
	public void setAppropriateReason(PaymentMenthodEnum appropriateReason) {
		this.appropriateReason = appropriateReason;
	}
	
	public Money getAppropriateAmount() {
		return appropriateAmount;
	}
	
	public void setAppropriateAmount(Money appropriateAmount) {
		this.appropriateAmount = appropriateAmount;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
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
