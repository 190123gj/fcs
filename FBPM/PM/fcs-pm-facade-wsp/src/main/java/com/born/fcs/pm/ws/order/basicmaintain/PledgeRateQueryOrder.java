package com.born.fcs.pm.ws.order.basicmaintain;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.base.QueryPageBase;

/**
 * 常用抵质押物明细及抵（质）押率 查询 order
 * 
 * @author lirz
 * 
 * 2016-3-31 下午2:29:53
 */
public class PledgeRateQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = -7678278338524828081L;
	
	private long pledgeRateId;
	private int pledgeLevel;
	private long typeId;
	private long propertyId;
	private String pledgeName;
	private String status;
	
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
	
	public long getTypeId() {
		return typeId;
	}
	
	public void setTypeId(long typeId) {
		this.typeId = typeId;
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
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
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
