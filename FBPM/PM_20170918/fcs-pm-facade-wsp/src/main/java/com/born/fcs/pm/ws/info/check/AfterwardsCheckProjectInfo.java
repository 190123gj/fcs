package com.born.fcs.pm.ws.info.check;

import java.util.Date;

import com.born.fcs.pm.ws.enums.CheckReportEditionEnums;
import com.born.fcs.pm.ws.info.common.FormProjectInfo;
import com.yjf.common.lang.util.money.Money;

public class AfterwardsCheckProjectInfo extends FormProjectInfo {
	
	private static final long serialVersionUID = 6708654470011753850L;
	
	private long id;
	
	private Date checkDate;
	
	private String checkAddress;
	
	private CheckReportEditionEnums edition;
	
	private String rounds;
	
	private Money usedAmount = new Money(0, 0);
	
	private Money repayedAmount = new Money(0, 0);
	
	private String useWay;
	
	private Date riskReviewAddTime;
	
	private Date rawUpdateTime;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public Date getCheckDate() {
		return this.checkDate;
	}
	
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	
	public String getRounds() {
		return this.rounds;
	}
	
	public void setRounds(String rounds) {
		this.rounds = rounds;
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
	
	public Date getRiskReviewAddTime() {
		return this.riskReviewAddTime;
	}
	
	public void setRiskReviewAddTime(Date riskReviewAddTime) {
		this.riskReviewAddTime = riskReviewAddTime;
	}
	
	public Date getRawUpdateTime() {
		return this.rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
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
	
	public CheckReportEditionEnums getEdition() {
		return this.edition;
	}
	
	public void setEdition(CheckReportEditionEnums edition) {
		this.edition = edition;
	}
	
}
