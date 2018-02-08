package com.born.fcs.pm.ws.order.finvestigation.declare;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * 更新母公司会议类型
 * 
 * @author lirz
 * 
 * 2016-8-16 下午2:27:24
 */
public class UpdateCouncilTypeOrder extends DeclareBaseOrder {
	
	private static final long serialVersionUID = 6478815587147130593L;
	
	//会议类型
	private String councilType;
	
	private String councilStatus;
	
	private long councilApplyId;
	
	public String getCouncilType() {
		return this.councilType;
	}
	
	public void setCouncilType(String councilType) {
		this.councilType = councilType;
	}
	
	public String getCouncilStatus() {
		return councilStatus;
	}
	
	public void setCouncilStatus(String councilStatus) {
		this.councilStatus = councilStatus;
	}
	
	public long getCouncilApplyId() {
		return councilApplyId;
	}
	
	public void setCouncilApplyId(long councilApplyId) {
		this.councilApplyId = councilApplyId;
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
