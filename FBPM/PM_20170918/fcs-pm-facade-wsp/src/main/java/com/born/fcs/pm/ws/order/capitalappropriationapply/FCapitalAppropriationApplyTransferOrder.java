package com.born.fcs.pm.ws.order.capitalappropriationapply;

import com.born.fcs.pm.ws.order.base.ProcessOrder;
import com.yjf.common.lang.util.money.Money;

/**
 * 资金划付申请Order-资金调动
 *
 *
 * @author wuzj
 *
 */
public class FCapitalAppropriationApplyTransferOrder extends ProcessOrder {
	
	private static final long serialVersionUID = 145849898738206232L;
	/** 转入银行 */
	private String inAccount;
	/** 转出银行 */
	private String outAccount;
	/** 转出金额 */
	private Money outAmount = new Money(0, 0);
	/** 备注 */
	private String remark;
	
	public boolean isNull() {
		return isNull(inAccount) && isNull(outAccount) && isNull(outAmount);
	}
	
	public void setOutAmountStr(String outAmountStr) {
		if (outAmountStr != null)
			this.outAmount = Money.amout(outAmountStr);
	}
	
	public String getInAccount() {
		return this.inAccount;
	}
	
	public void setInAccount(String inAccount) {
		this.inAccount = inAccount;
	}
	
	public String getOutAccount() {
		return this.outAccount;
	}
	
	public void setOutAccount(String outAccount) {
		this.outAccount = outAccount;
	}
	
	public Money getOutAmount() {
		return this.outAmount;
	}
	
	public void setOutAmount(Money outAmount) {
		this.outAmount = outAmount;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
