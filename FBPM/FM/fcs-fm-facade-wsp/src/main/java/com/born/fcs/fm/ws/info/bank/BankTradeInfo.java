package com.born.fcs.fm.ws.info.bank;

import java.util.Date;

import com.born.fcs.fm.ws.enums.FundDirectionEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 银行交易流水
 * @author wuzj
 */
public class BankTradeInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -1424933880216832214L;
	
	/** 交易ID */
	private long tradeId;
	/** 交易流水号 */
	private String tradeNo;
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
	/** 新增时间 */
	private Date rawAddTime;
	/** 修改时间 */
	private Date rawUpdateTime;
	
	public long getTradeId() {
		return tradeId;
	}
	
	public void setTradeId(long tradeId) {
		this.tradeId = tradeId;
	}
	
	public String getTradeNo() {
		return tradeNo;
	}
	
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
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
	
	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
}
