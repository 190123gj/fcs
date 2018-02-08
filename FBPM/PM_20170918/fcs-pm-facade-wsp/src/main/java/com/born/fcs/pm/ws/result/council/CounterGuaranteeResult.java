package com.born.fcs.pm.ws.result.council;

import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.yjf.common.lang.util.money.Money;

public class CounterGuaranteeResult extends FcsBaseResult {
	
	private static final long serialVersionUID = 850689278494585946L;
	private Money pledgeAssessPrice = new Money(0L); //抵押评估价格
	private Money pledgePrice = new Money(0L); //抵押价格
	
	private Money mortgageAssessPrice = new Money(0L); //质押评估价格
	private Money mortgagePrice = new Money(0L); //质押价格	
	
	//合计保证额度
	private Money guarantorAmount = new Money(0L);
	
	public Money getPledgeAssessPrice() {
		return this.pledgeAssessPrice;
	}
	
	public void setPledgeAssessPrice(Money pledgeAssessPrice) {
		this.pledgeAssessPrice = pledgeAssessPrice;
	}
	
	public Money getPledgePrice() {
		return this.pledgePrice;
	}
	
	public void setPledgePrice(Money pledgePrice) {
		this.pledgePrice = pledgePrice;
	}
	
	public Money getMortgageAssessPrice() {
		return this.mortgageAssessPrice;
	}
	
	public void setMortgageAssessPrice(Money mortgageAssessPrice) {
		this.mortgageAssessPrice = mortgageAssessPrice;
	}
	
	public Money getMortgagePrice() {
		return this.mortgagePrice;
	}
	
	public void setMortgagePrice(Money mortgagePrice) {
		this.mortgagePrice = mortgagePrice;
	}
	
	public Money getGuarantorAmount() {
		return this.guarantorAmount;
	}
	
	public void setGuarantorAmount(Money guarantorAmount) {
		this.guarantorAmount = guarantorAmount;
	}
	
}
