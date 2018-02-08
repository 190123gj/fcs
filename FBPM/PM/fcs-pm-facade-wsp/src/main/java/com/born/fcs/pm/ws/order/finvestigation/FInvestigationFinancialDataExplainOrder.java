package com.born.fcs.pm.ws.order.finvestigation;

import java.util.List;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 财务数据解释说明
 * 
 * @author lirz
 * 
 * 2016-4-8 下午5:32:00
 */
public class FInvestigationFinancialDataExplainOrder extends ProcessOrder {
	
	private static final long serialVersionUID = 587601937236593001L;
	
	private long id; //被解释的主键ID
	private String itemName; //被解释的科目
	private String itemDate; //被解释的科目时间
	private String itemValue; //被解释的金额(元)
	private String explaination; //解释
	private List<FInvestigationFinancialReviewKpiOrder> kpiExplains; //解释条目
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public String getItemDate() {
		return itemDate;
	}
	
	public void setItemDate(String itemDate) {
		this.itemDate = itemDate;
	}
	
	public String getItemValue() {
		return itemValue;
	}
	
	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}
	
	public String getExplaination() {
		return explaination;
	}
	
	public void setExplaination(String explaination) {
		this.explaination = explaination;
	}
	
	public List<FInvestigationFinancialReviewKpiOrder> getKpiExplains() {
		return kpiExplains;
	}
	
	public void setKpiExplains(List<FInvestigationFinancialReviewKpiOrder> kpiExplains) {
		this.kpiExplains = kpiExplains;
	}
	
}
