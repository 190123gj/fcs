package com.born.fcs.pm.ws.order.release;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * 更新状态
 * 
 * @author lirz
 * 
 * 2016-9-23 下午5:04:24
 * 
 */
public class UpdateReleaseStatusOrder extends UpdateReleaseBaseOrder {
	
	private static final long serialVersionUID = -4657109592600863938L;
	
	private String status;
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
