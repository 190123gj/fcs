/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.info.check;

import java.util.ArrayList;
import java.util.List;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 
 * 保后检查报告 核实情况 反担保措施 其他重要事项检查 预警信息或关注事项
 * 
 * @author lirz
 * 
 * 2016-6-4 下午2:52:05
 */
public class AfterwardsCheckReportItemInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 7947459510541071581L;
	
	private String itemName;
	private List<FAfterwardsCheckReportItemInfo> itemInfos;
	
	public void add(FAfterwardsCheckReportItemInfo itemInfo) {
		if (null == itemInfos) {
			itemInfos = new ArrayList<>();
		}
		itemInfos.add(itemInfo);
	}
	
	public int getSize() {
		return (null == itemInfos) ? 0 : itemInfos.size();
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public List<FAfterwardsCheckReportItemInfo> getItemInfos() {
		return itemInfos;
	}
	
	public void setItemInfos(List<FAfterwardsCheckReportItemInfo> itemInfos) {
		this.itemInfos = itemInfos;
	}
	
}
