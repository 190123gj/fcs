package com.born.fcs.pm.ws.info.finvestigation;

import java.util.List;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 财务数据解释说明
 * 
 * @author lirz
 * 
 * 2016-4-8 下午5:32:00
 */
public class FInvestigationFinancialDataExplainInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 8294778388689728269L;
	
	private long id; //被解释的主键ID
	private String explaination; //解释
	private List<FInvestigationFinancialReviewKpiInfo> kpiExplains; //解释条目
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getExplaination() {
		return explaination;
	}
	
	public void setExplaination(String explaination) {
		this.explaination = explaination;
	}
	
	public List<FInvestigationFinancialReviewKpiInfo> getKpiExplains() {
		return kpiExplains;
	}
	
	public void setKpiExplains(List<FInvestigationFinancialReviewKpiInfo> kpiExplains) {
		this.kpiExplains = kpiExplains;
	}
	
}
