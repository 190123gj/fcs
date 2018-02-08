package com.born.fcs.pm.ws.result.summary;

import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 保后汇总
 * @author ji
 * 
 */
public class AfterwardsProjectSummaryResult extends FcsBaseResult {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2930982082134004612L;
	
	private int hs;//户数
	
	private Money amount = new Money(0, 0);
	
	public int getHs() {
		return hs;
	}
	
	public void setHs(int hs) {
		this.hs = hs;
	}
	
	public Money getAmount() {
		return amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
}
