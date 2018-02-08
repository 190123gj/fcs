package com.born.fcs.rm.ws.order.report.project;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.rm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 项目情况信息
 * 
 * @author lirz
 * 
 * 2016-8-9 下午6:28:32
 */
public class ProjectItemOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -3037023754774907624L;
	/** 主键 */
	private long projectItemId;
	/** 报告ID */
	private long reportId;
	/** 指标分类 */
	private String itemType;
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
