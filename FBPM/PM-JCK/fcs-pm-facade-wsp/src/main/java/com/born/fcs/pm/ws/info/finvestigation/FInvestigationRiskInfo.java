package com.born.fcs.pm.ws.info.finvestigation;

import java.util.Date;

import com.born.fcs.pm.ws.info.common.SimpleFormProjectInfo;

/**
 * 风险分析 结论意见
 * 
 * @author lirz
 *
 * 2016-3-11 上午9:32:47
 */
public class FInvestigationRiskInfo extends SimpleFormProjectInfo{

	private static final long serialVersionUID = -3872511453365952261L;
	
	private long riskId;
	private String riskAnalysis; //风险点分析
	private String conclusion; //结论意见
	private Date rawAddTime;
	private Date rawUpdateTime;

	public long getRiskId() {
		return riskId;
	}
	
	public void setRiskId(long riskId) {
		this.riskId = riskId;
	}

	public String getRiskAnalysis() {
		return riskAnalysis;
	}
	
	public void setRiskAnalysis(String riskAnalysis) {
		this.riskAnalysis = riskAnalysis;
	}

	public String getConclusion() {
		return conclusion;
	}
	
	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
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
