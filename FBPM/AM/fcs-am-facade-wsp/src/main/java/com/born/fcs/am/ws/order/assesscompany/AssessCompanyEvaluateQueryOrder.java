/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.am.ws.order.assesscompany;

import com.born.fcs.pm.ws.base.QueryPageBase;

/**
 * 评估公司Order
 *
 * @author jil
 *
 */
public class AssessCompanyEvaluateQueryOrder extends QueryPageBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9197977649827339459L;

	private Long id;

	private long applyId;

	private long companyId;

	private String companyName;

	private long evaluatePerson;

	private String evaluatePersonAccount;

	private String evaluatePersonName;

	private String isReview;

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

	public String getIsReview() {
		return isReview;
	}

	public void setIsReview(String isReview) {
		this.isReview = isReview;
	}

}
