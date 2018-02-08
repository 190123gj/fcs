package com.born.fcs.pm.ws.order.check;

import java.util.Date;

import com.born.fcs.pm.ws.order.base.ProjectFormOrderBase;
import com.yjf.common.lang.util.money.Money;

public class FAfterwardsCheckOrder extends ProjectFormOrderBase {
	
	private static final long serialVersionUID = -587645107132510680L;
	
	private Long id;
	
	private Date checkDate;
	
	private String checkAddress;
	
	private String edition;
	
	private int roundYear;

	private int roundTime;
	
	private Money amount = new Money(0, 0);
	
	private Money usedAmount = new Money(0, 0);
	
	private Money repayedAmount = new Money(0, 0);
	
	private String useWay;
	
	private Date rawAddTime;
	
	private long copiedFormId;
	
	public Date getCheckDate() {
		return this.checkDate;
	}
	
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	
	public int getRoundYear() {
		return roundYear;
	}

	public void setRoundYear(int roundYear) {
		this.roundYear = roundYear;
	}

	public int getRoundTime() {
		return roundTime;
	}

	public void setRoundTime(int roundTime) {
		this.roundTime = roundTime;
	}

	public Money getAmount() {
		return this.amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public Money getUsedAmount() {
		return this.usedAmount;
	}
	
	public void setUsedAmount(Money usedAmount) {
		this.usedAmount = usedAmount;
	}
	
	public Money getRepayedAmount() {
		return this.repayedAmount;
	}
	
	public void setRepayedAmount(Money repayedAmount) {
		this.repayedAmount = repayedAmount;
	}
	
	public Date getRawAddTime() {
		return this.rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public String getCheckAddress() {
		return this.checkAddress;
	}
	
	public void setCheckAddress(String checkAddress) {
		this.checkAddress = checkAddress;
	}
	
	public String getUseWay() {
		return this.useWay;
	}
	
	public void setUseWay(String useWay) {
		this.useWay = useWay;
	}
	
	public String getEdition() {
		return this.edition;
	}
	
	public void setEdition(String edition) {
		this.edition = edition;
	}
	
	public Long getId() {
		return (null == this.id) ? 0L : this.id.longValue();
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public long getCopiedFormId() {
		return this.copiedFormId;
	}

	public void setCopiedFormId(long copiedFormId) {
		this.copiedFormId = copiedFormId;
	}
	
}
