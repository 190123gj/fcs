package com.born.fcs.pm.ws.info.check;

import java.util.Date;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 企业资产/负债情况调查工作底稿
 * 
 * @author lirz
 *
 * 2016-6-6 上午11:42:36
 */
public class FAfterwardsCheckReportFinancialInfo extends BaseToStringInfo{

	private static final long serialVersionUID = 7983760464316381484L;
	
	private long financialId;
	private long formId;
	private String financialType;
	private String financialItem;
	private String financialName;
	private Money origialAmount = new Money(0, 0);
	private Money itemBalance = new Money(0, 0);
	private int origialCount;
	private String origialAge;
	private Money badDebtAmount = new Money(0, 0);
	private String remark;
	private BooleanEnum delAble;
	private int sortOrder;
	private String constructionContract;
	private String refundCertificate;
	private Date rawAddTime;
	private Date rawUpdateTime;

	public long getFinancialId() {
		return financialId;
	}
	
	public void setFinancialId(long financialId) {
		this.financialId = financialId;
	}

	public long getFormId() {
		return formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}

	public String getFinancialType() {
		return financialType;
	}
	
	public void setFinancialType(String financialType) {
		this.financialType = financialType;
	}

	public String getFinancialItem() {
		return financialItem;
	}
	
	public void setFinancialItem(String financialItem) {
		this.financialItem = financialItem;
	}

	public String getFinancialName() {
		return financialName;
	}
	
	public void setFinancialName(String financialName) {
		this.financialName = financialName;
	}

	public Money getOrigialAmount() {
		return origialAmount;
	}
	
	public void setOrigialAmount(Money origialAmount) {
		if (origialAmount == null) {
			this.origialAmount = new Money(0, 0);
		} else {
			this.origialAmount = origialAmount;
		}
	}

	public Money getItemBalance() {
		return this.itemBalance;
	}

	public void setItemBalance(Money itemBalance) {
		if (itemBalance == null) {
			this.itemBalance = new Money(0, 0);
		} else {
			this.itemBalance = itemBalance;
		}		
	}

	public int getOrigialCount() {
		return origialCount;
	}
	
	public void setOrigialCount(int origialCount) {
		this.origialCount = origialCount;
	}

	public String getOrigialAge() {
		return origialAge;
	}
	
	public void setOrigialAge(String origialAge) {
		this.origialAge = origialAge;
	}

	public Money getBadDebtAmount() {
		return badDebtAmount;
	}
	
	public void setBadDebtAmount(Money badDebtAmount) {
		if (badDebtAmount == null) {
			this.badDebtAmount = new Money(0, 0);
		} else {
			this.badDebtAmount = badDebtAmount;
		}
	}

	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BooleanEnum getDelAble() {
		return delAble;
	}

	public void setDelAble(BooleanEnum delAble) {
		this.delAble = delAble;
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

	public String getConstructionContract() {
		return constructionContract;
	}

	public void setConstructionContract(String constructionContract) {
		this.constructionContract = constructionContract;
	}

	public String getRefundCertificate() {
		return refundCertificate;
	}

	public void setRefundCertificate(String refundCertificate) {
		this.refundCertificate = refundCertificate;
	}
}
