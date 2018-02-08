package com.born.fcs.pm.ws.order.check;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

public class FAfterwardsCheckCollectionOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -5056066396298767700L;
	
	private long collectId;
	private long formId;
	private String collectType;
	private String collectItem;
	private String collectCode;
	private String collected;
	private int sortOrder;
	
	public long getCollectId() {
		return collectId;
	}
	
	public void setCollectId(long collectId) {
		this.collectId = collectId;
	}
	
	public long getFormId() {
		return formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public String getCollectType() {
		return collectType;
	}
	
	public void setCollectType(String collectType) {
		this.collectType = collectType;
	}
	
	public String getCollectItem() {
		return collectItem;
	}
	
	public void setCollectItem(String collectItem) {
		this.collectItem = collectItem;
	}
	
	public String getCollectCode() {
		return collectCode;
	}
	
	public void setCollectCode(String collectCode) {
		this.collectCode = collectCode;
	}
	
	public String getCollected() {
		return collected;
	}
	
	public void setCollected(String collected) {
		this.collected = collected;
	}
	
	public int getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	@Override
    public String toString() {

        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
