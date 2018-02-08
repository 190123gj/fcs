package com.born.fcs.pm.ws.info.finvestigation;

import java.util.Date;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 保证人保证能力总体评价（非必填）
 * 
 * @author lirz
 * 
 * 2016-3-10 下午6:25:55
 */
public class FInvestigationCsRationalityReviewGuarantorAbilityInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 5223640972365711434L;
	
	private long id;
	private long csrReviewId;
	private String guarantor; //保证人姓名
	private String abilityLevel; //担保能力评级
	private Money totalCapital; //上年净资产总额
	private Money intangibleAssets; //除土地使用权以外的无形资产
	private Money contingentLiability; //或有负债
	private Money guaranteeAmount; //对外可提供担保额度
	private int sortOrder;
	private Date rawAddTime;
	private Date rawUpdateTime;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getCsrReviewId() {
		return csrReviewId;
	}
	
	public void setCsrReviewId(long csrReviewId) {
		this.csrReviewId = csrReviewId;
	}
	
	public String getGuarantor() {
		return guarantor;
	}
	
	public void setGuarantor(String guarantor) {
		this.guarantor = guarantor;
	}
	
	public String getAbilityLevel() {
		return abilityLevel;
	}
	
	public void setAbilityLevel(String abilityLevel) {
		this.abilityLevel = abilityLevel;
	}
	
	public Money getTotalCapital() {
		return totalCapital;
	}
	
	public void setTotalCapital(Money totalCapital) {
		this.totalCapital = totalCapital;
	}
	
	public Money getIntangibleAssets() {
		return intangibleAssets;
	}
	
	public void setIntangibleAssets(Money intangibleAssets) {
		this.intangibleAssets = intangibleAssets;
	}
	
	public Money getContingentLiability() {
		return contingentLiability;
	}
	
	public void setContingentLiability(Money contingentLiability) {
		this.contingentLiability = contingentLiability;
	}
	
	public Money getGuaranteeAmount() {
		return guaranteeAmount;
	}
	
	public void setGuaranteeAmount(Money guaranteeAmount) {
		this.guaranteeAmount = guaranteeAmount;
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
