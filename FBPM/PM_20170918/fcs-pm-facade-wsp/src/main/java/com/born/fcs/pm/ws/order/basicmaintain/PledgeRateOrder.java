package com.born.fcs.pm.ws.order.basicmaintain;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 常用抵质押物明细及抵（质）押率
 * 
 * @author lirz
 *
 * 2016-3-31 下午2:29:53
 */
public class PledgeRateOrder extends ProcessOrder {
	
	private static final long serialVersionUID = 728231889271325386L;
	
	private long pledgeRateId;
	private int pledgeLevel;
	private String typeName;
	private Long typeId;
	private String propertyName;
	private Long propertyId;
	private String pledgeName;
	private double rate;
	private String status;
	private Date rawAddTime;
	private Date rawUpdateTime;
	
	//========== getters and setters ==========
	
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
	
	public Long getTypeId() {
		return typeId;
	}
	
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	
	public String getPropertyName() {
		return propertyName;
	}
	
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	
	public Long getPropertyId() {
		return propertyId;
	}
	
	public void setPropertyId(Long propertyId) {
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
