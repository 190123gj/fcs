package com.born.fcs.pm.ws.info.risklevel;

import com.born.fcs.pm.ws.enums.CheckPhaseEnum;
import com.born.fcs.pm.ws.enums.RiskLevelEnum;
import com.born.fcs.pm.ws.info.common.SimpleFormProjectVOInfo;

/**
 * 风险评级列表
 * 
 * @author lirz
 * 
 * 2016-5-19 下午1:58:37
 */
public class RiskLevelInfo extends SimpleFormProjectVOInfo {
	
	private static final long serialVersionUID = 2710279923721123786L;
	
	private long riskLevelId; //主键
	private long reevaluationId; //复评人id
	private RiskLevelEnum evaluationLevel; //初评等级
	private RiskLevelEnum reevaluationLevel; //复评等级
	private RiskLevelEnum checkLevel; //最终评级
	private CheckPhaseEnum checkPhase; //阶段
	
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
	
	public RiskLevelEnum getEvaluationLevel() {
		return evaluationLevel;
	}
	
	public void setEvaluationLevel(RiskLevelEnum evaluationLevel) {
		this.evaluationLevel = evaluationLevel;
	}
	
	public RiskLevelEnum getReevaluationLevel() {
		return reevaluationLevel;
	}
	
	public void setReevaluationLevel(RiskLevelEnum reevaluationLevel) {
		this.reevaluationLevel = reevaluationLevel;
	}

	public RiskLevelEnum getCheckLevel() {
		return this.checkLevel;
	}

	public void setCheckLevel(RiskLevelEnum checkLevel) {
		this.checkLevel = checkLevel;
	}

	public CheckPhaseEnum getCheckPhase() {
		return checkPhase;
	}

	public void setCheckPhase(CheckPhaseEnum checkPhase) {
		this.checkPhase = checkPhase;
	}
	
}
