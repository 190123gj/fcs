package com.born.fcs.pm.ws.order.finvestigation;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 尽职调查报告 - 授信方案 - 过程控制(客户主体评级/资产负债率/其他)
 * 
 * @author lirz
 * 
 * 2016-3-21 下午4:02:30
 */
public class FInvestigationCreditSchemeProcessControlOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 2099803699888392971L;
	
	private long id; //主键
	private long formId;
	private String type;
	private String upRate;
	private String upBp;
	private String downRate;
	private String downBp;
	private String content;
	private int sortOrder;
	
	public boolean isNull() {
		return isNull(upRate) 
				&& isNull(upBp) 
				&& isNull(downRate) 
				&& isNull(downBp)
				&& isNull(content);
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getUpRate() {
		return this.upRate;
	}
	
	public void setUpRate(String upRate) {
		this.upRate = upRate;
	}
	
	public String getUpBp() {
		return this.upBp;
	}
	
	public void setUpBp(String upBp) {
		this.upBp = upBp;
	}
	
	public String getDownRate() {
		return this.downRate;
	}
	
	public void setDownRate(String downRate) {
		this.downRate = downRate;
	}
	
	public String getDownBp() {
		return this.downBp;
	}
	
	public void setDownBp(String downBp) {
		this.downBp = downBp;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public int getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
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
