package com.born.fcs.pm.ws.order.finvestigation;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 
 * 更新复评意见
 * 
 * @author lirz
 * 
 * 2016-9-19 下午5:35:29
 * 
 */
public class UpdateInvestigationCreditSchemePledgeAssetRemarkOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -1356891398291131065L;
	
	private long id;
	private String remark;
	
	@Override
	public void check() {
		validateGreaterThan(id, "资产ID");
		validateHasText(remark, "复评意见");
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getRemark() {
		return this.remark;
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
