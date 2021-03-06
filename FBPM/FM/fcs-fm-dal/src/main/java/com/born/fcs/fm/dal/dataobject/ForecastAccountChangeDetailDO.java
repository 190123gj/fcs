/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.fm.dal.dataobject;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

// auto generated imports
import com.yjf.common.lang.util.money.Money;
import java.util.Date;

/**
 * A data object class directly models database table <tt>forecast_account_change_detail</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/forecast_account_change_detail.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */
public class ForecastAccountChangeDetailDO implements Serializable{
	/** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -4282603875229233564L;

    //========== properties ==========

	private long id;

	private long forecastId;

	private long userId;

	private String userAccount;

	private String userName;

	private String forecastMemo;

	private Date orignalDate;

	private Date newDate;

	private Money occurAmount = new Money(0, 0);

	private Money originalAmount = new Money(0, 0);

	private Money amount = new Money(0, 0);

	private String isManual;

	private Date rawAddTime;

	private Date rawUpdateTime;

    //========== getters and setters ==========

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
		if (occurAmount == null) {
			this.occurAmount = new Money(0, 0);
		} else {
			this.occurAmount = occurAmount;
		}
	}

	public Money getOriginalAmount() {
		return originalAmount;
	}
	
	public void setOriginalAmount(Money originalAmount) {
		if (originalAmount == null) {
			this.originalAmount = new Money(0, 0);
		} else {
			this.originalAmount = originalAmount;
		}
	}

	public Money getAmount() {
		return amount;
	}
	
	public void setAmount(Money amount) {
		if (amount == null) {
			this.amount = new Money(0, 0);
		} else {
			this.amount = amount;
		}
	}

	public String getIsManual() {
		return isManual;
	}
	
	public void setIsManual(String isManual) {
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


	/**
     * @return
     *
     * @see java.lang.Object#toString()
     */
	@Override
    public String toString() {

        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
