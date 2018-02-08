/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.info.check;

import java.util.Date;

import com.born.fcs.pm.ws.enums.AfterCheckItemTypeEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 
 * 保后检查报告 核实情况 反担保措施 其他重要事项检查 预警信息或关注事项
 * 
 * @author lirz
 *
 * 2016-6-4 下午2:52:05
 */
public class FAfterwardsCheckReportItemInfo extends BaseToStringInfo{

	private static final long serialVersionUID = 7947459510541071581L;
	
	private long itemId;
	private long formId;
	private AfterCheckItemTypeEnum itemType;
	private String itemName;
	private String itemValue1;
	private String itemValue2;
	private String itemValue3;
	private String itemDesc;
	private String delAble;
	private int sortOrder;
	private Date rawAddTime;
	private Date rawUpdateTime;

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

	public AfterCheckItemTypeEnum getItemType() {
		return itemType;
	}
	
	public void setItemType(AfterCheckItemTypeEnum itemType) {
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

	public String getDelAble() {
		return delAble;
	}

	public void setDelAble(String delAble) {
		this.delAble = delAble;
	}

	public int getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}

	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
}
