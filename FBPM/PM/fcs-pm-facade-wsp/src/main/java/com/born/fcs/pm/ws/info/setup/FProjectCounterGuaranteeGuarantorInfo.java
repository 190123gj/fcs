package com.born.fcs.pm.ws.info.setup;

import java.util.Date;

import com.born.fcs.pm.ws.info.common.SimpleFormProjectInfo;
import com.yjf.common.lang.util.money.Money;

public class FProjectCounterGuaranteeGuarantorInfo extends SimpleFormProjectInfo {
	
	private static final long serialVersionUID = 1254838511251529543L;
	
	private long id;
	
	private String guarantor;
	
	private String legalPersion;
	
	private Money registerCapital = new Money(0, 0);
	
	private Money totalAsset = new Money(0, 0);
	
	private Money externalGuaranteeAmount = new Money(0, 0);
	
	private int sortOrder;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getGuarantor() {
		return this.guarantor;
	}

	public void setGuarantor(String guarantor) {
		this.guarantor = guarantor;
	}

	public String getLegalPersion() {
		return this.legalPersion;
	}

	public void setLegalPersion(String legalPersion) {
		this.legalPersion = legalPersion;
	}

	public Money getRegisterCapital() {
		return this.registerCapital;
	}

	public void setRegisterCapital(Money registerCapital) {
		this.registerCapital = registerCapital;
	}

	public Money getTotalAsset() {
		return this.totalAsset;
	}

	public void setTotalAsset(Money totalAsset) {
		this.totalAsset = totalAsset;
	}

	public Money getExternalGuaranteeAmount() {
		return this.externalGuaranteeAmount;
	}

	public void setExternalGuaranteeAmount(Money externalGuaranteeAmount) {
		this.externalGuaranteeAmount = externalGuaranteeAmount;
	}

	public int getSortOrder() {
		return this.sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
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
