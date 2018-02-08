package com.born.fcs.pm.ws.order.finvestigation;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 保证人保证能力总体评价（非必填）
 * 
 * @author lirz
 * 
 * 2016-3-10 下午6:25:55
 */
public class FInvestigationCsRationalityReviewGuarantorAbilityOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 1273352701558855558L;
	private long id;
	private long csrReviewId;
	private String guarantor; //保证人姓名
	private String abilityLevel; //担保能力评级
	private String totalCapitalStr; //上年净资产总额
	private String intangibleAssetsStr; //除土地使用权以外的无形资产
	private String contingentLiabilityStr; //或有负债
	private String guaranteeAmountStr; //对外可提供担保额度
	private int sortOrder;
	
	public boolean isNull() {
		return isNull(guarantor) && isNull(abilityLevel) && isNull(totalCapitalStr)
				&& isNull(intangibleAssetsStr) && isNull(contingentLiabilityStr)
				&& isNull(guaranteeAmountStr);
	}
	
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
		if (isNull(this.totalCapitalStr)) {
			return new Money(0L);
		}
		return Money.amout(this.totalCapitalStr);
	}
	
	public String getTotalCapitalStr() {
		return totalCapitalStr;
	}
	
	public void setTotalCapitalStr(String totalCapitalStr) {
		this.totalCapitalStr = totalCapitalStr;
	}
	
	public Money getIntangibleAssets() {
		if (isNull(this.intangibleAssetsStr)) {
			return new Money(0L);
		}
		return Money.amout(this.intangibleAssetsStr);
	}
	
	public String getIntangibleAssetsStr() {
		return intangibleAssetsStr;
	}
	
	public void setIntangibleAssetsStr(String intangibleAssetsStr) {
		this.intangibleAssetsStr = intangibleAssetsStr;
	}
	
	public Money getContingentLiability() {
		if (isNull(this.contingentLiabilityStr)) {
			return new Money(0L);
		}
		return Money.amout(this.contingentLiabilityStr);
	}
	
	public String getContingentLiabilityStr() {
		return contingentLiabilityStr;
	}
	
	public void setContingentLiabilityStr(String contingentLiabilityStr) {
		this.contingentLiabilityStr = contingentLiabilityStr;
	}
	
	public Money getGuaranteeAmount() {
		if (isNull(this.guaranteeAmountStr)) {
			return new Money(0L);
		}
		return Money.amout(this.guaranteeAmountStr);
	}
	
	public String getGuaranteeAmountStr() {
		return guaranteeAmountStr;
	}
	
	public void setGuaranteeAmountStr(String guaranteeAmountStr) {
		this.guaranteeAmountStr = guaranteeAmountStr;
	}
	
	public int getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
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
