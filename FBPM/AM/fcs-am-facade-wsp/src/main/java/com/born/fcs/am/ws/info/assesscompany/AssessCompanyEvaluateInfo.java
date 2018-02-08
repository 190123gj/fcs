package com.born.fcs.am.ws.info.assesscompany;

import java.util.Date;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

public class AssessCompanyEvaluateInfo extends BaseToStringInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1424284006143885289L;

	private long id;

	private long applyId;

	private long companyId;

	private String companyName;

	private long evaluatePerson;

	private String evaluatePersonAccount;

	private String evaluatePersonName;

	private int workSituation;

	private int attachment;

	private int technicalLevel;

	private int evaluationEfficiency;

	private int cooperationSituation;

	private int serviceAttitude;

	private BooleanEnum isReview;

	private String remark;

	private Date rawAddTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getApplyId() {
		return applyId;
	}

	public void setApplyId(long applyId) {
		this.applyId = applyId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public long getEvaluatePerson() {
		return evaluatePerson;
	}

	public void setEvaluatePerson(long evaluatePerson) {
		this.evaluatePerson = evaluatePerson;
	}

	public String getEvaluatePersonAccount() {
		return evaluatePersonAccount;
	}

	public void setEvaluatePersonAccount(String evaluatePersonAccount) {
		this.evaluatePersonAccount = evaluatePersonAccount;
	}

	public String getEvaluatePersonName() {
		return evaluatePersonName;
	}

	public void setEvaluatePersonName(String evaluatePersonName) {
		this.evaluatePersonName = evaluatePersonName;
	}

	public int getWorkSituation() {
		return workSituation;
	}

	public void setWorkSituation(int workSituation) {
		this.workSituation = workSituation;
	}

	public int getAttachment() {
		return attachment;
	}

	public void setAttachment(int attachment) {
		this.attachment = attachment;
	}

	public int getTechnicalLevel() {
		return technicalLevel;
	}

	public void setTechnicalLevel(int technicalLevel) {
		this.technicalLevel = technicalLevel;
	}

	public int getEvaluationEfficiency() {
		return evaluationEfficiency;
	}

	public void setEvaluationEfficiency(int evaluationEfficiency) {
		this.evaluationEfficiency = evaluationEfficiency;
	}

	public int getCooperationSituation() {
		return cooperationSituation;
	}

	public void setCooperationSituation(int cooperationSituation) {
		this.cooperationSituation = cooperationSituation;
	}

	public int getServiceAttitude() {
		return serviceAttitude;
	}

	public void setServiceAttitude(int serviceAttitude) {
		this.serviceAttitude = serviceAttitude;
	}

	public BooleanEnum getIsReview() {
		return isReview;
	}

	public void setIsReview(BooleanEnum isReview) {
		this.isReview = isReview;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getRawAddTime() {
		return rawAddTime;
	}

	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
}
