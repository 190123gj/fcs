package com.born.fcs.pm.ws.order.risklevel;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 风险评分详细
 * 
 * @author lirz
 * 
 * 2016-5-18 下午8:49:58
 */
public class FRiskLevelDetailOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -8692188530276015719L;
	private Long id;
	private Long riskLevelId;
	private Long scoreTemplateId;
	private Double evaluation;
	private Double reevaluation;
	private String evaluationReason;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getRiskLevelId() {
		return riskLevelId;
	}
	
	public void setRiskLevelId(Long riskLevelId) {
		this.riskLevelId = riskLevelId;
	}
	
	public Long getScoreTemplateId() {
		return scoreTemplateId;
	}
	
	public void setScoreTemplateId(Long scoreTemplateId) {
		this.scoreTemplateId = scoreTemplateId;
	}
	
	public Double getEvaluation() {
		return isNull(evaluation) ? 0d : evaluation.doubleValue();
	}
	
	public void setEvaluation(Double evaluation) {
		this.evaluation = evaluation;
	}
	
	public Double getReevaluation() {
		return isNull(reevaluation) ? 0d : reevaluation.doubleValue();
	}
	
	public void setReevaluation(Double reevaluation) {
		this.reevaluation = reevaluation;
	}
	
	public String getEvaluationReason() {
		return evaluationReason;
	}
	
	public void setEvaluationReason(String evaluationReason) {
		this.evaluationReason = evaluationReason;
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
