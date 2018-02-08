package com.born.fcs.pm.ws.info.basicmaintain;

import java.util.Date;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 常用抵质押物明细及抵（质）押率
 * 
 * @author lirz
 *
 * 2016-3-31 下午2:29:53
 */
public class PledgeRateInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -3250755123444424016L;
	
	private long pledgeRateId; //主键
	private int pledgeLevel; // 层级
	private String typeName; //分类名称(一级)
	private long typeId; //第二级所属分类的id
	private String propertyName; //第二级名称
	private long propertyId; //第三所属第二级分类的id
	private String pledgeName; //第三级名称
	private double rate; //抵押率
	private String status; //状态
	private Date rawAddTime;
	private Date rawUpdateTime;
	
	public long getPledgeRateId() {
		return pledgeRateId;
	}
	
	public void setPledgeRateId(long pledgeRateId) {
		this.pledgeRateId = pledgeRateId;
	}
	
	public int getPledgeLevel() {
		return pledgeLevel;
	}
	
	public void setPledgeLevel(int pledgeLevel) {
		this.pledgeLevel = pledgeLevel;
	}
	
	public String getTypeName() {
		return typeName;
	}
	
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public long getTypeId() {
		return typeId;
	}
	
	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}
	
	public String getPropertyName() {
		return propertyName;
	}
	
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	
	public long getPropertyId() {
		return propertyId;
	}
	
	public void setPropertyId(long propertyId) {
		this.propertyId = propertyId;
	}
	
	public String getPledgeName() {
		return pledgeName;
	}
	
	public void setPledgeName(String pledgeName) {
		this.pledgeName = pledgeName;
	}
	
	public double getRate() {
		return rate;
	}
	
	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
