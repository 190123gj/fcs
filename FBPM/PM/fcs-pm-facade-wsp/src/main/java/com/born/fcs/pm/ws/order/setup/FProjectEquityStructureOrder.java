package com.born.fcs.pm.ws.order.setup;

import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 项目立项 - 股权结构Order
 *
 * @author wuzj
 *
 */
public class FProjectEquityStructureOrder extends SetupFormSaveOrder {
	
	private static final long serialVersionUID = 2102299957896809135L;
	
	private Long id;
	
	private String stockholder;
	
	/**
	 * 前台接收
	 */
	private String capitalContributionStr;
	
	private Money capitalContributions = Money.zero();
	
	/** 货币 默认元 */
	private String amountType;
	
	private String contributionWay;
	
	private Double equityRatio;
	
	private Integer sortOrder;
	
	public boolean isNull() {
		return isNull(stockholder) && isNull(capitalContributions) && isNull(contributionWay)
				&& isNull(equityRatio);
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getStockholder() {
		return this.stockholder;
	}
	
	public void setStockholder(String stockholder) {
		this.stockholder = stockholder;
	}
	
	public String getCapitalContributionStr() {
		return this.capitalContributionStr;
	}
	
	public void setCapitalContributionStr(String capitalContributionStr) {
		this.capitalContributionStr = capitalContributionStr;
	}
	
	public Money getCapitalContributions() {
		if (StringUtil.isNotBlank(capitalContributionStr))
			return Money.amout(capitalContributionStr);
		return this.capitalContributions;
	}
	
	public void setCapitalContributions(Money capitalContributions) {
		this.capitalContributions = capitalContributions;
	}
	
	public String getAmountType() {
		if (amountType == null || "".equals(amountType))
			return "元";
		return this.amountType;
	}
	
	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}
	
	public String getContributionWay() {
		return this.contributionWay;
	}
	
	public void setContributionWay(String contributionWay) {
		this.contributionWay = contributionWay;
	}
	
	public Double getEquityRatio() {
		return this.equityRatio;
	}
	
	public void setEquityRatio(Double equityRatio) {
		this.equityRatio = equityRatio;
	}
	
	public Integer getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
}
