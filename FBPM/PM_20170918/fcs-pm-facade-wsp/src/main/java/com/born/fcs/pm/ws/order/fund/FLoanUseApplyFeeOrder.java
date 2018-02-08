package com.born.fcs.pm.ws.order.fund;

import java.util.Date;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 放用款申请 - 费用收取情况
 *
 * @author wuzj
 *
 */
public class FLoanUseApplyFeeOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 8330534025897733016L;
	/** ID */
	private Long id;
	/** 申请ID */
	private Long applyId;
	/** 收费类型 */
	private String feeType;
	/** 收费类型描述 */
	private String feeTypeDesc;
	/** 收费基数 */
	private Money chargeBase;
	/** 收费费率 */
	private Double chargeRate;
	/** 计费期间 */
	private Date startTime;
	private String startTimeStr;
	/** 计费期间 */
	private Date endTime;
	private String endTimeStr;
	/** 收费金额 */
	private Money chargeAmount;
	/** 备注 */
	private String remark;
	
	public boolean isNull() {
		return isNull(feeType) && isNull(chargeBase) && isNull(chargeRate) && isNull(startTime)
				&& isNull(endTime) && isNull(chargeAmount);
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getApplyId() {
		return this.applyId;
	}
	
	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}
	
	public String getFeeType() {
		return this.feeType;
	}
	
	public void setFeeType(String feeType) {
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
	
	public void setChargeBaseStr(String chargeBaseStr) {
		if (chargeBaseStr != null && !"".equals(chargeBaseStr))
			this.chargeBase = Money.amout(chargeBaseStr);
	}
	
	public void setChargeAmount(Money chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	
	public void setChargeAmountStr(String chargeAmountStr) {
		if (chargeAmountStr != null && !"".equals(chargeAmountStr))
			this.chargeAmount = Money.amout(chargeAmountStr);
	}
	
	public Double getChargeRate() {
		return this.chargeRate;
	}
	
	public void setChargeRate(Double chargeRate) {
		this.chargeRate = chargeRate;
	}
	
	public Date getStartTime() {
		return this.startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public String getStartTimeStr() {
		return this.startTimeStr;
	}
	
	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}
	
	public Date getEndTime() {
		return this.endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public String getEndTimeStr() {
		return this.endTimeStr;
	}
	
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	
	public Money getChargeAmount() {
		return this.chargeAmount;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
