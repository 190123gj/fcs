package com.born.fcs.pm.ws.order.projectcreditcondition;

import java.util.Date;

import com.born.fcs.pm.ws.order.base.ProcessOrder;
import com.yjf.common.lang.util.money.Money;

/**
 * 项目授信条件-保证金落实情况Order
 *
 *
 * @author Ji
 *
 */
public class ProjectCreditMarginOrder extends ProcessOrder {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9048823722982212112L;
	
	private long confirmId;
	
	private String creditId;
	
	private String projectCode;
	
	private Money marginAmount = new Money(0, 0);
	
	private String accountName;
	
	private String bank;
	
	private String account;
	
	private int period;
	
	private String strPeriod;
	
	private String periodUnit;
	
	private Date inTime;
	
	private String isPledge;
	
	private String isFrozen;
	
	public long getConfirmId() {
		return confirmId;
	}
	
	public void setConfirmId(long confirmId) {
		this.confirmId = confirmId;
	}
	
	public String getCreditId() {
		return creditId;
	}
	
	public void setCreditId(String creditId) {
		this.creditId = creditId;
	}
	
	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public Money getMarginAmount() {
		return marginAmount;
	}
	
	public void setMarginAmount(Money marginAmount) {
		this.marginAmount = marginAmount;
	}
	
	public String getAccountName() {
		return accountName;
	}
	
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public String getBank() {
		return bank;
	}
	
	public void setBank(String bank) {
		this.bank = bank;
	}
	
	public String getAccount() {
		return account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	
	public int getPeriod() {
		return period;
	}
	
	public void setPeriod(int period) {
		this.period = period;
	}
	
	public String getPeriodUnit() {
		return periodUnit;
	}
	
	public void setPeriodUnit(String periodUnit) {
		this.periodUnit = periodUnit;
	}
	
	public Date getInTime() {
		return inTime;
	}
	
	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}
	
	public String getIsPledge() {
		return isPledge;
	}
	
	public void setIsPledge(String isPledge) {
		this.isPledge = isPledge;
	}
	
	public String getIsFrozen() {
		return isFrozen;
	}
	
	public void setIsFrozen(String isFrozen) {
		this.isFrozen = isFrozen;
	}
	
	public String getStrPeriod() {
		return strPeriod;
	}
	
	public void setStrPeriod(String strPeriod) {
		this.strPeriod = strPeriod;
	}
	
}
