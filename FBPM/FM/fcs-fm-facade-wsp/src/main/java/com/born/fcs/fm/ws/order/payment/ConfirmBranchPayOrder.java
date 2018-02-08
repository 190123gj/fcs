package com.born.fcs.fm.ws.order.payment;

import java.util.Date;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 费用支付分支机构确认付款
 * @author wuzj
 */
public class ConfirmBranchPayOrder extends ProcessOrder {
	
	private static final long serialVersionUID = 6502982610733235522L;
	
	private String billNo;
	
	private Date branchPayTime;
	
	@Override
	public void check() {
		validateNotNull(billNo, "单据号");
	}
	
	public String getBillNo() {
		return billNo;
	}
	
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	
	public Date getBranchPayTime() {
		return branchPayTime;
	}
	
	public void setBranchPayTime(Date branchPayTime) {
		this.branchPayTime = branchPayTime;
	}
	
}
