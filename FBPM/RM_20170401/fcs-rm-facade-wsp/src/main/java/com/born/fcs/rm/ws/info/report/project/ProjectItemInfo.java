package com.born.fcs.rm.ws.info.report.project;

import java.util.Date;

import com.born.fcs.rm.ws.enums.ProjectItemTypeEnum;
import com.born.fcs.rm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 项目情况信息
 * 
 * @author lirz
 * 
 * 2016-8-9 下午6:28:32
 */
public class ProjectItemInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 1958280241919648334L;
	/** 主键 */
	private long projectItemId;
	/** 报告ID */
	private long reportId;
	/** 指标分类 */
	private ProjectItemTypeEnum itemType;
	/** 指标名称 */
	private String itemName;
	/** 指标单位 */
	private String itemUnit;
	/** 金额 */
	private Money amount;
	/** 排序号 */
	private int sortOrder;
	/** 本月发生时间 */
	private Date itemTime;
	/** 创建时间 */
	private Date rawAddTime;
	/** 修改时间 */
	private Date rawUpdateTime;
	
	public long getProjectItemId() {
		return projectItemId;
	}
	
	public void setProjectItemId(long projectItemId) {
		this.projectItemId = projectItemId;
	}
	
	public long getReportId() {
		return reportId;
	}
	
	public void setReportId(long reportId) {
		this.reportId = reportId;
	}
	
	public ProjectItemTypeEnum getItemType() {
		return itemType;
	}
	
	public void setItemType(ProjectItemTypeEnum itemType) {
		this.itemType = itemType;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public String getItemUnit() {
		return itemUnit;
	}
	
	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}
	
	public Money getAmount() {
		return amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public int getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public Date getItemTime() {
		return itemTime;
	}
	
	public void setItemTime(Date itemTime) {
		this.itemTime = itemTime;
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
