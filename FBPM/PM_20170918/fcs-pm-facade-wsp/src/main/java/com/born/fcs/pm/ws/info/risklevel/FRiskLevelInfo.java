package com.born.fcs.pm.ws.info.risklevel;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.enums.CheckPhaseEnum;
import com.born.fcs.pm.ws.enums.RiskLevelEnum;
import com.born.fcs.pm.ws.info.common.SimpleFormProjectInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 风险评级
 * 
 * @author lirz
 * 
 * 2016-5-18 下午8:47:03
 */
public class FRiskLevelInfo extends SimpleFormProjectInfo {
	
	private static final long serialVersionUID = 7705847216654040714L;

	private long id;
	private String busiType;
	private String busiTypeName;
	private Money creditAmount = new Money(0, 0);
	private double evaluation;
	private RiskLevelEnum evaluationLevel;
	private long reevaluationId;
	private String reevaluationAccount;
	private String reevaluationName;
	private double reevaluation;
	private RiskLevelEnum reevaluationLevel;
	private String enterpriseType;
	private String projectType;
	private RiskLevelEnum checkLevel1;
	private RiskLevelEnum checkLevel2;
	private RiskLevelEnum checkLevel3;
	private RiskLevelEnum checkLevel;
	private Date rawAddTime;
	private Date rawUpdateTime;
	
	private String hasEvaluationDesc; //使用初评特别说明:YES/NO
	private String evaluationDesc; //初评特别说明
	private String hasReevaluationDesc; //使用复评特别说明
	private String reevaluationDesc; //复评特别说明
	private CheckPhaseEnum checkPhase; //阶段
	
	//企业评分
	private List<FRiskLevelScoreTemplateInfo> enterprise;
	//项目评分
	private List<FRiskLevelScoreTemplateInfo> project;
	//反担保评分
	private List<FRiskLevelScoreTemplateInfo> counter;
	
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
	
	public double getEvaluation() {
		return evaluation;
	}
	
	public void setEvaluation(double evaluation) {
		this.evaluation = evaluation;
	}
	
	public RiskLevelEnum getEvaluationLevel() {
		return evaluationLevel;
	}
	
	public void setEvaluationLevel(RiskLevelEnum evaluationLevel) {
		this.evaluationLevel = evaluationLevel;
	}
	
	public long getReevaluationId() {
		return reevaluationId;
	}
	
	public void setReevaluationId(long reevaluationId) {
		this.reevaluationId = reevaluationId;
	}
	
	public String getReevaluationAccount() {
		return reevaluationAccount;
	}
	
	public void setReevaluationAccount(String reevaluationAccount) {
		this.reevaluationAccount = reevaluationAccount;
	}
	
	public String getReevaluationName() {
		return reevaluationName;
	}
	
	public void setReevaluationName(String reevaluationName) {
		this.reevaluationName = reevaluationName;
	}
	
	public double getReevaluation() {
		return reevaluation;
	}
	
	public void setReevaluation(double reevaluation) {
		this.reevaluation = reevaluation;
	}
	
	public RiskLevelEnum getReevaluationLevel() {
		return reevaluationLevel;
	}
	
	public void setReevaluationLevel(RiskLevelEnum reevaluationLevel) {
		this.reevaluationLevel = reevaluationLevel;
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
	
	public RiskLevelEnum getCheckLevel1() {
		return this.checkLevel1;
	}

	public void setCheckLevel1(RiskLevelEnum checkLevel1) {
		this.checkLevel1 = checkLevel1;
	}

	public RiskLevelEnum getCheckLevel2() {
		return this.checkLevel2;
	}

	public void setCheckLevel2(RiskLevelEnum checkLevel2) {
		this.checkLevel2 = checkLevel2;
	}

	public RiskLevelEnum getCheckLevel3() {
		return this.checkLevel3;
	}

	public void setCheckLevel3(RiskLevelEnum checkLevel3) {
		this.checkLevel3 = checkLevel3;
	}

	public RiskLevelEnum getCheckLevel() {
		return this.checkLevel;
	}

	public void setCheckLevel(RiskLevelEnum checkLevel) {
		this.checkLevel = checkLevel;
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

	public List<FRiskLevelScoreTemplateInfo> getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(List<FRiskLevelScoreTemplateInfo> enterprise) {
		this.enterprise = enterprise;
	}

	public List<FRiskLevelScoreTemplateInfo> getProject() {
		return project;
	}

	public void setProject(List<FRiskLevelScoreTemplateInfo> project) {
		this.project = project;
	}

	public List<FRiskLevelScoreTemplateInfo> getCounter() {
		return counter;
	}

	public void setCounter(List<FRiskLevelScoreTemplateInfo> counter) {
		this.counter = counter;
	}

	public String getHasEvaluationDesc() {
		return this.hasEvaluationDesc;
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
		return this.hasReevaluationDesc;
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

	public CheckPhaseEnum getCheckPhase() {
		return checkPhase;
	}

	public void setCheckPhase(CheckPhaseEnum checkPhase) {
		this.checkPhase = checkPhase;
	}
	
}
