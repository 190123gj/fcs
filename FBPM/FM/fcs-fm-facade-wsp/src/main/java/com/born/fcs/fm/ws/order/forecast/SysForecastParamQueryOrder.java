/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午6:50:42 创建
 */
package com.born.fcs.fm.ws.order.forecast;

import com.born.fcs.fm.ws.enums.ForecastTypeEnum;
import com.born.fcs.fm.ws.enums.FundDirectionEnum;
import com.born.fcs.pm.ws.base.QueryPageBase;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
public class SysForecastParamQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = 1L;
	
	///// 通过资金流向和预测类型进行更新
	
	/** 资金流向 IN/OUT */
	private FundDirectionEnum fundDirection;
	/** 预测类型 */
	private ForecastTypeEnum forecastType;
	
	public FundDirectionEnum getFundDirection() {
		return this.fundDirection;
	}
	
	public void setFundDirection(FundDirectionEnum fundDirection) {
		this.fundDirection = fundDirection;
	}
	
	public ForecastTypeEnum getForecastType() {
		return this.forecastType;
	}
	
	public void setForecastType(ForecastTypeEnum forecastType) {
		this.forecastType = forecastType;
	}
	
	//	/** 预测时间 */
	//	private String forecastTime;
	//	/** 预测时间单位 */
	//	private String forecastTimeType;
	//	/** 额外预测时间 */
	//	private String forecastOtherTime;
	//	/** 额外预测时间单位 */
	//	private String forecastOtherTimeType;
	
}
