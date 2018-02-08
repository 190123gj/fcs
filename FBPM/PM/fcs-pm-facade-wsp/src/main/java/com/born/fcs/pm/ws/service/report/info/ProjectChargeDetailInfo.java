package com.born.fcs.pm.ws.service.report.info;

import java.util.Date;

import com.born.fcs.pm.ws.enums.FeeTypeEnum;
import com.yjf.common.lang.util.money.Money;

/**
 * 项目收费明细
 *
 * @author wuzj
 */
public class ProjectChargeDetailInfo extends ProjectReportInfo {
	
	private static final long serialVersionUID = -7799554531697945357L;
	
	/** 收费类型 */
	private FeeTypeEnum feeType;
	/** 收费金额 */
	private Money chargeAmount = new Money(0, 0);
	/** 收费时间 */
	private Date chargeTime;
	/** 银行账号 */
	private String chargeAccount;
	/** 收费基数 */
	private Money chargeBase = new Money(0, 0);
	/** 费率 */
	private double chargeRate;
	/** 计费期间 */
	private Date chargeStartTime;
	/** 计费期间 */
	private Date chargeEndTime;
	
	public FeeTypeEnum getFeeType() {
		return this.feeType;
	}
	
	public void setFeeType(FeeTypeEnum feeType) {
		this.feeType = feeType;
	}
	
	public Money getChargeAmount() {
		return this.chargeAmount;
	}
	
	public void setChargeAmount(Money chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	
	public Date getChargeTime() {
		return this.chargeTime;
	}
	
	public void setChargeTime(Date chargeTime) {
		this.chargeTime = chargeTime;
	}
	
	public String getChargeAccount() {
		return this.chargeAccount;
	}
	
	public void setChargeAccount(String chargeAccount) {
		this.chargeAccount = chargeAccount;
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
	
	public Date getChargeStartTime() {
		return this.chargeStartTime;
	}
	
	public void setChargeStartTime(Date chargeStartTime) {
		this.chargeStartTime = chargeStartTime;
	}
	
	public Date getChargeEndTime() {
		return this.chargeEndTime;
	}
	
	public void setChargeEndTime(Date chargeEndTime) {
		this.chargeEndTime = chargeEndTime;
	}
	
}
