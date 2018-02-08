package com.born.fcs.pm.ws.info.check;

import java.util.Date;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

public class FAfterwardsCheckContentCounterGuaranteeItemInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -7568218974710253519L;
	
	private long id;
	
	private long cgId;
	
	private String type;
	
	private String itemType;
	
	private String itemTypeDesc;
	
	private BooleanEnum itemValue;
	
	private String remark;
	
	private int sortOrder;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getCgId() {
		return this.cgId;
	}
	
	public void setCgId(long cgId) {
		this.cgId = cgId;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getItemType() {
		return this.itemType;
	}
	
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	
	public String getItemTypeDesc() {
		return this.itemTypeDesc;
	}
	
	public void setItemTypeDesc(String itemTypeDesc) {
		this.itemTypeDesc = itemTypeDesc;
	}
	
	public BooleanEnum getItemValue() {
		return this.itemValue;
	}
	
	public void setItemValue(BooleanEnum itemValue) {
		this.itemValue = itemValue;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public int getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public Date getRawAddTime() {
		return this.rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return this.rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
}
