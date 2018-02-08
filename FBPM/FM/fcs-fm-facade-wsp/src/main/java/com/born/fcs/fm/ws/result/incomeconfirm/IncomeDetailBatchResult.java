package com.born.fcs.fm.ws.result.incomeconfirm;

import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.money.Money;

public class IncomeDetailBatchResult<T> extends QueryBaseBatchResult<T> {
	
	private static final long serialVersionUID = -3703649161198573248L;
	
	private Money sumConfirmedAmount = new Money(0, 0);
	
	private Money sumEstimatedAmount = new Money(0, 0);
	
	public Money getSumConfirmedAmount() {
		return sumConfirmedAmount;
	}
	
	public void setSumConfirmedAmount(Money sumConfirmedAmount) {
		this.sumConfirmedAmount = sumConfirmedAmount;
	}
	
	public Money getSumEstimatedAmount() {
		return sumEstimatedAmount;
	}
	
	public void setSumEstimatedAmount(Money sumEstimatedAmount) {
		this.sumEstimatedAmount = sumEstimatedAmount;
	}
	
}
