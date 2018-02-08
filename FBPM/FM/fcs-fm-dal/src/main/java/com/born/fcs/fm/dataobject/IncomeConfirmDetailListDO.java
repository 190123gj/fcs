package com.born.fcs.fm.dataobject;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.base.QueryPermissionPageBase;
// auto generated imports
import com.yjf.common.lang.util.money.Money;

public class IncomeConfirmDetailListDO extends QueryPermissionPageBase {
	
	private static final long serialVersionUID = 4379012481318949836L;
	
	private long incomeId;
	
	private String projectCode;
	
	private String projectName;
	
	private long customerId;
	
	private String customerName;
	
	private String busiType;
	
	private String busiTypeName;
	
	private Money chargedAmount = new Money(0, 0);
	
	private Money confirmedIncomeAmount = new Money(0, 0);
	
	private Money notConfirmedIncomeAmount = new Money(0, 0);
	
	private Money thisMonthConfirmedIncomeAmount = new Money(0, 0);
	
	private String incomeConfirmStatus;
	
	private String incomePeriod;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	private long detailId;
	
	private Money systemEstimatedAmount = new Money(0, 0);
	
	private Money incomeConfirmedAmount = new Money(0, 0);
	
	private String isConfirmed;
	
	private String confirmStatus;
	
	private Date detailAddTime;
	
	private Date detailUpdateTime;
	
	public long getIncomeId() {
		return incomeId;
	}
	
	public void setIncomeId(long incomeId) {
		this.incomeId = incomeId;
	}
	
	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getBusiType() {
		return busiType;
	}
	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	public String getBusiTypeName() {
		return busiTypeName;
	}
	
	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}
	
	public Money getChargedAmount() {
		return chargedAmount;
	}
	
	public void setChargedAmount(Money chargedAmount) {
		if (chargedAmount == null) {
			this.chargedAmount = new Money(0, 0);
		} else {
			this.chargedAmount = chargedAmount;
		}
	}
	
	public Money getConfirmedIncomeAmount() {
		return confirmedIncomeAmount;
	}
	
	public void setConfirmedIncomeAmount(Money confirmedIncomeAmount) {
		if (confirmedIncomeAmount == null) {
			this.confirmedIncomeAmount = new Money(0, 0);
		} else {
			this.confirmedIncomeAmount = confirmedIncomeAmount;
		}
	}
	
	public Money getNotConfirmedIncomeAmount() {
		return notConfirmedIncomeAmount;
	}
	
	public void setNotConfirmedIncomeAmount(Money notConfirmedIncomeAmount) {
		if (notConfirmedIncomeAmount == null) {
			this.notConfirmedIncomeAmount = new Money(0, 0);
		} else {
			this.notConfirmedIncomeAmount = notConfirmedIncomeAmount;
		}
	}
	
	public Money getThisMonthConfirmedIncomeAmount() {
		return thisMonthConfirmedIncomeAmount;
	}
	
	public void setThisMonthConfirmedIncomeAmount(Money thisMonthConfirmedIncomeAmount) {
		if (thisMonthConfirmedIncomeAmount == null) {
			this.thisMonthConfirmedIncomeAmount = new Money(0, 0);
		} else {
			this.thisMonthConfirmedIncomeAmount = thisMonthConfirmedIncomeAmount;
		}
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
	
	public String getIncomePeriod() {
		return incomePeriod;
	}
	
	public void setIncomePeriod(String incomePeriod) {
		this.incomePeriod = incomePeriod;
	}
	
	public String getIncomeConfirmStatus() {
		return incomeConfirmStatus;
	}
	
	public void setIncomeConfirmStatus(String incomeConfirmStatus) {
		this.incomeConfirmStatus = incomeConfirmStatus;
	}
	
	public long getDetailId() {
		return detailId;
	}
	
	public void setDetailId(long detailId) {
		this.detailId = detailId;
	}
	
	public Money getSystemEstimatedAmount() {
		return systemEstimatedAmount;
	}
	
	public void setSystemEstimatedAmount(Money systemEstimatedAmount) {
		this.systemEstimatedAmount = systemEstimatedAmount;
	}
	
	public Money getIncomeConfirmedAmount() {
		return incomeConfirmedAmount;
	}
	
	public void setIncomeConfirmedAmount(Money incomeConfirmedAmount) {
		this.incomeConfirmedAmount = incomeConfirmedAmount;
	}
	
	public String getIsConfirmed() {
		return isConfirmed;
	}
	
	public void setIsConfirmed(String isConfirmed) {
		this.isConfirmed = isConfirmed;
	}
	
	public String getConfirmStatus() {
		return confirmStatus;
	}
	
	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}
	
	public Date getDetailAddTime() {
		return detailAddTime;
	}
	
	public void setDetailAddTime(Date detailAddTime) {
		this.detailAddTime = detailAddTime;
	}
	
	public Date getDetailUpdateTime() {
		return detailUpdateTime;
	}
	
	public void setDetailUpdateTime(Date detailUpdateTime) {
		this.detailUpdateTime = detailUpdateTime;
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
