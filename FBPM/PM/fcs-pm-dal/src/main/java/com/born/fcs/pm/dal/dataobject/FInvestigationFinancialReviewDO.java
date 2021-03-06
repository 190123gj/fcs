/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dal.dataobject;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

// auto generated imports
import java.util.Date;

/**
 * A data object class directly models database table <tt>f_investigation_financial_review</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/f_investigation_financial_review.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */
public class FInvestigationFinancialReviewDO implements Serializable{
	/** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -4282603875229233564L;

    //========== properties ==========

	private long FReviewId;

	private long formId;

	private int subIndex;

	private String isActive;

	private String projectCode;

	private String projectName;

	private long customerId;

	private String customerName;

	private String importExcel;

	private String amountUnit1;

	private String amountUnit2;

	private String amountUnit3;

	private String amountUnit;

	private String isAudited;

	private String auditUnit;

	private String auditSuggest;

	private String auditSuggestExplain;

	private String debtPayingAbility;

	private String operatingAbility;

	private String profitAbility;

	private String cashFlowExplain;

	private String assetQualityReview;

	private Date rawAddTime;

	private Date rawUpdateTime;

    //========== getters and setters ==========

	public long getFReviewId() {
		return FReviewId;
	}
	
	public void setFReviewId(long FReviewId) {
		this.FReviewId = FReviewId;
	}

	public long getFormId() {
		return formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}

	public int getSubIndex() {
		return subIndex;
	}
	
	public void setSubIndex(int subIndex) {
		this.subIndex = subIndex;
	}

	public String getIsActive() {
		return isActive;
	}
	
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getImportExcel() {
		return importExcel;
	}
	
	public void setImportExcel(String importExcel) {
		this.importExcel = importExcel;
	}

	public String getAmountUnit1() {
		return amountUnit1;
	}
	
	public void setAmountUnit1(String amountUnit1) {
		this.amountUnit1 = amountUnit1;
	}

	public String getAmountUnit2() {
		return amountUnit2;
	}
	
	public void setAmountUnit2(String amountUnit2) {
		this.amountUnit2 = amountUnit2;
	}

	public String getAmountUnit3() {
		return amountUnit3;
	}
	
	public void setAmountUnit3(String amountUnit3) {
		this.amountUnit3 = amountUnit3;
	}

	public String getAmountUnit() {
		return amountUnit;
	}
	
	public void setAmountUnit(String amountUnit) {
		this.amountUnit = amountUnit;
	}

	public String getIsAudited() {
		return isAudited;
	}
	
	public void setIsAudited(String isAudited) {
		this.isAudited = isAudited;
	}

	public String getAuditUnit() {
		return auditUnit;
	}
	
	public void setAuditUnit(String auditUnit) {
		this.auditUnit = auditUnit;
	}

	public String getAuditSuggest() {
		return auditSuggest;
	}
	
	public void setAuditSuggest(String auditSuggest) {
		this.auditSuggest = auditSuggest;
	}

	public String getAuditSuggestExplain() {
		return auditSuggestExplain;
	}
	
	public void setAuditSuggestExplain(String auditSuggestExplain) {
		this.auditSuggestExplain = auditSuggestExplain;
	}

	public String getDebtPayingAbility() {
		return debtPayingAbility;
	}
	
	public void setDebtPayingAbility(String debtPayingAbility) {
		this.debtPayingAbility = debtPayingAbility;
	}

	public String getOperatingAbility() {
		return operatingAbility;
	}
	
	public void setOperatingAbility(String operatingAbility) {
		this.operatingAbility = operatingAbility;
	}

	public String getProfitAbility() {
		return profitAbility;
	}
	
	public void setProfitAbility(String profitAbility) {
		this.profitAbility = profitAbility;
	}

	public String getCashFlowExplain() {
		return cashFlowExplain;
	}
	
	public void setCashFlowExplain(String cashFlowExplain) {
		this.cashFlowExplain = cashFlowExplain;
	}

	public String getAssetQualityReview() {
		return assetQualityReview;
	}
	
	public void setAssetQualityReview(String assetQualityReview) {
		this.assetQualityReview = assetQualityReview;
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
