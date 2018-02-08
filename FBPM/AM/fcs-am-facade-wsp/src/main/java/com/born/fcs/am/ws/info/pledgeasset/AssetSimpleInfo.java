/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.am.ws.info.pledgeasset;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 尽调所需要的查询列表数据
 * 
 * @author lirz
 *
 * 2016-8-23 下午6:16:12
 */
public class AssetSimpleInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -802105434824508935L;
	
	private long assetsId;
	
	private long typeId;
	
	private String assetType;
	
	private String assetTypeDesc;
	
	private long ownershipId;
	
	private String ownershipName;
	
	private String isCustomer;
	
	private String warrantNo;
	
	private Money evaluationPrice = new Money(0, 0);
	
	private String status;
	
	private double pledgeRate;
	
	private Money mortgagePrice = new Money(0, 0);
	
	private String assetRemarkInfo;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	// ========== getters and setters ==========
	
	public long getAssetsId() {
		return assetsId;
	}
	
	public void setAssetsId(long assetsId) {
		this.assetsId = assetsId;
	}
	
	public long getTypeId() {
		return typeId;
	}
	
	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}
	
	public String getAssetType() {
		return assetType;
	}
	
	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}
	
	public String getAssetTypeDesc() {
		return assetTypeDesc;
	}
	
	public void setAssetTypeDesc(String assetTypeDesc) {
		this.assetTypeDesc = assetTypeDesc;
	}
	
	public long getOwnershipId() {
		return ownershipId;
	}
	
	public void setOwnershipId(long ownershipId) {
		this.ownershipId = ownershipId;
	}
	
	public String getOwnershipName() {
		return ownershipName;
	}
	
	public void setOwnershipName(String ownershipName) {
		this.ownershipName = ownershipName;
	}
	
	public String getIsCustomer() {
		return isCustomer;
	}
	
	public void setIsCustomer(String isCustomer) {
		this.isCustomer = isCustomer;
	}
	
	public String getWarrantNo() {
		return warrantNo;
	}
	
	public void setWarrantNo(String warrantNo) {
		this.warrantNo = warrantNo;
	}
	
	public Money getEvaluationPrice() {
		return evaluationPrice;
	}
	
	public void setEvaluationPrice(Money evaluationPrice) {
		if (evaluationPrice == null) {
			this.evaluationPrice = new Money(0, 0);
		} else {
			this.evaluationPrice = evaluationPrice;
		}
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public double getPledgeRate() {
		return pledgeRate;
	}
	
	public void setPledgeRate(double pledgeRate) {
		this.pledgeRate = pledgeRate;
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
	
	public Money getMortgagePrice() {
		return mortgagePrice;
	}
	
	public void setMortgagePrice(Money mortgagePrice) {
		this.mortgagePrice = mortgagePrice;
	}
	
	public String getAssetRemarkInfo() {
		return assetRemarkInfo;
	}
	
	public void setAssetRemarkInfo(String assetRemarkInfo) {
		this.assetRemarkInfo = assetRemarkInfo;
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
