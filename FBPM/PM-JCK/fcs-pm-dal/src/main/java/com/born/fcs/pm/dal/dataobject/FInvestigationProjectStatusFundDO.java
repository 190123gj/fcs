/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dal.dataobject;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

// auto generated imports
import com.yjf.common.lang.util.money.Money;
import java.util.Date;

/**
 * A data object class directly models database table <tt>f_investigation_project_status_fund</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/f_investigation_project_status_fund.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */
public class FInvestigationProjectStatusFundDO implements Serializable{
	/** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -4282603875229233564L;

    //========== properties ==========

	private long id;

	private long statusId;

	private String item;

	private String itemCode;

	private Money itemAmount = new Money(0, 0);

	private double itemPercent;

	private String fundSource;

	private String fundCode;

	private Money fundAmount = new Money(0, 0);

	private double fundPercent;

	private int sortOrder;

	private Date rawAddTime;

	private Date rawUpdateTime;

    //========== getters and setters ==========

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public long getStatusId() {
		return statusId;
	}
	
	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}

	public String getItem() {
		return item;
	}
	
	public void setItem(String item) {
		this.item = item;
	}

	public String getItemCode() {
		return itemCode;
	}
	
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public Money getItemAmount() {
		return itemAmount;
	}
	
	public void setItemAmount(Money itemAmount) {
		if (itemAmount == null) {
			this.itemAmount = new Money(0, 0);
		} else {
			this.itemAmount = itemAmount;
		}
	}

	public double getItemPercent() {
		return itemPercent;
	}
	
	public void setItemPercent(double itemPercent) {
		this.itemPercent = itemPercent;
	}

	public String getFundSource() {
		return fundSource;
	}
	
	public void setFundSource(String fundSource) {
		this.fundSource = fundSource;
	}

	public String getFundCode() {
		return fundCode;
	}
	
	public void setFundCode(String fundCode) {
		this.fundCode = fundCode;
	}

	public Money getFundAmount() {
		return fundAmount;
	}
	
	public void setFundAmount(Money fundAmount) {
		if (fundAmount == null) {
			this.fundAmount = new Money(0, 0);
		} else {
			this.fundAmount = fundAmount;
		}
	}

	public double getFundPercent() {
		return fundPercent;
	}
	
	public void setFundPercent(double fundPercent) {
		this.fundPercent = fundPercent;
	}

	public int getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
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
