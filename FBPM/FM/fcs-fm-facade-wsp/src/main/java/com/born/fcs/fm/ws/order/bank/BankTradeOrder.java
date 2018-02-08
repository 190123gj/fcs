package com.born.fcs.fm.ws.order.bank;

import java.util.Date;

import org.springframework.util.Assert;

import com.born.fcs.fm.ws.enums.FundDirectionEnum;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 银行交易流水新增Order
 * @author wuzj
 */
public class BankTradeOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -9023734018623959995L;
	
	/** 交易账号 */
	private String accountNo;
	/** 外部流水号(付款单、收款单等编号) */
	private String outBizNo;
	/** 外部流水号明细（付款单、收款单等费用明细ID） */
	private String outBizDetailNo;
	/** 资金流向 IN/OUT */
	private FundDirectionEnum fundDirection;
	/** 发生金额 */
	private Money amount = new Money(0, 0);
	/** 交易时间 */
	private Date tradeTime;
	/** 备注 */
	private String remark;
	/** 银行账号不存在时忽略 */
	private boolean ignoreAccountIfNotExist;
	
	@Override
	public void check() {
		if (!ignoreAccountIfNotExist) {
			validateNotNull(accountNo, "账号");
			validateNotNull(fundDirection, "资金流向");
			validateNotNull(tradeTime, "交易时间");
			Assert.isTrue(amount.getCent() > 0, "交易金额不能小于0");
		}
	}
	
	public String getAccountNo() {
		return accountNo;
	}
	
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	public String getOutBizNo() {
		return outBizNo;
	}
	
	public void setOutBizNo(String outBizNo) {
		this.outBizNo = outBizNo;
	}
	
	public String getOutBizDetailNo() {
		return outBizDetailNo;
	}
	
	public void setOutBizDetailNo(String outBizDetailNo) {
		this.outBizDetailNo = outBizDetailNo;
	}
	
	public FundDirectionEnum getFundDirection() {
		return fundDirection;
	}
	
	public void setFundDirection(FundDirectionEnum fundDirection) {
		this.fundDirection = fundDirection;
	}
	
	public Money getAmount() {
		return amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public Date getTradeTime() {
		return tradeTime;
	}
	
	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public boolean isIgnoreAccountIfNotExist() {
		return ignoreAccountIfNotExist;
	}
	
	public void setIgnoreAccountIfNotExist(boolean ignoreAccountIfNotExist) {
		this.ignoreAccountIfNotExist = ignoreAccountIfNotExist;
	}
	
}
