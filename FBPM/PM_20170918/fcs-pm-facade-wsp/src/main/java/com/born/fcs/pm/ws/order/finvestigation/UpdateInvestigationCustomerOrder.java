package com.born.fcs.pm.ws.order.finvestigation;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 
 * 更新尽调客户信息
 *
 * @author lirz
 * 
 * 2016-12-12 下午3:24:12
 *
 */
public class UpdateInvestigationCustomerOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 8647118912652949948L;
	
	private long formId;
	private String projectCode;
	private int toIndex = -1;
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}


	public int getToIndex() {
		return this.toIndex;
	}

	public void setToIndex(int toIndex) {
		this.toIndex = toIndex;
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
