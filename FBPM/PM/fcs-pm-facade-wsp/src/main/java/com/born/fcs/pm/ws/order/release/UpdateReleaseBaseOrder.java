package com.born.fcs.pm.ws.order.release;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 
 * 更新order
 * 
 * @author lirz
 * 
 * 2016-9-23 下午5:04:24
 * 
 */
public class UpdateReleaseBaseOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -4961132933967592386L;
	
	private String projectCode;
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
