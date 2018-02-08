package com.born.fcs.pm.ws.info.common;

import java.util.Date;

import com.born.fcs.pm.ws.enums.ScaleRuleKpiTypeEnum;

public class EnterpriseScaleRuleInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -6386117970216205812L;
	
	private int ruleId;
	
	private String industryCode;
	
	private String industryName;
	
	private ScaleRuleKpiTypeEnum kpiType;
	
	private String kpiVariable;
	
	private String scaleHugeScript;
	
	private String scaleBigScript;
	
	private String scaleMediumScript;
	
	private String scaleSmallScript;
	
	private String scaleTinyScript;
	
	private String version;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public int getRuleId() {
		return this.ruleId;
	}
	
	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}
	
	public String getIndustryCode() {
		return this.industryCode;
	}
	
	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}
	
	public String getIndustryName() {
		return this.industryName;
	}
	
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	
	public ScaleRuleKpiTypeEnum getKpiType() {
		return this.kpiType;
	}
	
	public void setKpiType(ScaleRuleKpiTypeEnum kpiType) {
		this.kpiType = kpiType;
	}
	
	public String getKpiVariable() {
		return this.kpiVariable;
	}
	
	public void setKpiVariable(String kpiVariable) {
		this.kpiVariable = kpiVariable;
	}
	
	public String getScaleHugeScript() {
		return this.scaleHugeScript;
	}
	
	public void setScaleHugeScript(String scaleHugeScript) {
		this.scaleHugeScript = scaleHugeScript;
	}
	
	public String getScaleBigScript() {
		return this.scaleBigScript;
	}
	
	public void setScaleBigScript(String scaleBigScript) {
		this.scaleBigScript = scaleBigScript;
	}
	
	public String getScaleMediumScript() {
		return this.scaleMediumScript;
	}
	
	public void setScaleMediumScript(String scaleMediumScript) {
		this.scaleMediumScript = scaleMediumScript;
	}
	
	public String getScaleSmallScript() {
		return this.scaleSmallScript;
	}
	
	public void setScaleSmallScript(String scaleSmallScript) {
		this.scaleSmallScript = scaleSmallScript;
	}
	
	public String getScaleTinyScript() {
		return this.scaleTinyScript;
	}
	
	public void setScaleTinyScript(String scaleTinyScript) {
		this.scaleTinyScript = scaleTinyScript;
	}
	
	public String getVersion() {
		return this.version;
	}
	
	public void setVersion(String version) {
		this.version = version;
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
