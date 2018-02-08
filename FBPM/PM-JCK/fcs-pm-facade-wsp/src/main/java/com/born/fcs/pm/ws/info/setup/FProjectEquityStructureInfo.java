package com.born.fcs.pm.ws.info.setup;

import java.util.Date;

import com.born.fcs.pm.ws.info.common.SimpleFormProjectInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 股权结构
 *
 *
 * @author wuzj
 *
 */
public class FProjectEquityStructureInfo extends SimpleFormProjectInfo {
	
	private static final long serialVersionUID = -4533037364897177655L;
	
	private long id;
	
	private String stockholder;
	
	private Money capitalContributions = new Money(0, 0);
	
	private String contributionWay;
	
	private double equityRatio;
	
	private int sortOrder;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStockholder() {
		return this.stockholder;
	}

	public void setStockholder(String stockholder) {
		this.stockholder = stockholder;
	}

	public Money getCapitalContributions() {
		return this.capitalContributions;
	}

	public void setCapitalContributions(Money capitalContributions) {
		this.capitalContributions = capitalContributions;
	}

	public String getContributionWay() {
		return this.contributionWay;
	}

	public void setContributionWay(String contributionWay) {
		this.contributionWay = contributionWay;
	}

	public double getEquityRatio() {
		return this.equityRatio;
	}

	public void setEquityRatio(double equityRatio) {
		this.equityRatio = equityRatio;
	}

	public int getSortOrder() {
		return this.sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Date getRawAddTime() {
		return this.rawAddTime;
	}

	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}

	public Date getRawUpdateTime() {
		return this.rawUpdateTime;
	}

	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
}
