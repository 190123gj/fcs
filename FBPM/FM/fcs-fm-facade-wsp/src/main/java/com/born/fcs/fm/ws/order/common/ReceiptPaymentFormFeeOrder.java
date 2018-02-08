package com.born.fcs.fm.ws.order.common;

import java.util.Date;

import com.born.fcs.fm.ws.enums.SubjectCostTypeEnum;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 收款单/付款单数据来源 费用明细Order
 * @author wuzj
 */
public class ReceiptPaymentFormFeeOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -6194093380728753805L;
	
	/** 费用类型 */
	private SubjectCostTypeEnum feeType;
	/** 费用 */
	private Money amount = Money.zero();
	/** 付款/收款账号 */
	private String account;
	/** 付款/收款时间 */
	private Date occurTime;
	/** 保证金存入账户 */
	private String depositAccount;
	/** 保证金比例 */
	private double depositRate;
	/** 保证金存入时间 */
	private Date depositTime;
	/** 保证金存入时间期限 */
	private int depositPeriod;
	/** 保证金存入时间期限单位 */
	private String periodUnit;
	/** 附件 */
	private String attach;
	/** 收款付款的备注 */
	private String remark;
	
	public SubjectCostTypeEnum getFeeType() {
		return feeType;
	}
	
	public void setFeeType(SubjectCostTypeEnum feeType) {
		this.feeType = feeType;
	}
	
	public Money getAmount() {
		return amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public String getAccount() {
		return account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	
	public Date getOccurTime() {
		return occurTime;
	}
	
	public void setOccurTime(Date occurTime) {
		this.occurTime = occurTime;
	}
	
	public String getDepositAccount() {
		return depositAccount;
	}
	
	public void setDepositAccount(String depositAccount) {
		this.depositAccount = depositAccount;
	}
	
	public double getDepositRate() {
		return depositRate;
	}
	
	public void setDepositRate(double depositRate) {
		this.depositRate = depositRate;
	}
	
	public Date getDepositTime() {
		return depositTime;
	}
	
	public void setDepositTime(Date depositTime) {
		this.depositTime = depositTime;
	}
	
	public int getDepositPeriod() {
		return depositPeriod;
	}
	
	public void setDepositPeriod(int depositPeriod) {
		this.depositPeriod = depositPeriod;
	}
	
	public String getPeriodUnit() {
		return periodUnit;
	}
	
	public void setPeriodUnit(String periodUnit) {
		this.periodUnit = periodUnit;
	}
	
	public String getAttach() {
		return attach;
	}
	
	public void setAttach(String attach) {
		this.attach = attach;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
