/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午5:06:11 创建
 */
package com.born.fcs.fm.ws.info.forecast;

import java.util.Date;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 变化情况
 * @author wuzj
 */
public class ForecastAccountChangeDetailInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 4640391172362604333L;
	
	private long id;
	
	private long forecastId;
	
	private long userId;
	
	private String userAccount;
	
	private String userName;
	
	private String forecastMemo;
	/** 原预计发生日期 */
	private Date orignalDate;
	/** 预计新发生日期 */
	private Date newDate;
	/** 发生变化金额 */
	private Money occurAmount = new Money(0, 0);
	/** 原金额 */
	private Money originalAmount = new Money(0, 0);
	/** 现金额 */
	private Money amount = new Money(0, 0);
	/** 是否人工调整的 */
	private BooleanEnum isManual;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getForecastId() {
		return forecastId;
	}
	
	public void setForecastId(long forecastId) {
		this.forecastId = forecastId;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getUserAccount() {
		return userAccount;
	}
	
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getForecastMemo() {
		return forecastMemo;
	}
	
	public void setForecastMemo(String forecastMemo) {
		this.forecastMemo = forecastMemo;
	}
	
	public Date getOrignalDate() {
		return orignalDate;
	}
	
	public void setOrignalDate(Date orignalDate) {
		this.orignalDate = orignalDate;
	}
	
	public Date getNewDate() {
		return newDate;
	}
	
	public void setNewDate(Date newDate) {
		this.newDate = newDate;
	}
	
	public Money getOccurAmount() {
		return occurAmount;
	}
	
	public void setOccurAmount(Money occurAmount) {
		this.occurAmount = occurAmount;
	}
	
	public Money getOriginalAmount() {
		return originalAmount;
	}
	
	public void setOriginalAmount(Money originalAmount) {
		this.originalAmount = originalAmount;
	}
	
	public Money getAmount() {
		return amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public BooleanEnum getIsManual() {
		return isManual;
	}
	
	public void setIsManual(BooleanEnum isManual) {
		this.isManual = isManual;
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
