package com.born.fcs.pm.ws.info.risklevel;

import java.util.Date;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 
 * 评分模板
 * 
 * @author lirz
 * 
 * 2016-5-19 下午6:36:58
 */
public class FRiskLevelScoreTemplateInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 458670516094042386L;
	
	private long id;
	private String evaluationType;
	private String indexNo;
	private String index1;
	private double score1;
	private String index2;
	private double score2;
	private int sortOrder;
	private Date rawAddTime;
	private Date rawUpdateTime;
	
	private double evaluation; //初评得分
	private double reevaluation; //复评得分
	private String evaluationReason; //分配完成率M/N变动30%以上具体说明原因
	
	private String idFlag; //小项和
	private String classFlag; //小项的子项
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getEvaluationType() {
		return evaluationType;
	}
	
	public void setEvaluationType(String evaluationType) {
		this.evaluationType = evaluationType;
	}
	
	public String getIndexNo() {
		return indexNo;
	}
	
	public void setIndexNo(String indexNo) {
		this.indexNo = indexNo;
	}
	
	public String getIndex1() {
		return index1;
	}
	
	public void setIndex1(String index1) {
		this.index1 = index1;
	}
	
	public double getScore1() {
		return score1;
	}
	
	public void setScore1(double score1) {
		this.score1 = score1;
	}
	
	public String getIndex2() {
		return index2;
	}
	
	public void setIndex2(String index2) {
		this.index2 = index2;
	}
	
	public double getScore2() {
		return score2;
	}
	
	public void setScore2(double score2) {
		this.score2 = score2;
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

	public String getIdFlag() {
		return idFlag;
	}

	public void setIdFlag(String idFlag) {
		this.idFlag = idFlag;
	}

	public String getClassFlag() {
		return classFlag;
	}

	public void setClassFlag(String classFlag) {
		this.classFlag = classFlag;
	}
	
}
