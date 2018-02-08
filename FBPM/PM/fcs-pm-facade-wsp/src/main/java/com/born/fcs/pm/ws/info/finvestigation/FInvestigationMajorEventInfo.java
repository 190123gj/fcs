package com.born.fcs.pm.ws.info.finvestigation;

import java.util.Date;

/**
 * 重大事项调查
 * 
 * @author lirz
 * 
 * 2016-3-10 下午5:19:46
 */
public class FInvestigationMajorEventInfo extends InvestigationBaseInfo {
	
	private static final long serialVersionUID = -1530708168153186281L;
	
	private long id;
	//其他重点财务情况调查（民间借贷、异常科目等）
	private String financialCondition;
	//多元化投资调查
	private String investment;
	//其他重大事项调查（城市开发类项目对当地经济、财政、支持程度的分析填写本项内容）
	private String otherEvents;
	private Date rawAddTime;
	private Date rawUpdateTime;
	
	//========== getters and setters ==========
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getFinancialCondition() {
		return financialCondition;
	}
	
	public void setFinancialCondition(String financialCondition) {
		this.financialCondition = financialCondition;
	}
	
	public String getInvestment() {
		return investment;
	}
	
	public void setInvestment(String investment) {
		this.investment = investment;
	}
	
	public String getOtherEvents() {
		return otherEvents;
	}
	
	public void setOtherEvents(String otherEvents) {
		this.otherEvents = otherEvents;
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
