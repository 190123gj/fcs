package com.born.fcs.pm.ws.order.counterguarantee;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 解保申请-还款
 *
 * @author lirz
 * 
 * 2016-10-29 下午2:19:39
 *
 */
public class FCounterGuaranteeRepayOrder extends ValidateOrderBase{

    private static final long serialVersionUID = 6246971518986442627L;

	private long id;

	private long formId;

	private Money repayAmount = new Money(0, 0);
	
	private String repayAmountStr;

	private String repayTime;
	
	private long capitalChannelId;

	private String capitalChannelCode;

	private String capitalChannelType;

	private String capitalChannelName;

	private long capitalSubChannelId;

	private String capitalSubChannelCode;

	private String capitalSubChannelType;

	private String capitalSubChannelName;

	private Money actualDepositAmount = new Money(0, 0);

	private Money liquidityLoanAmount = new Money(0, 0);

	private Money fixedAssetsFinancingAmount = new Money(0, 0);

	private Money acceptanceBillAmount = new Money(0, 0);

	private Money creditLetterAmount = new Money(0, 0);

	private int sortOrder;

	private Date rawAddTime;

	private Date rawUpdateTime;
	
	public boolean isNull() {
		return isNull(repayTime) 
				&& isNull(getRepayAmount()) 
				&& isNull(repayAmountStr) 
				&& isNull(capitalChannelName)
				&& isNull(capitalSubChannelName);
	}

    //========== getters and setters ==========

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public long getFormId() {
		return formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}

	public Money getRepayAmount() {
		if (Money.zero().equals(repayAmount)) {
			return liquidityLoanAmount.add(fixedAssetsFinancingAmount).add(acceptanceBillAmount)
				.add(creditLetterAmount);
		} else {
			return repayAmount;
		}
	}
	
	public void setRepayAmount(Money repayAmount) {
		if (repayAmount == null) {
			this.repayAmount = new Money(0, 0);
		} else {
			this.repayAmount = repayAmount;
		}
	}

	public String getRepayAmountStr() {
		return this.repayAmountStr;
	}

	public void setRepayAmountStr(String repayAmountStr) {
		this.repayAmountStr = repayAmountStr;
		if (!isNull(repayAmountStr)) {
			this.repayAmount = Money.amout(repayAmountStr);
		} else {
			this.repayAmount = new Money(0, 0);
		}
	}

	public String getRepayTime() {
		return repayTime;
	}
	
	public void setRepayTime(String repayTime) {
		this.repayTime = repayTime;
	}

	public long getCapitalChannelId() {
		return this.capitalChannelId;
	}

	public void setCapitalChannelId(long capitalChannelId) {
		this.capitalChannelId = capitalChannelId;
	}

	public String getCapitalChannelCode() {
		return this.capitalChannelCode;
	}

	public void setCapitalChannelCode(String capitalChannelCode) {
		this.capitalChannelCode = capitalChannelCode;
	}

	public String getCapitalChannelType() {
		return this.capitalChannelType;
	}

	public void setCapitalChannelType(String capitalChannelType) {
		this.capitalChannelType = capitalChannelType;
	}

	public String getCapitalChannelName() {
		return this.capitalChannelName;
	}

	public void setCapitalChannelName(String capitalChannelName) {
		this.capitalChannelName = capitalChannelName;
	}

	public long getCapitalSubChannelId() {
		return this.capitalSubChannelId;
	}

	public void setCapitalSubChannelId(long capitalSubChannelId) {
		this.capitalSubChannelId = capitalSubChannelId;
	}

	public String getCapitalSubChannelCode() {
		return this.capitalSubChannelCode;
	}

	public void setCapitalSubChannelCode(String capitalSubChannelCode) {
		this.capitalSubChannelCode = capitalSubChannelCode;
	}

	public String getCapitalSubChannelType() {
		return this.capitalSubChannelType;
	}

	public void setCapitalSubChannelType(String capitalSubChannelType) {
		this.capitalSubChannelType = capitalSubChannelType;
	}

	public String getCapitalSubChannelName() {
		return this.capitalSubChannelName;
	}

	public void setCapitalSubChannelName(String capitalSubChannelName) {
		this.capitalSubChannelName = capitalSubChannelName;
	}

	public Money getActualDepositAmount() {
		return this.actualDepositAmount;
	}

	public void setActualDepositAmount(Money actualDepositAmount) {
		this.actualDepositAmount = actualDepositAmount;
	}
	
	public void setActualDepositAmountStr(String actualDepositAmountStr) {
		if (StringUtil.isNotBlank(actualDepositAmountStr)) {
			this.actualDepositAmount = Money.amout(actualDepositAmountStr);
		}
	}

	public Money getLiquidityLoanAmount() {
		return this.liquidityLoanAmount;
	}

	public void setLiquidityLoanAmount(Money liquidityLoanAmount) {
		this.liquidityLoanAmount = liquidityLoanAmount;
	}
	
	public void setLiquidityLoanAmountStr(String liquidityLoanAmountStr) {
		if (StringUtil.isNotBlank(liquidityLoanAmountStr)) {
			this.liquidityLoanAmount = Money.amout(liquidityLoanAmountStr);
		}
	}

	public Money getFixedAssetsFinancingAmount() {
		return this.fixedAssetsFinancingAmount;
	}

	public void setFixedAssetsFinancingAmount(Money fixedAssetsFinancingAmount) {
		this.fixedAssetsFinancingAmount = fixedAssetsFinancingAmount;
	}
	
	public void setFixedAssetsFinancingAmountStr(String fixedAssetsFinancingAmountStr) {
		if (StringUtil.isNotBlank(fixedAssetsFinancingAmountStr)) {
			this.fixedAssetsFinancingAmount = Money.amout(fixedAssetsFinancingAmountStr);
		}
	}

	public Money getAcceptanceBillAmount() {
		return this.acceptanceBillAmount;
	}

	public void setAcceptanceBillAmount(Money acceptanceBillAmount) {
		this.acceptanceBillAmount = acceptanceBillAmount;
	}
	
	public void setAcceptanceBillAmountStr(String acceptanceBillAmountStr) {
		if (StringUtil.isNotBlank(acceptanceBillAmountStr)) {
			this.acceptanceBillAmount = Money.amout(acceptanceBillAmountStr);
		}
	}

	public Money getCreditLetterAmount() {
		return this.creditLetterAmount;
	}

	public void setCreditLetterAmount(Money creditLetterAmount) {
		this.creditLetterAmount = creditLetterAmount;
	}
	
	public void setCreditLetterAmountStr(String creditLetterAmountStr) {
		if (StringUtil.isNotBlank(creditLetterAmountStr)) {
			this.creditLetterAmount = Money.amout(creditLetterAmountStr);
		}
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
