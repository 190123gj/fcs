package com.born.fcs.face.integration.util;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.yjf.common.lang.util.money.Money;

/**
 * 外币转换成人民币结果
 * @author wuzj
 */
public class CnyResult extends FcsBaseResult {
	
	private static final long serialVersionUID = 2646911770245260302L;
	
	/** 人民币金额 */
	private Money amount;
	/*** 外币金额 */
	private String currencyAmount;
	/** 外币代码 */
	private String currencyCode;
	/** 汇率 */
	private String exchangeRate;
	/** 汇率更新时间 */
	private Date exchangeTime;
	
	public Money getAmount() {
		return this.amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public String getCurrencyAmount() {
		return this.currencyAmount;
	}
	
	public void setCurrencyAmount(String currencyAmount) {
		this.currencyAmount = currencyAmount;
	}
	
	public String getCurrencyCode() {
		return this.currencyCode;
	}
	
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	public String getExchangeRate() {
		return this.exchangeRate;
	}
	
	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	
	public Date getExchangeTime() {
		return this.exchangeTime;
	}
	
	public void setExchangeTime(Date exchangeTime) {
		this.exchangeTime = exchangeTime;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
