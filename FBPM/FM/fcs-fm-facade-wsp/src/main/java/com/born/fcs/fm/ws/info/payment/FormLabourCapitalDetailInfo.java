package com.born.fcs.fm.ws.info.payment;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

public class FormLabourCapitalDetailInfo extends BaseToStringInfo {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	private long detailId;
	
	private long labourCapitalId;
	
	private String expenseType;
	private String reverse;
	
	private Money amount = new Money(0, 0);
	
	private Money taxAmount = new Money(0, 0);
	
	private Money fpAmount = new Money(0, 0);
	
	private Money xjAmount = new Money(0, 0);
	
	private long deptId;
	
	private String deptName;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	private Money balanceAmount = new Money(0, 0);
	
	//========== getters and setters ==========
	
	public long getDetailId() {
		return detailId;
	}
	
	public Money getFpAmount() {
		return fpAmount;
	}
	
	public void setFpAmount(Money fpAmount) {
		this.fpAmount = fpAmount;
	}
	
	public String getReverse() {
		return this.reverse;
	}
	
	public void setReverse(String reverse) {
		this.reverse = reverse;
	}
	
	public Money getXjAmount() {
		return xjAmount;
	}
	
	public void setXjAmount(Money xjAmount) {
		this.xjAmount = xjAmount;
	}
	
	public void setDetailId(long detailId) {
		this.detailId = detailId;
	}
	
	public String getDeptName() {
		return deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public Money getBalanceAmount() {
		return balanceAmount;
	}
	
	public void setBalanceAmount(Money balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	
	public long getLabourCapitalId() {
		return this.labourCapitalId;
	}
	
	public void setLabourCapitalId(long labourCapitalId) {
		this.labourCapitalId = labourCapitalId;
	}
	
	public String getExpenseType() {
		return expenseType;
	}
	
	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}
	
	public Money getAmount() {
		return amount;
	}
	
	public void setAmount(Money amount) {
		if (amount == null) {
			this.amount = new Money(0, 0);
		} else {
			this.amount = amount;
		}
	}
	
	public Money getTaxAmount() {
		return taxAmount;
	}
	
	public void setTaxAmount(Money taxAmount) {
		if (taxAmount == null) {
			this.taxAmount = new Money(0, 0);
		} else {
			this.taxAmount = taxAmount;
		}
	}
	
	public long getDeptId() {
		return deptId;
	}
	
	public void setDeptId(long deptId) {
		this.deptId = deptId;
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
	
	/**
	 * @return
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
