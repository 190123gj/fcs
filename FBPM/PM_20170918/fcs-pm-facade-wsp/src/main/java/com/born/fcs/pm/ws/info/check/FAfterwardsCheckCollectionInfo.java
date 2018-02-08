package com.born.fcs.pm.ws.info.check;

import java.util.Date;

import com.born.fcs.pm.ws.enums.CollectCodeEnum;
import com.born.fcs.pm.ws.enums.CollectTypeEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

public class FAfterwardsCheckCollectionInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 2732496113870881289L;
	
	private long collectId;
	private long formId;
	private CollectTypeEnum collectType;
	private String collectItem;
	private CollectCodeEnum collectCode;
	private String collected;
	private int sortOrder;
	private Date rawAddTime;
	private Date rawUpdateTime;
	
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
	
	public CollectTypeEnum getCollectType() {
		return collectType;
	}
	
	public void setCollectType(CollectTypeEnum collectType) {
		this.collectType = collectType;
	}
	
	public String getCollectItem() {
		return collectItem;
	}
	
	public void setCollectItem(String collectItem) {
		this.collectItem = collectItem;
	}
	
	public CollectCodeEnum getCollectCode() {
		return collectCode;
	}
	
	public void setCollectCode(CollectCodeEnum collectCode) {
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
