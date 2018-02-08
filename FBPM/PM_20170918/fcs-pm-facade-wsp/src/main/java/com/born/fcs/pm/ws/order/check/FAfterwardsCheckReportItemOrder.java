/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.order.check;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 
 * 保后检查报告 核实情况 反担保措施 其他重要事项检查 预警信息或关注事项
 * 
 * @author lirz
 *
 * 2016-6-4 下午2:52:05
 */
public class FAfterwardsCheckReportItemOrder extends ValidateOrderBase{

	private static final long serialVersionUID = 6376650554730404976L;

	private long itemId;
	private long formId;
	private String itemType;
	private String itemName;
	private String itemValue1;
	private String itemValue2;
	private String itemValue3;
	private String itemDesc;
	private String delAble;
	private int sortOrder;
	
	public boolean isNull() {
		return isNull(itemName)
				&& isNull(itemValue1)
				&& isNull(itemValue2)
				&& isNull(itemValue3)
				&& isNull(itemDesc)
				&& isNull(delAble)
				;
	}

    //========== getters and setters ==========

	public long getItemId() {
		return itemId;
	}
	
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public long getFormId() {
		return formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}

	public String getItemType() {
		return itemType;
	}
	
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getItemName() {
		return itemName;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemValue1() {
		return itemValue1;
	}
	
	public void setItemValue1(String itemValue1) {
		this.itemValue1 = itemValue1;
	}

	public String getItemValue2() {
		return itemValue2;
	}
	
	public void setItemValue2(String itemValue2) {
		this.itemValue2 = itemValue2;
	}

	public String getItemValue3() {
		return itemValue3;
	}
	
	public void setItemValue3(String itemValue3) {
		this.itemValue3 = itemValue3;
	}

	public String getItemDesc() {
		return itemDesc;
	}
	
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public int getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getDelAble() {
		return delAble;
	}

	public void setDelAble(String delAble) {
		this.delAble = delAble;
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
