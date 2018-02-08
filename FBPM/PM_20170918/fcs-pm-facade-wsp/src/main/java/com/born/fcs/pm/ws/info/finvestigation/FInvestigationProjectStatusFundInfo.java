package com.born.fcs.pm.ws.info.finvestigation;

import java.util.Date;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 项目投资与资金筹措
 * 
 * @author lirz
 * 
 * 2016-3-10 下午5:35:04
 */
public class FInvestigationProjectStatusFundInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 5876202072495595026L;
	
	private long id;
	private long statusId;
	private String item; //项目
	private String itemCode; //项目标识
	private Money itemAmount = new Money(0, 0); //项目金额
	private double itemPercent; //项目百分比
	private String fundSource; //资金来源
	private String fundCode; //资金来源标识
	private Money fundAmount = new Money(0, 0); //资金来源金额
	private double fundPercent; //资金来源百分比
	private int sortOrder;
	private Date rawAddTime;
	private Date rawUpdateTime;
	
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
		this.itemAmount = itemAmount;
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
		this.fundAmount = fundAmount;
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
	
}
