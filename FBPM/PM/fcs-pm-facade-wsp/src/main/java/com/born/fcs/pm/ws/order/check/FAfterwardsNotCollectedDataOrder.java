package com.born.fcs.pm.ws.order.check;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 
 * 未收集到该资料
 * 
 * @author lirz
 * 
 * 2016-10-26 下午3:04:20
 * 
 */
public class FAfterwardsNotCollectedDataOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -9020175683883695427L;
	
	private long id;
	
	private long formId;
	
	private String amType;
	
	private String remark;
	
	public boolean isNull() {
		return isNull(amType) && isNull(remark);
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getFormId() {
		return formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public String getAmType() {
		return this.amType;
	}
	
	public void setAmType(String amType) {
		this.amType = amType;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
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
