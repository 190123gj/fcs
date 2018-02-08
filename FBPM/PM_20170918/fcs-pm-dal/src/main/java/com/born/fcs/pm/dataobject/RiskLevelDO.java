package com.born.fcs.pm.dataobject;

import com.born.fcs.pm.ws.enums.CheckPhaseEnum;

/**
 * 风险评级列表
 * 
 * @author lirz
 *
 * 2016-5-19 上午10:40:58
 */
public class RiskLevelDO extends SimpleFormProjectDO {
	
	private static final long serialVersionUID = -3441744228195738031L;
	
	private long riskLevelId; //主键
	private long reevaluationId; //复评人id
	private String evaluationLevel; //初评等级
	private String reevaluationLevel; //复评等级
	private String checkLevel; //复评等级
	private String checkPhase; //阶段
	
	public long getRiskLevelId() {
		return riskLevelId;
	}
	public void setRiskLevelId(long riskLevelId) {
		this.riskLevelId = riskLevelId;
	}
	public long getReevaluationId() {
		return reevaluationId;
	}
	public void setReevaluationId(long reevaluationId) {
		this.reevaluationId = reevaluationId;
	}
	public String getEvaluationLevel() {
		return evaluationLevel;
	}
	public void setEvaluationLevel(String evaluationLevel) {
		this.evaluationLevel = evaluationLevel;
	}
	public String getReevaluationLevel() {
		return reevaluationLevel;
	}
	public void setReevaluationLevel(String reevaluationLevel) {
		this.reevaluationLevel = reevaluationLevel;
	}
	public String getCheckLevel() {
		return this.checkLevel;
	}
	public void setCheckLevel(String checkLevel) {
		this.checkLevel = checkLevel;
	}
	public String getCheckPhase() {
		return checkPhase;
	}
	public void setCheckPhase(String checkPhase) {
		this.checkPhase = checkPhase;
	}
	
}
