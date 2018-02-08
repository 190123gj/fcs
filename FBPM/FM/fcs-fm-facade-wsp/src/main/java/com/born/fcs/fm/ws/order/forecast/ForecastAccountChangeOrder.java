package com.born.fcs.fm.ws.order.forecast;

import com.born.fcs.fm.ws.enums.ForecastFeeTypeEnum;
import com.born.fcs.pm.ws.order.base.ProcessOrder;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 预测数据修改
 * @author wuzj
 * 
 */
public class ForecastAccountChangeOrder extends ProcessOrder {
	
	private static final long serialVersionUID = -6741273147486153956L;
	
	/**
	 * 发生项目
	 */
	private String projectCode;
	
	/** 发生费用 */
	private ForecastFeeTypeEnum feeType;
	
	/** 备注 */
	private String forecastMemo;
	
	/** 发生金额 */
	private Money amount = Money.zero();
	
	@Override
	public void check() {
		validateNotNull(projectCode, "项目编号");
		validateNotNull(feeType, "费用类型");
	}
	
	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public ForecastFeeTypeEnum getFeeType() {
		return feeType;
	}
	
	public void setFeeType(ForecastFeeTypeEnum feeType) {
		this.feeType = feeType;
	}
	
	public String getForecastMemo() {
		return forecastMemo;
	}
	
	public void setForecastMemo(String forecastMemo) {
		this.forecastMemo = forecastMemo;
	}
	
	public Money getAmount() {
		return amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
}
