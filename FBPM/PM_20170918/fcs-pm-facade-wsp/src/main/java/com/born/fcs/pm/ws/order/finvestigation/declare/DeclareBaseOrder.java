package com.born.fcs.pm.ws.order.finvestigation.declare;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 
 * 尽调申明 base order
 * 
 * @author lirz
 * 
 * 2016-8-16 下午2:20:05
 */
public class DeclareBaseOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 2741644533562856716L;
	//主键ID
	private long investigateId;
	
	@Override
	public void check() {
		validateGreaterThan(investigateId, "id");
	}
	
	public long getInvestigateId() {
		return investigateId;
	}
	
	public void setInvestigateId(long investigateId) {
		this.investigateId = investigateId;
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
