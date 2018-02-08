package com.born.fcs.pm.ws.result.financialproject;

import com.born.fcs.pm.ws.result.base.FormBaseResult;

public class FinancialProjectSetupResult extends FormBaseResult {
	
	private static final long serialVersionUID = -4034162401259998887L;
	
	//是否第一次购买
	private boolean firstBuy = true;
	
	public boolean isFirstBuy() {
		return this.firstBuy;
	}
	
	public void setFirstBuy(boolean firstBuy) {
		this.firstBuy = firstBuy;
	}
	
}
