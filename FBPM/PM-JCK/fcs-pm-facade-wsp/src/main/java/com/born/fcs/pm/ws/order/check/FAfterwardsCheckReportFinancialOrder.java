package com.born.fcs.pm.ws.order.check;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.math.NumberUtils;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 企业资产/负债情况调查工作底稿
 * 
 * @author lirz
 *
 * 2016-6-6 上午11:42:36
 */
public class FAfterwardsCheckReportFinancialOrder extends ValidateOrderBase{

	private static final long serialVersionUID = 8776795301055460794L;

	private long financialId;
	private long formId;
	private String financialType;
	private String financialItem;
	private String financialName;
	private Money origialAmount = new Money(0, 0);
	private int origialCount;
	private String origialAge;
	private Money badDebtAmount = new Money(0, 0);
	private String remark;
	private String delAble;
	private int sortOrder;
	private String constructionContract;
	private String refundCertificate;
	
	public boolean isNull() {
		return isNull(financialItem)
				&& isNull(financialName)
				&& isNull(origialAmount)
				&& origialCount <= 0
				&& isNull(origialAge)
				&& isNull(constructionContract)
				&& isNull(refundCertificate)
				&& isNull(badDebtAmount)
				&& isNull(remark)
				&& isNull(delAble)
				;
	}

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
	
	public void setOrigialAmountStr(String origialAmountStr) {
		if (!isNull(origialAmountStr)) {
			this.origialAmount = Money.amout(origialAmountStr);
		}
	}

	public int getOrigialCount() {
		return origialCount;
	}
	
	public void setOrigialCount(int origialCount) {
		this.origialCount = origialCount;
	}
	
	public void setOrigialCountStr(String origialCountStr) {
		if (!isNull(origialCountStr)) {
			this.origialCount = NumberUtils.toInt(origialCountStr);
		}
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
	
	public void setBadDebtAmountStr(String badDebtAmountStr) {
		if (!isNull(badDebtAmountStr)) {
			this.badDebtAmount = Money.amout(badDebtAmountStr);
		}
	}

	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDelAble() {
		return delAble;
	}

	public void setDelAble(String delAble) {
		this.delAble = delAble;
	}

	public int getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
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
