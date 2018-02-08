package com.born.fcs.pm.ws.info.fund;

import java.util.Date;

import com.born.fcs.pm.ws.enums.FeeTypeEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 放用款申请 - 费用收取情况
 *
 * @author wuzj
 *
 */
public class FLoanUseApplyFeeInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 726968691865006972L;
	/** ID */
	private long id;
	/** 申请ID */
	private long applyId;
	/** 收费类型 */
	private FeeTypeEnum feeType;
	/** 收费类型描述 */
	private String feeTypeDesc;
	/** 收费基数 */
	private Money chargeBase = new Money(0, 0);
	/** 收费费率 */
	private double chargeRate;
	/** 计费期间 */
	private Date startTime;
	/** 计费期间 */
	private Date endTime;
	/** 收费金额 */
	private Money chargeAmount = new Money(0, 0);
	/** 备注 */
	private String remark;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getApplyId() {
		return this.applyId;
	}
	
	public void setApplyId(long applyId) {
		this.applyId = applyId;
	}
	
	public FeeTypeEnum getFeeType() {
		return this.feeType;
	}
	
	public void setFeeType(FeeTypeEnum feeType) {
		this.feeType = feeType;
	}
	
	public String getFeeTypeDesc() {
		return this.feeTypeDesc;
	}
	
	public void setFeeTypeDesc(String feeTypeDesc) {
		this.feeTypeDesc = feeTypeDesc;
	}
	
	public Money getChargeBase() {
		return this.chargeBase;
	}
	
	public void setChargeBase(Money chargeBase) {
		this.chargeBase = chargeBase;
	}
	
	public double getChargeRate() {
		return this.chargeRate;
	}
	
	public void setChargeRate(double chargeRate) {
		this.chargeRate = chargeRate;
	}
	
	public Date getStartTime() {
		return this.startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return this.endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public Money getChargeAmount() {
		return this.chargeAmount;
	}
	
	public void setChargeAmount(Money chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
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
	
}
