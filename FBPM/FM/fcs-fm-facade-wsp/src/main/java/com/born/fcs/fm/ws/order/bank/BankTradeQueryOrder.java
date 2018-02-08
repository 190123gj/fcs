package com.born.fcs.fm.ws.order.bank;

import java.util.Date;

import com.born.fcs.fm.ws.enums.FundDirectionEnum;
import com.born.fcs.pm.ws.base.QueryPageBase;

/**
 * 银行交易流水查询Order
 * @author wuzj
 */
public class BankTradeQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = 949675805544551795L;
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
	/** 交易时间 */
	private Date tradeTimeStart;
	/** 交易时间 */
	private Date tradeTimeEnd;
	
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
	
	public Date getTradeTimeStart() {
		return tradeTimeStart;
	}
	
	public void setTradeTimeStart(Date tradeTimeStart) {
		this.tradeTimeStart = tradeTimeStart;
	}
	
	public Date getTradeTimeEnd() {
		return tradeTimeEnd;
	}
	
	public void setTradeTimeEnd(Date tradeTimeEnd) {
		this.tradeTimeEnd = tradeTimeEnd;
	}
	
}
