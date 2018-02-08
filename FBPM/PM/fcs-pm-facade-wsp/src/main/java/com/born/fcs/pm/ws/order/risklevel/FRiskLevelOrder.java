package com.born.fcs.pm.ws.order.risklevel;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.order.base.ProjectFormOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 风险评级
 * 
 * @author lirz
 * 
 * 2016-5-18 下午8:47:03
 */
public class FRiskLevelOrder extends ProjectFormOrderBase {
	
	private static final long serialVersionUID = -1747515863282422531L;
	
	private long id;
	private String busiType;
	private String busiTypeName;
	private Money creditAmount = new Money(0, 0);
	private Double evaluation;
	private Double reevaluation;
	private String enterpriseType;
	private String projectType;
	//企业评分
	private List<FRiskLevelDetailOrder> enterprise;
	//项目评分
	private List<FRiskLevelDetailOrder> project;
	//反担保评分
	private List<FRiskLevelDetailOrder> counter;
	
	private String phases = "E"; //评级阶段(初评/复评)  E 初评,RE 表复评 
	
	private String evaluationLevel; //初评等级
	private String hasEvaluationDesc; //使用初评特别说明:YES/NO
	private String evaluationDesc; //初评特别说明
	private String reevaluationLevel; //复评等级
	private String hasReevaluationDesc; //使用复评特别说明
	private String reevaluationDesc; //复评特别说明
	
	/**
	 * 判断是否处于复评阶段
	 * 
	 * @return
	 */
	public boolean isRe() {
		return (null != this.phases && "RE".equals(this.phases));
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getBusiType() {
		return busiType;
	}
	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	public String getBusiTypeName() {
		return busiTypeName;
	}
	
	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}
	
	public Money getCreditAmount() {
		return creditAmount;
	}
	
	public void setCreditAmount(Money creditAmount) {
		this.creditAmount = creditAmount;
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
	
	public String getEnterpriseType() {
		return enterpriseType;
	}
	
	public void setEnterpriseType(String enterpriseType) {
		this.enterpriseType = enterpriseType;
	}
	
	public String getProjectType() {
		return projectType;
	}
	
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	
	public List<FRiskLevelDetailOrder> getEnterprise() {
		return enterprise;
	}
	
	public void setEnterprise(List<FRiskLevelDetailOrder> enterprise) {
		this.enterprise = enterprise;
	}
	
	public List<FRiskLevelDetailOrder> getProject() {
		return project;
	}
	
	public void setProject(List<FRiskLevelDetailOrder> project) {
		this.project = project;
	}
	
	public List<FRiskLevelDetailOrder> getCounter() {
		return counter;
	}
	
	public void setCounter(List<FRiskLevelDetailOrder> counter) {
		this.counter = counter;
	}
	
	public String getPhases() {
		return phases;
	}
	
	public void setPhases(String phases) {
		this.phases = phases;
	}
	
	public String getHasEvaluationDesc() {
		if (BooleanEnum.YES.code().equals(this.hasEvaluationDesc)) {
			return BooleanEnum.YES.code();
		}
		return BooleanEnum.NO.code();
	}

	public void setHasEvaluationDesc(String hasEvaluationDesc) {
		this.hasEvaluationDesc = hasEvaluationDesc;
	}

	public String getEvaluationDesc() {
		return this.evaluationDesc;
	}

	public void setEvaluationDesc(String evaluationDesc) {
		this.evaluationDesc = evaluationDesc;
	}

	public String getHasReevaluationDesc() {
		if (BooleanEnum.YES.code().equals(this.hasReevaluationDesc)) {
			return BooleanEnum.YES.code();
		}
		return BooleanEnum.NO.code();		
	}

	public void setHasReevaluationDesc(String hasReevaluationDesc) {
		this.hasReevaluationDesc = hasReevaluationDesc;
	}

	public String getReevaluationDesc() {
		return this.reevaluationDesc;
	}

	public void setReevaluationDesc(String reevaluationDesc) {
		this.reevaluationDesc = reevaluationDesc;
	}

	public String getEvaluationLevel() {
		return this.evaluationLevel;
	}

	public void setEvaluationLevel(String evaluationLevel) {
		this.evaluationLevel = evaluationLevel;
	}

	public String getReevaluationLevel() {
		return this.reevaluationLevel;
	}

	public void setReevaluationLevel(String reevaluationLevel) {
		this.reevaluationLevel = reevaluationLevel;
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
