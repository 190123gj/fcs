package com.born.fcs.pm.ws.info.risklevel;

import java.util.Date;

import com.born.fcs.pm.ws.enums.EvaluationTypeEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 风险评分详细
 * 
 * @author lirz
 *
 * 2016-5-18 下午8:49:58
 */
public class FRiskLevelDetailInfo extends BaseToStringInfo{

	private static final long serialVersionUID = 5970732745226934563L;
	private long id;
	private long riskLevelId;
	private EvaluationTypeEnum evaluationType;
	private String detailNo;
	private String evaluationDesc;
	private double score;
	private double evaluation;
	private double reevaluation;
	private String evaluationReason;
	private int sortOrder;
	private Date rawAddTime;
	private Date rawUpdateTime;

    //========== getters and setters ==========

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public long getRiskLevelId() {
		return riskLevelId;
	}
	
	public void setRiskLevelId(long riskLevelId) {
		this.riskLevelId = riskLevelId;
	}

	public EvaluationTypeEnum getEvaluationType() {
		return evaluationType;
	}
	
	public void setEvaluationType(EvaluationTypeEnum evaluationType) {
		this.evaluationType = evaluationType;
	}

	public String getDetailNo() {
		return detailNo;
	}
	
	public void setDetailNo(String detailNo) {
		this.detailNo = detailNo;
	}

	public String getEvaluationDesc() {
		return evaluationDesc;
	}
	
	public void setEvaluationDesc(String evaluationDesc) {
		this.evaluationDesc = evaluationDesc;
	}

	public double getScore() {
		return score;
	}
	
	public void setScore(double score) {
		this.score = score;
	}

	public double getEvaluation() {
		return evaluation;
	}
	
	public void setEvaluation(double evaluation) {
		this.evaluation = evaluation;
	}

	public double getReevaluation() {
		return reevaluation;
	}
	
	public void setReevaluation(double reevaluation) {
		this.reevaluation = reevaluation;
	}

	public String getEvaluationReason() {
		return evaluationReason;
	}
	
	public void setEvaluationReason(String evaluationReason) {
		this.evaluationReason = evaluationReason;
	}

	public int getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
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
