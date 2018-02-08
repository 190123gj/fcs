package com.born.fcs.pm.ws.order.setup;

import java.util.List;

/**
 * 项目立项 - 银行贷款Order
 *
 * @author wuzj
 *
 */
public class FProjectBankLoanBatchOrder extends SetupFormSaveOrder {
	
	private static final long serialVersionUID = -6942066559093712082L;
	
	List<FProjectBankLoanOrder> bankLoans;
	
	public List<FProjectBankLoanOrder> getBankLoans() {
		return this.bankLoans;
	}
	
	public void setBankLoans(List<FProjectBankLoanOrder> bankLoans) {
		this.bankLoans = bankLoans;
	}
}
