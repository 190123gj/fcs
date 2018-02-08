package com.born.fcs.am.ws.order.assesscompany;

import com.born.fcs.pm.ws.order.base.ProcessOrder;

/**
 * 评估公司Order
 *
 * @author jil
 *
 */
public class AssessCompanyEvaluateOrder extends ProcessOrder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5261768715640324198L;

	private Long id;

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

	private String isReview;

	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getIsReview() {
		return isReview;
	}

	public void setIsReview(String isReview) {
		this.isReview = isReview;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
